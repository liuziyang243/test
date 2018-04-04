package com.crscd.passengerservice.display.format.po;

/**
 * Created by cuishiqing on 2017/7/18.
 * '屏幕管理信息表'
 */
public class ScreenManageBean {
    // 屏幕ID
    private int screenID;
    // 屏幕状态标志
    // 0XA1:屏幕开启 0XA2:屏幕待机 0XA3:屏幕下电 0XA4:通信中断
    private String onOff;
    // 当前版式
    private String currentFormatNo;
    // 空闲版式
    private String standbyFormatNo;
    // 火灾版式
    private String fasFormatNo;
    // 当前使用版式
    private String usedFormat;
    // 当前使用版式版本
    private String version;
    // 版式下发状态(1:已下发 0:未下发)
    private String formatSendStatus;

    public String getFasFormatNo() {
        return fasFormatNo;
    }

    public void setFasFormatNo(String fasFormatNo) {
        this.fasFormatNo = fasFormatNo;
    }

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }

    public String getCurrentFormatNo() {
        return currentFormatNo;
    }

    public void setCurrentFormatNo(String currentFormatNo) {
        this.currentFormatNo = currentFormatNo;
    }

    public String getStandbyFormatNo() {
        return standbyFormatNo;
    }

    public void setStandbyFormatNo(String standbyFormatNo) {
        this.standbyFormatNo = standbyFormatNo;
    }

    public String getUsedFormat() {
        return usedFormat;
    }

    public void setUsedFormat(String usedFormat) {
        this.usedFormat = usedFormat;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFormatSendStatus() {
        return formatSendStatus;
    }

    public void setFormatSendStatus(String formatSendStatus) {
        this.formatSendStatus = formatSendStatus;
    }
}
