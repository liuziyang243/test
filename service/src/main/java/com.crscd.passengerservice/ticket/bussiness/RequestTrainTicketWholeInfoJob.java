package com.crscd.passengerservice.ticket.bussiness;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.restful.client.ApacheBasedRestHttpClient;
import com.crscd.framework.restful.client.RestHttpClient;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.ticket.serviceinterface.TicketMsgManageInterface;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cuishiqing on 2017/8/24.
 */
public class RequestTrainTicketWholeInfoJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(RequestTrainTicketWholeInfoJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("RequestTrainTicketWholeInfoJob start at " + DateTimeUtil.getCurrentDatetimeString());
        RestHttpClient client = new ApacheBasedRestHttpClient();
        String url = ConfigHelper.getString("TicketWholeInfoURL");
        String wholeInfoString = client.getContent(url);
        TicketMsgManageInterface tmi = ContextHelper.getTicketMsgManager();
        tmi.dealWithTrainTicketMsgWhole(wholeInfoString);
    }
}
