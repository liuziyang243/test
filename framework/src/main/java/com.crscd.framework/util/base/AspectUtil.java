package com.crscd.framework.util.base;

import com.crscd.framework.util.text.StringUtil;

/**
 * Create by: lzy
 * Date: 2016/6/21
 * Time: 8:04
 */
public class AspectUtil {

    public static String getObjectName(String typeName) {
        if (typeName.contains("<")) {
            String a[] = typeName.split("<");
            String b[] = a[1].split(">");
            return b[0];
        } else {
            return typeName;
        }
    }

    /**
     * 获取方法返回值对象的类名，即不包含List之类的结构
     */
    public static String getReturnTypeName(String typeName) {
        return StringUtil.getMatchString(typeName, "com\\.crscd\\.PassengerService(\\.\\w+)+");
    }
}
