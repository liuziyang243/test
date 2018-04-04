package com.crscd.passengerservice.plan.dto;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.domainobject.OrganizeTemplate;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 9:15
 */
public class OrganizeTemplateDTO {
    /***************** 列车时刻表信息 ******************/
    /* 列车车次号 */
    private String trainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    private boolean validFlag; // 判断是否有效
    /* 为了能够兼容跨天的车次，到达时间可以+1天 */
    /* 到达出发的相对天数 */
    private int arriveDelayDays;
    private int departureDelayDays;

    /* 模版对应的车站名称 */
    private String stationName;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 计划股道号 */
    private int planedTrackNum;
    /* 停靠站台 */
    private String dockingPlatform; //自动计算

    /***************** 客运组织业务模版信息 ******************/
    /* 进站上车开检时间基准 */
    private TrainTimeBaseEnum startAboardCheckBase;
    /* 进站上车开检相对时间, 单位是分钟 */
    private int startAboardCheckTimeOffset;
    /* 进站上车停检时间基准, 单位是分钟 */
    private TrainTimeBaseEnum stopAboardCheckBase;
    /* 进站上车停检相对时间 */
    private int stopAboardCheckTimeOffset;
    // 候车区列表
    private List<String> waitZoneList;
    // 车站进站口
    private List<String> stationEntrancePort;
    // 车站出站口
    private List<String> stationExitPort;
    // 乘车检票口，如果在进站口开设检票口，则检票口与进站口会重合
    private List<String> aboardCheckGate;
    // 出站检票口，根据实际情况，出站检票口可能与车站出站口指同一位置
    private List<String> exitCheckGate;
    // 广播模版组名称
    private String broadcastTemplateGroupName;
    // 是否数据库中已经存在编辑过的信息
    private boolean modifiedFlag;

    public OrganizeTemplateDTO() {
    }

    public OrganizeTemplateDTO(BasicPlan plan, BasicTrainStationInfo station, OrganizeTemplate template, ConfigManager manager, boolean modified) {
        this.trainNum = plan.getTrainNum();
        this.trainType = plan.getTrainType();
        this.trainDirection = plan.getTrainDirection();
        this.startStation = plan.getStartStation().getStationName();
        this.finalStation = plan.getFinalStation().getStationName();
        this.validPeriodStart = DateTimeFormatterUtil.convertDateToString(plan.getValidPeriodStart());
        this.validPeriodEnd = DateTimeFormatterUtil.convertDateToString(plan.getValidPeriodEnd());
        this.validFlag = DateTimeUtil.isInInterval(DateTimeUtil.getCurrentDate(), DateTimeFormatterUtil.convertStringToDate(this.validPeriodStart), DateTimeFormatterUtil.convertStringToDate(this.validPeriodEnd));
        this.arriveDelayDays = station.getArriveDelayDays();
        this.departureDelayDays = station.getDepartureDelayDays();
        this.planedArriveTime = DateTimeFormatterUtil.convertTimeToString(station.getPlanedArriveTime());
        this.planedDepartureTime = DateTimeFormatterUtil.convertTimeToString(station.getPlanedDepartureTime());
        this.stationName = station.getStationInfo().getStationName();
        this.stationType = station.getStationType();
        this.planedTrackNum = station.getPlanedTrackNum();
        this.dockingPlatform = manager.getPlatformByTrackNum(this.stationName, this.planedTrackNum);
        this.startAboardCheckBase = template.getStartAboardCheckBase();
        this.startAboardCheckTimeOffset = template.getStartAboardCheckTimeOffset();
        this.stopAboardCheckBase = template.getStopAboardCheckBase();
        this.stopAboardCheckTimeOffset = template.getStopAboardCheckTimeOffset();
        this.waitZoneList = template.getWaitZoneList();
        this.stationEntrancePort = template.getStationEntrancePort();
        this.stationExitPort = template.getStationExitPort();
        this.exitCheckGate = template.getExitCheckGate();
        this.aboardCheckGate = template.getAboardCheckGate();
        this.broadcastTemplateGroupName = template.getBroadcastTemplateGroupName();
        this.modifiedFlag = modified;
    }

    public boolean getModifiedFlag() {
        return modifiedFlag;
    }

    public void setModifiedFlag(boolean modifiedFlag) {
        this.modifiedFlag = modifiedFlag;
    }

    public boolean getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(boolean validFlag) {
        this.validFlag = validFlag;
    }


    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public TrainTypeEnum getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainTypeEnum trainType) {
        this.trainType = trainType;
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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public StationTypeEnum getStationType() {
        return stationType;
    }

    public void setStationType(StationTypeEnum stationType) {
        this.stationType = stationType;
    }

    public String getPlanedDepartureTime() {
        return planedDepartureTime;
    }

    public void setPlanedDepartureTime(String planedDepartureTime) {
        this.planedDepartureTime = planedDepartureTime;
    }

    public String getPlanedArriveTime() {
        return planedArriveTime;
    }

    public void setPlanedArriveTime(String planedArriveTime) {
        this.planedArriveTime = planedArriveTime;
    }

    public int getPlanedTrackNum() {
        return planedTrackNum;
    }

    public void setPlanedTrackNum(int planedTrackNum) {
        this.planedTrackNum = planedTrackNum;
    }

    public String getDockingPlatform() {
        return dockingPlatform;
    }

    public void setDockingPlatform(String dockingPlatform) {
        this.dockingPlatform = dockingPlatform;
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

    public List<String> getWaitZoneList() {
        return waitZoneList;
    }

    public void setWaitZoneList(List<String> waitZoneList) {
        this.waitZoneList = waitZoneList;
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

    public String getBroadcastTemplateGroupName() {
        return broadcastTemplateGroupName;
    }

    public void setBroadcastTemplateGroupName(String broadcastTemplateGroupName) {
        this.broadcastTemplateGroupName = broadcastTemplateGroupName;
    }

    public int getArriveDelayDays() {
        return arriveDelayDays;
    }

    public void setArriveDelayDays(int arriveDelayDays) {
        this.arriveDelayDays = arriveDelayDays;
    }

    public int getDepartureDelayDays() {
        return departureDelayDays;
    }

    public void setDepartureDelayDays(int departureDelayDays) {
        this.departureDelayDays = departureDelayDays;
    }
}
