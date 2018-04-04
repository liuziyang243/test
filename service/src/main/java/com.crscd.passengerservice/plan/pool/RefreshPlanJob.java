package com.crscd.passengerservice.plan.pool;

import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.context.ContextHelper;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * refresh job for loading and clear data in pool
 *
 * @author lzy
 * Date: 2017/10/17
 * Time: 8:54
 */

@DisallowConcurrentExecution
public class RefreshPlanJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(RefreshPlanJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        DispatchPlanDataPool dispatchPlanDataPool = ContextHelper.getDispatchPlanPool();
        dispatchPlanDataPool.refreshData();
        PassengerPlanDataPool passengerPlanDataPool = ContextHelper.getPassengerPlanPool();
        passengerPlanDataPool.refreshData();
        GuidePlanDataPool guidePlanDataPool = ContextHelper.getGuidePlanPool();
        guidePlanDataPool.refreshData();
        BroadcastPlanDataPool broadcastPlanDataPool = ContextHelper.getBroadcastPlanPool();
        broadcastPlanDataPool.refreshData();
        logger.warn("Make data pool refresh at " + DateTimeUtil.getCurrentDatetimeString());
    }
}
