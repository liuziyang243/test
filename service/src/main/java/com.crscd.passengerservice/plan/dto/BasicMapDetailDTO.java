package com.crscd.passengerservice.plan.dto;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */
public class BasicMapDetailDTO {
    private String uuid;
    private String receiveTime;
    private List<BasicTrainDetailDTO> ctcTrainDetailList;//ctc 的基本图信息分析
    private List<BasicTrainDetailDTO> TrainDetailList;//平台 基本图信息分析

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }


    public List<BasicTrainDetailDTO> getCtcTrainDetailList() {
        return ctcTrainDetailList;
    }

    public void setCtcTrainDetailList(List<BasicTrainDetailDTO> ctcTrainDetailList) {
        this.ctcTrainDetailList = ctcTrainDetailList;
    }

    public List<BasicTrainDetailDTO> getTrainDetailList() {
        return TrainDetailList;
    }

    public void setTrainDetailList(List<BasicTrainDetailDTO> trainDetailList) {
        TrainDetailList = trainDetailList;
    }
}
