package com.crscd.passengerservice.ctc.domainobject;


import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;

import java.time.LocalDateTime;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 9:53
 */
public class CtcBasicTrainStationInfo {
    /* 车站信息 */
    private String stationCode;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车在当前车站的计划出发时间 */
    private LocalDateTime planedDepartureTime;
    /* 列车在当前车站的计划到达时间 */
    private LocalDateTime planedArriveTime;
    /* 计划股道号 */
    private int planedTrackNum;

    private String arriveTrainNum;

    private String departureTrainNum;


    public LocalDateTime getPlanedDepartureTime() {
        // 终到站没有发车时间
        if (this.stationType.equals(StationTypeEnum.FINAL)) {
            return null;
        }
        return planedDepartureTime;
    }

    public void setPlanedDepartureTime(LocalDateTime planedDepartureTime) {
        // 终到站没有发车时间
        /*
        if (this.stationType.equals(StationTypeEnum.FINAL)) {
            throw new IllegalArgumentException("you can't set departue time to final station!");
        }
        */
        this.planedDepartureTime = planedDepartureTime;
    }

    public LocalDateTime getPlanedArriveTime() {
        // 始发站没有到站时间
        if (this.stationType.equals(StationTypeEnum.START)) {
            return null;
        }
        return planedArriveTime;
    }

    public void setPlanedArriveTime(LocalDateTime planedArriveTime) {
        // 始发站没有到站时间
        /*
        if (this.stationType.equals(StationTypeEnum.START)) {
            throw new IllegalArgumentException("you can't set arrive time to start station!");
        }
        */
        this.planedArriveTime = planedArriveTime;
    }

    public int getPlanedTrackNum() {
        return planedTrackNum;
    }

    public void setPlanedTrackNum(int planedTrackNum) {
        this.planedTrackNum = planedTrackNum;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public StationTypeEnum getStationType() {
        return stationType;
    }

    public void setStationType(StationTypeEnum stationType) {
        this.stationType = stationType;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getStationCode().hashCode();
        result = 31 * result + this.getStationType().hashCode();
        result = 31 * result + this.getPlanedTrackNum();
        result = 31 * result + this.getPlanedArriveTime().hashCode();
        result = 31 * result + this.getPlanedDepartureTime().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() == obj.getClass()) {
            CtcBasicTrainStationInfo outerObj = (CtcBasicTrainStationInfo) obj;
            return this.getStationCode().equals(outerObj.getStationCode())
                    && this.getStationType().equals(outerObj.getStationType())
                    && this.getPlanedTrackNum() == outerObj.getPlanedTrackNum()
                    && this.getPlanedArriveTime().equals(outerObj.getPlanedArriveTime())
                    && this.getPlanedDepartureTime().equals(outerObj.getPlanedDepartureTime());
        }
        return false;
    }

    public String getArriveTrainNum() {
        return arriveTrainNum;
    }

    public void setArriveTrainNum(String arriveTrainNum) {
        this.arriveTrainNum = arriveTrainNum;
    }

    public String getDepartureTrainNum() {
        return departureTrainNum;
    }

    public void setDepartureTrainNum(String departureTrainNum) {
        this.departureTrainNum = departureTrainNum;
    }
}
