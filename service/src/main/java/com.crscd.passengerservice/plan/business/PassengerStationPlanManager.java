package com.crscd.passengerservice.plan.business;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.notice.domainobject.NoticeMessage;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.plan.domainobject.PassengerStationPlan;
import com.crscd.passengerservice.plan.enumtype.PassengerPlanModifyEnum;
import com.crscd.passengerservice.plan.pool.PassengerPlanDataPool;

import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 9:13
 */
public class PassengerStationPlanManager extends AbstractPlanManager<PassengerStationPlan, PassengerPlanDataPool> {

    public PassengerStationPlanManager() {
    }

    public boolean updatePlan(String planKey, Map<PassengerPlanModifyEnum, String> modifyList) {
        PassengerStationPlan plan = getPlanDataPool().getPlan(planKey);
        for (Map.Entry<PassengerPlanModifyEnum, String> entry : modifyList.entrySet()) {
            switch (entry.getKey()) {
                case START_CHECK_TIME:
                    plan.setStartAboardCheckTime(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()));
                    break;
                case STOP_CHECK_TIME:
                    plan.setStopAboardCheckTime(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()));
                    break;
                case WAIT_ZONE:
                    plan.setWaitZone(getZoneListFromJson(entry.getValue()));
                    break;
                case ENTRANCE_PORT:
                    plan.setStationEntrancePort(getZoneListFromJson(entry.getValue()));
                    break;
                case EXIT_PORT:
                    plan.setStationExitPort(getZoneListFromJson(entry.getValue()));
                    break;
                case ABOARD_CHECK_GATE:
                    plan.setAboardCheckGate(getZoneListFromJson(entry.getValue()));
                    break;
                case EXIT_CHECK_GATE:
                    plan.setExitCheckGate(getZoneListFromJson(entry.getValue()));
                    break;
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return getPlanDataPool().updatePlan(planKey, plan);
    }

    @Override
    public boolean modifyPlan(NoticeMessage notice) {
        if (!notice.getReceiver().equals(ReceiverEnum.PASSENGER_PLAN)) {
            throw new IllegalArgumentException("Get wrong notice from system!" + notice.toString());
        }
        String planKey = new KeyBase(notice.getTrainNum(), notice.getPlanDate(), notice.getStationName()).toString();
        PassengerStationPlan plan = getPlanDataPool().getPlan(planKey);
        Map<NoticeModifyEnum, String> modfiyMap = notice.getModifiedDataMap();
        for (Map.Entry<NoticeModifyEnum, String> entry : modfiyMap.entrySet()) {
            switch (entry.getKey()) {
                case ACTUAL_TRACK_NUM:
                    plan.setActualTrackNum(CastUtil.castInt(entry.getValue()));
                    break;
                case ACTUAL_ARRIVE_TIME:
                    plan.setActualArriveTime(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()));
                    plan.setArriveStateReason(getReason(notice.getSender()));
                    break;
                case ACTUAL_DEPARTURE_TIME:
                    plan.setActualDepartureTime(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()));
                    plan.setDepartureStateReason(getReason(notice.getSender()));
                    break;
                case MANUAL_SUSPEND:
                    plan.setManualSuspendFlag(CastUtil.castBoolean(entry.getValue()));
                    break;
                case START_CHECK_TIME:
                    plan.setStartAboardCheckTime(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()));
                    break;
                case STOP_CHECK_TIME:
                    plan.setStopAboardCheckTime(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()));
                    break;
                case WAIT_ZONE:
                    plan.setWaitZone(getZoneListFromJson(entry.getValue()));
                    break;
                case ENTRANCE_PORT:
                    plan.setStationEntrancePort(getZoneListFromJson(entry.getValue()));
                    break;
                case EXIT_PORT:
                    plan.setStationExitPort(getZoneListFromJson(entry.getValue()));
                    break;
                case ABOARD_CHECK_PORT:
                    plan.setAboardCheckGate(getZoneListFromJson(entry.getValue()));
                    break;
                case EXIT_CHECK_PORT:
                    plan.setExitCheckGate(getZoneListFromJson(entry.getValue()));
                    break;
                case CHECK_STATUS:
                    // take check status from ticket system, not to process yet
                    break;
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return getPlanDataPool().updatePlan(planKey, plan);
    }

    @Override
    public boolean checkModifyValid(NoticeMessage notice) {
        if (!notice.getReceiver().equals(ReceiverEnum.PASSENGER_PLAN)) {
            throw new IllegalArgumentException("Get wrong notice from system!" + notice.toString());
        }
        String planKey = new KeyBase(notice.getTrainNum(), notice.getPlanDate(), notice.getStationName()).toString();
        PassengerStationPlan plan = getPlanDataPool().getPlan(planKey);
        Map<NoticeModifyEnum, String> modfiyMap = notice.getModifiedDataMap();
        for (Map.Entry<NoticeModifyEnum, String> entry : modfiyMap.entrySet()) {
            switch (entry.getKey()) {
                case ACTUAL_TRACK_NUM:
                    if (CastUtil.castInt(entry.getValue()) != plan.getActualTrackNum()) {
                        return true;
                    }
                    break;
                case ACTUAL_ARRIVE_TIME:
                    if (!plan.getActualArriveTime()
                            .equals(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()))) {
                        return true;
                    }
                    break;
                case ACTUAL_DEPARTURE_TIME:
                    if (!plan.getActualDepartureTime()
                            .equals(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()))) {
                        return true;
                    }
                    break;
                case MANUAL_SUSPEND:
                    if (plan.getManualSuspendFlag() != CastUtil.castBoolean(entry.getValue())) {
                        return true;
                    }
                    break;
                case START_CHECK_TIME:
                    if (!plan.getStartAboardCheckTime().equals(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()))) {
                        return true;
                    }
                    break;
                case STOP_CHECK_TIME:
                    if (!plan.getStopAboardCheckTime().equals(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()))) {
                        return true;
                    }
                    break;
                case WAIT_ZONE:
                    if (!ListUtil.isNotEmptySameList(plan.getWaitZone(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case ENTRANCE_PORT:
                    if (!ListUtil.isNotEmptySameList(plan.getStationEntrancePort(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case EXIT_PORT:
                    if (!ListUtil.isNotEmptySameList(plan.getStationExitPort(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case ABOARD_CHECK_PORT:
                    if (!ListUtil.isNotEmptySameList(plan.getAboardCheckGate(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case EXIT_CHECK_PORT:
                    if (!ListUtil.isNotEmptySameList(plan.getExitCheckGate(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case CHECK_STATUS:
                    // take check status from ticket system, not to process yet
                    break;
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return false;
    }
}
