package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.domainobject.StationInfo;
import com.crscd.passengerservice.plan.enumtype.*;
import com.crscd.passengerservice.plan.util.PlanHelper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 11:48
 */
public abstract class BaseTrainPlan extends BasePlan {
    private static final Logger logger = LoggerFactory.getLogger(BaseTrainPlan.class);
    /* plan key */
    protected String planKey;
    /*************   车次信息   ****************/
    /* 列车车次号 */
    protected String trainNum;
    /* 列车车次号，暂时备用 */
    protected String uniqueTrainNum;
    /* 列车类型 */
    protected TrainTypeEnum trainType;
    /* 计划日期 */
    protected LocalDate planDate;
    /* 列车在当前车站是始发、通过还是终到 */
    protected StationTypeEnum stationType;
    /* 列车开行方向 */
    protected TrainDirectionEnum trainDirection;
    /* 始发站 */
    protected StationInfo startStation;
    /* 终到站 */
    protected StationInfo finalStation;
    /* 当前站 */
    protected StationInfo presentStation;

    /*************** 有效期及停开信息 ******************/
    /* 有效期起止 备用于以后支持列车时刻表配置一段时间停开 */
    protected LocalDate validPeriodStart;
    protected LocalDate validPeriodEnd;
    /* 停开起止 */
    protected LocalDate trainSuspendStart;
    protected LocalDate trainSuspendEnd;
    protected boolean manualSuspendFlag;
    /**************** 列车运行计划信息 *****************/
    /* 计划股道,来自列车时刻表 */
    protected int planedTrackNum;
    /* 计划股道,来自界面修改 */
    protected int actualTrackNum;
    /* 停靠站台 */
    protected String dockingPlatform; //自动计算
    /* 列车在当前车站的计划出发时间 */
    protected LocalDateTime planedDepartureTime;
    /* 列车在当前车站的实际出发时间 */
    protected LocalDateTime actualDepartureTime;
    /* 列车实际出发时间状态 */
    protected TrainTimeStateEnum departureTimeState; //自动计算
    /* 列车实际出发时间早点或晚点原因 */
    protected LateEarlyReasonEnum departureStateReason;
    /* 列车在当前车站的计划到达时间 */
    protected LocalDateTime planedArriveTime;
    /* 列车在当前车站的实际到达时间 */
    protected LocalDateTime actualArriveTime;
    /* 列车实际到达时间状态 */
    protected TrainTimeStateEnum arriveTimeState; //自动计算
    /* 列车实际到达时间早点或晚点原因 */
    protected LateEarlyReasonEnum arriveStateReason;
    /**************** 记录是否修改 *****************/
    protected boolean trackNumEditedState;//计划股道是否被手动修改,自动判断
    protected boolean arriveTimeEditedState;//计划到点是否被手动修改,自动判断
    protected boolean departureTimeEditedState;//计划发点是否被手动修改,自动判断
    /*************** 计划信息 *******************/
    protected String lastEditedTimestamp; //自动计算
    protected ConfigManager manager;
    private boolean validFlag; //自动计算
    private boolean suspendFlag; //自动计算
    private boolean planEditedState; // 计划是否被修改过,自动计算
    private boolean overTimeFlag; // 判断计划是否已经过点

    // 使用前台信息的构造器
    BaseTrainPlan(BasicPlan plan, String date, String stationName, ConfigManager manager) {
        super();
        this.manager = manager;
        LocalDate planDate = DateTimeFormatterUtil.convertStringToDate(date);
        if (null == planDate) {
            throw new IllegalArgumentException("Plan date is illegal! Input date is" + date);
        }
        this.trainNum = plan.getTrainNum();
        this.uniqueTrainNum = trainNum;
        this.trainType = plan.getTrainType();
        this.planDate = DateTimeFormatterUtil.convertStringToDate(date);
        this.trainDirection = plan.getTrainDirection();
        this.startStation = plan.getStartStation();
        this.finalStation = plan.getFinalStation();
        this.presentStation = manager.getStationInfoByStationName(stationName);
        if (this.presentStation.equals(startStation)) {
            this.stationType = StationTypeEnum.START;
        } else if (this.presentStation.equals(finalStation)) {
            this.stationType = StationTypeEnum.FINAL;
        } else {
            this.stationType = StationTypeEnum.PASS;
        }
        this.planKey = new KeyBase(this).toString();

        this.validPeriodStart = plan.getValidPeriodStart();
        this.validPeriodEnd = plan.getValidPeriodEnd();
        this.trainSuspendStart = plan.getTrainSuspendStart();
        this.trainSuspendEnd = plan.getTrainSuspendEnd();

        if (this.presentStation == null) {
            logger.warn("[BaseTrainPlan] Present Station-" + stationName + " of trainNum-" + this.trainNum + " with planKey-" + this.planKey + " is null!");
        } else {
            try {
                this.planedTrackNum = plan.getSpecifiedTrainStationInfo(this.presentStation.getStationName()).getPlanedTrackNum();
                int arriveDelayDays = plan.getSpecifiedTrainStationInfo(this.presentStation.getStationName()).getArriveDelayDays();
                int departureDelayDays = plan.getSpecifiedTrainStationInfo(this.presentStation.getStationName()).getDepartureDelayDays();
                LocalTime planedAtime = plan.getSpecifiedTrainStationInfo(this.presentStation.getStationName()).getPlanedArriveTime();
                LocalTime planedDtime = plan.getSpecifiedTrainStationInfo(this.presentStation.getStationName()).getPlanedDepartureTime();
                if (hasArriveTime()) {
                    if (null != planedAtime) {
                        this.planedArriveTime = LocalDateTime.of(planDate.plusDays(arriveDelayDays), planedAtime);
                    } else {
                        logger.warn("[BaseTrainPlan] Train %s at station %s has empty arrive time!", plan.getTrainNum(), stationName);
                        this.planedArriveTime = null;
                    }
                } else {
                    this.planedArriveTime = null;
                }

                if (hasDepartureTime()) {
                    if (null != planedDtime) {
                        this.planedDepartureTime = LocalDateTime.of(planDate.plusDays(departureDelayDays), planedDtime);
                    } else {
                        logger.warn("[BaseTrainPlan] Train %s at station %s has empty departure time!", plan.getTrainNum(), stationName);
                        this.planedDepartureTime = null;
                    }
                } else {
                    this.planedDepartureTime = null;
                }
            } catch (Exception e) {
                logger.error("[BaseTrainPlan] Error:", e);
            }
        }
        this.actualTrackNum = this.planedTrackNum;
        this.actualArriveTime = this.planedArriveTime;
        this.actualDepartureTime = this.planedDepartureTime;
        this.manualSuspendFlag = false;
    }

    // 空的构造器, 通过读取po构造
    public BaseTrainPlan(ConfigManager manager) {
        this.manager = manager;
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

    public String getUniqueTrainNum() {
        return uniqueTrainNum;
    }

    public void setUniqueTrainNum(String uniqueTrainNum) {
        this.uniqueTrainNum = uniqueTrainNum;
    }

    public TrainTypeEnum getTrainType() {
        return trainType;
    }

    public LocalDate getPlanDate() {
        return planDate;
    }

    public StationTypeEnum getStationType() {
        return stationType;
    }

    public TrainDirectionEnum getTrainDirection() {
        return trainDirection;
    }

    public StationInfo getStartStation() {
        return startStation;
    }

    public StationInfo getFinalStation() {
        return finalStation;
    }

    public StationInfo getPresentStation() {
        return presentStation;
    }

    public LocalDate getValidPeriodStart() {
        return validPeriodStart;
    }

    public void setValidPeriodStart(LocalDate validPeriodStart) {
        this.validPeriodStart = validPeriodStart;
        this.lastEditedTimestamp = DateTimeUtil.getCurrentDatetimeString();
    }

    public LocalDate getValidPeriodEnd() {
        return validPeriodEnd;
    }

    public void setValidPeriodEnd(LocalDate validPeriodEnd) {
        this.validPeriodEnd = validPeriodEnd;
        this.lastEditedTimestamp = DateTimeUtil.getCurrentDatetimeString();
    }

    public boolean getValidFlag() {
        return calcPlanedDateValid(planDate, validPeriodStart, validPeriodEnd);
    }

    public LocalDate getTrainSuspendStart() {
        return trainSuspendStart;
    }

    public void setTrainSuspendStart(LocalDate trainSuspendStart) {
        this.trainSuspendStart = trainSuspendStart;
        this.lastEditedTimestamp = DateTimeUtil.getCurrentDatetimeString();
    }

    public LocalDate getTrainSuspendEnd() {
        return trainSuspendEnd;
    }

    public void setTrainSuspendEnd(LocalDate trainSuspendEnd) {
        this.trainSuspendEnd = trainSuspendEnd;
        this.lastEditedTimestamp = DateTimeUtil.getCurrentDatetimeString();
    }

    public boolean getSuspendFlag() {
        return calcPlanedDateValid(planDate, trainSuspendStart, trainSuspendEnd);
    }

    public int getPlanedTrackNum() {
        return planedTrackNum;
    }

    public int getActualTrackNum() {
        return actualTrackNum;
    }

    public void setActualTrackNum(int actualTrackNum) {
        this.actualTrackNum = actualTrackNum;
        this.lastEditedTimestamp = DateTimeUtil.getCurrentDatetimeString();
    }

    public String getDockingPlatform() {
        return manager.getPlatformByTrackNum(this.presentStation.getStationName(), this.actualTrackNum);
    }

    public LocalDateTime getPlanedDepartureTime() {
        return planedDepartureTime;
    }

    public LocalDateTime getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(LocalDateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
        this.lastEditedTimestamp = DateTimeUtil.getCurrentDatetimeString();
    }

    public TrainTimeStateEnum getDepartureTimeState() {
        return PlanHelper.isActualPlanTimeEarlyOrLate(this.planedDepartureTime, this.actualDepartureTime);
    }

    public LateEarlyReasonEnum getDepartureStateReason() {
        return departureStateReason;
    }

    public void setDepartureStateReason(LateEarlyReasonEnum departureStateReason) {
        if (!(this.getDepartureTimeState().equals(TrainTimeStateEnum.ON_TIME) || this.getDepartureTimeState().equals(TrainTimeStateEnum.INVALID))) {
            this.departureStateReason = departureStateReason;
        }
    }

    public LocalDateTime getPlanedArriveTime() {
        return planedArriveTime;
    }

    public LocalDateTime getActualArriveTime() {
        return actualArriveTime;
    }

    public void setActualArriveTime(LocalDateTime actualArriveTime) {
        this.actualArriveTime = actualArriveTime;
        this.lastEditedTimestamp = DateTimeUtil.getCurrentDatetimeString();
    }

    public TrainTimeStateEnum getArriveTimeState() {
        return PlanHelper.isActualPlanTimeEarlyOrLate(this.planedArriveTime, this.actualArriveTime);
    }

    public LateEarlyReasonEnum getArriveStateReason() {
        return arriveStateReason;
    }

    public void setArriveStateReason(LateEarlyReasonEnum arriveStateReason) {
        if (!(this.getArriveTimeState().equals(TrainTimeStateEnum.ON_TIME) || this.getArriveTimeState().equals(TrainTimeStateEnum.INVALID))) {
            this.arriveStateReason = arriveStateReason;
        }
    }

    public boolean getTrackNumEditedState() {
        return !(this.planedTrackNum == this.actualTrackNum);
    }

    public boolean getArriveTimeEditedState() {
        return !(this.planedArriveTime == this.actualArriveTime);
    }

    public boolean getDepartureTimeEditedState() {
        return !(this.planedDepartureTime == this.actualDepartureTime);
    }

    public boolean getPlanEditedState() {
        return getTrackNumEditedState() || getArriveTimeEditedState() || getDepartureTimeEditedState();
    }

    public String getLastEditedTimestamp() {
        return lastEditedTimestamp;
    }

    public boolean getManualSuspendFlag() {
        return manualSuspendFlag;
    }

    public void setManualSuspendFlag(boolean manualSuspendFlag) {
        this.manualSuspendFlag = manualSuspendFlag;
    }

    public boolean getOverTimeFlag() {
        // 如果当前时间已经在发车时间之后，则认为本条计划已经失效
        return null != getActualDepartureTime() && LocalDateTime.now().isAfter(getActualDepartureTime());
    }

    LocalDateTime calcActualCheckTime(TrainTimeBaseEnum base, int offset) {
        switch (base) {
            case ARRIVE_TIME:
                return this.getActualArriveTime().plusMinutes(offset);
            case DEPARTURE_TIME:
                return this.getActualDepartureTime().plusMinutes(offset);
            default:
                throw new IllegalArgumentException(base.toString() + " is not support in calculate check time!");
        }
    }

    private boolean calcPlanedDateValid(LocalDate planDate, LocalDate start, LocalDate end) {
        if (null == start && null == end) {
            return true;
        } else if (null == start) {
            return end.isAfter(planDate);
        } else if (null == end) {
            return planDate.isAfter(start);
        } else {
            return DateTimeUtil.isInInterval(planDate, start, end);
        }
    }

    boolean convertFlagToBool(int flag) {
        if (flag == 0) {
            return false;
        } else if (flag == 1) {
            return true;
        } else {
            throw new IllegalArgumentException("Flag can be only 0 or 1. " + flag);
        }
    }

    CheckStateEnum getCheckState(LocalDateTime start, LocalDateTime end) {
        CheckStateEnum result = CheckStateEnum.NOT_VALID;
        if (null != start && null != end) {
            if (DateTimeUtil.isInInterval(LocalDateTime.now(), start, end)) {
                result = CheckStateEnum.START_CHECKING;
            } else if (start.isAfter(LocalDateTime.now())) {
                result = CheckStateEnum.WAIT_CHECKING;
            } else {
                result = CheckStateEnum.STOP_CHECKING;
            }
        }
        return result;
    }

    /**
     * 判断车次是否有到达时间
     *
     * @return boolean
     */
    private boolean hasArriveTime() {
        return this.stationType.equals(StationTypeEnum.PASS) || this.stationType.equals(StationTypeEnum.FINAL);
    }

    /**
     * 判断车次是否有发车时间
     *
     * @return boolean
     */
    private boolean hasDepartureTime() {
        return this.stationType.equals(StationTypeEnum.PASS) || this.stationType.equals(StationTypeEnum.START);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("planKey", planKey)
                .append("trainNum", trainNum)
                .append("uniqueTrainNum", uniqueTrainNum)
                .append("trainType", trainType)
                .append("stationType", stationType)
                .append("startStation", startStation)
                .append("finalStation", finalStation)
                .append("presentStation", presentStation)
                .append("validPeriodStart", validPeriodStart)
                .append("validPeriodEnd", validPeriodEnd)
                .append("validFlag", validFlag)
                .append("trainSuspendStart", trainSuspendStart)
                .append("trainSuspendEnd", trainSuspendEnd)
                .append("suspendFlag", suspendFlag)
                .append("planedTrackNum", planedTrackNum)
                .append("actualTrackNum", actualTrackNum)
                .append("dockingPlatform", dockingPlatform)
                .append("planedDepartueTime", planedDepartureTime)
                .append("actualDepartureTime", actualDepartureTime)
                .append("departureTimeState", departureTimeState)
                .append("arriveTimeState", arriveTimeState)
                .append("trackNumEditedState", trackNumEditedState)
                .append("departureTimeEditedState", departureTimeEditedState)
                .append("arriveTimeEditedState", arriveTimeEditedState)
                .append("planGenTimestamp", getGenerateTimestamp())
                .append("lastEditedTimestamp", lastEditedTimestamp)
                .toString();
    }
}
