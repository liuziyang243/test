package com.crscd.passengerservice.plan.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.job.IndependentJobHelper;
import com.crscd.passengerservice.plan.pool.RefreshPlanJob;

/**
 * @author lzy
 * Date: 2017/10/17
 * Time: 9:06
 */
public class RefreshPlanDataPoolManager {

    private String schedulerName;
    private String refreshTime;
    private IndependentJobHelper refreshDataJobScheduler;

    public RefreshPlanDataPoolManager(String schedulerName, String refreshTimeCorn) {
        this.refreshTime = refreshTimeCorn;
        if (null == refreshTimeCorn) {
            this.refreshTime = ConfigHelper.getString("defaultRefreshTime");
        }
        refreshDataJobScheduler = new IndependentJobHelper(schedulerName, "2");
    }

    public void startRefreshPlanDataPool() {
        refreshDataJobScheduler.startCronRepeatedJob(RefreshPlanJob.class, refreshTime);
    }
}
