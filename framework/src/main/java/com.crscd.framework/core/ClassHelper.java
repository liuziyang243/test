package com.crscd.framework.core;

import com.crscd.framework.orm.annotation.EqualBean;
import com.crscd.framework.util.reflect.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 根据条件获取相关类
 *
 * @author huangyong
 * @since 1.0
 */

public class ClassHelper {

    /**
     * 定义集合类(用于存放所加载的类)
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getString("basicPackagePath");
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }


    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有Bean类,包括类名结尾为Bean以及有@EqualBean注解的类
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.getSimpleName().contains("Bean")) {
                beanClassSet.add(cls);
            }
        }
        Set<Class<?>> otherBeanClassSet = getClassSetByAnnotation(EqualBean.class);
        beanClassSet.addAll(otherBeanClassSet);
        return beanClassSet;
    }
}
