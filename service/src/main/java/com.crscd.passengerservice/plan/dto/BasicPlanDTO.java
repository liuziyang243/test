package com.crscd.passengerservice.plan.dto;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 9:06
 */
public class BasicPlanDTO {
    /* 列车车次号 */
    private String trainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    /* 用于显示本站发车时间 */
    private String arriveTime;
    /* 用于显示本站到达时间 */
    private String departureTime;

    public BasicPlanDTO() {
        arriveTime = "--";
        departureTime = "--";
    }

    public BasicPlanDTO(BasicPlan plan) {
        this();
        this.trainNum = plan.getTrainNum();
        this.trainType = plan.getTrainType();
        this.trainDirection = plan.getTrainDirection();
        this.startStation = plan.getStartStation().getStationName();
        this.finalStation = plan.getFinalStation().getStationName();
        this.validPeriodStart = DateTimeFormatterUtil.convertDateToString(plan.getValidPeriodStart());
        this.validPeriodEnd = DateTimeFormatterUtil.convertDateToString(plan.getValidPeriodEnd());
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public TrainTypeEnum getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainTypeEnum trainType) {
        this.trainType = trainType;
    }

    public TrainDirectionEnum getTrainDirection() {
        return trainDirection;
    }

    public void setTrainDirection(TrainDirectionEnum trainDirection) {
        this.trainDirection = trainDirection;
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

    public String getValidPeriodStart() {
        return validPeriodStart;
    }

    public void setValidPeriodStart(String validPeriodStart) {
        this.validPeriodStart = validPeriodStart;
    }

    public String getValidPeriodEnd() {
        return validPeriodEnd;
    }

    public void setValidPeriodEnd(String validPeriodEnd) {
        this.validPeriodEnd = validPeriodEnd;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "BasicPlanDTO{" +
                "trainNum='" + trainNum + '\'' +
                ", trainType=" + trainType +
                ", trainDirection=" + trainDirection +
                ", startStation='" + startStation + '\'' +
                ", finalStation='" + finalStation + '\'' +
                ", validPeriodStart='" + validPeriodStart + '\'' +
                ", validPeriodEnd='" + validPeriodEnd + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }
}
