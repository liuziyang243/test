package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.domainobject.StationInfo;
import com.crscd.passengerservice.plan.dto.BasicTrainStationDTO;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import com.crscd.passengerservice.plan.po.BasicTrainStationInfoBean;
import com.crscd.passengerservice.plan.util.PlanHelper;

import java.time.LocalTime;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 9:53
 */
public class BasicTrainStationInfo {
    /* ID */
    private long id;
    /* 车站信息 */
    private StationInfo stationInfo;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车在当前车站的计划出发时间 */
    private LocalTime planedDepartureTime;
    /* 列车在当前车站的计划到达时间 */
    private LocalTime planedArriveTime;
    /* 为了能够兼容跨天的车次，到达时间可以+1天 */
    /* 到达出发的相对天数 */
    private int arriveDelayDays;
    private int departureDelayDays;
    /* 计划股道号 */
    private int planedTrackNum;
    /* 从起始站的车站里程 */
    private float mileageFromStartStation;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;

    // 从po初始化
    public BasicTrainStationInfo(BasicTrainStationInfoBean bean, ConfigManager manager) {
        this.id = bean.getId();
        this.stationInfo = manager.getStationInfoByStationName(bean.getStationName());
        this.planedArriveTime = DateTimeFormatterUtil.convertStringToTime(bean.getPlanedArriveTime());
        this.planedDepartureTime = DateTimeFormatterUtil.convertStringToTime(bean.getPlanedDepartureTime());
        this.planedTrackNum = bean.getPlanedTrackNum();
        this.startStation = bean.getStartStation();
        this.finalStation = bean.getFinalStation();
        this.arriveDelayDays = bean.getArriveDelayDays();
        this.departureDelayDays = bean.getDepartureDelayDays();
    }

    // 从dto初始化
    public BasicTrainStationInfo(BasicTrainStationDTO dto, ConfigManager manager) {
        this.stationInfo = manager.getStationInfoByStationName(dto.getStationName());
        this.planedArriveTime = DateTimeFormatterUtil.convertStringToTime(dto.getPlanedArriveTime());
        this.planedDepartureTime = DateTimeFormatterUtil.convertStringToTime(dto.getPlanedDepartureTime());
        this.planedTrackNum = dto.getPlanedTrackNum();
        this.arriveDelayDays = dto.getArriveDelayDays();
        this.departureDelayDays = dto.getDepartureDelayDays();
    }

    //从CTC车站信息初始化
    public BasicTrainStationInfo(BasicTrainStationDetail basicTrainStationDetail, ConfigManager manager) {
        this.stationInfo = manager.getStationInfoByStationCode(basicTrainStationDetail.getStationCode());
        this.startStation = basicTrainStationDetail.getStartStation();
        this.finalStation = basicTrainStationDetail.getFinalStation();
        this.planedArriveTime = DateTimeFormatterUtil.convertStringToTime(basicTrainStationDetail.getPlanArriveTime());
        this.planedDepartureTime = DateTimeFormatterUtil.convertStringToTime(basicTrainStationDetail.getPlanDepartureTime());
        this.planedTrackNum = basicTrainStationDetail.getPlanTrackNum();
        this.arriveDelayDays = basicTrainStationDetail.getArriveDelayDays();
        this.departureDelayDays = basicTrainStationDetail.getDepartureDelayDays();

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public StationInfo getStationInfo() {
        return stationInfo;
    }

    public StationTypeEnum getStationType() {
        if (this.stationInfo.getStationName().equals(startStation)) {
            return StationTypeEnum.START;
        } else if (this.stationInfo.getStationName().equals(finalStation)) {
            return StationTypeEnum.FINAL;
        } else {
            return StationTypeEnum.PASS;
        }
    }

    public LocalTime getPlanedDepartureTime() {
        // 终到站没有发车时间
        if (this.getStationType().equals(StationTypeEnum.FINAL)) {
            return null;
        }
        return planedDepartureTime;
    }

    public void setPlanedDepartureTime(LocalTime planedDepartureTime) {
        // 终到站没有发车时间
        if (this.getStationType().equals(StationTypeEnum.FINAL)) {
            throw new IllegalArgumentException("you can't set departue time to final station!");
        }
        this.planedDepartureTime = planedDepartureTime;
    }

    public LocalTime getPlanedArriveTime() {
        // 始发站没有到站时间
        if (this.getStationType().equals(StationTypeEnum.START)) {
            return null;
        }
        return planedArriveTime;
    }

    public void setPlanedArriveTime(LocalTime planedArriveTime) {
        // 始发站没有到站时间
        if (this.getStationType().equals(StationTypeEnum.START)) {
            throw new IllegalArgumentException("you can't set arrive time to start station!");
        }
        this.planedArriveTime = planedArriveTime;
    }

    public int getPlanedTrackNum() {
        return planedTrackNum;
    }

    public void setPlanedTrackNum(int planedTrackNum) {
        this.planedTrackNum = planedTrackNum;
    }

    public float getMileageFromStartStation() {
        return PlanHelper.getMileageFromStartStation(startStation, this.stationInfo.getStationName());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicTrainStationInfo info = (BasicTrainStationInfo) o;

        if (getPlanedTrackNum() != info.getPlanedTrackNum()) {
            return false;
        }
        if (!getStationInfo().equals(info.getStationInfo())) {
            return false;
        }
        if (!getPlanedDepartureTime().equals(info.getPlanedDepartureTime())) {
            return false;
        }
        return getPlanedArriveTime().equals(info.getPlanedArriveTime());
    }

    @Override
    public int hashCode() {
        int result = getStationInfo().hashCode();
        result = 31 * result + getPlanedDepartureTime().hashCode();
        result = 31 * result + getPlanedArriveTime().hashCode();
        result = 31 * result + getPlanedTrackNum();
        return result;
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
