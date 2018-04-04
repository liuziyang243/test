package com.crscd.passengerservice.warning.business;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 17:31
 */
public class DeviceMonitorManager {
    private List<ObjectMonitorFramework> monitorFrameworkList;

    public DeviceMonitorManager(List<ObjectMonitorFramework> monitorFrameworkList) {
        this.monitorFrameworkList = monitorFrameworkList;
    }

    public void start() {
        for (ObjectMonitorFramework framework : monitorFrameworkList) {
            framework.start();
        }
    }
}
