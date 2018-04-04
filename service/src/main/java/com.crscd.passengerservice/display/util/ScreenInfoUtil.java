package com.crscd.passengerservice.display.util;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;

/**
 * Created by cuishiqing on 2017/12/14.
 */
public class ScreenInfoUtil {
    private static DataSet oracleDataSet;

    public static String getScreenIpById(int screenId) {
        ScreenConfig screenConfig = oracleDataSet.select(ScreenConfig.class, "screenId = ?", screenId);
        return screenConfig.getScreenIp();
    }

    public static int getScreenIdByIp(String screenIp) {
        ScreenConfig screenConfig = oracleDataSet.select(ScreenConfig.class, "screenIp = ?", screenIp);
        return screenConfig.getScreenID();
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        ScreenInfoUtil.oracleDataSet = oracleDataSet;
    }
}
