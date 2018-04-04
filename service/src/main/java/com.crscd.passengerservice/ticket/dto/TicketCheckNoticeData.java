package com.crscd.passengerservice.ticket.dto;

import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;

import java.util.HashMap;

/**
 * Created by cuishiqing on 2017/9/4.
 * 用于生成来自客票系统的notice
 */
public class TicketCheckNoticeData {
    // 组标识
    private long noticeGroup;
    // 车次号
    private String trainNum;
    // 站名
    private String stationName;
    // 计划日期
    private String planDate;
    // 修改记录
    private HashMap<NoticeModifyEnum, String> modifiedDataMap;

    public TicketCheckNoticeData() {
        this.modifiedDataMap = new HashMap<>();
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

    public long getNoticeGroup() {
        return noticeGroup;
    }

    public void setNoticeGroup(long noticeGroup) {
        this.noticeGroup = noticeGroup;
    }
}
