package com.crscd.passengerservice.device.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 16:58
 */
public class ScreenDeviceBean {
    @OrmIgnore
    private long id;
    // 厂家
    private String manufacturer;
    // 联系方式
    private String contactInfo;
    // 设备位置
    private String deviceLocation;
    // 自定义信息，key-信息键值，value-信息
    // key_value
    private List<String> customizedConfig;
    // 屏幕ID
    private String screenID;
    // 屏幕名称
    private String screenName;

    public ScreenDeviceBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public List<String> getCustomizedConfig() {
        return customizedConfig;
    }

    public void setCustomizedConfig(List<String> customizedConfig) {
        this.customizedConfig = customizedConfig;
    }

    public String getScreenID() {
        return screenID;
    }

    public void setScreenID(String screenID) {
        this.screenID = screenID;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
