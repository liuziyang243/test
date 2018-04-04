package com.crscd.passengerservice.display.format.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/7/17.
 */
public class FormatInfoBean {
    /*自增长ID*/
    @OrmIgnore
    private int id;
    /*版式存储地址*/
    private String storagePath;
    /*屏幕业务类型*/
    private String screenType;
    /*车站编码*/
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

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getScreenColor() {
        return screenColor;
    }

    public void setScreenColor(String screenColor) {
        this.screenColor = screenColor;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String type) {
        screenType = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
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
