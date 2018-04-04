package com.crscd.passengerservice.device.dto;

import com.crscd.passengerservice.device.enumtype.CommStateEnum;
import com.crscd.passengerservice.device.enumtype.DeviceStateEnum;
import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;

import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 17:06
 */
public class BroadcastDeviceDTO {
    private long id;
    // 设备ID
    private String broadcastDevID;
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
    // 设备状态
    private DeviceStateEnum deviceState;
    // 通信状态
    private CommStateEnum commState;

    public BroadcastDeviceDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBroadcastDevID() {
        return broadcastDevID;
    }

    public void setBroadcastDevID(String broadcastDevID) {
        this.broadcastDevID = broadcastDevID;
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

    public DeviceStateEnum getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceStateEnum deviceState) {
        this.deviceState = deviceState;
    }

    public CommStateEnum getCommState() {
        return commState;
    }

    public void setCommState(CommStateEnum commState) {
        this.commState = commState;
    }
}
