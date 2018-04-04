package com.crscd.framework.translation.serviceinterface.impl;

import com.crscd.framework.translation.annotation.TranslateAttribute;
import com.crscd.framework.translation.dao.daointerface.EntryDaoInterface;
import com.crscd.framework.translation.serviceinterface.TranslatorInterface;
import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.reflect.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 *
 * Date: 2017/9/5
 * Time: 14:40
 */
public class TranslatorInterfaceImpl implements TranslatorInterface {
    private static final Logger logger = LoggerFactory.getLogger(TranslatorInterfaceImpl.class);
    private EntryDaoInterface entryDao;

    public void setEntryDao(EntryDaoInterface entryDao) {
        this.entryDao = entryDao;
    }

    @Override
    public String makeTranslation(String word, String lan) {
        Map<String, String> entryMap = entryDao.getEntryMap(lan);
        if (entryMap.containsKey(word.toLowerCase())) {
            return entryMap.get(word.toLowerCase());
        }
        return word;
    }

    @Override
    public Object makeTranslation(List<Object> objectList, String lan, String clazzName) {
        List<Object> tranList = new ArrayList<>();
        for (Object obj : objectList) {
            tranList.add(makeTranslation(obj, lan, clazzName));
        }
        return tranList;
    }

    /**
     * 利用反射对包含@translateAttribute注释的属性进行翻译
     */
    @Override
    public Object makeTranslation(Object obj, String lan, String clazzName) {
        // 判断翻译对象是否为null
        if (null == obj) {
            return null;
        }
        // 判断翻译对象是否为string
        if (obj instanceof String) {
            String result = (String) obj;
            return makeTranslation(result, lan);
        }
        // 如果对象是自定义的类型
        // 获取全部继承父类的属性
        Class<?> clazz = obj.getClass();
        List<Field> fieldList = new ArrayList<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            ListUtil.copyListToArray(fieldList, fields);
        }
        Field[] allFields = ObjectUtil.convertFieldListToFieldArray(fieldList);
        // 为了防止翻译过程由于引用同一个对象对原有对象属性进行了修改，需要构造一个新的对象作为返回值
        Object tranObj;
        try {
            tranObj = Class.forName(clazzName).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error("translation error:", e);
            // 如果创建对象失败，则直接将原有对象返回
            return obj;
        }
        try {
            // 对全部属性进行翻译
            if (allFields != null) {
                for (Field field : allFields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object fieldValue = field.get(obj);
                    // 对@translateAttribute注解标记的属性进行翻译
                    if (field.isAnnotationPresent(TranslateAttribute.class)) {
                        if (fieldValue != null) {
                            if (makeTranslateForField(lan, tranObj, fieldName, fieldValue)) {
                                continue;
                            }
                        }
                    }
                    // 不属于翻译类型的直接跳过，将原属性值填充到新对象中
                    ObjectUtil.setField(tranObj, fieldName, fieldValue);
                    field.set(tranObj, fieldValue);
                }
            }
            return tranObj;
        } catch (Exception e) {
            logger.error("translation error:", e);
            // 如果翻译过程出现失败，则直接将原有对象返回
            return obj;
        }
    }

    /**
     * 对对象的域进行翻译
     * 目前支持如下类型的属性翻译：
     * - String
     * - list<String>
     * - 包含String的Map<>
     *
     * @param lan
     * @param tranObj
     * @param fieldName
     * @param fieldValue
     * @return
     */
    private boolean makeTranslateForField(String lan, Object tranObj, String fieldName, Object fieldValue) {
        // 支持对string类型的字段进行翻译
        if (fieldValue instanceof String) {
            Object translatedValue = makeTranslation(fieldValue.toString(), lan);
            ObjectUtil.setField(tranObj, fieldName, translatedValue);
            return true;
        }
        // 支持对List<string>类型的字段进行翻译
        else if (fieldValue instanceof List) {
            List<Object> originList = CastUtil.cast(fieldValue);
            if (ListUtil.isNotEmpty(originList) && originList.get(0) instanceof String) {
                List<String> strList = new ArrayList<>();
                for (Object str : originList) {
                    String translatedValue = makeTranslation(str.toString(), lan);
                    strList.add(translatedValue);
                }
                ObjectUtil.setField(tranObj, fieldName, strList);
                return true;
            }
        }
        // 支持对含有string类型的map进行翻译
        else if (fieldValue instanceof Map) {
            Map<Object, Object> originMap = CastUtil.cast(fieldValue);
            if (MapUtil.isNotEmpty(originMap)) {
                Map<Object, Object> newMap = new HashMap<>();
                for (Map.Entry<Object, Object> entry : originMap.entrySet()) {
                    Object key, value;
                    if (entry.getKey() instanceof String) {
                        key = makeTranslation(entry.getKey().toString(), lan);
                    } else {
                        key = entry.getKey();
                    }
                    if (entry.getValue() instanceof String) {
                        value = makeTranslation(entry.getValue().toString(), lan);
                    } else {
                        value = entry.getValue();
                    }
                    newMap.put(key, value);
                }
                ObjectUtil.setField(tranObj, fieldName, newMap);
                return true;
            }
        }
        // 添加支持其他类型属性判断及翻译
        return false;
    }
}
