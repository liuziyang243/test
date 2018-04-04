package com.crscd.passengerservice.config.serviceinterface.implement;

import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.serviceinterface.SystemInfoInterface;
import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;
import com.crscd.passengerservice.plan.util.PlanHelper;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 12:58
 */
public class SystemInfoInterfaceImpl implements SystemInfoInterface {
    private ConfigManager configManager;

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public TrainDirectionEnum getTrainDirection(String startStation, String finalStation) {
        return PlanHelper.getTrainDirection(startStation, finalStation);
    }
}
