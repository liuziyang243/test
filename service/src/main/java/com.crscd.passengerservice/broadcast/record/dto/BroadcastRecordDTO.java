package com.crscd.passengerservice.broadcast.record.dto;

import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 13:45
 */
public class BroadcastRecordDTO {
    // 广播计划key
    // key ->关联单条广播计划的key，车次广播计划也根据规则拼接出相应的key
    private String key;
    // 车次
    private String trainNum;
    // 广播类型
    private BroadcastKindEnum broadcastKind;
    // 执行模式
    private BroadcastModeEnum broadcastMode;
    // 计划执行时间
    private String planedBroadcastTime;
    // 实际执行时间
    private String actualBroadcastTime;
    // 广播区域
    private List<String> broadcastArea;
    // 广播内容模版名称
    private String broadcastContentName;
    // 广播内容
    private String broadcastContent;
    // 执行状态：unexecuted: 未执行， wait:等待播放，active:正在播放，finish：执行完成, fail：执行失败
    private BroadcastStateEnum operationState;
    // 广播优先级
    private int priorityLevel;

    public BroadcastRecordDTO() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
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

    public String getPlanedBroadcastTime() {
        return planedBroadcastTime;
    }

    public void setPlanedBroadcastTime(String planedBroadcastTime) {
        this.planedBroadcastTime = planedBroadcastTime;
    }

    public String getActualBroadcastTime() {
        return actualBroadcastTime;
    }

    public void setActualBroadcastTime(String actualBroadcastTime) {
        this.actualBroadcastTime = actualBroadcastTime;
    }

    public String getBroadcastContentName() {
        return broadcastContentName;
    }

    public void setBroadcastContentName(String broadcastContentName) {
        this.broadcastContentName = broadcastContentName;
    }
}
