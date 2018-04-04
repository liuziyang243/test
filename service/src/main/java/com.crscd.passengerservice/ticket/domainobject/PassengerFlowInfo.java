package com.crscd.passengerservice.ticket.domainobject;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public class PassengerFlowInfo {
    /*车次*/
    private String trainNum;
    /*站码*/
    private String stationCode;
    /*日期*/
    private String date;
    /*上车人数*/
    private int passengerGetOn;
    /*下车人数*/
    private int passengerGetOff;
    /*信息更新时间*/
    private String updateTime;

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof PassengerFlowInfo) {
            PassengerFlowInfo u = (PassengerFlowInfo) obj;
            return this.trainNum.equals(u.trainNum)
                    && this.stationCode.equals(u.stationCode)
                    && this.date.equals(u.date)
                    && this.passengerGetOn == u.passengerGetOn
                    && this.passengerGetOff == u.passengerGetOff;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = trainNum.hashCode();
        result = 31 * result + stationCode.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + passengerGetOn;
        result = 31 * result + passengerGetOff;
        return result;
    }
}
