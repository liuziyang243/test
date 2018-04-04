package com.crscd.passengerservice.warning.objectdomain;

import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.enumtype.WarningLevelEnum;
import com.crscd.passengerservice.warning.po.DeviceWarningBean;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 13:53
 */
public class DeviceWarningInfo extends AbstractWarningInfo {

    // 设备类型
    private DeviceTypeEnum deviceType;
    // 设备所在区域
    private String deviceArea;
    // 设备所在车站
    private String station;

    // 用于生成告警信息
    public DeviceWarningInfo(String warningMessage, SystemEnum system, String deviceID, WarningLevelEnum warningLevel, DeviceTypeEnum deviceType, String deviceArea, String station) {
        super(system, deviceID, warningLevel, warningMessage);
        this.deviceType = deviceType;
        this.deviceArea = deviceArea;
        this.station = station;
    }

    // 用于从po初始化告警信息
    public DeviceWarningInfo(DeviceWarningBean bean) {
        super(bean.getBelongSystem(), bean.getIdentification(), bean.getWarningLevel(), bean.getWarningMessage());
        this.setId(bean.getId());
        this.setGenerateTime(bean.getGenerateTime());
        this.setConfirmTime(bean.getConfirmTime());
        this.setConfirmUser(bean.getConfirmUser());
        this.setConfirmState(bean.getConfirmState());
        this.setDeviceType(bean.getDeviceType());
        this.setDeviceArea(bean.getDeviceArea());
        this.setStation(bean.getStation());
    }

    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceTypeEnum deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceArea() {
        return deviceArea;
    }

    public void setDeviceArea(String deviceArea) {
        this.deviceArea = deviceArea;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
