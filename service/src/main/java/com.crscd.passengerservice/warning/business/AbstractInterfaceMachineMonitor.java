package com.crscd.passengerservice.warning.business;

import com.crscd.passengerservice.warning.dao.InterfaceMachineWarningDAO;
import com.crscd.passengerservice.warning.enumtype.InterfaceMachineTypeEnum;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.enumtype.WarningLevelEnum;
import com.crscd.passengerservice.warning.objectdomain.InterfaceMachineWarningInfo;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 16:02
 */
public abstract class AbstractInterfaceMachineMonitor implements MonitoringInterface {
    private InterfaceMachineWarningDAO dao;

    public void setDao(InterfaceMachineWarningDAO dao) {
        this.dao = dao;
    }

    // 生成一条设备断线状态告警记录
    void genOffLineWarning(String identification, InterfaceMachineTypeEnum type, int interval) {
        String msg = "The connection of interface machine" + identification + " at center has turned to fault. Please pay attention to it.";
        InterfaceMachineWarningInfo info = new InterfaceMachineWarningInfo(SystemEnum.INTERFACE_MACHINE, identification, WarningLevelEnum.PROMPT, msg, type, "center");
        dao.insertWarning(info);

        // todo: mysql不兼容判断时间区间的语句，待切换至oracle后再改回来
//        String msg = "The connection of interface machine" + identification + " at center has turned to fault. Please pay attention to it.";
//        InterfaceMachineWarningInfo info = new InterfaceMachineWarningInfo(SystemEnum.INTERFACE_MACHINE, identification, WarningLevelEnum.PROMPT, msg, type, "center");
//        if (!dao.isExitSameWarning(info, interval)) {
//            dao.insertWarning(info);
//        }
    }

    //生成外系统接入故障告警记录
    void genExternolSystemWarning(String identification, InterfaceMachineTypeEnum type, int interval) {
        String msg = "The connection of " + type + " system and interface machine" + identification + "at center has turned to fault. Please pay attention to it.";
        InterfaceMachineWarningInfo info = new InterfaceMachineWarningInfo(SystemEnum.INTERFACE_MACHINE, identification, WarningLevelEnum.PROMPT, msg, type, "center");
        if (!dao.isExitSameWarning(info, interval)) {
            dao.insertWarning(info);
        }
    }
}
