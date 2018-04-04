package com.crscd.framework.dao.extdbutils;

import com.alibaba.fastjson.JSON;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import org.apache.commons.dbutils.BeanProcessor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

/**
 * Improvement:
 * This extension supports convert json string to List<string>
 * in target bean property. Mean while, it extracts the match rule between
 * column name and property name to be a interface.
 * <p>
 *
 * @author lzy
 * Date: 2017/7/11
 * Time: 16:24
 */
public class ExtBeanProcessor extends BeanProcessor {

    /**
     * Set a bean's primitive properties to these defaults when SQL NULL
     * is returned.  These are the same as the defaults that ResultSet get*
     * methods return in the event of a NULL column.
     */
    private static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = new HashMap<>();

    static {
        PRIMITIVE_DEFAULTS.put(Integer.TYPE, 0);
        PRIMITIVE_DEFAULTS.put(Short.TYPE, (short) 0);
        PRIMITIVE_DEFAULTS.put(Byte.TYPE, (byte) 0);
        PRIMITIVE_DEFAULTS.put(Float.TYPE, 0f);
        PRIMITIVE_DEFAULTS.put(Double.TYPE, 0d);
        PRIMITIVE_DEFAULTS.put(Long.TYPE, 0L);
        PRIMITIVE_DEFAULTS.put(Boolean.TYPE, Boolean.FALSE);
        PRIMITIVE_DEFAULTS.put(Character.TYPE, (char) 0);
        PRIMITIVE_DEFAULTS.put(List.class, new ArrayList<>());
    }

    private Matcher matcher;

    public ExtBeanProcessor() {
        // default Matcher is same as super class
        this(new MappingMatcher());
    }

    public ExtBeanProcessor(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<? extends T> type) throws SQLException {

        PropertyDescriptor[] props = this.propertyDescriptors(type);

        ResultSetMetaData rsmd = rs.getMetaData();
        int[] columnToProperty = this.mapColumnsToProperties(rsmd, props);

        return this.createBean(rs, type, props, columnToProperty);
    }

    @Override
    public <T> List<T> toBeanList(ResultSet rs, Class<? extends T> type) throws SQLException {
        List<T> results = new ArrayList<>();

        if (!rs.next()) {
            return results;
        }

        PropertyDescriptor[] props = this.propertyDescriptors(type);
        ResultSetMetaData rsmd = rs.getMetaData();
        int[] columnToProperty = this.mapColumnsToProperties(rsmd, props);

        do {
            results.add(this.createBean(rs, type, props, columnToProperty));
        } while (rs.next());

        return results;
    }

    /**
     * Returns a PropertyDescriptor[] for the given Class.
     *
     * @param c The Class to retrieve PropertyDescriptors for.
     * @return A PropertyDescriptor[] describing the Class.
     * @throws SQLException if introspection failed.
     */
    private PropertyDescriptor[] propertyDescriptors(Class<?> c)
            throws SQLException {
        // Introspector caches BeanInfo classes for better performance
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(c);

        } catch (IntrospectionException e) {
            throw new SQLException(
                    "Bean introspection failed: " + e.getMessage());
        }

        return beanInfo.getPropertyDescriptors();
    }

    /**
     * Creates a new object and initializes its fields from the ResultSet.
     *
     * @param <T>              The type of bean to create
     * @param rs               The result set.
     * @param type             The bean type (the return type of the object).
     * @param props            The property descriptors.
     * @param columnToProperty The column indices in the result set.
     * @return An initialized object.
     * @throws SQLException if a database error occurs.
     */
    private <T> T createBean(ResultSet rs, Class<T> type,
                             PropertyDescriptor[] props, int[] columnToProperty)
            throws SQLException {

        T bean = this.newInstance(type);

        for (int i = 1; i < columnToProperty.length; i++) {
            if (columnToProperty[i] == PROPERTY_NOT_FOUND) {
                continue;
            }
            PropertyDescriptor prop = props[columnToProperty[i]];
            Class<?> propType = prop.getPropertyType();

            Object value = null;
            if (propType != null) {
                value = this.processColumn(rs, i, propType);
                if (value == null && propType.isPrimitive()) {
                    value = PRIMITIVE_DEFAULTS.get(propType);
                }
            }
            this.callSetter(bean, prop, value);
        }

        return bean;
    }

    /**
     * extend BeanProcessor ability to use matcher to handle the rule of mapping
     * columns to properties
     */
    @Override
    protected int[] mapColumnsToProperties(ResultSetMetaData rsmd,
                                           PropertyDescriptor[] props) throws SQLException {
        if (matcher == null) {
            throw new IllegalStateException("Matcher must be set!");
        }
        int cols = rsmd.getColumnCount();
        int columnToProperty[] = new int[cols + 1];
        Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);
        for (int col = 1; col <= cols; col++) {
            /* getColumnLabel() returns the label after "as" in SQL
               Example:
                select id as user_no from users
                - getColumnLabel would return 'user_no'
                - getColumnName would return 'id'
            */
            String columnName = rsmd.getColumnLabel(col);
            if (null == columnName || 0 == columnName.length()) {
                columnName = rsmd.getColumnName(col);
            }

            // for the columnToPropertyOverrides is private in the super class
            // we use MappingMatcher to make up the loss

            for (int i = 0; i < props.length; i++) {
                // this part is different from original BeanProcessor
                if (matcher.match(columnName, props[i].getName())) {
                    columnToProperty[col] = i;
                    break;
                }
            }
        }
        return columnToProperty;
    }

    /*
     * We make some revision for this class to improve the handle ability.
     * The new function can support following extending cases:
     * - handle converting to List<String> before invoking setter.
     */

    /**
     * Calls the setter method on the target object for the given property.
     * If no setter method exists for the property, this method does nothing.
     *
     * @param target The object to set the property on.
     * @param prop   The property to set.
     * @param value  The value to pass into the setter.
     * @throws SQLException if an error occurs setting the property.
     */
    private void callSetter(Object target, PropertyDescriptor prop, Object value)
            throws SQLException {

        Method setter = prop.getWriteMethod();

        if (setter == null) {
            return;
        }

        Class<?>[] params = setter.getParameterTypes();
        try {
            // convert types for some popular ones
            if (value instanceof Date) {
                final String targetType = params[0].getName();
                if ("java.sql.Date".equals(targetType)) {
                    value = new java.sql.Date(((Date) value).getTime());
                } else if ("java.sql.Time".equals(targetType)) {
                    value = new Time(((Date) value).getTime());
                } else if ("java.sql.Timestamp".equals(targetType)) {
                    Timestamp tsValue = (Timestamp) value;
                    int nanos = tsValue.getNanos();
                    value = new Timestamp(tsValue.getTime());
                    ((Timestamp) value).setNanos(nanos);
                }
                // make extension to support LocalDateTime in JDK-8
                else if ("java.time.LocalDateTime".equals(targetType)) {
                    Instant instant = ((Date) value).toInstant();
                    ZoneId zone = ZoneId.systemDefault();
                    value = LocalDateTime.ofInstant(instant, zone);
                } else if ("java.time.LocalDate".equals(targetType)) {
                    String date = value.toString();
                    value = DateTimeFormatterUtil.convertStringToDate(date);
                } else if ("java.time.LocalTime".equals(targetType)) {
                    String time = value.toString();
                    value = DateTimeFormatterUtil.convertStringToTime(time);
                }
            } else if (value instanceof String && params[0].isEnum()) {
                value = Enum.valueOf(params[0].asSubclass(Enum.class), (String) value);
            }
            // make extension to support convert json string to ArrayList<String>
            else if (value instanceof String && params[0].isAssignableFrom(List.class)) {
                String jsonString = (String) value;
                List<String> sList = JSON.parseArray(jsonString, String.class);
                value = sList == null ? new ArrayList<String>() : sList;
            }

            // Don't call setter if the value object isn't the right type
            if (this.isCompatibleType(value, params[0])) {
                setter.invoke(target, value);
            } else {
                throw new SQLException(
                        "Cannot set " + prop.getName() + ": incompatible types, cannot convert "
                                + value.getClass().getName() + " to " + params[0].getName());
                // value cannot be null here because isCompatibleType allows null
            }

        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            throw new SQLException(
                    "Cannot set " + prop.getName() + ": " + e.getMessage());

        }
    }

    /**
     * ResultSet.getObject() returns an Integer object for an INT column.  The
     * setter method for the property might take an Integer or a primitive int.
     * This method returns true if the value can be successfully passed into
     * the setter method.  Remember, Method.invoke() handles the unwrapping
     * of Integer into an int.
     *
     * @param value The value to be passed into the setter method.
     * @param type  The setter's parameter type (non-null)
     * @return boolean True if the value is compatible (null => true)
     */
    private boolean isCompatibleType(Object value, Class<?> type) {
        // Do object check first, then primitives
        if (value == null || type.isInstance(value)) {
            return true;

        } else if (type.equals(Integer.TYPE) && value instanceof Integer) {
            return true;

        } else if (type.equals(Long.TYPE) && value instanceof Long) {
            return true;

        } else if (type.equals(Double.TYPE) && value instanceof Double) {
            return true;

        } else if (type.equals(Float.TYPE) && value instanceof Float) {
            return true;

        } else if (type.equals(Short.TYPE) && value instanceof Short) {
            return true;

        } else if (type.equals(Byte.TYPE) && value instanceof Byte) {
            return true;

        } else if (type.equals(Character.TYPE) && value instanceof Character) {
            return true;

        } else if (type.equals(Boolean.TYPE) && value instanceof Boolean) {
            return true;

        }
        // make extension to support ArrayList
        else if (type.isAssignableFrom(List.class) && value instanceof List) {
            return true;
        }
        //make extension to support Clob
        else {
            return type.isAssignableFrom(Clob.class) && value instanceof Clob;
        }
        //make extension to support Enum
    }

    @Override
    protected Object processColumn(ResultSet rs, int index, Class<?> propType)
            throws SQLException {

        boolean flag1 = !propType.isPrimitive();
        boolean flag2 = rs.getObject(index) == null;
        if (flag1 && flag2) {
            return null;
        }

        if (!propType.isPrimitive() && rs.getObject(index) == null) {
            return null;
        }

        // add support for json string
        if (propType.equals(String.class) || propType.equals(List.class)) {
            return rs.getString(index);
        } else {
            return super.processColumn(rs, index, propType);
        }
    }

}
