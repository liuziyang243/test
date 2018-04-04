package com.crscd.passengerservice.warning.objectdomain;

import com.crscd.passengerservice.warning.enumtype.InterfaceMachineTypeEnum;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.enumtype.WarningLevelEnum;
import com.crscd.passengerservice.warning.po.InterfaceMachineWarningBean;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 15:04
 */
public class InterfaceMachineWarningInfo extends AbstractWarningInfo {
    // 接口机类型
    private InterfaceMachineTypeEnum machineType;
    // 如果在中心则为center
    private String station;

    // 用于生成告警信息
    public InterfaceMachineWarningInfo(SystemEnum system, String identification, WarningLevelEnum warningLevel, String message, InterfaceMachineTypeEnum type, String station) {
        super(system, identification, warningLevel, message);
        this.machineType = type;
        this.station = station;
    }

    // 用于从po初始化告警信息
    public InterfaceMachineWarningInfo(InterfaceMachineWarningBean bean) {
        super(bean.getBelongSystem(), bean.getIdentification(), bean.getWarningLevel(), bean.getWarningMessage());
        this.setId(bean.getId());
        this.setGenerateTime(bean.getGenerateTime());
        this.setConfirmTime(bean.getConfirmTime());
        this.setConfirmUser(bean.getConfirmUser());
        this.setConfirmState(bean.getConfirmState());
        this.setMachineType(bean.getMachineType());
        this.setStation(bean.getStation());
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
