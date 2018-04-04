package com.crscd.passengerservice.config.po;

import com.crscd.passengerservice.config.enumtype.FirstRegionEnum;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/31
 * Time: 14:22
 */
public class StationRegionConfigBean {
    // ID
    private long id;
    // 二级区名称
    // (可以和一级区名称相同，如果一级区和二级区事实指的是同一个位置)
    private String secondaryRegion;
    // 一级区名称
    private FirstRegionEnum firstRegion;
    // 所属车站名称
    private String stationName;
    // 二级区对应的广播区列表
    private List<String> broadcastAreaList;

    public StationRegionConfigBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecondaryRegion() {
        return secondaryRegion;
    }

    public void setSecondaryRegion(String secondaryRegion) {
        this.secondaryRegion = secondaryRegion;
    }

    public FirstRegionEnum getFirstRegion() {
        return firstRegion;
    }

    public void setFirstRegion(FirstRegionEnum firstRegion) {
        this.firstRegion = firstRegion;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<String> getBroadcastAreaList() {
        return broadcastAreaList;
    }

    public void setBroadcastAreaList(List<String> broadcastAreaList) {
        this.broadcastAreaList = broadcastAreaList;
    }
}
