package com.crscd.passengerservice.display.business;

import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.context.ContextHelper;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 11:30
 */
// 显示任务是每个车站启动自己的刷新程序，因此允许并发
public class MakeDisplayJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(MakeDisplayJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        try {
            JobDetail jobDetail = context.getJobDetail();
            JobDataMap dataMap = jobDetail.getJobDataMap();
            String stationName = dataMap.getString(ServiceConstant.STATION_NAME);
            MakeGuidePlanOnScreen worker = ContextHelper.getGuidePlanOnScreenRefresher();
            // 刷新一个车站的全部屏幕信息
            worker.makeScreenDataRefreshForStation(stationName);
            logger.debug("[Display] Make display data refresh on all screen for station " + stationName + " at " + DateTimeUtil.getCurrentDatetimeString());
        } catch (Exception e) {
            logger.error("[MakeDisplayJob]" + e.toString());
        }
    }
}
