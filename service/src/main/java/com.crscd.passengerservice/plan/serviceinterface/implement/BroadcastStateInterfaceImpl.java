package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.record.business.BroadcastRecordManager;
import com.crscd.passengerservice.broadcast.record.domainobject.BroadcastRecordKey;
import com.crscd.passengerservice.plan.business.BroadcastStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.serviceinterface.BroadcastStateInterface;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 12:51
 */
public class BroadcastStateInterfaceImpl implements BroadcastStateInterface {

    private BroadcastStationPlanManager manager;
    private BroadcastRecordManager recordManager;

    public void setManager(BroadcastStationPlanManager manager) {
        this.manager = manager;
    }

    public void setRecordManager(BroadcastRecordManager recordManager) {
        this.recordManager = recordManager;
    }

    @Override
    public void setBroadcastPlanExecuteState(String recordKey, BroadcastStateEnum state) {
        String planKey = new BroadcastRecordKey(recordKey).getBroadcastKey();
        manager.setPlanExecuteState(planKey, state);
        recordManager.updateBroadcastRecord(recordKey, state);
    }

    @Override
    public int getBroadcastPlanPriority(String recordKey) {
        String planKey = new BroadcastRecordKey(recordKey).getBroadcastKey();
        BroadcastStationPlan plan = manager.getPlan(planKey);
        return plan.getBroadcastPriorityLevel();
    }
}
