package com.crscd.framework.translation.serviceinterface;

import java.util.List;

/**
 * Created by lzy
 * Date: 2016/6/2
 * Time: 15:29
 */
public interface TranslatorInterface {
    /* 对一个词进行翻译 */
    String makeTranslation(String word, String lan);

    /* 对一个对象进行翻译 */
    Object makeTranslation(Object obj, String lan, String clazzName);

    /* 对一组对象进行翻译*/
    Object makeTranslation(List<Object> objectList, String lan, String clazzName);


}
