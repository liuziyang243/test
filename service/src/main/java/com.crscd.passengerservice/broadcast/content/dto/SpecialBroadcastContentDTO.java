package com.crscd.passengerservice.broadcast.content.dto;

/**
 * Created by liuziyang
 * Create date: 2017/9/9
 */
public class SpecialBroadcastContentDTO {
    private long id;
    // 专题广播名称
    private String contentName;
    // 专题广播内容
    private String broadcastContent;
    // 专题广播类型
    private String contentType;
    // 专题广播使用的车站
    private int stationName;

    public SpecialBroadcastContentDTO() {
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

    public String getBroadcastContent() {
        return broadcastContent;
    }

    public void setBroadcastContent(String broadcastContent) {
        this.broadcastContent = broadcastContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getStationName() {
        return stationName;
    }

    public void setStationName(int stationName) {
        this.stationName = stationName;
    }
}
