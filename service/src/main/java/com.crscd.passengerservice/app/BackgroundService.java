package com.crscd.passengerservice.app;

import com.crscd.passengerservice.ctc.business.CtcMsgRefreshManager;
import com.crscd.passengerservice.display.business.MakeGuidePlanOnScreenManager;
import com.crscd.passengerservice.display.screencontrolserver.business.ScreenCtrlServerHeartbeatManager;
import com.crscd.passengerservice.plan.business.BroadcastStationPlanManager;
import com.crscd.passengerservice.plan.business.RefreshPlanDataPoolManager;
import com.crscd.passengerservice.ticket.bussiness.TicketInfoRefreshManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lzy
 * @author lzy
 * Date: 2017/9/13
 * Time: 18:03
 */
public class BackgroundService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundService.class);
    private MakeGuidePlanOnScreenManager guidePlanOnScreenManager;
    private TicketInfoRefreshManager ticketInfoRefreshManager;
    private BroadcastStationPlanManager broadcastStationPlanManager;
    private CtcMsgRefreshManager ctcMsgRefreshManager;
    private RefreshPlanDataPoolManager refreshPlanDataPoolManager;
    private ScreenCtrlServerHeartbeatManager screenCtrlServerHeartbeatManager;

    public void setScreenCtrlServerHeartbeatManager(ScreenCtrlServerHeartbeatManager screenCtrlServerHeartbeatManager) {
        this.screenCtrlServerHeartbeatManager = screenCtrlServerHeartbeatManager;
    }

    public void setBroadcastStationPlanManager(BroadcastStationPlanManager broadcastStationPlanManager) {
        this.broadcastStationPlanManager = broadcastStationPlanManager;
    }

    public void setGuidePlanOnScreenManager(MakeGuidePlanOnScreenManager guidePlanOnScreenManager) {
        this.guidePlanOnScreenManager = guidePlanOnScreenManager;
    }

    public void setTicketInfoRefreshManager(TicketInfoRefreshManager ticketInfoRefreshManager) {
        this.ticketInfoRefreshManager = ticketInfoRefreshManager;
    }

    public void setCtcMsgRefreshManager(CtcMsgRefreshManager ctcMsgRefreshManager) {
        this.ctcMsgRefreshManager = ctcMsgRefreshManager;
    }

    public void setRefreshPlanDataPoolManager(RefreshPlanDataPoolManager refreshPlanDataPoolManager) {
        this.refreshPlanDataPoolManager = refreshPlanDataPoolManager;
    }

    public void init() {
        refreshPlanDataPoolManager.startRefreshPlanDataPool();
        LOGGER.info("Start plan data pool refreshing.");
        broadcastStationPlanManager.loadAllBroadcastPlansToScheduler();
        LOGGER.info("Load all broadcast plan to scheduler.");
        guidePlanOnScreenManager.startRefreshGuidePlanOnScreen();
        LOGGER.info("Start guide plan info refresh onto screen.");
//        ticketInfoRefreshManager.startTicketRefreshJobs();
//        LOGGER.info("Start ticket info refresh.");
//        ctcMsgRefreshManager.StartCtcMsgJobs();
//        LOGGER.info("Start ctc info refresh.");
//        screenCtrlServerHeartbeatManager.startScreenCtrlServerHeartbeatJobs();
//        LOGGER.info("Start screen server heart beat refresh.");
    }


}
