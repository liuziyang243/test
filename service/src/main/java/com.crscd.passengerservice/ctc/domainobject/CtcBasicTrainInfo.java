package com.crscd.passengerservice.ctc.domainobject;


import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

import java.util.LinkedHashMap;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 8:17
 */
public class CtcBasicTrainInfo {
    /* 列车车次号 */
    private String trainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStationCode;
    /* 终到站 */
    private String finalStationCode;
    /* 车站列表 key: stationCode */
    private LinkedHashMap<String, CtcBasicTrainStationInfo> stationInfoList;

    public CtcBasicTrainInfo() {
        startStationCode = "--";
        finalStationCode = "--";
        stationInfoList = new LinkedHashMap<>();
    }

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

    public TrainDirectionEnum getTrainDirection() {
        return trainDirection;
    }

    public void setTrainDirection(TrainDirectionEnum trainDirection) {
        this.trainDirection = trainDirection;
    }

    public String getStartStationCode() {
        return startStationCode;
    }

    public void setStartStationCode(String startStationCode) {
        this.startStationCode = startStationCode;
    }

    public String getFinalStationCode() {
        return finalStationCode;
    }

    public void setFinalStationCode(String finalStationCode) {
        this.finalStationCode = finalStationCode;
    }

    public LinkedHashMap<String, CtcBasicTrainStationInfo> getStationInfoList() {
        return stationInfoList;
    }

    public void setStationInfoList(LinkedHashMap<String, CtcBasicTrainStationInfo> stationInfoList) {
        this.stationInfoList = stationInfoList;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getTrainNum().hashCode();
        result = 31 * result + this.getTrainType().hashCode();
        result = 31 * result + this.getTrainDirection().hashCode();
        result = 31 * result + this.getStartStationCode().hashCode();
        result = 31 * result + this.getFinalStationCode().hashCode();
        result = 31 * result + this.getStationInfoList().hashCode();
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
            CtcBasicTrainInfo outerObj = (CtcBasicTrainInfo) obj;
            return this.getTrainNum().equals(outerObj.getTrainNum())
                    && this.getTrainType().equals(outerObj.getTrainType())
                    && this.getTrainDirection().equals(outerObj.getTrainDirection())
                    && this.getStartStationCode().equals(outerObj.getStartStationCode())
                    && this.getFinalStationCode().equals(outerObj.getFinalStationCode())
                    && this.getStationInfoList().equals(outerObj.getStationInfoList());
        }
        return false;
    }
}
