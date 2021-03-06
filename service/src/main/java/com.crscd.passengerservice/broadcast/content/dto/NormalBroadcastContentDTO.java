package com.crscd.passengerservice.broadcast.content.dto;

import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;

/**
 * Created by liuziyang
 * Create date: 2017/9/9
 */
public class NormalBroadcastContentDTO {
    // id
    private long id;
    // 广播内容名称 -> 作业内容(广播业务模版)
    private String contentName;
    // 广播属性：到发、变更或者其他， toArrive, alteration, others
    private BroadcastKindEnum broadcastKind;
    // 站名
    private String stationName;
    // 本地语言广播详细内容
    private String contentInLocalLan;
    // 英文广播详细内容
    private String contentInEng;

    public NormalBroadcastContentDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentInLocalLan() {
        return contentInLocalLan;
    }

    public void setContentInLocalLan(String contentInLocalLan) {
        this.contentInLocalLan = contentInLocalLan;
    }

    public String getContentInEng() {
        return contentInEng;
    }

    public void setContentInEng(String contentInEng) {
        this.contentInEng = contentInEng;
    }

    public BroadcastKindEnum getBroadcastKind() {
        return broadcastKind;
    }

    public void setBroadcastKind(BroadcastKindEnum broadcastKind) {
        this.broadcastKind = broadcastKind;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
