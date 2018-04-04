package com.crscd.passengerservice.warning.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.passengerservice.warning.enumtype.InterfaceMachineTypeEnum;
import com.crscd.passengerservice.warning.enumtype.MonitorStateEnum;

import java.util.List;

/**
 * Created by cuishiqing on 2017/12/1.
 */
public class ScreenCtrlServerMonitorImpl extends AbstractInterfaceMachineMonitor {
    @Override
    public List<String> getMonitorObjectList() {
        return null;
    }

    @Override
    public List<MonitorProperty> getPropertyList() {
        return null;
    }

    @Override
    public MonitorStateEnum getMonitoringState(String objectID, String property) {
        return null;
    }

    @Override
    public void notifyAbnormal(String objectID, String property) {
        int interval = ConfigHelper.getInt("travelService.screenSuppressWarningInterval", 10);
        genOffLineWarning(objectID, InterfaceMachineTypeEnum.SCREENCONTROLLER, interval);
    }

    @Override
    public void notifyRecover(String objectID, String property) {

    }
}
