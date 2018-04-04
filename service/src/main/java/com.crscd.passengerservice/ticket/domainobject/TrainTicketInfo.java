package com.crscd.passengerservice.ticket.domainobject;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public class TrainTicketInfo {
    private String trainNum;
    private String stationCode;
    private String date;
    private String seatName;
    private int ticketNum;
    private String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof TrainTicketInfo) {
            TrainTicketInfo u = (TrainTicketInfo) obj;
            return this.trainNum.equals(u.trainNum)
                    && this.stationCode.equals(u.stationCode)
                    && this.date.equals(u.date)
                    && this.seatName.equals(u.seatName)
                    && this.ticketNum == u.ticketNum;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = trainNum.hashCode();
        result = 31 * result + stationCode.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + seatName.hashCode();
        result = 31 * result + ticketNum;
        return result;
    }
}
