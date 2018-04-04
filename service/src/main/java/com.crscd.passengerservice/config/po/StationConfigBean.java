package com.crscd.passengerservice.config.po;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 13:09
 */
public class StationConfigBean {
    /* 站码 唯一值 */
    private String stationCode;
    /* 站名,由站码确定 */
    private String stationName;
    /* 里程 */
    private float mileage;
    /* 局码 */
    private String bureauCode;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public String getBureauCode() {
        return bureauCode;
    }

    public void setBureauCode(String bureauCode) {
        this.bureauCode = bureauCode;
    }
}
