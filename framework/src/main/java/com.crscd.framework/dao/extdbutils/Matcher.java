package com.crscd.framework.dao.extdbutils;

/**
 * @author lzy
 * Date: 2017/7/11
 * Time: 16:25
 */
public interface Matcher {
    /**
     * Make match to be a interface
     *
     * @param columnName   the name of columnName in dataset
     * @param propertyName the name of propertyName
     * @return return whether match
     */
    boolean match(String columnName, String propertyName);
}
