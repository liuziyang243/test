package com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cuishiqing on 2017/8/30.
 */
@XmlRootElement(name = "Frame")
public class Frame {
    private String Name;
    private String ShowName;
    private String ScreenType;
    private String ScreenWidth;
    private String ScreenHeight;
    private String ScreenColor;
    private DTVar dtVar;

    @XmlElement(name = "Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @XmlElement(name = "ShowName")
    public String getShowName() {
        return ShowName;
    }

    public void setShowName(String showName) {
        ShowName = showName;
    }

    @XmlElement(name = "ScreenType")
    public String getScreenType() {
        return ScreenType;
    }

    public void setScreenType(String screenType) {
        ScreenType = screenType;
    }

    @XmlElement(name = "ScreenWidth")
    public String getScreenWidth() {
        return ScreenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        ScreenWidth = screenWidth;
    }

    @XmlElement(name = "ScreenHeight")
    public String getScreenHeight() {
        return ScreenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        ScreenHeight = screenHeight;
    }

    @XmlElement(name = "ScreenColor")
    public String getScreenColor() {
        return ScreenColor;
    }

    public void setScreenColor(String screenColor) {
        ScreenColor = screenColor;
    }

    @XmlElement(name = "DTVar")
    public DTVar getDtVar() {
        return dtVar;
    }

    public void setDtVar(DTVar dtVar) {
        this.dtVar = dtVar;
    }
}
