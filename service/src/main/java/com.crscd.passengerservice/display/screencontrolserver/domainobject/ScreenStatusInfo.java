package com.crscd.passengerservice.display.screencontrolserver.domainobject;

/**
 * Created by cuishiqing on 2017/12/6.
 */
public class ScreenStatusInfo {
    //屏幕IP
    private String ip;
    //屏幕状态
    private String state;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
