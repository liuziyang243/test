package com.crscd.framework;

/**
 * @author lzy
 * Date: 2017/7/14
 * Time: 12:29
 */
public interface FrameworkConstant {
    String UTF_8 = "UTF-8";

    String SQL_PROPS = "smart-sql.properties";
    String CONFIG_PROPS = "TravelService.properties";
    String QUARTZ_PROPS = "travelServiceQuartz.properties";

    //soapHeader的命名空间
    String QNAMESPACE = "crscd.com.cn";
    String PK_NAME = "id";
    String BEAN_MARKER = "Bean";
    String DEL_BY_PK = "id=?";

    String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    String TIME_FORMAT_PATTERN = "HH:mm:ss";
    String TIME_NOSECOND_PATTERN = "HH:mm";

    String EMPTY_TIME = "--";

    String QUERY_ALL = "All";
}
