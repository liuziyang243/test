package com.crscd.passengerservice.plan.po;

import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.plan.enumtype.*;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:33
 */
public class BroadcastStationPlanBean {
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
    /** 计划股道,来自界面修改 */
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
    private String lastEditedTimestamp; //自动计算

    /****************** 客运信息 **********************/
    /** 进站上车开检时间基准 */
    private TrainTimeBaseEnum startAboardCheckBase;
    /** 进站上车开检相对时间, 单位是分钟 */
    private int startAboardCheckTimeOffset;
    /** 手动检票时间设置，允许直接修改 */
    private String startAboardCheckTime;

    /** 进站上车停检时间基准, 单位是分钟 */
    private TrainTimeBaseEnum stopAboardCheckBase;
    /** 进站上车停检相对时间 */
    private int stopAboardCheckTimeOffset;
    /** 手动检票时间设置，允许直接修改 */
    private String stopAboardCheckTime;

    // 候车区列表
    private List<String> waitZone;
    // 车站进站口
    private List<String> stationEntrancePort;
    // 车站出站口
    private List<String> stationExitPort;
    // 乘车检票口，如果在进站口开设检票口，则检票口与进站口会重合
    private List<String> aboardCheckGate;
    // 出站检票口，根据实际情况，出站检票口可能与车站出站口指同一位置
    private List<String> exitCheckGate;

    /**************** 广播业务内容 **********************/
    // 广播属性：到发、变更或者其他， toArrive, alteration, others
    private BroadcastKindEnum broadcastKind;
    // 广播时间基准，通过广播模版获取
    private TrainTimeBaseEnum broadcastBaseTime;
    // 相对时间,单位是分钟
    private int broadcastTimeOffset;
    // 广播执行时间，可以手动设置
    private String broadcastTime;

    // 作业内容->广播内容模版名称
    private String broadcastContentName;
    // 作业模式
    private BroadcastModeEnum broadcastOperationMode;
    // 优先级
    private int broadcastPriorityLevel;
    // 广播区列表<-自动生成
    private List<String> broadcastArea;

    // 广播计划执行状态
    private BroadcastStateEnum broadcastState;

    public BroadcastStationPlanBean() {
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

    public TrainTimeBaseEnum getStartAboardCheckBase() {
        return startAboardCheckBase;
    }

    public void setStartAboardCheckBase(TrainTimeBaseEnum startAboardCheckBase) {
        this.startAboardCheckBase = startAboardCheckBase;
    }

    public int getStartAboardCheckTimeOffset() {
        return startAboardCheckTimeOffset;
    }

    public void setStartAboardCheckTimeOffset(int startAboardCheckTimeOffset) {
        this.startAboardCheckTimeOffset = startAboardCheckTimeOffset;
    }

    public String getStartAboardCheckTime() {
        return startAboardCheckTime;
    }

    public void setStartAboardCheckTime(String startAboardCheckTime) {
        this.startAboardCheckTime = startAboardCheckTime;
    }

    public TrainTimeBaseEnum getStopAboardCheckBase() {
        return stopAboardCheckBase;
    }

    public void setStopAboardCheckBase(TrainTimeBaseEnum stopAboardCheckBase) {
        this.stopAboardCheckBase = stopAboardCheckBase;
    }

    public int getStopAboardCheckTimeOffset() {
        return stopAboardCheckTimeOffset;
    }

    public void setStopAboardCheckTimeOffset(int stopAboardCheckTimeOffset) {
        this.stopAboardCheckTimeOffset = stopAboardCheckTimeOffset;
    }

    public String getStopAboardCheckTime() {
        return stopAboardCheckTime;
    }

    public void setStopAboardCheckTime(String stopAboardCheckTime) {
        this.stopAboardCheckTime = stopAboardCheckTime;
    }

    public List<String> getWaitZone() {
        return waitZone;
    }

    public void setWaitZone(List<String> waitZone) {
        this.waitZone = waitZone;
    }

    public List<String> getStationEntrancePort() {
        return stationEntrancePort;
    }

    public void setStationEntrancePort(List<String> stationEntrancePort) {
        this.stationEntrancePort = stationEntrancePort;
    }

    public List<String> getStationExitPort() {
        return stationExitPort;
    }

    public void setStationExitPort(List<String> stationExitPort) {
        this.stationExitPort = stationExitPort;
    }

    public List<String> getAboardCheckGate() {
        return aboardCheckGate;
    }

    public void setAboardCheckGate(List<String> aboardCheckGate) {
        this.aboardCheckGate = aboardCheckGate;
    }

    public List<String> getExitCheckGate() {
        return exitCheckGate;
    }

    public void setExitCheckGate(List<String> exitCheckGate) {
        this.exitCheckGate = exitCheckGate;
    }

    public BroadcastKindEnum getBroadcastKind() {
        return broadcastKind;
    }

    public void setBroadcastKind(BroadcastKindEnum broadcastKind) {
        this.broadcastKind = broadcastKind;
    }

    public TrainTimeBaseEnum getBroadcastBaseTime() {
        return broadcastBaseTime;
    }

    public void setBroadcastBaseTime(TrainTimeBaseEnum broadcastBaseTime) {
        this.broadcastBaseTime = broadcastBaseTime;
    }

    public int getBroadcastTimeOffset() {
        return broadcastTimeOffset;
    }

    public void setBroadcastTimeOffset(int broadcastTimeOffset) {
        this.broadcastTimeOffset = broadcastTimeOffset;
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
}
