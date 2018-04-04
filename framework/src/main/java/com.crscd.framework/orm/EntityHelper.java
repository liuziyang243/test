package com.crscd.framework.orm;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.core.ClassHelper;
import com.crscd.framework.orm.annotation.Column;
import com.crscd.framework.orm.annotation.EqualBean;
import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.framework.orm.annotation.Table;
import com.crscd.framework.util.collection.ArrayUtil;
import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.text.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 初始化 Entity 结构
 *
 * @author lzy
 * @since 1.0
 */
public class EntityHelper {

    private static final Logger logger = LoggerFactory.getLogger(EntityHelper.class);
    private static Map<Class<?>, Map<String, String>> beanClassFieldMapMap = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Map<String, String> fieldMap = initEntityFieldMapMap(beanClass);
            beanClassFieldMapMap.put(beanClass, fieldMap);
        }
    }

    public static String getTableName(Class<?> entityClass) {
        String className = entityClass.getSimpleName();
        if (className.contains("Bean") || entityClass.isAnnotationPresent(EqualBean.class)) {
            if (entityClass.isAnnotationPresent(Table.class)) {
                return entityClass.getAnnotation(Table.class).value();
            } else {
                return StringUtil.deletePart(className, FrameworkConstant.BEAN_MARKER);
            }
        } else {
            logger.debug("不允许对非Bean类" + className + "进行数据库ORM操作！");
            return null;
        }
    }

    public static String getColumnName(Class<?> entityClass, String fieldName) {
        String columnName = getFieldMap(entityClass).get(fieldName);
        return StringUtil.isNotEmpty(columnName) ? columnName : fieldName;
    }

    public static Map<String, String> getColumnMap(Class<?> entityClass) {
        return MapUtil.invert(getFieldMap(entityClass));
    }

    private static Map<String, String> getFieldMap(Class<?> entityClass) {
        Map<String, String> fieldMap = beanClassFieldMapMap.get(entityClass);
        if (null == fieldMap) {
            fieldMap = initEntityFieldMapMap(entityClass);
            beanClassFieldMapMap.put(entityClass, fieldMap);
        }
        return fieldMap;

    }

    private static Map<String, String> initEntityFieldMapMap(Class<?> entityClass) {
        // 创建一个 fieldMap（用于存放列名与字段名的映射关系）
        Map<String, String> fieldMap = new HashMap<String, String>();
        // 获取并遍历该实体类中所有的字段（不包括父类中的方法）
        Field[] fields = entityClass.getDeclaredFields();
        if (ArrayUtil.isNotEmpty(fields)) {
            for (Field field : fields) {
                String fieldName = field.getName();
                String columnName = fieldName;
                // 判断该字段上是否存在忽略映射的注解
                if (field.isAnnotationPresent(OrmIgnore.class)) {
                    continue;
                }
                if (field.isAnnotationPresent(Column.class)) {
                    columnName = field.getAnnotation(Column.class).value();
                } else {
                    columnName = fieldName;
                }
                fieldMap.put(fieldName, columnName);
            }
        }
        return fieldMap;
    }
}
