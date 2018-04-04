package com.crscd.passengerservice.plan.business;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.notice.domainobject.NoticeMessage;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.plan.domainobject.DispatchStationPlan;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.plan.enumtype.DispatchPlanModifyEnum;
import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;
import com.crscd.passengerservice.plan.pool.DispatchPlanDataPool;

import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 9:12
 */
public class DispatchStationPlanManager extends AbstractPlanManager<DispatchStationPlan, DispatchPlanDataPool> {

    public DispatchStationPlanManager() {
    }

    public boolean updatePlan(String planKey, Map<DispatchPlanModifyEnum, String> modifyList, LateEarlyReasonEnum modifyArriveReason, LateEarlyReasonEnum modifyDepartureReason) {
        DispatchStationPlan plan = getPlanDataPool().getPlan(planKey);
        for (Map.Entry<DispatchPlanModifyEnum, String> entry : modifyList.entrySet()) {
            switch (entry.getKey()) {
                case ACTUAL_TRACK_NUM:
                    plan.setActualTrackNum(CastUtil.castInt(entry.getValue()));
                    break;
                case MANUAL_SUSPEND:
                    plan.setManualSuspendFlag(CastUtil.castBoolean(entry.getValue()));
                    break;
                case ACTUAL_ARRIVE_TIME:
                    plan.setActualArriveTime(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()));
                    if (null == modifyArriveReason) {
                        plan.setArriveStateReason(LateEarlyReasonEnum.NONE);
                    } else {
                        plan.setArriveStateReason(modifyArriveReason);
                    }
                    break;
                case ACTUAL_DEPARTURE_TIME:
                    plan.setActualDepartureTime(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()));
                    if (null == modifyDepartureReason) {
                        plan.setDepartureStateReason(LateEarlyReasonEnum.NONE);
                    } else {
                        plan.setDepartureStateReason(modifyDepartureReason);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return getPlanDataPool().updatePlan(planKey, plan);
    }

    @Override
    public boolean modifyPlan(NoticeMessage notice) {
        if (!notice.getReceiver().equals(ReceiverEnum.DISPATCH_PLAN)) {
            throw new IllegalArgumentException("Get wrong notice from system!" + notice.toString());
        }
        String planKey = new KeyBase(notice.getTrainNum(), notice.getPlanDate(), notice.getStationName()).toString();
        DispatchStationPlan plan = getPlanDataPool().getPlan(planKey);
        Map<NoticeModifyEnum, String> modifyMap = notice.getModifiedDataMap();
        for (Map.Entry<NoticeModifyEnum, String> entry : modifyMap.entrySet()) {
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
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return getPlanDataPool().updatePlan(planKey, plan);
    }

    @Override
    public boolean checkModifyValid(NoticeMessage notice) {
        if (!notice.getReceiver().equals(ReceiverEnum.DISPATCH_PLAN)) {
            throw new IllegalArgumentException("Get wrong notice from system!" + notice.toString());
        }
        String planKey = new KeyBase(notice.getTrainNum(), notice.getPlanDate(), notice.getStationName()).toString();
        DispatchStationPlan plan = getPlanDataPool().getPlan(planKey);
        Map<NoticeModifyEnum, String> modifyMap = notice.getModifiedDataMap();
        for (Map.Entry<NoticeModifyEnum, String> entry : modifyMap.entrySet()) {
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
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return false;
    }
}
