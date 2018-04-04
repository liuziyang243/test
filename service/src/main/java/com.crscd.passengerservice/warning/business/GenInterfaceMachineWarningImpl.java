package com.crscd.passengerservice.warning.business;

import com.crscd.passengerservice.warning.dao.InterfaceMachineWarningDAO;
import com.crscd.passengerservice.warning.enumtype.InterfaceMachineTypeEnum;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.enumtype.WarningLevelEnum;
import com.crscd.passengerservice.warning.objectdomain.InterfaceMachineWarningInfo;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 16:43
 */
public class GenInterfaceMachineWarningImpl implements GenInterfaceMachineWarningInterface {
    private InterfaceMachineWarningDAO dao;

    public void setDao(InterfaceMachineWarningDAO dao) {
        this.dao = dao;
    }

    @Override
    public void genSingleTicketWarning(String identification, String message, WarningLevelEnum warningLevel, String station, int interval) {
        InterfaceMachineWarningInfo info = new InterfaceMachineWarningInfo(SystemEnum.INTERFACE_MACHINE, identification, warningLevel, message, InterfaceMachineTypeEnum.TICKET, station);
        if (!dao.isExitSameWarning(info, interval)) {
            dao.insertWarning(info);
        }
    }
}
