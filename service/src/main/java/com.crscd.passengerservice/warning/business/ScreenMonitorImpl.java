package com.crscd.passengerservice.warning.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.util.base.CastUtil;
import com.crscd.passengerservice.device.enumtype.DeviceStateEnum;
import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;
import com.crscd.passengerservice.warning.enumtype.MonitorModeEnum;
import com.crscd.passengerservice.warning.enumtype.MonitorStateEnum;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.enumtype.ThresholdCompareModeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 17:32
 */
public class ScreenMonitorImpl extends AbstractDeviceMonitor {
    @Override
    public List<String> getMonitorObjectList() {
        return configManager.getAllScreenID();
    }

    @Override
    public List<MonitorProperty> getPropertyList() {
        MonitorProperty deviceStateProperty = new MonitorProperty("deviceState", MonitorModeEnum.IMMEDIATE, -1, -1, ThresholdCompareModeEnum.NOT_VALID);
        MonitorProperty powerState = new MonitorProperty("powerState", MonitorModeEnum.IMMEDIATE, -1, -1, ThresholdCompareModeEnum.NOT_VALID);
        List<MonitorProperty> result = new ArrayList<>();
        result.add(deviceStateProperty);
        result.add(powerState);
        return result;
    }

    @Override
    public MonitorStateEnum getMonitoringState(String objectID, String property) {
        // 采用严格的判断方式，只有当能查到状态且状态为正常的时候才返回设备状态正常，
        // 否则认为设备状态异常
        if (stateInfoInterface.getDeviceStateByDevID(objectID).equals(DeviceStateEnum.NORMAL)) {
            return MonitorStateEnum.NORMAL;
        }
        return MonitorStateEnum.ABNORMAL;
    }

    @Override
    public void notifyAbnormal(String objectID, String property) {
        int interval = ConfigHelper.getInt("travelService.screenSuppressWarningInterval", 10);
        String station = configManager.getStationNameByScreenID(CastUtil.castInt(objectID));
        String area = configManager.getScreenAreaByScreenID(CastUtil.castInt(objectID));
        if ("deviceState".equals(property)) {
            genDeviceStateWarningMessage(objectID, SystemEnum.GUIDE, DeviceTypeEnum.SCREEN, area, station, interval);
        } else if ("powerState".equals(property)) {
            genDevicePowerWarningMessage(objectID, SystemEnum.GUIDE, DeviceTypeEnum.SCREEN, area, station, interval);
        }
    }

    @Override
    public void notifyRecover(String objectID, String property) {
        String station = configManager.getStationNameByScreenID(CastUtil.castInt(objectID));
        String area = configManager.getScreenAreaByScreenID(CastUtil.castInt(objectID));
        if ("deviceState".equals(property)) {
            String msg = "The state of screen-" + objectID + " has turn to normal.";
            genRecoverPrompt(msg, objectID, SystemEnum.GUIDE, DeviceTypeEnum.SCREEN, area, station);
        } else if ("powerState".equals(property)) {
            String msg = "The power of screen-" + objectID + " has turn to normal.";
            genRecoverPrompt(msg, objectID, SystemEnum.GUIDE, DeviceTypeEnum.SCREEN, area, station);
        }
    }


}
