package com.crscd.passengerservice.ctc.dto;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * @author lzy
 * Created on 2017/9/13.
 */
public class CtcNoticeData {
    private long noticeGroup;
    private String trainNum;
    private String stationName;
    private String planDate;
    private HashMap<NoticeModifyEnum, String> modifiedDataMap;

    public CtcNoticeData() {
        this.modifiedDataMap = new HashMap<>();
    }

    public CtcNoticeData(long noticeGroup, String trainNum, String stationName, LocalDate planDate, HashMap<NoticeModifyEnum, String> modifiedDataMap) {
        this.noticeGroup = noticeGroup;
        this.trainNum = trainNum;
        this.stationName = stationName;
        this.planDate = DateTimeFormatterUtil.convertDateToString(planDate);
        this.modifiedDataMap = modifiedDataMap;
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
