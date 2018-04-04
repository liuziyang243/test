package com.crscd.framework.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zs
 * on 2017/7/19.
 * 后台逻辑和数据库共用同一套结构体时，在class前添加注解@EqualBean
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualBean {

}
