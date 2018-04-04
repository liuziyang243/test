package com.crscd.passengerservice.config.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/11/22.
 */
public class ScreenCtrlServerConfigBean {
    /*自增长ID*/
    @OrmIgnore
    private int id;
    /*站名*/
    private String stationName;
    /*综显服务器IP*/
    private String ip;
    /*综显服务器端口*/
    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
