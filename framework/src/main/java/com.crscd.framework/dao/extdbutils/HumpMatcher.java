package com.crscd.framework.dao.extdbutils;

/**
 * Convert Hump style property name
 *
 * @author lzy
 * Date: 2017/7/11
 * Time: 16:49
 */
public class HumpMatcher implements Matcher {

    @Override
    public boolean match(String columnName, String propertyName) {
        if (columnName == null) {
            return false;
        }
        columnName = columnName.toLowerCase();
        String[] array = columnName.split("_");
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            if (!"".equals(str) && i > 0) {
                StringBuilder builder = new StringBuilder();
                str = builder.append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
            }
            strBuilder.append(str);
        }
        return strBuilder.toString().equals(propertyName);
    }
}
