package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.broadcast.content.domainobject.NormalBroadcastContent;
import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.template.business.BroadcastTemplateManager;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplate;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.enumtype.CheckStateEnum;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;
import com.crscd.passengerservice.plan.po.BroadcastStationPlanBean;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 9:46
 */
public class BroadcastStationPlan extends BaseTrainPlan {

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

    /**************** 广播业务内容 **********************/
    // 广播属性：到发、变更或者其他， toArrive, alteration, others
    private BroadcastKindEnum broadcastKind;
    // 广播时间基准，通过广播模版获取
    private TrainTimeBaseEnum broadcastBaseTime;
    // 相对时间,单位是分钟
    private int broadcastTimeOffset;
    // 利用广播基准和相对时间计算得到的广播执行时间，自动计算得到
    private LocalDateTime autoCalcBroadcastTime;
    // 广播执行时间，可以手动设置
    private LocalDateTime broadcastTime;

    // 作业内容->广播内容模版名称
    private String broadcastContentName;
    // 作业模式
    private BroadcastModeEnum broadcastOperationMode;
    // 优先级
    private int broadcastPriorityLevel;
    // 广播区列表<-根据广播业务模版生成
    private List<String> broadcastArea;

    // 广播计划执行状态
    private BroadcastStateEnum broadcastState;

    // 通过列车时刻表、课运组织业务模版、广播业务模版生成计划
    public BroadcastStationPlan(BasicPlan plan, String date, String stationName, OrganizeTemplate template, BroadcastTemplate broadcastTemplate, ConfigManager manager, BroadcastTemplateManager templateManager) {
        super(plan, date, stationName, manager);
        this.startAboardCheckBase = template.getStartAboardCheckBase();
        this.startAboardCheckTimeOffset = template.getStartAboardCheckTimeOffset();
        this.stopAboardCheckBase = template.getStopAboardCheckBase();
        this.stopAboardCheckTimeOffset = template.getStopAboardCheckTimeOffset();

        this.waitZone = template.getWaitZoneList();
        this.stationEntrancePort = template.getStationEntrancePort();
        this.stationExitPort = template.getStationExitPort();
        this.aboardCheckGate = template.getAboardCheckGate();
        this.exitCheckGate = template.getExitCheckGate();

        NormalBroadcastContent content = broadcastTemplate.getBroadcastContent();
        this.broadcastBaseTime = broadcastTemplate.getBaseTime();
        this.broadcastTimeOffset = broadcastTemplate.getTimeOffset();
        this.broadcastTime = this.getAutoCalcBroadcastTime();
        this.broadcastContentName = content.getContentName();
        this.broadcastOperationMode = broadcastTemplate.getOperationMode();
        this.broadcastPriorityLevel = broadcastTemplate.getPriorityLevel();
        this.broadcastKind = broadcastTemplate.getBroadcastKind();
        this.broadcastState = BroadcastStateEnum.WAIT_EXECUTE;
        this.broadcastArea = templateManager.getBroadcastAreaList(broadcastTemplate, this);

        this.planKey = new BroadcastKey(this).toString();
    }

    public BroadcastStationPlan(BroadcastStationPlanBean bean, ConfigManager manager) {
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

        this.broadcastBaseTime = bean.getBroadcastBaseTime();
        this.broadcastTimeOffset = bean.getBroadcastTimeOffset();
        this.broadcastTime = DateTimeFormatterUtil.convertStringToDatetime(bean.getBroadcastTime());
        this.broadcastContentName = bean.getBroadcastContentName();
        this.broadcastOperationMode = bean.getBroadcastOperationMode();
        this.broadcastPriorityLevel = bean.getBroadcastPriorityLevel();
        this.broadcastArea = bean.getBroadcastArea();
        this.broadcastKind = bean.getBroadcastKind();
        this.broadcastState = bean.getBroadcastState();
    }

    public Object deepClone() throws IOException,
            ClassNotFoundException {
        //将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        //从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (oi.readObject());
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
        }
        return null;
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

    public LocalDateTime getAutoCalcBroadcastTime() {
        return calcBroadcastTime();
    }

    public LocalDateTime getBroadcastTime() {
        return broadcastTime;
    }

    public void setBroadcastTime(LocalDateTime broadcastTime) {
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

    public BroadcastKindEnum getBroadcastKind() {
        return broadcastKind;
    }

    public void setBroadcastKind(BroadcastKindEnum broadcastKind) {
        this.broadcastKind = broadcastKind;
    }

    public BroadcastStateEnum getBroadcastState() {
        return broadcastState;
    }

    public void setBroadcastState(BroadcastStateEnum broadcastState) {
        this.broadcastState = broadcastState;
    }

    private LocalDateTime calcBroadcastTime() {
        switch (broadcastBaseTime) {
            case ARRIVE_TIME:
                if (this.stationType.equals(StationTypeEnum.START)) {
                    throw new IllegalArgumentException("There is no arrive time at start station! plankey-" + this.planKey);
                }
                return this.actualArriveTime.plusMinutes(broadcastTimeOffset);
            case DEPARTURE_TIME:
                if (this.stationType.equals(StationTypeEnum.FINAL)) {
                    throw new IllegalArgumentException("There is no departure time at final station! plankey-" + this.planKey);
                }
                return this.actualDepartureTime.plusMinutes(broadcastTimeOffset);
            case START_CHECK:
                if (this.stationType.equals(StationTypeEnum.FINAL)) {
                    throw new IllegalArgumentException("There is no check time at final station! plankey-" + this.planKey);
                }
                return this.startAboardCheckTime.plusMinutes(broadcastTimeOffset);
            case STOP_CHECK:
                if (this.stationType.equals(StationTypeEnum.FINAL)) {
                    throw new IllegalArgumentException("There is no check time at final station! plankey-" + this.planKey);
                }
                return this.stopAboardCheckTime.plusMinutes(broadcastTimeOffset);
            case NOT_VALID:
                // 变更广播只能手动执行
                return null;
            default:
                throw new IllegalArgumentException(broadcastBaseTime.toString() + " is not support in calculate check time!");
        }
    }
}
