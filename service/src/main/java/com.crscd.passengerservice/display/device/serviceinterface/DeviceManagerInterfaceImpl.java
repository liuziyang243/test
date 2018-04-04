package com.crscd.passengerservice.display.device.serviceinterface;

import com.crscd.passengerservice.display.device.DeviceManager;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusAskInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cuishiqing on 2018/1/10.
 */
public class DeviceManagerInterfaceImpl implements DeviceManagerInterface {
    private DeviceManager deviceManager;

    public void setDeviceManager(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    @Override
    public HashMap<String, Boolean> deviceControlLine(String action) {
        return deviceManager.deviceControlLine(action);
    }

    @Override
    public boolean deviceControlStation(String action, String stationName) {
        return deviceManager.deviceControlStation(action, stationName);
    }

    @Override
    public boolean deviceControlByIps(String action, String stationName, List<String> screenIps) {
        return deviceManager.deviceControlByIps(action, stationName, screenIps);
    }

    @Override
    public List<ScreenStatusInfo> getScreenStatusByIp(List<ScreenStatusAskInfo> statusAskInfos, String stationName) {
        return deviceManager.getScreenStatusByIp(statusAskInfos, stationName);
    }

    @Override
    public List<ScreenStatusInfo> getScreenStatusStation(String stationName) {
        return deviceManager.getScreenStatusStation(stationName);
    }
}
