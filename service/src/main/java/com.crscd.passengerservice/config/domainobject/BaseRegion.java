package com.crscd.passengerservice.config.domainobject;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 11:30
 */
public abstract class BaseRegion {
    /* 归属车站信息 */
    private StationInfo stationInfo;
    /* 区域名称 */
    private String name;

    BaseRegion(StationInfo info, String name) {
        this.stationInfo = info;
        this.name = name;
    }

    public StationInfo getStationInfo() {
        return stationInfo;
    }

    public String getName() {
        return name;
    }
}
