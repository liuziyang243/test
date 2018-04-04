package com.crscd.passengerservice.display.screencontrolserver.util;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.config.po.ScreenCtrlServerConfigBean;

/**
 * Created by cuishiqing on 2017/12/6.
 */
public class UrlUtil {
    private DataSet oracleDataSet;

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public String getUrl(String ip, String interfaceAdd) {
        ScreenCtrlServerConfigBean bean = oracleDataSet.select(ScreenCtrlServerConfigBean.class, "ip = ?", ip);
        String port = bean.getPort();
        String url = "http://" + ip + ":" + port + interfaceAdd;
        return url;
    }
}
