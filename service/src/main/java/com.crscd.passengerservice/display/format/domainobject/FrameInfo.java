package com.crscd.passengerservice.display.format.domainobject;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class FrameInfo {
    /*自增长ID*/
    private int id;
    /*帧名称*/
    private String frameName;
    /*显示帧名称*/
    private String showName;
    /*帧归属车站名称*/
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
    private String frameData;

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

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
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

    public String getUsedFormat() {
        return usedFormat;
    }

    public void setUsedFormat(String usedFormat) {
        this.usedFormat = usedFormat;
    }

    public String getFrameData() {
        return frameData;
    }

    public void setFrameData(String frameData) {
        this.frameData = frameData;
    }
}
