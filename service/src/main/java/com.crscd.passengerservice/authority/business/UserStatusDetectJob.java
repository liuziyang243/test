package com.crscd.passengerservice.authority.business;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author zs
 * @date 2017/8/29
 */
public class UserStatusDetectJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(UserStatusDetectJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.debug("UserStatusDetectJob start at " + LocalDateTime.now());
        UserStatusManager.updateStatus();
    }

}
