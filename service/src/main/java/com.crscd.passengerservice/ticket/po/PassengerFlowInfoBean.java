package com.crscd.passengerservice.ticket.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/8/24.
 */
public class PassengerFlowInfoBean {
    /* ID */
    @OrmIgnore
    private long id;
    /*车次*/
    private String trainNum;
    /*站码*/
    private String stationName;
    /*日期*/
    private String ticketDate;
    /*上车人数*/
    private int passengerGetOn;
    /*下车人数*/
    private int passengerGetOff;
    /*信息更新时间*/
    private String updateTime;

    public PassengerFlowInfoBean() {
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

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String date) {
        this.ticketDate = ticketDate;
    }

    public int getPassengerGetOn() {
        return passengerGetOn;
    }

    public void setPassengerGetOn(int passengerGetOn) {
        this.passengerGetOn = passengerGetOn;
    }

    public int getPassengerGetOff() {
        return passengerGetOff;
    }

    public void setPassengerGetOff(int passengerGetOff) {
        this.passengerGetOff = passengerGetOff;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
