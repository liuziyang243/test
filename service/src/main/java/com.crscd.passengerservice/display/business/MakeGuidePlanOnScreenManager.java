package com.crscd.passengerservice.display.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.job.IndependentJobHelper;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.config.business.ConfigManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 17:41
 */
public class MakeGuidePlanOnScreenManager {
    private final int refreshInterval = ConfigHelper.getInt("travelService.displayScreenRefreshInterval");

    private ConfigManager configManager;

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void startRefreshGuidePlanOnScreen() {
        List<String> stationCodeList = configManager.getAllStationName();
        for (String stationName : stationCodeList) {
            Map<String, Object> params = new HashMap<>();
            params.put(ServiceConstant.STATION_NAME, stationName);
            // 启动周期性刷新程序
            // 每个车站启动一个刷新的worker
            IndependentJobHelper refreshDataJobScheduler = new IndependentJobHelper("display", String.valueOf(stationCodeList.size()));
            refreshDataJobScheduler.startRepeatedJobNow(MakeDisplayJob.class, refreshInterval, params);
        }
    }
}
