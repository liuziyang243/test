package com.crscd.passengerservice.display.screencontrolserver.domainobject;

/**
 * Created by cuishiqing on 2017/12/6.
 */
public class ScreenControlInfo {
    //屏幕IP地址
    private String ip;
    //屏幕类型
    private String type;
    //执行动作
    private String action;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
