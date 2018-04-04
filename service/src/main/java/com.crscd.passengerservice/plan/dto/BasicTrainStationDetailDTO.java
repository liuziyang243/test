package com.crscd.passengerservice.plan.dto;

import com.crscd.framework.translation.annotation.TranslateAttribute;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationDetail;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;

/**
 * Created by Administrator on 2017/9/19.
 */
public class BasicTrainStationDetailDTO {
    private String stationCode;
    @TranslateAttribute
    private String stationName;
    private int planTrackNum;

    private String planArriveTime;
    private String planDepartureTime;

    @TranslateAttribute
    private String stationTypeTrans;

    //到达出发的相对天数
    private int arriveDelayDays;
    private int departureDelayDays;

    public BasicTrainStationDetailDTO() {

    }

    public BasicTrainStationDetailDTO(BasicTrainStationDetail basicTrainStationDetail) {

        this.stationCode = basicTrainStationDetail.getStationCode();
        this.stationName = basicTrainStationDetail.getStationName();
        this.planTrackNum = basicTrainStationDetail.getPlanTrackNum();
        this.planArriveTime = basicTrainStationDetail.getPlanArriveTime();
        this.planDepartureTime = basicTrainStationDetail.getPlanDepartureTime();
        this.stationTypeTrans = StationTypeEnum.getStationTypeString(basicTrainStationDetail.getStationType());
        this.arriveDelayDays = basicTrainStationDetail.getArriveDelayDays();
        this.departureDelayDays = basicTrainStationDetail.getDepartureDelayDays();
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

    public String getStationTypeTrans() {
        return stationTypeTrans;
    }

    public void setStationTypeTrans(String stationTypeTrans) {
        this.stationTypeTrans = stationTypeTrans;
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
