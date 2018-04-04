package com.crscd.passengerservice.warning.po;

import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.passengerservice.warning.enumtype.InterfaceMachineTypeEnum;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.enumtype.WarningConfirmStateEnum;
import com.crscd.passengerservice.warning.enumtype.WarningLevelEnum;
import com.crscd.passengerservice.warning.objectdomain.InterfaceMachineWarningInfo;

/**
 * @author lzy
 * @author lzy
 * Date: 2017/9/21
 * Time: 15:07
 */
public class InterfaceMachineWarningBean {
    /*告警信息唯一ID*/
    @OrmIgnore
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
    // 接口机类型
    private InterfaceMachineTypeEnum machineType;
    // 如果在中心则为center
    private String station;

    public InterfaceMachineWarningBean() {
    }

    // 从DO初始化
    public InterfaceMachineWarningBean(InterfaceMachineWarningInfo info) {
        this.identification = info.getIdentification();
        this.generateTime = info.getGenerateTime();
        this.belongSystem = info.getBelongSystem();
        this.warningLevel = info.getWarningLevel();
        this.warningMessage = info.getWarningMessage();
        this.confirmUser = info.getConfirmUser();
        this.confirmTime = info.getConfirmTime();
        this.confirmState = info.getConfirmState();
        this.machineType = info.getMachineType();
        this.station = info.getStation();
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

    public InterfaceMachineTypeEnum getMachineType() {
        return machineType;
    }

    public void setMachineType(InterfaceMachineTypeEnum machineType) {
        this.machineType = machineType;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
