package com.crscd.passengerservice.warning.dto;

import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.enumtype.WarningConfirmStateEnum;
import com.crscd.passengerservice.warning.enumtype.WarningLevelEnum;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 14:10
 */
public class DeviceWarningDTO {
    /*告警信息唯一ID*/
    private long id;
    /*告警源唯一标识*/
    private String identification;
    /*产生告警的时间*/
    private String generateTime;
    /*所属系统*/
    private SystemEnum belongSystem;
    /*告警级别*/
    private WarningLevelEnum warningLevel;
    /*告警信息描述*/
    private String warningMessage;
    /*确认用户名称*/
    private String confirmUser;
    /*确认时间*/
    private String confirmTime;
    /*告警的确认状态：已确认(confirmed)和未确认(unconfirmed)*/
    private WarningConfirmStateEnum confirmState;
    // 设备类型
    private DeviceTypeEnum deviceType;
    // 设备所在区域
    private String deviceArea;
    // 设备所在车站
    private String station;

    public DeviceWarningDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(String generateTime) {
        this.generateTime = generateTime;
    }

    public SystemEnum getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(SystemEnum belongSystem) {
        this.belongSystem = belongSystem;
    }

    public WarningLevelEnum getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(WarningLevelEnum warningLevel) {
        this.warningLevel = warningLevel;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public WarningConfirmStateEnum getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(WarningConfirmStateEnum confirmState) {
        this.confirmState = confirmState;
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
