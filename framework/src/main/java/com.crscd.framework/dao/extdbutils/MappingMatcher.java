package com.crscd.framework.dao.extdbutils;

import java.util.HashMap;
import java.util.Map;

/**
 * this is to match original method in the original class
 *
 * @author lzy
 * Date: 2017/7/11
 * Time: 16:28
 */
public class MappingMatcher implements Matcher {

    private final Map<String, String> columnToPropertyOverrides;

    public MappingMatcher() {
        this(new HashMap<String, String>());
    }

    public MappingMatcher(Map<String, String> columnToPropertyOverrides) {
        if (columnToPropertyOverrides == null) {
            throw new IllegalArgumentException("columnToPropertyOverrides map cannot be null");
        }
        this.columnToPropertyOverrides = columnToPropertyOverrides;
    }

    @Override
    public boolean match(String columnName, String propsName) {
        String propertyName = columnToPropertyOverrides.get(columnName);
        if (propertyName == null) {
            propertyName = columnName;
        }
        return propertyName.equalsIgnoreCase(propsName);
    }
}
