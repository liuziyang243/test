package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.enumtype.CheckStateEnum;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;
import com.crscd.passengerservice.plan.po.PassengerStationPlanBean;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 9:46
 */
public class PassengerStationPlan extends BaseTrainPlan {
    /**************** 检票信息 *****************/
    /* 进站上车开检时间基准 */
    private TrainTimeBaseEnum startAboardCheckBase;
    /* 进站上车开检相对时间, 单位是分钟 */
    private int startAboardCheckTimeOffset;
    /* 进站上车开检时间,自动计算 */
    private LocalDateTime autoStartAboardCheckTime;
    /* 手动检票时间设置，允许直接修改 */
    private LocalDateTime startAboardCheckTime;

    /* 进站上车停检时间基准, 单位是分钟 */
    private TrainTimeBaseEnum stopAboardCheckBase;
    /* 进站上车停检相对时间 */
    private int stopAboardCheckTimeOffset;
    /* 进站上车停检时间,自动计算 */
    private LocalDateTime autoStopAboardCheckTime;
    /* 手动检票时间设置，允许直接修改 */
    private LocalDateTime stopAboardCheckTime;

    /* 检票时间有效性, 如果开检时间小于停检时间则认为有效，自动计算 */
    private boolean checkPeriodSetValid;
    /*
     * 当前是否处于检票状态
     * 属性自动计算
     */
    private CheckStateEnum checkState;

    /**************** 区域信息 *****************/
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

    // 通过列车时刻表和客运组织业务模版生成计划
    public PassengerStationPlan(BasicPlan plan, String date, String stationName, OrganizeTemplate template, ConfigManager manager) {
        super(plan, date, stationName, manager);
        this.startAboardCheckBase = template.getStartAboardCheckBase();
        this.startAboardCheckTimeOffset = template.getStartAboardCheckTimeOffset();
        this.stopAboardCheckBase = template.getStopAboardCheckBase();
        this.stopAboardCheckTimeOffset = template.getStopAboardCheckTimeOffset();
        this.startAboardCheckTime = this.getAutoStartAboardCheckTime();
        this.stopAboardCheckTime = this.getAutoStopAboardCheckTime();

        waitZone = template.getWaitZoneList();
        stationEntrancePort = template.getStationEntrancePort();
        stationExitPort = template.getStationExitPort();
        aboardCheckGate = template.getAboardCheckGate();
        exitCheckGate = template.getExitCheckGate();
    }

    // 通过po读取计划
    public PassengerStationPlan(PassengerStationPlanBean bean, ConfigManager manager) {
        super(manager);
        this.id = bean.getId();
        this.setGenerateTimestamp(bean.getGenerateTimestamp());
        this.planKey = bean.getPlanKey();
        this.trainNum = bean.getTrainNum();
        this.uniqueTrainNum = trainNum;
        this.planDate = DateTimeFormatterUtil.convertStringToDate(bean.getPlanDate());
        this.stationType = bean.getStationType();
        this.trainType = bean.getTrainType();
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
        this.startAboardCheckBase = bean.getStartAboardCheckBase();
        this.startAboardCheckTimeOffset = bean.getStartAboardCheckTimeOffset();
        this.startAboardCheckTime = DateTimeFormatterUtil.convertStringToDatetime(bean.getStartAboardCheckTime());
        this.stopAboardCheckBase = bean.getStopAboardCheckBase();
        this.stopAboardCheckTimeOffset = bean.getStopAboardCheckTimeOffset();
        this.stopAboardCheckTime = DateTimeFormatterUtil.convertStringToDatetime(bean.getStopAboardCheckTime());
        this.waitZone = bean.getWaitZone();
        this.stationEntrancePort = bean.getStationEntrancePort();
        this.stationExitPort = bean.getStationExitPort();
        this.aboardCheckGate = bean.getAboardCheckGate();
        this.exitCheckGate = bean.getExitCheckGate();
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

    public LocalDateTime getAutoStartAboardCheckTime() {
        if (!this.getStationType().equals(StationTypeEnum.FINAL)) {
            return calcActualCheckTime(this.startAboardCheckBase, this.startAboardCheckTimeOffset);
        } else {
            return null;
        }
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

    public LocalDateTime getAutoStopAboardCheckTime() {
        if (!this.getStationType().equals(StationTypeEnum.FINAL)) {
            return calcActualCheckTime(this.stopAboardCheckBase, this.stopAboardCheckTimeOffset);
        }
        return null;
    }

    public boolean getCheckPeriodSetValid() {
        if (!this.getStationType().equals(StationTypeEnum.FINAL)) {
            return this.getAutoStopAboardCheckTime().isAfter(this.getAutoStartAboardCheckTime());
        }
        return false;
    }

    public CheckStateEnum getCheckState() {
        if (!this.getStationType().equals(StationTypeEnum.FINAL)) {
            return getCheckState(this.startAboardCheckTime, this.stopAboardCheckTime);
        }
        return CheckStateEnum.NOT_VALID;
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

    public LocalDateTime getStartAboardCheckTime() {
        if (!this.getStationType().equals(StationTypeEnum.FINAL)) {
            return startAboardCheckTime;
        }
        return null;
    }

    public void setStartAboardCheckTime(LocalDateTime startAboardCheckTime) {
        this.startAboardCheckTime = startAboardCheckTime;
    }

    public LocalDateTime getStopAboardCheckTime() {
        if (!this.getStationType().equals(StationTypeEnum.FINAL)) {
            return stopAboardCheckTime;
        }
        return null;
    }

    public void setStopAboardCheckTime(LocalDateTime stopAboardCheckTime) {
        this.stopAboardCheckTime = stopAboardCheckTime;
    }
}
