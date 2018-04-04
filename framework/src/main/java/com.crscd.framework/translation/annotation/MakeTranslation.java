package com.crscd.framework.translation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicate the returen result is to be translated before return to client.
 * Created by lzy
 * Date: 2016/6/2
 * Time: 15:32
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MakeTranslation {
}
