package com.crscd.passengerservice.plan.dto;

import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.plan.enumtype.*;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:36
 */
public class BroadcastStationPlanDTO {
    /**
     * plan key
     */
    private String planKey;
    /*************   车次信息   ****************/
    /**
     * 列车车次号
     */
    private String trainNum;
    /**
     * 列车车次号，暂时备用
     */
    private String uniqueTrainNum;
    /**
     * 列车类型
     */
    private TrainTypeEnum trainType;
    /**
     * 计划日期
     */
    private String planDate;
    /**
     * 列车在当前车站是始发、通过还是终到
     */
    private StationTypeEnum stationType;
    /**
     * 列车开行方向
     */
    private TrainDirectionEnum trainDirection;
    /**
     * 始发站
     */
    private String startStation;
    /**
     * 终到站
     */
    private String finalStation;

    /*************** 有效期及停开信息 ******************/
    /**
     * 有效期起止
     */
    private String validPeriodStart;
    private String validPeriodEnd;
    private boolean validFlag; //自动计算
    /**
     * 停开起止
     */
    private boolean manualSuspendFlag;

    /**************** 列车运行计划信息 *****************/
    /**
     * 计划股道,来自列车时刻表
     */
    private int planedTrackNum;
    /**
     * 计划股道,来自界面修改
     */
    private int actualTrackNum;
    /**
     * 停靠站台
     */
    private String dockingPlatform; //自动计算

    /**
     * 列车在当前车站的计划出发时间
     */
    private String planedDepartureTime;
    /**
     * 列车在当前车站的实际出发时间
     */
    private String actualDepartureTime;
    /**
     * 列车实际出发时间状态
     */
    private TrainTimeStateEnum departureTimeState; //自动计算
    /**
     * 列车实际出发时间早点或晚点原因
     */
    private LateEarlyReasonEnum departureStateReason;

    /**
     * 列车在当前车站的计划到达时间
     */
    private String planedArriveTime;
    /**
     * 列车在当前车站的实际到达时间
     */
    private String actualArriveTime;
    /**
     * 列车实际到达时间状态
     */
    private TrainTimeStateEnum arriveTimeState; //自动计算
    /**
     * 列车实际到达时间早点或晚点原因
     */
    private LateEarlyReasonEnum arriveStateReason;

    /**************** 记录是否修改 *****************/
    private boolean PlanEditedState; // 计划是否被修改过,自动计算
    private boolean overTimeFlag; //判断计划是否已经失效

    /**************** 检票信息 *****************/
    /**
     * 手动检票时间设置，允许直接修改
     */
    private String startAboardCheckTime;

    /**
     * 手动检票时间设置，允许直接修改
     */
    private String stopAboardCheckTime;

    /**
     * 当前是否处于检票状态
     * 属性自动计算
     */
    private CheckStateEnum checkState;

    /**************** 广播业务内容 **********************/
    // 广播执行时间，可以手动设置
    private String broadcastTime;

    // 作业内容->广播内容模版名称
    private String broadcastContentName;
    // 作业模式
    private BroadcastModeEnum broadcastOperationMode;
    // 优先级
    private int broadcastPriorityLevel;
    // 广播区列表<-根据广播业务模版生成
    private List<String> broadcastArea;

    private String broadcastContentInEng;

    private String broadcastContentInLocalLan;

    // 广播计划执行状态
    private BroadcastStateEnum broadcastState;

    public BroadcastStationPlanDTO() {
    }

    public boolean isOverTimeFlag() {
        return overTimeFlag;
    }

    public void setOverTimeFlag(boolean overTimeFlag) {
        this.overTimeFlag = overTimeFlag;
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

    public boolean isManualSuspendFlag() {
        return manualSuspendFlag;
    }

    public void setManualSuspendFlag(boolean manualSuspendFlag) {
        this.manualSuspendFlag = manualSuspendFlag;
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

    public boolean isPlanEditedState() {
        return PlanEditedState;
    }

    public void setPlanEditedState(boolean planEditedState) {
        PlanEditedState = planEditedState;
    }

    public String getStartAboardCheckTime() {
        return startAboardCheckTime;
    }

    public void setStartAboardCheckTime(String startAboardCheckTime) {
        this.startAboardCheckTime = startAboardCheckTime;
    }

    public String getStopAboardCheckTime() {
        return stopAboardCheckTime;
    }

    public void setStopAboardCheckTime(String stopAboardCheckTime) {
        this.stopAboardCheckTime = stopAboardCheckTime;
    }

    public CheckStateEnum getCheckState() {
        return checkState;
    }

    public void setCheckState(CheckStateEnum checkState) {
        this.checkState = checkState;
    }

    public String getBroadcastTime() {
        return broadcastTime;
    }

    public void setBroadcastTime(String broadcastTime) {
        this.broadcastTime = broadcastTime;
    }

    public String getBroadcastContentName() {
        return broadcastContentName;
    }

    public void setBroadcastContentName(String broadcastContentName) {
        this.broadcastContentName = broadcastContentName;
    }

    public BroadcastModeEnum getBroadcastOperationMode() {
        return broadcastOperationMode;
    }

    public void setBroadcastOperationMode(BroadcastModeEnum broadcastOperationMode) {
        this.broadcastOperationMode = broadcastOperationMode;
    }

    public int getBroadcastPriorityLevel() {
        return broadcastPriorityLevel;
    }

    public void setBroadcastPriorityLevel(int broadcastPriorityLevel) {
        this.broadcastPriorityLevel = broadcastPriorityLevel;
    }

    public List<String> getBroadcastArea() {
        return broadcastArea;
    }

    public void setBroadcastArea(List<String> broadcastArea) {
        this.broadcastArea = broadcastArea;
    }

    public BroadcastStateEnum getBroadcastState() {
        return broadcastState;
    }

    public void setBroadcastState(BroadcastStateEnum broadcastState) {
        this.broadcastState = broadcastState;
    }

    public String getBroadcastContentInLocalLan() {
        return broadcastContentInLocalLan;
    }

    public void setBroadcastContentInLocalLan(String broadcastContentInLocalLan) {
        this.broadcastContentInLocalLan = broadcastContentInLocalLan;
    }

    public String getBroadcastContentInEng() {
        return broadcastContentInEng;
    }

    public void setBroadcastContentInEng(String broadcastContentInEng) {
        this.broadcastContentInEng = broadcastContentInEng;
    }

    @Override
    public String toString() {
        return "BroadcastStationPlanDTO{" +
                "planKey='" + planKey + '\'' +
                ", trainNum='" + trainNum + '\'' +
                ", uniqueTrainNum='" + uniqueTrainNum + '\'' +
                ", trainType=" + trainType +
                ", planDate='" + planDate + '\'' +
                ", stationType=" + stationType +
                ", trainDirection=" + trainDirection +
                ", startStation='" + startStation + '\'' +
                ", finalStation='" + finalStation + '\'' +
                ", validPeriodStart='" + validPeriodStart + '\'' +
                ", validPeriodEnd='" + validPeriodEnd + '\'' +
                ", validFlag=" + validFlag +
                ", manualSuspendFlag=" + manualSuspendFlag +
                ", planedTrackNum=" + planedTrackNum +
                ", actualTrackNum=" + actualTrackNum +
                ", dockingPlatform='" + dockingPlatform + '\'' +
                ", planedDepartureTime='" + planedDepartureTime + '\'' +
                ", actualDepartureTime='" + actualDepartureTime + '\'' +
                ", departureTimeState=" + departureTimeState +
                ", departureStateReason=" + departureStateReason +
                ", planedArriveTime='" + planedArriveTime + '\'' +
                ", actualArriveTime='" + actualArriveTime + '\'' +
                ", arriveTimeState=" + arriveTimeState +
                ", arriveStateReason=" + arriveStateReason +
                ", PlanEditedState=" + PlanEditedState +
                ", overTimeFlag=" + overTimeFlag +
                ", startAboardCheckTime='" + startAboardCheckTime + '\'' +
                ", stopAboardCheckTime='" + stopAboardCheckTime + '\'' +
                ", checkState=" + checkState +
                ", broadcastTime='" + broadcastTime + '\'' +
                ", broadcastContentName='" + broadcastContentName + '\'' +
                ", broadcastOperationMode=" + broadcastOperationMode +
                ", broadcastPriorityLevel=" + broadcastPriorityLevel +
                ", broadcastArea=" + broadcastArea +
                ", broadcastContentInEng='" + broadcastContentInEng + '\'' +
                ", broadcastContentInLocalLan='" + broadcastContentInLocalLan + '\'' +
                ", broadcastState=" + broadcastState +
                '}';
    }
}
