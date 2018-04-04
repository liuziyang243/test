package com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/8/30.
 */
@XmlRootElement(name = "Format")
public class Format {
    private String ID;
    private String Name;
    private String ScreenType;
    private String ScreenWidth;
    private String ScreenHeight;
    private String ScreenColor;
    private List<DTVar> dtVarList = new ArrayList<>();

    @XmlElement(name = "ID")
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    @XmlElementWrapper(name = "DTVarList")
    @XmlElement(name = "DTVar")
    public List<DTVar> getDtVarList() {
        return dtVarList;
    }

    public void setDtVarList(List<DTVar> dtVarList) {
        this.dtVarList = dtVarList;
    }
}
