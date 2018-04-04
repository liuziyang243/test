package com.crscd.passengerservice.cctv.domainobject;

import com.crscd.framework.orm.annotation.EqualBean;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 11:26
 */
@EqualBean
public class RoundRollingGroup {
    // id
    private long id;
    // 轮询组名称
    private String groupName;
    // 轮询组包含的cameraID列表
    private List<String> cameraIDList;
    // 轮序间隔，单位是秒
    private int roundPollingInterval;
    // 轮训组所属车站名称
    private String stationName;

    public RoundRollingGroup() {
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

    public List<String> getCameraIDList() {
        return cameraIDList;
    }

    public void setCameraIDList(List<String> cameraIDList) {
        this.cameraIDList = cameraIDList;
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
