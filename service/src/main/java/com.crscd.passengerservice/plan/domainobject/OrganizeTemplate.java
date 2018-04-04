package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.orm.annotation.EqualBean;
import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/16
 */
@EqualBean
public class OrganizeTemplate {
    /* ID */
    @OrmIgnore
    private long id;
    /* 模版对应的车次号 */
    private String trainNum;
    /* 模版对应的车站名称 */
    private String stationName;
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

    public OrganizeTemplate() {
        waitZoneList = new ArrayList<>();
        stationEntrancePort = new ArrayList<>();
        stationExitPort = new ArrayList<>();
        aboardCheckGate = new ArrayList<>();
        exitCheckGate = new ArrayList<>();
    }

    public OrganizeTemplate(StationTypeEnum type) {
        this();
        if (type.equals(StationTypeEnum.START) || type.equals(StationTypeEnum.PASS)) {
            this.startAboardCheckBase = TrainTimeBaseEnum.DEPARTURE_TIME;
            this.startAboardCheckTimeOffset = -15;
            this.stopAboardCheckBase = TrainTimeBaseEnum.DEPARTURE_TIME;
            this.stopAboardCheckTimeOffset = -5;
        }
        broadcastTemplateGroupName = "default";
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
