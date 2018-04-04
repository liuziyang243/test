package com.crscd.passengerservice.cctv.dto;

import java.util.HashMap;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 11:29
 */
public class RoundRollingGroupDTO {
    // 轮询组ID，通过数据库获取
    private long id;
    // 轮询组名称
    private String groupName;
    // 轮询组包含的cameraID列表,key表示id, value表示name
    private HashMap<String, String> cameraIdList;
    // 轮序间隔，单位是秒
    private int roundPollingInterval;
    // 轮训组所属车站名称
    private String stationName;

    public RoundRollingGroupDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public HashMap<String, String> getCameraIdList() {
        return cameraIdList;
    }

    public void setCameraIdList(HashMap<String, String> cameraIdList) {
        this.cameraIdList = cameraIdList;
    }

    public int getRoundPollingInterval() {
        return roundPollingInterval;
    }

    public void setRoundPollingInterval(int roundPollingInterval) {
        this.roundPollingInterval = roundPollingInterval;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
