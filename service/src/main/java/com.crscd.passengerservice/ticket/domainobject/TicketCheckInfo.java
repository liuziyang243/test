package com.crscd.passengerservice.ticket.domainobject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public class TicketCheckInfo {
    //唯一检票计划 由站码+车次号+发车日期+检票计划类型组成
    private String uniqueCheckPlanKey;
    //站码
    private String stationCode;
    //车次号
    private String trainNum;
    //检票计划状态
    private String checkStatus;
    //始发站发车时间
    private String departureDate;
    //开检时间
    private String checkTime;
    //停检时间
    private String stopCheckTime;
    //检票进站口相关
    private Map<Integer, TicketBarrierInfo> ticketEntranceBarrierInfoMap;
    //检票出站口相关
    private Map<Integer, TicketBarrierInfo> ticketDepartureBarrierInfoMap;

    public TicketCheckInfo() {
        ticketEntranceBarrierInfoMap = new HashMap<>();
        ticketDepartureBarrierInfoMap = new HashMap<>();
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getStopCheckTime() {
        return stopCheckTime;
    }

    public void setStopCheckTime(String stopCheckTime) {
        this.stopCheckTime = stopCheckTime;
    }

    public String getUniqueCheckPlanKey() {
        return uniqueCheckPlanKey;
    }

    public void setUniqueCheckPlanKey(String uniqueCheckPlanKey) {
        this.uniqueCheckPlanKey = uniqueCheckPlanKey;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Map<Integer, TicketBarrierInfo> getTicketEntranceBarrierInfoMap() {
        return ticketEntranceBarrierInfoMap;
    }

    public void setTicketEntranceBarrierInfoMap(Map<Integer, TicketBarrierInfo> ticketEntranceBarrierInfoMap) {
        this.ticketEntranceBarrierInfoMap = ticketEntranceBarrierInfoMap;
    }

    public Map<Integer, TicketBarrierInfo> getTicketDepartureBarrierInfoMap() {
        return ticketDepartureBarrierInfoMap;
    }

    public void setTicketDepartureBarrierInfoMap(Map<Integer, TicketBarrierInfo> ticketDepartureBarrierInfoMap) {
        this.ticketDepartureBarrierInfoMap = ticketDepartureBarrierInfoMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketCheckInfo that = (TicketCheckInfo) o;

        if (!uniqueCheckPlanKey.equals(that.uniqueCheckPlanKey)) {
            return false;
        }
        if (!stationCode.equals(that.stationCode)) {
            return false;
        }
        if (!trainNum.equals(that.trainNum)) {
            return false;
        }
        if (!checkStatus.equals(that.checkStatus)) {
            return false;
        }
        if (!departureDate.equals(that.departureDate)) {
            return false;
        }
        if (!checkTime.equals(that.checkTime)) {
            return false;
        }
        if (!stopCheckTime.equals(that.stopCheckTime)) {
            return false;
        }
        if (!ticketEntranceBarrierInfoMap.equals(that.ticketEntranceBarrierInfoMap)) {
            return false;
        }
        return ticketDepartureBarrierInfoMap.equals(that.ticketDepartureBarrierInfoMap);

    }

    @Override
    public int hashCode() {
        int result = uniqueCheckPlanKey.hashCode();
        result = 31 * result + stationCode.hashCode();
        result = 31 * result + trainNum.hashCode();
        result = 31 * result + checkStatus.hashCode();
        result = 31 * result + departureDate.hashCode();
        result = 31 * result + checkTime.hashCode();
        result = 31 * result + stopCheckTime.hashCode();
        result = 31 * result + ticketEntranceBarrierInfoMap.hashCode();
        result = 31 * result + ticketDepartureBarrierInfoMap.hashCode();
        return result;
    }
}
