package com.crscd.passengerservice.plan.po;

import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 11:21
 */
public class BasicTrainStationInfoBean {
    @OrmIgnore
    private long id;
    /**
     * 列车车次号 <-> 关联BasicPlanBean
     */
    private String trainNum;
    /** 站名,由站码确定 */
    private String stationName;
    /** 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /** 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /** 为了能够兼容跨天的车次，到达时间可以+1天 */
    /** 到达出发的相对天数 */
    private int arriveDelayDays;
    private int departureDelayDays;
    /** 计划股道号 */
    private int planedTrackNum;
    /** 起始站  */
    private String startStation;
    /** 终到站  */
    private String finalStation;

    /**
     * 生成一个新的车站信息
     */
    public BasicTrainStationInfoBean(String trainNum, BasicTrainStationInfo info) {
        this.id = info.getId();
        this.trainNum = trainNum;
        this.stationName = info.getStationInfo().getStationName();
        this.planedDepartureTime = DateTimeFormatterUtil.convertTimeToString(info.getPlanedDepartureTime());
        this.planedArriveTime = DateTimeFormatterUtil.convertTimeToString(info.getPlanedArriveTime());
        this.planedTrackNum = info.getPlanedTrackNum();
        this.startStation = info.getStartStation();
        this.finalStation = info.getFinalStation();
        this.arriveDelayDays = info.getArriveDelayDays();
        this.departureDelayDays = info.getDepartureDelayDays();
    }

    public BasicTrainStationInfoBean() {
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

    public String getPlanedDepartureTime() {
        return planedDepartureTime;
    }

    public void setPlanedDepartureTime(String planedDepartureTime) {
        this.planedDepartureTime = planedDepartureTime;
    }

    public String getPlanedArriveTime() {
        return planedArriveTime;
    }

    public void setPlanedArriveTime(String planedArriveTime) {
        this.planedArriveTime = planedArriveTime;
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

    public int getPlanedTrackNum() {
        return planedTrackNum;
    }

    public void setPlanedTrackNum(int planedTrackNum) {
        this.planedTrackNum = planedTrackNum;
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
}
