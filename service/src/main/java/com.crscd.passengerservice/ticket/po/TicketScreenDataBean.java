package com.crscd.passengerservice.ticket.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public class TicketScreenDataBean {
    /* ID */
    @OrmIgnore
    private long id;
    /*车次*/
    private String trainNum;
    /*站码*/
    private String stationName;
    /*席别*/
    private String seatName;
    /*余票数量*/
    private int ticketNum;
    /*始发站*/
    private String startStation;
    /*终点站*/
    private String finalStation;
    /*计划到达时间*/
    private String planArriveTime;
    /*计划出发时间*/
    private String planDepartureTime;
    /*发车日期*/
    private String ticketDate;

    public TicketScreenDataBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
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

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }
}
