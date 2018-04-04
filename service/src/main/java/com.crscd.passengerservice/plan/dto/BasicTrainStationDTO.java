package com.crscd.passengerservice.plan.dto;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;

/**
 * @author lzy
 * Date: 2017/8/17
 * Time: 10:34
 */
public class BasicTrainStationDTO {
    /* 车站信息 */
    private String stationName;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 为了能够兼容跨天的车次，到达时间可以+1天 */
    /* 到达出发的相对天数 */
    private int arriveDelayDays;
    private int departureDelayDays;
    /* 计划股道号 */
    private int planedTrackNum;
    /* 从起始站的车站里程 */
    private float mileageFromStartStation;

    public BasicTrainStationDTO() {
    }

    public BasicTrainStationDTO(BasicTrainStationInfo info) {
        this.stationName = info.getStationInfo().getStationName();
        this.stationType = info.getStationType();
        this.planedDepartureTime = DateTimeFormatterUtil.convertTimeToString(info.getPlanedDepartureTime());
        this.planedArriveTime = DateTimeFormatterUtil.convertTimeToString(info.getPlanedArriveTime());
        this.planedTrackNum = info.getPlanedTrackNum();
        this.mileageFromStartStation = info.getMileageFromStartStation();
        this.arriveDelayDays = info.getArriveDelayDays();
        this.departureDelayDays = info.getDepartureDelayDays();
    }


    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public StationTypeEnum getStationType() {
        return stationType;
    }

    public void setStationType(StationTypeEnum stationType) {
        this.stationType = stationType;
    }

    public String getPlanedDepartureTime() {
        return planedDepartureTime;
    }

    public void setPlanedDepartureTime(String planedDepartureTime) {
        this.planedDepartureTime = planedDepartureTime;
    }

    public String getPlanedArriveTime() {
        return planedArriveTime;
    }

    public void setPlanedArriveTime(String planedArriveTime) {
        this.planedArriveTime = planedArriveTime;
    }

    public int getPlanedTrackNum() {
        return planedTrackNum;
    }

    public void setPlanedTrackNum(int planedTrackNum) {
        this.planedTrackNum = planedTrackNum;
    }

    public float getMileageFromStartStation() {
        return mileageFromStartStation;
    }

    public void setMileageFromStartStation(float mileageFromStartStation) {
        this.mileageFromStartStation = mileageFromStartStation;
    }

    public int getArriveDelayDays() {
        return arriveDelayDays;
    }

    public void setArriveDelayDays(int arriveDelayDays) {
        this.arriveDelayDays = arriveDelayDays;
    }

    public int getDepartureDelayDays() {
        return departureDelayDays;
    }

    public void setDepartureDelayDays(int departureDelayDays) {
        this.departureDelayDays = departureDelayDays;
    }
}
