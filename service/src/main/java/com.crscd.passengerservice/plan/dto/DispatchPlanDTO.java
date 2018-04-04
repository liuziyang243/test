package com.crscd.passengerservice.plan.dto;

import com.crscd.passengerservice.plan.enumtype.*;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:35
 */
public class DispatchPlanDTO {
    /* plan key */
    private String planKey;
    /*************   车次信息   ****************/
    /* 列车车次号 */
    private String trainNum;
    /* 列车车次号，暂时备用 */
    private String uniqueTrainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 计划日期 */
    private String planDate;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;

    /*************** 有效期及停开信息 ******************/
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    private boolean validFlag; //自动计算
    /* 停开起止 */
    private boolean manualSuspendFlag;

    /**************** 列车运行计划信息 *****************/
    /* 计划股道,来自列车时刻表 */
    private int planedTrackNum;
    /* 计划股道,来自界面修改 */
    private int actualTrackNum;
    /* 停靠站台 */
    private String dockingPlatform; //自动计算

    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的实际出发时间 */
    private String actualDepartureTime;
    /* 列车实际出发时间状态 */
    private TrainTimeStateEnum departureTimeState; //自动计算
    /* 列车实际出发时间早点或晚点原因 */
    private LateEarlyReasonEnum departureStateReason;

    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 列车在当前车站的实际到达时间 */
    private String actualArriveTime;
    /* 列车实际到达时间状态 */
    private TrainTimeStateEnum arriveTimeState; //自动计算
    /* 列车实际到达时间早点或晚点原因 */
    private LateEarlyReasonEnum arriveStateReason;

    /**************** 记录是否修改 *****************/
    private boolean PlanEditedState; // 计划是否被修改过,自动计算
    private boolean overTimeFlag; //判断计划是否已经失效

    public DispatchPlanDTO() {
    }

    public String getPlanKey() {
        return planKey;
    }

    public void setPlanKey(String planKey) {
        this.planKey = planKey;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getUniqueTrainNum() {
        return uniqueTrainNum;
    }

    public void setUniqueTrainNum(String uniqueTrainNum) {
        this.uniqueTrainNum = uniqueTrainNum;
    }

    public TrainTypeEnum getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainTypeEnum trainType) {
        this.trainType = trainType;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public StationTypeEnum getStationType() {
        return stationType;
    }

    public void setStationType(StationTypeEnum stationType) {
        this.stationType = stationType;
    }

    public TrainDirectionEnum getTrainDirection() {
        return trainDirection;
    }

    public void setTrainDirection(TrainDirectionEnum trainDirection) {
        this.trainDirection = trainDirection;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(String finalStation) {
        this.finalStation = finalStation;
    }

    public String getValidPeriodStart() {
        return validPeriodStart;
    }

    public void setValidPeriodStart(String validPeriodStart) {
        this.validPeriodStart = validPeriodStart;
    }

    public String getValidPeriodEnd() {
        return validPeriodEnd;
    }

    public void setValidPeriodEnd(String validPeriodEnd) {
        this.validPeriodEnd = validPeriodEnd;
    }

    public boolean isValidFlag() {
        return validFlag;
    }

    public void setValidFlag(boolean validFlag) {
        this.validFlag = validFlag;
    }

    public int getPlanedTrackNum() {
        return planedTrackNum;
    }

    public void setPlanedTrackNum(int planedTrackNum) {
        this.planedTrackNum = planedTrackNum;
    }

    public int getActualTrackNum() {
        return actualTrackNum;
    }

    public void setActualTrackNum(int actualTrackNum) {
        this.actualTrackNum = actualTrackNum;
    }

    public String getDockingPlatform() {
        return dockingPlatform;
    }

    public void setDockingPlatform(String dockingPlatform) {
        this.dockingPlatform = dockingPlatform;
    }

    public String getPlanedDepartureTime() {
        return planedDepartureTime;
    }

    public void setPlanedDepartureTime(String planedDepartureTime) {
        this.planedDepartureTime = planedDepartureTime;
    }

    public String getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(String actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public TrainTimeStateEnum getDepartureTimeState() {
        return departureTimeState;
    }

    public void setDepartureTimeState(TrainTimeStateEnum departureTimeState) {
        this.departureTimeState = departureTimeState;
    }

    public LateEarlyReasonEnum getDepartureStateReason() {
        return departureStateReason;
    }

    public void setDepartureStateReason(LateEarlyReasonEnum departureStateReason) {
        this.departureStateReason = departureStateReason;
    }

    public String getPlanedArriveTime() {
        return planedArriveTime;
    }

    public void setPlanedArriveTime(String planedArriveTime) {
        this.planedArriveTime = planedArriveTime;
    }

    public String getActualArriveTime() {
        return actualArriveTime;
    }

    public void setActualArriveTime(String actualArriveTime) {
        this.actualArriveTime = actualArriveTime;
    }

    public TrainTimeStateEnum getArriveTimeState() {
        return arriveTimeState;
    }

    public void setArriveTimeState(TrainTimeStateEnum arriveTimeState) {
        this.arriveTimeState = arriveTimeState;
    }

    public LateEarlyReasonEnum getArriveStateReason() {
        return arriveStateReason;
    }

    public void setArriveStateReason(LateEarlyReasonEnum arriveStateReason) {
        this.arriveStateReason = arriveStateReason;
    }

    public boolean getPlanEditedState() {
        return PlanEditedState;
    }

    public void setPlanEditedState(boolean planEditedState) {
        PlanEditedState = planEditedState;
    }

    public boolean getManualSuspendFlag() {
        return manualSuspendFlag;
    }

    public void setManualSuspendFlag(boolean manualSuspendFlag) {
        this.manualSuspendFlag = manualSuspendFlag;
    }

    public boolean getOverTimeFlag() {
        return overTimeFlag;
    }

    public void setOverTimeFlag(boolean overTimeFlag) {
        this.overTimeFlag = overTimeFlag;
    }
}
