package com.crscd.passengerservice.ctc.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.job.IndependentJobManager;

/**
 * Created by Administrator on 2017/9/7.
 */
public class StartCtcMsgJobs {
    private static final int GET_BASIC_MAP_PERIOD = ConfigHelper.getInt("getBasicMapPeriod");
    private static final int GET_PERIOD_PLANS_PERIOD = ConfigHelper.getInt("getPeriodPlansPeriod");
    private static final int GET_TRAIN_TIMES_PERIOD = ConfigHelper.getInt("getTrainTimesPeriod");

    public static void StartCtcMsgJobs() {
        //获取基本图信息Job
        IndependentJobManager.getCtcJobHelper().startRepeatedJobNow(GetCtcBasicMapJob.class, GET_BASIC_MAP_PERIOD);
        //获取阶段计划信息Job
        IndependentJobManager.getCtcJobHelper().startRepeatedJobNow(GetCtcPeriodPlansJob.class, GET_PERIOD_PLANS_PERIOD);
        //获取报点信息Job
        IndependentJobManager.getCtcJobHelper().startRepeatedJobNow(GetCtcTrainTimesJob.class, GET_TRAIN_TIMES_PERIOD);

    }
}
