package com.crscd.passengerservice.display.format.domainobject;

/**
 * Created by cuishiqing on 2017/8/31.
 */
public class FormatInfo {
    /*版式内容*/
    private String formatData;
    /*屏幕业务类型*/
    private String screenType;
    /*车站名称*/
    private String stationName;
    /*屏幕宽度*/
    private String screenWidth;
    /*屏幕高度*/
    private String screenHeight;
    /*屏幕颜色*/
    private String screenColor;
    /*版式名称*/
    private String formatName;
    /*版式号*/
    private String formatID;
    /*版式版本号*/
    private String version;

    public String getFormatData() {
        return formatData;
    }

    public void setFormatData(String formatData) {
        this.formatData = formatData;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getScreenColor() {
        return screenColor;
    }

    public void setScreenColor(String screenColor) {
        this.screenColor = screenColor;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatID() {
        return formatID;
    }

    public void setFormatID(String formatID) {
        this.formatID = formatID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
