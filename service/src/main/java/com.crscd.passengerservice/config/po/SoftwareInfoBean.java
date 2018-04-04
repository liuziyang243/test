package com.crscd.passengerservice.config.po;

/**
 * Created by Administrator on 2017/9/18.
 */
public class SoftwareInfoBean {
    //前台版本号
    private String clientVersion;

    //后台版本号
    private String backgroundVersion;

    public SoftwareInfoBean() {
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getBackgroundVersion() {
        return backgroundVersion;
    }

    public void setBackgroundVersion(String backgroundVersion) {
        this.backgroundVersion = backgroundVersion;
    }
}
