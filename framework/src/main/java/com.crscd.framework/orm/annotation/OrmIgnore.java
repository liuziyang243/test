package com.crscd.framework.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lzy
 * Date: 2016/4/1
 * Time: 12:11
 * 当属性不需要存储到数据库中，用该项可以在存取的时候忽略该属性
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrmIgnore {
}
