package com.crscd.passengerservice.config.po;

import com.crscd.framework.orm.annotation.EqualBean;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/31
 * Time: 14:31
 */
@EqualBean
public class BroadcastArea {
    // ID
    private long id;
    // 广播区名称
    private String broadcastZoneName;
    // 广播区域ID
    private int broadcastZoneID;
    // 广播区对应的二级区域列表
    private List<String> secondaryRegionList;
    // 车站名称
    private String stationName;
    // 分组名称
    private String groupName;

    public BroadcastArea() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBroadcastZoneName() {
        return broadcastZoneName;
    }

    public void setBroadcastZoneName(String broadcastZoneName) {
        this.broadcastZoneName = broadcastZoneName;
    }

    public int getBroadcastZoneID() {
        return broadcastZoneID;
    }

    public void setBroadcastZoneID(int broadcastZoneID) {
        this.broadcastZoneID = broadcastZoneID;
    }

    public List<String> getSecondaryRegionList() {
        return secondaryRegionList;
    }

    public void setSecondaryRegionList(List<String> secondaryRegionList) {
        this.secondaryRegionList = secondaryRegionList;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
