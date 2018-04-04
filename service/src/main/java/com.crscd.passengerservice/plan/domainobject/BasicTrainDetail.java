package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.enumtype.AnalyseEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */
public class BasicTrainDetail {
    private String trainNum;
    private TrainTypeEnum trainType = null;


    private String startStation;//stationName
    private String finalStation;//stationName

    private String arriveTimeThisStation = "--";//本站到达时间
    private String departureTimeThisStation = "--";//本站发车时间
    private int trackNumThisStation = -1;//股道

    private AnalyseEnum analyseResult;

    private List<BasicTrainStationDetail> basicTrainStationDetailList;

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

    public String getArriveTimeThisStation() {
        return arriveTimeThisStation;
    }

    public void setArriveTimeThisStation(String arriveTimeThisStation) {
        this.arriveTimeThisStation = arriveTimeThisStation;
    }

    public void setArriveTimeTheStation(LocalDateTime arriveTimeThisStation) {
        this.arriveTimeThisStation = DateTimeFormatterUtil.convertDatetimeToTimeString(arriveTimeThisStation);
    }

    public void setArriveTimeTheStation(LocalTime arriveTimeThisStation) {
        this.arriveTimeThisStation = DateTimeFormatterUtil.convertTimeToString(arriveTimeThisStation);
    }

    public String getDepartureTimeThisStation() {
        return departureTimeThisStation;
    }

    public void setDepartureTimeThisStation(String departureTimeThisStation) {
        this.departureTimeThisStation = departureTimeThisStation;
    }

    public void setDepartureTimeTheStation(LocalDateTime departureTimeThisStation) {
        this.departureTimeThisStation = DateTimeFormatterUtil.convertDatetimeToTimeString(departureTimeThisStation);
    }

    public void setDepartureTimeTheStation(LocalTime departureTimeThisStation) {
        this.departureTimeThisStation = DateTimeFormatterUtil.convertTimeToString(departureTimeThisStation);
    }

    public int getTrackNumThisStation() {
        return trackNumThisStation;
    }

    public void setTrackNumThisStation(int trackNumThisStation) {
        this.trackNumThisStation = trackNumThisStation;
    }

    public AnalyseEnum getAnalyseResult() {
        return analyseResult;
    }

    public void setAnalyseResult(AnalyseEnum analyseResult) {
        this.analyseResult = analyseResult;
    }


    public List<BasicTrainStationDetail> getBasicTrainStationDetailList() {
        return basicTrainStationDetailList;
    }

    public void setBasicTrainStationDetailList(List<BasicTrainStationDetail> basicTrainStationDetailList) {
        this.basicTrainStationDetailList = basicTrainStationDetailList;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getTrainNum().hashCode();
        result = 31 * result + this.getTrainType().hashCode();
        result = 31 * result + this.getStartStation().hashCode();
        result = 31 * result + this.getFinalStation().hashCode();
        result = 31 * result + this.getBasicTrainStationDetailList().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() == obj.getClass()) {
            BasicTrainDetail outerObj = (BasicTrainDetail) obj;
            return this.getTrainNum().equals(outerObj.getTrainNum())
                    && this.getTrainType().equals(outerObj.getTrainType())
                    && this.getStartStation().equals(outerObj.getStartStation())
                    && this.getFinalStation().equals(outerObj.getFinalStation())
                    && ListUtil.isSameList(this.getBasicTrainStationDetailList(), outerObj.getBasicTrainStationDetailList());
        }
        return false;
    }

}
