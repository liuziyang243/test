package com.crscd.framework.util.reflect;

import com.alibaba.fastjson.JSON;
import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.framework.util.collection.ListUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象操作工具类
 *
 * @author lzy
 * @since 1.0
 */
public class ObjectUtil {

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * 设置成员变量
     */
    public static void setField(Object obj, String fieldName, Object fieldValue) {
        try {
            if (PropertyUtils.isWriteable(obj, fieldName)) {
                PropertyUtils.setProperty(obj, fieldName, fieldValue);
            }
        } catch (Exception e) {
            logger.error("设置成员变量出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取成员变量
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object propertyValue = null;
        try {
            if (PropertyUtils.isReadable(obj, fieldName)) {
                propertyValue = PropertyUtils.getProperty(obj, fieldName);
            }
        } catch (Exception e) {
            logger.error("获取成员变量出错！", e);
            throw new RuntimeException(e);
        }
        return propertyValue;
    }

    /**
     * 获取成员变量
     *
     * @author lzy 专门为了兼容不符合javabean要求的书写名称
     */
    public static Object getFieldValueModified(Object obj, String fieldName) {
        Object propertyValue = null;
        try {
            propertyValue = com.crscd.framework.util.reflect.PropertyUtils.getFieldValueByNameModified(fieldName, obj);
        } catch (Exception e) {
            logger.error("获取成员变量出错！", e);
            throw new RuntimeException(e);
        }
        return propertyValue;
    }

    /**
     * 复制所有成员变量
     */
    public static void copyFields(Object source, Object target) {
        try {
            for (Field field : source.getClass().getDeclaredFields()) {
                // 若不为 static 成员变量，则进行复制操作
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true); // 可操作私有成员变量
                    field.set(target, field.get(source));
                }
            }
        } catch (Exception e) {
            logger.error("复制成员变量出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过反射创建实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className) {
        T instance;
        try {
            Class<?> commandClass = ClassUtil.loadClass(className);
            instance = (T) commandClass.newInstance();
        } catch (Exception e) {
            logger.error("创建实例出错！", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 获取对象的字段映射（字段名 => 字段值），忽略 static 字段以及autoIncrement注解
     *
     * @author lzy 修改时间：2016.01.18 修改内容：将内部调用函数改为getFieldMap2,并将忽略静态变量设置为false
     */
    public static Map<String, Object> getFieldMap(Object obj) {
        return getFieldMap3(obj, false);
    }

    /**
     * 获取对象的字段映射（字段名 => 字段值）
     */
    public static Map<String, Object> getFieldMap(Object obj, boolean isStaticIgnored) {
        Map<String, Object> fieldMap = new LinkedHashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isStaticIgnored && Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String fieldName = field.getName();
            Object fieldValue = ObjectUtil.getFieldValue(obj, fieldName);
            fieldMap.put(fieldName, fieldValue);
        }
        return fieldMap;
    }

    /**
     * 获取对象的字段映射（字段名 => 字段值）
     * 获取该类的全部属性，不包括从父类继承来的属性
     *
     * @author lzy 由于javabean规定：变量的前两个字母要么全部大写，要么全部小写，因此无法兼容目前设计的bean（为了适应厂家设计）
     * 因此在此无论大小写都兼容
     * <p>
     * 增加了对autoIncrement注解字段的过滤
     */
    public static Map<String, Object> getFieldMap2(Object obj, boolean isStaticIgnored) {
        Field[] fields = obj.getClass().getDeclaredFields();
        return getFieldMapCommon(obj, fields, isStaticIgnored);
    }

    /**
     * 获取对象的字段映射（字段名 => 字段值）
     * 只获取当前类的全部属性，包含从父类继承得到的属性
     *
     * @author lzy 由于javabean规定：变量的前两个字母要么全部大写，要么全部小写，因此无法兼容目前设计的bean（为了适应厂家设计）
     * 因此在此无论大小写都兼容
     * <p>
     * 增加了对autoIncrement注解字段的过滤
     */
    public static Map<String, Object> getFieldMap3(Object obj, boolean isStaticIgnored) {
        Class<?> clazz = obj.getClass();
        List<Field> fieldList = new ArrayList<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            ListUtil.copyArrayIntoList(fieldList, fields);
        }
        Field[] allfields = convertFieldListToFieldArray(fieldList);
        return getFieldMapCommon(obj, allfields, isStaticIgnored);
    }

    private static Map<String, Object> getFieldMapCommon(Object obj, Field[] fields, boolean isStaticIgnored) {
        Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
        for (Field field : fields) {
            if (isStaticIgnored && Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            // 增加了对autoIncrement注解字段的过滤
            if (field.isAnnotationPresent(OrmIgnore.class)) {
                continue;
            }
            String fieldName = field.getName();
            Object fieldValue = ObjectUtil.getFieldValueModified(obj, fieldName);
            if (fieldValue != null) {
                // 加入对List对象的特殊处理，将List对象转换为Json字符串
                if (fieldValue instanceof List) {
                    fieldValue = JSON.toJSONString(fieldValue);
                }
                // 加入对Enum对象的特殊处理，将其转换为string字符串
                if (fieldValue instanceof Enum) {
                    fieldValue = ((Enum) fieldValue).name();
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    public static Field[] convertFieldListToFieldArray(List<Field> list) {
        if (list.isEmpty()) {
            return null;
        } else {
            final int size = list.size();
            return list.toArray(new Field[size]);
        }
    }

    /**
     * 校验对象属性均不能为空!
     *
     * @param obj
     * @return
     * @author DengYang
     */
    public static boolean validField(Object obj) throws IllegalAccessException {
        if (obj == null) {
            throw new IllegalAccessException("参数不能为空!");
        }
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(obj) == null) {
                logger.error("==> " + field.getName() + "不能为空!");
                throw new IllegalAccessException(field.getName() + "不能为空!");
            }
        }
        return true;
    }

    /**
     * 指定字段名字,校验对象属性不能为空!
     *
     * @param obj
     * @return
     * @author DengYang
     */
    public static boolean validFieldByFileldName(Object obj, String... fieldName) throws IllegalAccessException {
        if (obj == null) {
            throw new IllegalAccessException("参数不能为空!");
        }
        if (fieldName == null || fieldName.length < 1) {
            throw new IllegalAccessException("fieldName参数不能为空!");
        }
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            for (String name : fieldName) {
                if (field.getName().equals(name)) {
                    if (field.get(obj) == null) {
                        logger.error("==> " + field.getName() + "不能为空!");
                        throw new IllegalAccessException(field.getName() + "不能为空!");
                    }
                }
            }
        }
        return true;
    }

    /**
     * 过滤掉字段后,校验剩余对象属性不能为空!
     *
     * @param obj
     * @return
     * @author DengYang
     */
    public static boolean validFieldWithFilter(Object obj, String... filterField) throws IllegalAccessException {
        if (obj == null) {
            return false;
        }
        if (filterField == null || filterField.length < 1) {
            throw new IllegalAccessException("fieldName参数不能为空!");
        }
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            for (String filter : filterField) {
                if (field.getName().equals(filter)) {
                    continue;
                }
                if (field.get(obj) == null) {
                    logger.error("==> " + field.getName() + "不能为空!");
                    throw new IllegalAccessException("fieldName参数不能为空!");
                }
            }
        }
        return true;
    }
}
