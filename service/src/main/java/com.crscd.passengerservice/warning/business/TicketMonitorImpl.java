package com.crscd.passengerservice.warning.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.passengerservice.warning.enumtype.InterfaceMachineTypeEnum;
import com.crscd.passengerservice.warning.enumtype.MonitorModeEnum;
import com.crscd.passengerservice.warning.enumtype.MonitorStateEnum;
import com.crscd.passengerservice.warning.enumtype.ThresholdCompareModeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 16:11
 */
public class TicketMonitorImpl extends AbstractInterfaceMachineMonitor {
    @Override
    public List<String> getMonitorObjectList() {
        List<String> result = new ArrayList<>();
        result.add("192.168.0.1");
        return result;
    }

    @Override
    public List<MonitorProperty> getPropertyList() {
        MonitorProperty commState = new MonitorProperty("commState", MonitorModeEnum.PERIOD_STATISTICS, 0.9f, 10, ThresholdCompareModeEnum.MORE);
        List<MonitorProperty> result = new ArrayList<>();
        result.add(commState);
        return result;
    }

    @Override
    public MonitorStateEnum getMonitoringState(String objectID, String property) {
        return null;
    }

    @Override
    public void notifyAbnormal(String objectID, String property) {
        int interval = ConfigHelper.getInt("travelService.screenSuppressWarningInterval", 10);
        if ("offLine".equals(property)) {
            genOffLineWarning(objectID, InterfaceMachineTypeEnum.TICKET, interval);
        } else if ("externolSystem".equals(property)) {
            genExternolSystemWarning(objectID, InterfaceMachineTypeEnum.TICKET, interval);
        }
    }

    @Override
    public void notifyRecover(String objectID, String property) {

    }
}
