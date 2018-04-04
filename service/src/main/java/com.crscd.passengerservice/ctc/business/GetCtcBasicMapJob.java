package com.crscd.passengerservice.ctc.business;

import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.context.ContextHelper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/9/7.
 */
public class GetCtcBasicMapJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(GetCtcBasicMapJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("GetCtcBasicMapJob start at " + DateTimeUtil.getCurrentDatetimeString());
        CtcMsgManager ctcMsgManager = ContextHelper.getCTCMsgManager();
        ctcMsgManager.getCtcBasicMap();
    }


}
