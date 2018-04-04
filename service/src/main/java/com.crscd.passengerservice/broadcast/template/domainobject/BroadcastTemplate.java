package com.crscd.passengerservice.broadcast.template.domainobject;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.passengerservice.broadcast.content.domainobject.NormalBroadcastContent;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.broadcast.template.po.BroadcastTemplateBean;
import com.crscd.passengerservice.config.enumtype.FirstRegionEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/16
 */
public class BroadcastTemplate {
    // ID
    private long id;
    /**************** 模版内容 ********************/
    // 时间基准，1.到点 2.发点 3.开检 4.停检
    // 时间基准：到点：arriveTime,发点：departureTime,开检:startCheckTime,停检：stopCheckTime
    private TrainTimeBaseEnum baseTime;
    // 相对时间,单位是分钟
    private int timeOffset;
    // 作业内容->广播内容模版名称
    private NormalBroadcastContent broadcastContent;
    // 广播属性：到发、变更或者其他， toArrive, alteration, others
    private BroadcastKindEnum broadcastKind;
    // 作业模式
    private BroadcastModeEnum operationMode;
    // 广播区域列表
    private List<String> broadcastArea;
    // 一级区列表
    private List<FirstRegionEnum> firstRegion;
    // 优先级
    private int priorityLevel;

    public BroadcastTemplate(BroadcastTemplateBean bean, NormalBroadcastContent content) {
        this.id = bean.getId();
        this.baseTime = bean.getBaseTime();
        this.timeOffset = bean.getTimeOffset();
        this.broadcastContent = content;
        this.broadcastKind = bean.getBroadcastKind();
        this.operationMode = bean.getOperationMode();
        this.broadcastArea = bean.getBroadcastArea();
        this.priorityLevel = bean.getPriorityLevel();
        this.firstRegion = new ArrayList<>();
        if (ListUtil.isNotEmpty(bean.getFirstRegion())) {
            for (String region : bean.getFirstRegion()) {
                firstRegion.add(FirstRegionEnum.fromString(region));
            }
        }
    }

    public long getId() {
        return id;
    }

    public TrainTimeBaseEnum getBaseTime() {
        return baseTime;
    }

    public int getTimeOffset() {
        return timeOffset;
    }

    public NormalBroadcastContent getBroadcastContent() {
        return broadcastContent;
    }

    public BroadcastModeEnum getOperationMode() {
        return operationMode;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public List<String> getBroadcastArea() {
        return broadcastArea;
    }

    public BroadcastKindEnum getBroadcastKind() {
        return broadcastKind;
    }

    public List<FirstRegionEnum> getFirstRegion() {
        return firstRegion;
    }
}
