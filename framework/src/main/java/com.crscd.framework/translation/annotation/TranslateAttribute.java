package com.crscd.framework.translation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark the field in the object need to be translated by aop.
 * Created by lzy
 * Date: 2016/6/2
 * Time: 15:55
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TranslateAttribute {
}
