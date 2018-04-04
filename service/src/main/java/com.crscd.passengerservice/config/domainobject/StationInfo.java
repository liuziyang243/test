package com.crscd.passengerservice.config.domainobject;

import com.crscd.passengerservice.config.po.StationConfigBean;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 11:52
 */
public class StationInfo {
    /* 站名,由站码确定 */
    private String stationName;
    /* 站码 */
    private String stationCode;
    /* 里程 */
    private float mileage;
    /* 局码 */
    private String bureauCode;

    public StationInfo(StationConfigBean bean) {
        this.stationName = bean.getStationName();
        this.stationCode = bean.getStationCode();
        this.bureauCode = bean.getBureauCode();
        this.mileage = bean.getMileage();
    }

    public String getBureauCode() {
        return bureauCode;
    }

    public float getMileage() {
        return mileage;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StationInfo info = (StationInfo) o;

        return stationName.equals(info.stationName);
    }

    @Override
    public int hashCode() {
        return stationName.hashCode();
    }
}
