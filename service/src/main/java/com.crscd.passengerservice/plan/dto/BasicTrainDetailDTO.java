package com.crscd.passengerservice.plan.dto;

import com.crscd.framework.translation.annotation.TranslateAttribute;
import com.crscd.passengerservice.plan.domainobject.BasicTrainDetail;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationDetail;
import com.crscd.passengerservice.plan.enumtype.AnalyseEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */
public class BasicTrainDetailDTO {
    private String trainNum;
    @TranslateAttribute
    private String trainTypeTrans;

    //stationName
    @TranslateAttribute
    private String startStation;
    @TranslateAttribute
    //stationName
    private String finalStation;
    //本站到达时间
    private String arriveTimeThisStation = "--";
    //本站发车时间
    private String departureTimeThisStation = "--";
    //股道
    private int trackNumThisStation = -1;


    private AnalyseEnum analyseResult;
    @TranslateAttribute
    private String analyseResultTrans;

    private List<BasicTrainStationDetailDTO> basicTrainStationDetailDTOs;


    public BasicTrainDetailDTO() {

    }

    public BasicTrainDetailDTO(BasicTrainDetail basicTrainDetail) {
        this.trainNum = basicTrainDetail.getTrainNum();
        this.trainTypeTrans = TrainTypeEnum.getTrainTypeString(basicTrainDetail.getTrainType());
        this.startStation = basicTrainDetail.getStartStation();
        this.finalStation = basicTrainDetail.getFinalStation();
        this.arriveTimeThisStation = basicTrainDetail.getArriveTimeThisStation();
        this.departureTimeThisStation = basicTrainDetail.getDepartureTimeThisStation();
        this.trackNumThisStation = basicTrainDetail.getTrackNumThisStation();
        this.analyseResultTrans = AnalyseEnum.getTypeString(basicTrainDetail.getAnalyseResult());
        this.analyseResult = basicTrainDetail.getAnalyseResult();
        List<BasicTrainStationDetail> basicTrainStationDetailList = basicTrainDetail.getBasicTrainStationDetailList();
        if (basicTrainStationDetailList.size() != 0) {
            List<BasicTrainStationDetailDTO> basicTrainStationDetailDTOs = new ArrayList<>();
            for (BasicTrainStationDetail basicTrainStationDetail : basicTrainStationDetailList) {
                BasicTrainStationDetailDTO basicTrainStationDetailDTO = new BasicTrainStationDetailDTO(basicTrainStationDetail);
                basicTrainStationDetailDTOs.add(basicTrainStationDetailDTO);
            }
            this.basicTrainStationDetailDTOs = basicTrainStationDetailDTOs;
        }
    }


    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getTrainTypeTrans() {
        return trainTypeTrans;
    }

    public void setTrainTypeTrans(String trainTypeTrans) {
        this.trainTypeTrans = trainTypeTrans;
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

    public String getDepartureTimeThisStation() {
        return departureTimeThisStation;
    }

    public void setDepartureTimeThisStation(String departureTimeThisStation) {
        this.departureTimeThisStation = departureTimeThisStation;
    }

    public int getTrackNumThisStation() {
        return trackNumThisStation;
    }

    public void setTrackNumThisStation(int trackNumThisStation) {
        this.trackNumThisStation = trackNumThisStation;
    }

    public String getAnalyseResultTrans() {
        return analyseResultTrans;
    }

    public void setAnalyseResultTrans(String analyseResultTrans) {
        this.analyseResultTrans = analyseResultTrans;
    }

    public List<BasicTrainStationDetailDTO> getBasicTrainStationDetailDTOs() {
        return basicTrainStationDetailDTOs;
    }

    public void setBasicTrainStationDetailDTOs(List<BasicTrainStationDetailDTO> basicTrainStationDetailDTOs) {
        this.basicTrainStationDetailDTOs = basicTrainStationDetailDTOs;
    }

    public AnalyseEnum getAnalyseResult() {
        return analyseResult;
    }

    public void setAnalyseResult(AnalyseEnum analyseResult) {
        this.analyseResult = analyseResult;
    }
}
