package com.crscd.passengerservice.display.format.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/7/17.
 */
public class FrameInfoBean {
    /*自增长ID*/
    @OrmIgnore
    private int id;
    /*帧名称*/
    private String frameName;
    /*显示帧名称*/
    private String showName;
    /*帧归属车站编号*/
    private String stationName;
    /*屏幕类型*/
    private String screenType;
    /*屏幕宽度*/
    private String screenWidth;
    /*屏幕高度*/
    private String screenHeight;
    /*屏幕颜色*/
    private String screenColor;
    /*使用标记*/
    private String usedFormat;
    /**/
    private String storagePath;

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
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

    public void setScreenType(String screenType) {
        this.screenType = screenType;
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

    public void setScreenHeight(String screenHight) {
        this.screenHeight = screenHight;
    }

    public String getScreenColor() {
        return screenColor;
    }

    public void setScreenColor(String screenColor) {
        this.screenColor = screenColor;
    }

    public String getUsedFormat() {
        return usedFormat;
    }

    public void setUsedFormat(String usedFormat) {
        this.usedFormat = usedFormat;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
