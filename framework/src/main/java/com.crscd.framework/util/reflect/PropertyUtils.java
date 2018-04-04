package com.crscd.framework.util.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    /**
     * 判断对象的属性是否具备setter方法
     * 主要是为了处理不符合javabean属性命名规则的情况
     */
    public static boolean isWriteable(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return false;
        }
        BeanInfo bi0 = null;
        try {
            bi0 = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] pd0 = bi0.getPropertyDescriptors();
            for (PropertyDescriptor aPd0 : pd0) {
                if (aPd0.getName().equalsIgnoreCase(fieldName)) {
                    return true;
                }
            }
        } catch (IntrospectionException e) {
            logger.error("PropertyUtils isWriteable error", e);
        }
        return false;
    }

    /**
     * 完成两个对象之间的属性拷贝
     */
    public static <E, T> void copyProperty(E dst, T src) {
        BeanUtils.copyProperties(dst, src);
    }

    /**
     * 根据属性名获取属性值(特殊处理，不考虑javabean对属性名称的规定)
     */
    public static Object getFieldValueByNameModified(String fieldName, Object o) {
        Method method = null;
        String getter = "get" + fieldName;

        try {
            method = o.getClass().getMethod(getter);
        } catch (SecurityException e) {
            logger.error("getFieldValueByNameModified SecurityException ", e);
            //e.printStackTrace();
        } catch (NoSuchMethodException e) {
            //logger.error("getFieldValueByNameModified NoSuchMethodException ", e);
            //e.printStackTrace();
            //处理如果首字母大写的情况
            return getFieldValueByName(fieldName, o);
        }

        //invoke getMethod
        try {
            return method.invoke(o);
        } catch (Exception e) {
            logger.error("method Exception", e);
            return null;
        }
    }

    /**
     * 根据属性名获取属性值
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        Object value = null;
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            value = method.invoke(o);
        } catch (NoSuchMethodException e) {
//            logger.error("Method get" + fieldName + " is null", e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    /**
     * 获取属性名数组
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    @SuppressWarnings({"rawtypes", "unused", "unchecked"})
    public static List getFiledsInfo(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        //String[] fieldNames = new String[fields.length];
        List list = new ArrayList();
        Map infoMap = null;
        for (Field field : fields) {
            infoMap = new HashMap();
            infoMap.put("type", field.getType().toString());
            infoMap.put("name", field.getName());
            infoMap.put("value", getFieldValueByName(field.getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     */
    public static Object[] getFiledValues(Object o) {
        String[] fieldNames = getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }

}
