package com.crscd.passengerservice.device.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 17:02
 */
public class BroadcastDeviceBean {
    // 数据库id
    @OrmIgnore
    private long id;
    // 厂家
    private String manufacturer;
    // 联系方式
    private String contactInfo;
    // 设备位置
    private String deviceLocation;
    // 自定义信息，key-信息键值，value-信息
    private Map<String, String> customizedConfig;
    // 设备ID
    private String broadcastDevID;

    public BroadcastDeviceBean() {
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

    public Map<String, String> getCustomizedConfig() {
        return customizedConfig;
    }

    public void setCustomizedConfig(Map<String, String> customizedConfig) {
        this.customizedConfig = customizedConfig;
    }

    public String getBroadcastDevID() {
        return broadcastDevID;
    }

    public void setBroadcastDevID(String broadcastDevID) {
        this.broadcastDevID = broadcastDevID;
    }
}
