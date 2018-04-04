package com.crscd.passengerservice.broadcast.business;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;
import com.crscd.passengerservice.plan.business.PassengerStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.BroadcastKey;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.plan.domainobject.PassengerStationPlan;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 15:08
 */

// 专门处理车次广播
public class TrainManualBroadcastManager {
    private BroadcastPlanExecuteManager executeManager;
    private PassengerStationPlanManager passengerPlanManager;

    public void setExecuteManager(BroadcastPlanExecuteManager executeManager) {
        this.executeManager = executeManager;
    }

    public void setPassengerPlanManager(PassengerStationPlanManager passengerPlanManager) {
        this.passengerPlanManager = passengerPlanManager;
    }

    public ResultMessage makeTrainManualBroadcast(String station, String trainNum, String contentName, String localContent, String engContent, List<String> broadcastArea, int priorityLevel) {
        String planDate = DateTimeUtil.getCurrentDateString();
        KeyBase keyBase = new KeyBase(trainNum, planDate, station);
        PassengerStationPlan plan = passengerPlanManager.getPlan(keyBase.toString());
        Map<NormalSubstitutePropertyEnum, Object> propertyMap = getPropertyListFromPlan(plan);
        String planKey = new BroadcastKey(trainNum, planDate, station, BroadcastKindEnum.TRAIN_MANUAL).toString();
        return executeManager.makeSingleBroadcastByDriver(planKey, contentName, localContent, engContent, broadcastArea, priorityLevel, propertyMap);
    }

    private Map<NormalSubstitutePropertyEnum, Object> getPropertyListFromPlan(PassengerStationPlan plan) {
        Map<NormalSubstitutePropertyEnum, Object> propertyEnumObjectMap = new HashMap<>();
        for (NormalSubstitutePropertyEnum propertyEnum : NormalSubstitutePropertyEnum.values()) {
            switch (propertyEnum) {
                case PLATFORM:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.PLATFORM, plan.getDockingPlatform());
                    break;
                case EXIT_PORT:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.EXIT_PORT, plan.getStationExitPort());
                    break;
                case TRAIN_NUM:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.TRAIN_NUM, plan.getTrainNum());
                    break;
                case WAIT_ZONE:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.WAIT_ZONE, plan.getWaitZone());
                    break;
                case ENTRANCE_PORT:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ENTRANCE_PORT, plan.getStationEntrancePort());
                    break;
                case FINAL_STATION:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.FINAL_STATION, plan.getFinalStation().getStationName());
                    break;
                case START_STATION:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.START_STATION, plan.getStartStation().getStationName());
                    break;
                case EXIT_CHECK_GATE:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.EXIT_CHECK_GATE, plan.getExitCheckGate());
                    break;
                case STOP_CHECK_TIME:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.STOP_CHECK_TIME, DateTimeFormatterUtil.convertDatetimeToString(plan.getStopAboardCheckTime()));
                    break;
                case ACTUAL_TRACK_NUM:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ACTUAL_TRACK_NUM, plan.getActualTrackNum());
                    break;
                case START_CHECK_TIME:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.START_CHECK_TIME, DateTimeFormatterUtil.convertDatetimeToString(plan.getStartAboardCheckTime()));
                    break;
                case ABOARD_CHECK_GATE:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ABOARD_CHECK_GATE, plan.getAboardCheckGate());
                    break;
                case ACTUAL_ARRIVE_TIME:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ACTUAL_ARRIVE_TIME, DateTimeFormatterUtil.convertDatetimeToString(plan.getActualArriveTime()));
                    break;
                case ACTUAL_DEPARTURE_TIME:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ACTUAL_DEPARTURE_TIME, DateTimeFormatterUtil.convertDatetimeToString(plan.getActualDepartureTime()));
                    break;
                default:
                    throw new IllegalArgumentException("Wrong arg for broadcast plan property! " + propertyEnum);
            }
        }
        return propertyEnumObjectMap;
    }
}
