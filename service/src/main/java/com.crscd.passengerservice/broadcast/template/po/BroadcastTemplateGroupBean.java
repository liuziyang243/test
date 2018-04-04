package com.crscd.passengerservice.broadcast.template.po;

import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:39
 */
public class BroadcastTemplateGroupBean {
    // 唯一标识ID
    @OrmIgnore
    private long id;
    // 广播组名称
    private String templateGroupName;
    // 广播组所属车站站名
    private String stationName;
    // 广播属性：到发或者变更 onArrive, alteration
    private BroadcastKindEnum broadcastKind;
    // 是否可以修改，1表示可以修改，0表示不可修改
    private int revisability;

    public BroadcastTemplateGroupBean() {
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

    public BroadcastKindEnum getBroadcastKind() {
        return broadcastKind;
    }

    public void setBroadcastKind(BroadcastKindEnum broadcastKind) {
        this.broadcastKind = broadcastKind;
    }

    public int getRevisability() {
        return revisability;
    }

    public void setRevisability(int revisability) {
        this.revisability = revisability;
    }
}
