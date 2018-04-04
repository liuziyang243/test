package com.crscd.passengerservice.broadcast.template.po;

import com.crscd.framework.orm.annotation.EqualBean;
import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:39
 */
@EqualBean
public class BroadcastTemplateBean {
    // ID
    @OrmIgnore
    private long id;
    // 模版名称->列车作业名称
    private String templateGroupName;
    // 模版使用的车站站名
    private String stationName;
    // 时间基准，1.到点 2.发点 3.开检 4.停检
    // 时间基准：到点：arriveTime,发点：departureTime,开检:startCheckTime,停检：stopCheckTime
    private TrainTimeBaseEnum baseTime;
    // 相对时间,单位是分钟
    private int timeOffset;
    // 作业内容->广播内容模版名称
    private String broadcastContentName;
    // 作业模式
    private BroadcastModeEnum operationMode;
    // 优先级
    private int priorityLevel;
    // 作业区域
    private List<String> broadcastArea;
    // 一级区名称
    private List<String> firstRegion;
    // 广播类型：到发或者变更 toArrive, alteration
    private BroadcastKindEnum broadcastKind;

    public BroadcastTemplateBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTemplateGroupName() {
        return templateGroupName;
    }

    public void setTemplateGroupName(String templateGroupName) {
        this.templateGroupName = templateGroupName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public TrainTimeBaseEnum getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(TrainTimeBaseEnum baseTime) {
        this.baseTime = baseTime;
    }

    public int getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(int timeOffset) {
        this.timeOffset = timeOffset;
    }

    public String getBroadcastContentName() {
        return broadcastContentName;
    }

    public void setBroadcastContentName(String broadcastContentName) {
        this.broadcastContentName = broadcastContentName;
    }

    public BroadcastModeEnum getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(BroadcastModeEnum operationMode) {
        this.operationMode = operationMode;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
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

    public List<String> getFirstRegion() {
        return firstRegion;
    }

    public void setFirstRegion(List<String> firstRegion) {
        this.firstRegion = firstRegion;
    }
}
