package com.crscd.passengerservice.ctc.domainobject;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Create by: liujiang
 * Date: 2016/7/8
 * Time: 10:43
 */
public class CtcTrainTimeUnit {
    private int uniqueId;//列车id 保证一个调度区段内唯一
    private int dispatchAreaId;//调度区段id
    private String trainNum;//车次号
    private String stationCode;//报点车站站码
    private int trackNum;//股道编号
    private int timeProperty;//报点属性
    private int timeType;//报点类型
    private LocalDateTime time;//到发时间
    private LocalTime timeChange;//早晚点时间

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getDispatchAreaId() {
        return dispatchAreaId;
    }

    public void setDispatchAreaId(int dispatchAreaId) {
        this.dispatchAreaId = dispatchAreaId;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public int getTrackNum() {
        return trackNum;
    }

    public void setTrackNum(int trackNum) {
        this.trackNum = trackNum;
    }

    public int getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(int timeProperty) {
        this.timeProperty = timeProperty;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalTime getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(LocalTime timeChange) {
        this.timeChange = timeChange;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof CtcTrainTimeUnit) {
            CtcTrainTimeUnit u = (CtcTrainTimeUnit) obj;
            return this.uniqueId == u.uniqueId
                    && this.dispatchAreaId == u.dispatchAreaId
                    && this.trainNum.equals(u.trainNum)
                    && this.stationCode.equals(u.stationCode)
                    && this.trackNum == u.trackNum
                    && this.timeProperty == u.timeProperty
                    && this.timeType == u.timeType
                    && this.time.equals(u.time)
                    && this.timeChange.equals(u.timeChange);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getUniqueId();
        result = 31 * result + this.getDispatchAreaId();
        result = 31 * result + this.getTrainNum().hashCode();
        result = 31 * result + this.getStationCode().hashCode();
        result = 31 * result + this.getTrackNum();
        result = 31 * result + this.getTimeProperty();
        result = 31 * result + this.getTimeType();
        result = 31 * result + this.getTime().hashCode();
        result = 31 * result + this.getTimeChange().hashCode();
        return result;
    }
}
