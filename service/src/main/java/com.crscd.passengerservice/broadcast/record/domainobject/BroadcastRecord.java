package com.crscd.passengerservice.broadcast.record.domainobject;

import com.crscd.framework.orm.annotation.EqualBean;
import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.plan.domainobject.BroadcastKey;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/28
 * Time: 9:18
 */
@EqualBean
public class BroadcastRecord {
    @OrmIgnore
    private long id;
    // 广播计划key
    // recordKey ->关联单条广播计划的key，车次广播计划也根据规则拼接出相应的key
    private String recordKey;
    // 车次
    private String trainNum;
    // 车站
    private String stationName;
    // 广播类型
    private BroadcastKindEnum broadcastKind;
    // 执行模式
    private BroadcastModeEnum broadcastMode;
    // 实际调用驱动接口时间
    private String callDriverTime;
    // 广播内容模版名称
    private String broadcastContentName;
    // 广播区域
    private List<String> broadcastArea;
    // 广播内容
    // todo: 将来得考虑压缩处理存储广播内容
    private String broadcastContent;
    // 执行状态：unexecuted: 未执行， wait:等待播放，active:正在播放，finish：执行完成, fail：执行失败
    private BroadcastStateEnum operationState;
    // 广播优先级
    private int priorityLevel;
    // 记录时间
    private String recordTime;

    public BroadcastRecord() {
    }

    public BroadcastRecord(BroadcastStationPlan plan, String content) {
        this.recordKey = new BroadcastRecordKey(plan).toString();
        this.trainNum = plan.getTrainNum();
        this.stationName = plan.getPresentStation().getStationName();
        this.broadcastKind = plan.getBroadcastKind();
        this.broadcastMode = plan.getBroadcastOperationMode();
        this.broadcastArea = plan.getBroadcastArea();
        this.broadcastContent = content;
        this.operationState = plan.getBroadcastState();
        this.priorityLevel = plan.getBroadcastPriorityLevel();
        this.recordTime = DateTimeUtil.getCurrentDatetimeString();
        this.callDriverTime = DateTimeUtil.getCurrentDatetimeString();
        this.broadcastContentName = plan.getBroadcastContentName();
    }

    public BroadcastRecord(String planKey, String contentName, String content, List<String> broadcastArea, int priorityLevel) {
        BroadcastKey broadcastKey = new BroadcastKey(planKey);
        this.recordKey = new BroadcastRecordKey(broadcastKey).toString();
        this.trainNum = broadcastKey.getTrainNum();
        this.stationName = broadcastKey.getStationName();
        this.broadcastKind = BroadcastKindEnum.TRAIN_MANUAL;
        this.broadcastMode = BroadcastModeEnum.MANUAL;
        this.broadcastArea = broadcastArea;
        this.broadcastContent = content;
        this.operationState = BroadcastStateEnum.WAIT_EXECUTE;
        this.priorityLevel = priorityLevel;
        this.recordTime = DateTimeUtil.getCurrentDatetimeString();
        this.callDriverTime = DateTimeUtil.getCurrentDatetimeString();
        this.broadcastContentName = contentName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCallDriverTime() {
        return callDriverTime;
    }

    public void setCallDriverTime(String callDriverTime) {
        this.callDriverTime = callDriverTime;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
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

    public BroadcastKindEnum getBroadcastKind() {
        return broadcastKind;
    }

    public void setBroadcastKind(BroadcastKindEnum broadcastKind) {
        this.broadcastKind = broadcastKind;
    }

    public BroadcastModeEnum getBroadcastMode() {
        return broadcastMode;
    }

    public void setBroadcastMode(BroadcastModeEnum broadcastMode) {
        this.broadcastMode = broadcastMode;
    }

    public List<String> getBroadcastArea() {
        return broadcastArea;
    }

    public void setBroadcastArea(List<String> broadcastArea) {
        this.broadcastArea = broadcastArea;
    }

    public String getBroadcastContent() {
        return broadcastContent;
    }

    public void setBroadcastContent(String broadcastContent) {
        this.broadcastContent = broadcastContent;
    }

    public BroadcastStateEnum getOperationState() {
        return operationState;
    }

    public void setOperationState(BroadcastStateEnum operationState) {
        this.operationState = operationState;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getBroadcastContentName() {
        return broadcastContentName;
    }

    public void setBroadcastContentName(String broadcastContentName) {
        this.broadcastContentName = broadcastContentName;
    }
}
