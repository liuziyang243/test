package com.crscd.passengerservice.display.format.domainobject;

import java.util.List;

/**
 * Created by cuishiqing on 2017/9/5.
 */
public class PlanDataElement {
    //车次
    private String trainNum;
    //实际到达时间
    private String actualArriveTime;
    //实际出发时间
    private String actualDepartureTime;
    //始发站
    private String startStation;
    //终到站
    private String finalStation;
    //检票状态（未检票:unchecked、正在检票:checking、停止检票：stopcheck）
    private String checkInState;
    //到点状态（晚点:delay、正点：ontime、提早：advance）
    private String arriveState;
    //发点状态（晚点:delay、正点：ontime、提早：advance）
    private String departureState;
    //候车区
    private List<String> waitZone;
    //股道
    private String trackNumber;
    //站台
    private String platform;
    //检票口
    private List<String> entrancePort;
    //停开标志
    private String terminated;

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getActualArriveTime() {
        return actualArriveTime;
    }

    public void setActualArriveTime(String actualArriveTime) {
        this.actualArriveTime = actualArriveTime;
    }

    public String getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(String actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
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

    public String getCheckInState() {
        return checkInState;
    }

    public void setCheckInState(String checkInState) {
        this.checkInState = checkInState;
    }

    public String getArriveState() {
        return arriveState;
    }

    public void setArriveState(String arriveState) {
        this.arriveState = arriveState;
    }

    public String getDepartureState() {
        return departureState;
    }

    public void setDepartureState(String departureState) {
        this.departureState = departureState;
    }

    public List<String> getWaitZone() {
        return waitZone;
    }

    public void setWaitZone(List<String> waitZone) {
        this.waitZone = waitZone;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<String> getEntrancePort() {
        return entrancePort;
    }

    public void setEntrancePort(List<String> entrancePort) {
        this.entrancePort = entrancePort;
    }

    public String getTerminated() {
        return terminated;
    }

    public void setTerminated(String terminated) {
        this.terminated = terminated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlanDataElement that = (PlanDataElement) o;

        if (trainNum != null ? !trainNum.equals(that.trainNum) : that.trainNum != null) {
            return false;
        }
        if (actualArriveTime != null ? !actualArriveTime.equals(that.actualArriveTime) : that.actualArriveTime != null) {
            return false;
        }
        if (actualDepartureTime != null ? !actualDepartureTime.equals(that.actualDepartureTime) : that.actualDepartureTime != null) {
            return false;
        }
        if (startStation != null ? !startStation.equals(that.startStation) : that.startStation != null) {
            return false;
        }
        if (finalStation != null ? !finalStation.equals(that.finalStation) : that.finalStation != null) {
            return false;
        }
        if (checkInState != null ? !checkInState.equals(that.checkInState) : that.checkInState != null) {
            return false;
        }
        if (arriveState != null ? !arriveState.equals(that.arriveState) : that.arriveState != null) {
            return false;
        }
        if (departureState != null ? !departureState.equals(that.departureState) : that.departureState != null) {
            return false;
        }
        if (waitZone != null ? !waitZone.equals(that.waitZone) : that.waitZone != null) {
            return false;
        }
        if (trackNumber != null ? !trackNumber.equals(that.trackNumber) : that.trackNumber != null) {
            return false;
        }
        if (platform != null ? !platform.equals(that.platform) : that.platform != null) {
            return false;
        }
        if (entrancePort != null ? !entrancePort.equals(that.entrancePort) : that.entrancePort != null) {
            return false;
        }
        return terminated != null ? terminated.equals(that.terminated) : that.terminated == null;
    }

    @Override
    public int hashCode() {
        int result = trainNum != null ? trainNum.hashCode() : 0;
        result = 31 * result + (actualArriveTime != null ? actualArriveTime.hashCode() : 0);
        result = 31 * result + (actualDepartureTime != null ? actualDepartureTime.hashCode() : 0);
        result = 31 * result + (startStation != null ? startStation.hashCode() : 0);
        result = 31 * result + (finalStation != null ? finalStation.hashCode() : 0);
        result = 31 * result + (checkInState != null ? checkInState.hashCode() : 0);
        result = 31 * result + (arriveState != null ? arriveState.hashCode() : 0);
        result = 31 * result + (departureState != null ? departureState.hashCode() : 0);
        result = 31 * result + (waitZone != null ? waitZone.hashCode() : 0);
        result = 31 * result + (trackNumber != null ? trackNumber.hashCode() : 0);
        result = 31 * result + (platform != null ? platform.hashCode() : 0);
        result = 31 * result + (entrancePort != null ? entrancePort.hashCode() : 0);
        result = 31 * result + (terminated != null ? terminated.hashCode() : 0);
        return result;
    }
}
