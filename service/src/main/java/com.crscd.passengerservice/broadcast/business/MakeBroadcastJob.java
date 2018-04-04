package com.crscd.passengerservice.broadcast.business;

import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.plan.domainobject.BroadcastKey;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 13:00
 */
// 广播计划是全局调用，因此允许并发发生
public class MakeBroadcastJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(MakeBroadcastJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        try {
            JobDetail jobDetail = context.getJobDetail();
            JobDataMap dataMap = jobDetail.getJobDataMap();
            String planKey = dataMap.getString(ServiceConstant.KEY);
            // 调用进行自动广播的函数
            BroadcastPlanExecuteManager manager = ContextHelper.getBroadcastPlanExecuteManager();
            manager.makeAutoBroadcastForSinglePlan(planKey);

            BroadcastKey key = new BroadcastKey(planKey);
            // 打印信息
            String debugInfo = "Execute broadcast job [" +
                    "StationName:" + key.getStationName() + "-planKey:" +
                    planKey + "] at" +
                    DateTimeUtil.getCurrentDatetimeString() +
                    "\n";
            logger.debug(debugInfo);
        } catch (Exception e) {
            logger.error("执行自动广播 Job 出错！", e);
        }
    }
}
