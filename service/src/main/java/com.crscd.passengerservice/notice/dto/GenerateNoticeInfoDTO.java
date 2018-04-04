package com.crscd.passengerservice.notice.dto;

import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;

import java.util.HashMap;

/**
 * @author lzy
 * Date: 2017/8/29
 * Time: 14:06
 */
public class GenerateNoticeInfoDTO {
    /* 组id */
    private long noticeGroup;
    /* 生成notice的源 */
    private SenderEnum sender;
    /* 接收notice消息的对象，即修改对象 */
    private ReceiverEnum receiver;
    /* 生成notice消息的用户，也可能是系统 */
    private String generateUser;

    /*********** notice消息体 ************/
    private String trainNum;
    private String stationName;
    private String planDate;
    private HashMap<NoticeModifyEnum, String> modifiedDataMap;

    public GenerateNoticeInfoDTO() {
        this.modifiedDataMap = new HashMap<>();
    }

    public long getNoticeGroup() {
        return noticeGroup;
    }

    public void setNoticeGroup(long noticeGroup) {
        this.noticeGroup = noticeGroup;
    }

    public SenderEnum getSender() {
        return sender;
    }

    public void setSender(SenderEnum sender) {
        this.sender = sender;
    }

    public ReceiverEnum getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiverEnum receiver) {
        this.receiver = receiver;
    }

    public String getGenerateUser() {
        return generateUser;
    }

    public void setGenerateUser(String generateUser) {
        this.generateUser = generateUser;
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

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public HashMap<NoticeModifyEnum, String> getModifiedDataMap() {
        return modifiedDataMap;
    }

    public void setModifiedDataMap(HashMap<NoticeModifyEnum, String> modifiedDataMap) {
        this.modifiedDataMap = modifiedDataMap;
    }
}
