package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.ctc.domainobject.CtcBasicTrainStationInfo;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Created by Administrator on 2017/9/19.
 */
public class BasicTrainStationDetail {
    private String stationCode;
    private String stationName;
    private int planTrackNum;

    private String planArriveTime;
    private String planDepartureTime;

    private StationTypeEnum stationType;

    private float distance;

    //到达出发的相对天数
    private int arriveDelayDays;
    private int departureDelayDays;

    private String startStation;//stationName
    private String finalStation;//stationName

    public BasicTrainStationDetail() {

    }

    public BasicTrainStationDetail(BasicTrainStationInfo basicTrainStationInfo) {
        this.stationCode = basicTrainStationInfo.getStationInfo().getStationCode();
        this.stationName = basicTrainStationInfo.getStationInfo().getStationName();
        this.planTrackNum = basicTrainStationInfo.getPlanedTrackNum();
        this.planArriveTime = DateTimeFormatterUtil.convertTimeToString(basicTrainStationInfo.getPlanedArriveTime());
        this.planDepartureTime = DateTimeFormatterUtil.convertTimeToString(basicTrainStationInfo.getPlanedDepartureTime());
        this.stationType = basicTrainStationInfo.getStationType();
        this.distance = basicTrainStationInfo.getMileageFromStartStation();
        this.arriveDelayDays = basicTrainStationInfo.getArriveDelayDays();
        this.departureDelayDays = basicTrainStationInfo.getDepartureDelayDays();
        this.startStation = basicTrainStationInfo.getStartStation();
        this.finalStation = basicTrainStationInfo.getFinalStation();
    }

    public BasicTrainStationDetail(CtcBasicTrainStationInfo ctcBasicTrainStationInfo, LocalDate startDate, String startStation, String finalStation) {
        this.stationCode = ctcBasicTrainStationInfo.getStationCode();
        this.stationName = "--";
        this.planTrackNum = ctcBasicTrainStationInfo.getPlanedTrackNum();
        LocalDateTime planedArriveTime = ctcBasicTrainStationInfo.getPlanedArriveTime();
        LocalDateTime planedDepartureTime = ctcBasicTrainStationInfo.getPlanedDepartureTime();
        this.stationType = ctcBasicTrainStationInfo.getStationType();
        this.planArriveTime = DateTimeFormatterUtil.convertDatetimeToTimeString(planedArriveTime);
        this.planDepartureTime = DateTimeFormatterUtil.convertDatetimeToTimeString(planedDepartureTime);
        this.distance = 0;
        this.startStation = startStation;
        this.finalStation = finalStation;
        //计算相对时间
        //到达相对时间
        if (null != planedArriveTime) {
            LocalDate planedArriveDate = DateTimeFormatterUtil.convertDatetimeToDate(planedArriveTime);
            Period period = Period.between(startDate, planedArriveDate);
            this.arriveDelayDays = period.getDays();
        } else {
            this.arriveDelayDays = 0;
        }
        //出发相对时间
        if (null != planedDepartureTime) {
            LocalDate planedDepartureDate = DateTimeFormatterUtil.convertDatetimeToDate(planedDepartureTime);
            Period period = Period.between(startDate, planedDepartureDate);
            this.departureDelayDays = period.getDays();
        } else {
            this.departureDelayDays = 0;
        }


    }

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

    public int getPlanTrackNum() {
        return planTrackNum;
    }

    public void setPlanTrackNum(int planTrackNum) {
        this.planTrackNum = planTrackNum;
    }

    public String getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(String planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    public String getPlanDepartureTime() {
        return planDepartureTime;
    }

    public void setPlanDepartureTime(String planDepartureTime) {
        this.planDepartureTime = planDepartureTime;
    }

    public StationTypeEnum getStationType() {
        return stationType;
    }

    public void setStationType(StationTypeEnum stationType) {
        this.stationType = stationType;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getStationCode().hashCode();
        result = 31 * result + this.getStationName().hashCode();
        result = 31 * result + this.getPlanArriveTime().hashCode();
        result = 31 * result + this.getPlanDepartureTime().hashCode();
        result = 31 * result + this.getPlanTrackNum();
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
            BasicTrainStationDetail outerObj = (BasicTrainStationDetail) obj;
            return this.getStationCode().equals(outerObj.getStationCode())
                    && this.getStationName().equals(outerObj.getStationName())
                    && this.getPlanArriveTime().equals(outerObj.getPlanArriveTime())
                    && this.getPlanDepartureTime().equals(outerObj.getPlanDepartureTime())
                    && this.getPlanTrackNum() == outerObj.getPlanTrackNum();
        }
        return false;
    }


    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(String finalStation) {
        this.finalStation = finalStation;
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
