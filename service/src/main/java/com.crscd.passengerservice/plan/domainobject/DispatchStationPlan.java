package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.po.DispatchStationPlanBean;

/**
 * Created by liuziyang
 * Create date: 2017/8/15
 */
public class DispatchStationPlan extends BaseTrainPlan {

    public DispatchStationPlan(BasicPlan plan, String date, String stationName, ConfigManager manager) {
        super(plan, date, stationName, manager);
    }

    // copy info from DO
    public DispatchStationPlan(DispatchStationPlanBean bean, ConfigManager manager) {
        super(manager);
        this.id = bean.getId();
        this.setGenerateTimestamp(bean.getGenerateTimestamp());
        this.planKey = bean.getPlanKey();
        this.trainNum = bean.getTrainNum();
        this.uniqueTrainNum = trainNum;
        this.planDate = DateTimeFormatterUtil.convertStringToDate(bean.getPlanDate());
        this.trainType = bean.getTrainType();
        this.stationType = bean.getStationType();
        this.trainDirection = bean.getTrainDirection();
        this.startStation = manager.getStationInfoByStationName(bean.getStartStation());
        this.finalStation = manager.getStationInfoByStationName(bean.getFinalStation());
        this.presentStation = manager.getStationInfoByStationName(bean.getPresentStation());
        this.validPeriodStart = DateTimeFormatterUtil.convertStringToDate(bean.getValidPeriodStart());
        this.validPeriodEnd = DateTimeFormatterUtil.convertStringToDate(bean.getValidPeriodEnd());
        this.trainSuspendStart = DateTimeFormatterUtil.convertStringToDate(bean.getTrainSuspendStart());
        this.trainSuspendEnd = DateTimeFormatterUtil.convertStringToDate(bean.getTrainSuspendEnd());
        this.planedTrackNum = bean.getPlanedTrackNum();
        this.actualTrackNum = bean.getActualTrackNum();
        this.planedDepartureTime = DateTimeFormatterUtil.convertStringToDatetime(bean.getPlanedDepartureTime());
        this.actualDepartureTime = DateTimeFormatterUtil.convertStringToDatetime(bean.getActualDepartureTime());
        this.departureStateReason = bean.getDepartureStateReason();
        this.planedArriveTime = DateTimeFormatterUtil.convertStringToDatetime(bean.getPlanedArriveTime());
        this.actualArriveTime = DateTimeFormatterUtil.convertStringToDatetime(bean.getActualArriveTime());
        this.arriveStateReason = bean.getArriveStateReason();
        this.lastEditedTimestamp = bean.getLastEditedTimestamp();
        this.manualSuspendFlag = convertFlagToBool(bean.getManualSuspendFlag());
    }
}
