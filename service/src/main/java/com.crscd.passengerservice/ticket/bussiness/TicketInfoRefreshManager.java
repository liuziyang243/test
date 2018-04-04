package com.crscd.passengerservice.ticket.bussiness;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.job.IndependentJobHelper;
import com.crscd.framework.job.IndependentJobManager;

/**
 * @author cuishiqing
 * Created on 2017/8/25.
 */
public class TicketInfoRefreshManager {

    private static final int TICKET_INFO_PERIOD_CHANGE = ConfigHelper.getInt("TicketInfoUpdateTime");
    private static final int TICKET_CHECK_INFO_PERIOD_CHANGE = ConfigHelper.getInt("TicketCheckInfoUpdateTime");
    private static final int PASSENGER_FLOW_INFO_PERIOD_CHANGE = ConfigHelper.getInt("PassengerFlowInfoUpdateTime");
    private static final int TICKET_INFO_PERIOD_WHOLE = ConfigHelper.getInt("TicketInfoWholeUpdateTime");
    private static final int TICKET_CHECK_INFO_PERIOD_WHOLE = ConfigHelper.getInt("TicketCheckInfoWholeUpdateTime");
    private static final int PASSENGER_FLOW_INFO_PERIOD_WHOLE = ConfigHelper.getInt("PassengerFlowInfoWholeUpdateTime");

    public TicketInfoRefreshManager() {
    }

    public void startTicketRefreshJobs() {
        // 定义一个调度器
        IndependentJobHelper refreshDataJobScheduler = IndependentJobManager.getTicketJobHelper();
        //余票变更信息更新Job
        refreshDataJobScheduler.startRepeatedJobNow(RequestTrainTicketChangeInfoJob.class, TICKET_INFO_PERIOD_CHANGE);
        //余票完整信息更新Job
        refreshDataJobScheduler.startRepeatedJobNow(RequestTrainTicketWholeInfoJob.class, TICKET_INFO_PERIOD_WHOLE);
        //检票变更信息更新Job
        refreshDataJobScheduler.startRepeatedJobNow(RequestTicketCheckChangeInfoJob.class, TICKET_CHECK_INFO_PERIOD_CHANGE);
        //检票完整信息更新Job
        refreshDataJobScheduler.startRepeatedJobNow(RequestTicketCheckWholeInfoJob.class, TICKET_CHECK_INFO_PERIOD_WHOLE);
        //客流量变更信息更新Job
        refreshDataJobScheduler.startRepeatedJobNow(RequestPassengerFlowChangeInfoJob.class, PASSENGER_FLOW_INFO_PERIOD_CHANGE);
        //客流量完整信息更新Job
        refreshDataJobScheduler.startRepeatedJobNow(RequestPassengerFlowWholeInfoJob.class, PASSENGER_FLOW_INFO_PERIOD_WHOLE);

    }

}
