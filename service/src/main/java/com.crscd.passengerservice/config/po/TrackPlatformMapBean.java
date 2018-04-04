package com.crscd.passengerservice.config.po;

/**
 * @author lzy
 * Date: 2017/8/31
 * Time: 14:26
 * 股道与站台对应关系表
 */
public class TrackPlatformMapBean {
    // ID
    private long id;
    // 车站名称
    private String stationName;
    // 站台名称
    private String platform;
    // 股道号
    private int trackNum;

    public TrackPlatformMapBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getTrackNum() {
        return trackNum;
    }

    public void setTrackNum(int trackNum) {
        this.trackNum = trackNum;
    }
}
