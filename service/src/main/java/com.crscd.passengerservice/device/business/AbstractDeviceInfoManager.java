package com.crscd.passengerservice.device.business;

import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.device.dao.AbstractDeviceInfoDAO;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 8:47
 */
public abstract class AbstractDeviceInfoManager<T, M> implements DeviceInfoManagerInterface<T> {
    AbstractDeviceInfoDAO<M> deviceDAO;
    private DeviceStateInfoInterface stateInfoInterface;
    private ConfigManager configManager;

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setDeviceDAO(AbstractDeviceInfoDAO<M> deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    DeviceStateInfoInterface getStateInfoInterface() {
        return stateInfoInterface;
    }

    public void setStateInfoInterface(DeviceStateInfoInterface stateInfoInterface) {
        this.stateInfoInterface = stateInfoInterface;
    }
}
