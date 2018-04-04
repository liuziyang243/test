package com.crscd.passengerservice.device.objectdomain;

import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;

import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/9/17
 */
public abstract class BaseDeviceInfo {
    // 数据库id
    private long id;
    // 厂家
    private String manufacturer;
    // 联系方式
    private String contactInfo;
    // 设备位置
    private String deviceLocation;
    // 自定义信息，key-信息键值，value-信息
    private Map<String, String> customizedConfig;
    // 设备类型
    private DeviceTypeEnum deviceTypeEnum;

    public BaseDeviceInfo() {
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

    public DeviceTypeEnum getDeviceTypeEnum() {
        return deviceTypeEnum;
    }

    public void setDeviceTypeEnum(DeviceTypeEnum deviceTypeEnum) {
        this.deviceTypeEnum = deviceTypeEnum;
    }
}
