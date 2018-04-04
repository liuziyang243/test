package com.crscd.passengerservice.display.screencontrolserver.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.job.IndependentJobHelper;
import com.crscd.framework.job.IndependentJobManager;

/**
 * Created by cuishiqing on 2017/12/1.
 */
public class ScreenCtrlServerHeartbeatManager {
    private static final int HEARTBEAT_PERIOD = ConfigHelper.getInt("screenCtrlHeartbeatPeriod");

    public ScreenCtrlServerHeartbeatManager() {
    }

    public void startScreenCtrlServerHeartbeatJobs() {
        IndependentJobHelper heartbeatJobScheduler = IndependentJobManager.getTicketJobHelper();
        heartbeatJobScheduler.startRepeatedJobNow(HeartbeatJob.class, HEARTBEAT_PERIOD);
    }
}
