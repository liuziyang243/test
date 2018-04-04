package com.crscd.passengerservice.broadcast.serviceinterface;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.broadcast.business.BroadcastPlanExecuteManager;
import com.crscd.passengerservice.broadcast.business.TrainManualBroadcastManager;
import com.crscd.passengerservice.plan.business.PassengerStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.PassengerStationPlan;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/16
 */
public class BroadcastPlanExecuteInterfaceImpl implements BroadcastPlanExecuteInterface {
    private BroadcastPlanExecuteManager planExecuteManager;
    private TrainManualBroadcastManager broadcastManager;
    private PassengerStationPlanManager stationPlanManager;

    public void setStationPlanManager(PassengerStationPlanManager stationPlanManager) {
        this.stationPlanManager = stationPlanManager;
    }

    public void setPlanExecuteManager(BroadcastPlanExecuteManager planExecuteManager) {
        this.planExecuteManager = planExecuteManager;
    }

    public void setBroadcastManager(TrainManualBroadcastManager broadcastManager) {
        this.broadcastManager = broadcastManager;
    }

    @Override
    public ResultMessage makeManualBroadcast(String planKey) {
        return planExecuteManager.makeSingleBroadcastByDriver(planKey);
    }

    @Override
    public HashMap<String, ResultMessage> stopBroadcastPlan(String planKey) {
        return planExecuteManager.stopBroadcastPlanByDriver(planKey);
    }

    @Override
    public ResultMessage stopSingleBroadcast(String recordKey) {
        return planExecuteManager.stopSingleBroadcastByDriver(recordKey);
    }

    @Override
    public ResultMessage makeTrainManualBroadcast(String stationName, String trainNum, String contentName, String localContent, String EngContent, List<String> broadcastArea, int priorityLevel) {
        return broadcastManager.makeTrainManualBroadcast(stationName, trainNum, contentName, localContent, EngContent, broadcastArea, priorityLevel);
    }

    @Override
    public List<Integer> getPriorityList(String stationName) {
        String list = ConfigHelper.getString("travelService.PriorityList");
        String[] strList = list.split(",");
        List<Integer> result = new ArrayList<>();
        for (String str : strList) {
            result.add(CastUtil.castInt(str));
        }
        return result;
    }

    @Override
    // 通过客运计划获取当日车次
    // 主要用于车次广播
    public List<String> getTrainNumList(String stationName) {
        String date = DateTimeUtil.getCurrentDateString();
        List<PassengerStationPlan> planList = stationPlanManager.getPlanList(stationName, null, date, date, false);
        List<String> trainNumList = new ArrayList<>();
        for (PassengerStationPlan plan : planList) {
            if (!trainNumList.contains(plan.getTrainNum())) {
                trainNumList.add(plan.getTrainNum());
            }
        }
        return trainNumList;
    }
}
