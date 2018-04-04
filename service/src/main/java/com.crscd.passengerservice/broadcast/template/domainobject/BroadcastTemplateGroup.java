package com.crscd.passengerservice.broadcast.template.domainobject;

import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.po.BroadcastTemplateGroupBean;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/16
 */
public class BroadcastTemplateGroup {
    // 唯一标识ID
    private long id;
    // 广播组名称
    private String templateGroupName;
    // 广播组所属车站站名
    private String stationName;
    // 广播属性：到发或者变更 onArrive, alteration
    private BroadcastKindEnum broadcastKind;
    // 是否可以修改，1表示可以修改，0表示不可修改
    private int revisability;
    // 一组模版
    private List<BroadcastTemplate> broadcastTemplateList;

    public BroadcastTemplateGroup(BroadcastTemplateGroupBean bean, List<BroadcastTemplate> templateList) {
        this.id = bean.getId();
        this.templateGroupName = bean.getTemplateGroupName();
        this.stationName = bean.getStationName();
        this.broadcastKind = bean.getBroadcastKind();
        this.revisability = bean.getRevisability();
        broadcastTemplateList = templateList;
    }

    public long getId() {
        return id;
    }

    public String getTemplateGroupName() {
        return templateGroupName;
    }

    public BroadcastKindEnum getBroadcastKind() {
        return broadcastKind;
    }

    public int getRevisability() {
        return revisability;
    }

    public String getStationName() {
        return stationName;
    }

    public List<BroadcastTemplate> getBroadcastTemplateList() {
        return broadcastTemplateList;
    }
}
