package com.crscd.passengerservice.plan.po;

import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:32
 */
public class DispatchStationPlanBean {
    /**
     * plan id
     */
    @OrmIgnore
    private long id;
    /** Generated time record */
    private String generateTimestamp;
    /** plan key */
    private String planKey;

    /*************   车次信息   ****************/
    /** 列车车次号 */
    private String trainNum;
    /** 列车车次号，暂时备用 */
    private String uniqueTrainNum;
    /** 列车类型 */
    private TrainTypeEnum trainType;
    /** 计划日期 */
    private String planDate;
    /** 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /** 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /** 始发站 */
    private String startStation;
    /** 终到站 */
    private String finalStation;
    /** 当前站 */
    private String presentStation;

    /*************** 有效期及停开信息 ******************/
    /** 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;

    /** 停开起止 */
    private String trainSuspendStart;
    private String trainSuspendEnd;
    private int manualSuspendFlag;

    /**************** 列车运行计划信息 *****************/
    /** 计划股道,来自列车时刻表 */
    private int planedTrackNum;
    /** 实际股道,来自界面修改 */
    private int actualTrackNum;

    /** 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /** 列车在当前车站的实际出发时间 */
    private String actualDepartureTime;
    /** 列车实际出发时间早点或晚点原因 */
    private LateEarlyReasonEnum departureStateReason;

    /** 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /** 列车在当前车站的实际到达时间 */
    private String actualArriveTime;
    /** 列车实际到达时间早点或晚点原因 */
    private LateEarlyReasonEnum arriveStateReason;

    /****************** 计划信息 **********************/
    //自动计算
    private String lastEditedTimestamp;

    public DispatchStationPlanBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenerateTimestamp() {
        return generateTimestamp;
    }

    public void setGenerateTimestamp(String generateTimestamp) {
        this.generateTimestamp = generateTimestamp;
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

    public String getPresentStation() {
        return presentStation;
    }

    public void setPresentStation(String presentStation) {
        this.presentStation = presentStation;
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

    public String getTrainSuspendStart() {
        return trainSuspendStart;
    }

    public void setTrainSuspendStart(String trainSuspendStart) {
        this.trainSuspendStart = trainSuspendStart;
    }

    public String getTrainSuspendEnd() {
        return trainSuspendEnd;
    }

    public void setTrainSuspendEnd(String trainSuspendEnd) {
        this.trainSuspendEnd = trainSuspendEnd;
    }

    public int getManualSuspendFlag() {
        return manualSuspendFlag;
    }

    public void setManualSuspendFlag(int manualSuspendFlag) {
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

    public LateEarlyReasonEnum getArriveStateReason() {
        return arriveStateReason;
    }

    public void setArriveStateReason(LateEarlyReasonEnum arriveStateReason) {
        this.arriveStateReason = arriveStateReason;
    }

    public String getLastEditedTimestamp() {
        return lastEditedTimestamp;
    }

    public void setLastEditedTimestamp(String lastEditedTimestamp) {
        this.lastEditedTimestamp = lastEditedTimestamp;
    }
}
