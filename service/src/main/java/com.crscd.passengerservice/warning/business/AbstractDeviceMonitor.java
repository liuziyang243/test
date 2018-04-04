package com.crscd.passengerservice.warning.business;

import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.device.business.DeviceStateInfoInterface;
import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;
import com.crscd.passengerservice.warning.dao.DeviceWarningDAO;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.enumtype.WarningLevelEnum;
import com.crscd.passengerservice.warning.objectdomain.DeviceWarningInfo;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 9:36
 */
public abstract class AbstractDeviceMonitor implements MonitoringInterface {
    DeviceStateInfoInterface stateInfoInterface;
    ConfigManager configManager;
    private DeviceWarningDAO warningDAO;

    public void setStateInfoInterface(DeviceStateInfoInterface stateInfoInterface) {
        this.stateInfoInterface = stateInfoInterface;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setWarningDAO(DeviceWarningDAO warningDAO) {
        this.warningDAO = warningDAO;
    }

    // 生成一条设备状态告警记录
    // 根据数据库判断如果有重复的告警，xx分钟内不重复生成相同的告警
    void genDeviceStateWarningMessage(String objectID, SystemEnum system, DeviceTypeEnum type, String area, String station, int interval) {
        String msg = "The state of screen-" + objectID + " at " + area + " of " + station + " has turned to fault. Please pay attention to it.";
        DeviceWarningInfo info = new DeviceWarningInfo(msg, system, objectID, WarningLevelEnum.PROMPT, type, area, station);
        if (!warningDAO.isExitSameWarning(info, interval)) {
            warningDAO.insertWarning(info);
        }
    }

    // 生成一条设备电源告警记录
    // 根据数据库判断如果有重复的告警，xx分钟内不重复生成相同的告警
    void genDevicePowerWarningMessage(String objectID, SystemEnum system, DeviceTypeEnum type, String area, String station, int interval) {
        String msg = "The power of screen-" + objectID + " at " + area + " of " + station + " has turned to down. Please pay attention to it.";
        DeviceWarningInfo info = new DeviceWarningInfo(msg, system, objectID, WarningLevelEnum.PROMPT, type, area, station);
        if (!warningDAO.isExitSameWarning(info, interval)) {
            warningDAO.insertWarning(info);
        }
    }

    // 生成一条告警恢复提示
    void genRecoverPrompt(String msg, String objectID, SystemEnum system, DeviceTypeEnum type, String area, String station) {
        DeviceWarningInfo info = new DeviceWarningInfo(msg, system, objectID, WarningLevelEnum.PROMPT, type, area, station);
        warningDAO.insertWarning(info);
    }
}
