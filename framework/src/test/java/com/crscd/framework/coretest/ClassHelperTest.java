package com.crscd.framework.coretest;

import com.crscd.framework.core.ClassHelper;
import org.junit.Test;

import java.util.Set;

/**
 * Created by zs
 * on 2017/7/19.
 */
public class ClassHelperTest {
    @Test
    public void getBeanClassSetTest() {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            System.out.println(beanClass.getSimpleName());
        }
    }
}
