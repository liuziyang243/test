package com.crscd.passengerservice.display.format.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/7/17.
 */
public class DefaultFormatInfoBean {
    /*自增长ID*/
    @OrmIgnore
    private int id;
    /*版式XML描述*/
    private String formatData;
    /*屏幕类型*/
    private String screenType;
    /*屏幕宽度*/
    private int screenWidth;
    /*屏幕高度*/
    private int screenHeight;
    /*屏幕颜色*/
    private String screenColor;
    /*版式名称*/
    private String formatName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

}
