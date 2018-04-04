package com.crscd.passengerservice.config.domainobject;

import com.crscd.framework.orm.annotation.EqualBean;
import com.crscd.passengerservice.config.enumtype.ScreenColorEnum;
import com.crscd.passengerservice.config.enumtype.ScreenControllerTypeEnum;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 11:20
 */
@EqualBean
public class ScreenConfig {
    // 屏幕ID
    private int screenID;
    // 屏幕名称
    private String screenName;
    // 屏幕类型，根据地理位置划分
    private ScreenTypeEnum ScreenType;
    // 屏幕宽度
    private int screenWidth;
    // 屏幕高度
    private int screenHeight;
    // 屏幕颜色类型，双色，全彩
    private ScreenColorEnum screenColor;
    //屏幕IP
    private String screenIp;
    // 屏幕对应控制器
    //private int screenControllerID;
    // 控制器类型
    private ScreenControllerTypeEnum controllerType;
    //归属综显服务器IP
    private String serverIp;
    // 屏幕所属车站
    private String stationName;
    // 屏幕归属二级区
    private String secondaryRegion;

    public ScreenConfig() {
    }

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public ScreenTypeEnum getScreenType() {
        return ScreenType;
    }

    public void setScreenType(ScreenTypeEnum screenType) {
        ScreenType = screenType;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public ScreenColorEnum getScreenColor() {
        return screenColor;
    }

    public void setScreenColor(ScreenColorEnum screenColor) {
        this.screenColor = screenColor;
    }

    public ScreenControllerTypeEnum getControllerType() {
        return controllerType;
    }

    public void setControllerType(ScreenControllerTypeEnum controllerType) {
        this.controllerType = controllerType;
    }

    public String getSecondaryRegion() {
        return secondaryRegion;
    }

    public void setSecondaryRegion(String secondaryRegion) {
        this.secondaryRegion = secondaryRegion;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getScreenIp() {
        return screenIp;
    }

    public void setScreenIp(String screenIp) {
        this.screenIp = screenIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}
