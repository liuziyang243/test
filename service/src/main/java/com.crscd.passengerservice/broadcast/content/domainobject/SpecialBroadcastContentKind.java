package com.crscd.passengerservice.broadcast.content.domainobject;

import com.crscd.framework.orm.annotation.EqualBean;

/**
 * Created by liuziyang
 * Create date: 2017/9/9
 */
@EqualBean
public class SpecialBroadcastContentKind {
    private long id;
    // 专题广播类型
    private String contentType;
    // 专题广播使用的车站
    private String stationName;

    public SpecialBroadcastContentKind() {
    }

    public SpecialBroadcastContentKind(String contentType, String stationName) {
        this.contentType = contentType;
        this.stationName = stationName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
