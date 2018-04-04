package com.crscd.passengerservice.config.dto;

import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/6
 */
public class SystemInfoDTO {

    private String systemState;

    private HashMap<String, LateEarlyReasonEnum> lateEarlyReason;

    private ArrayList<Integer> onArriveBroadcastPriorityList;

    private ArrayList<Integer> alterationBroadcastPriorityList;

    private ArrayList<Integer> manualBroadcastPriorityList;

    private List<StationInfoDTO> stationInfoList;

    public SystemInfoDTO() {
    }

    public ArrayList<Integer> getOnArriveBroadcastPriorityList() {
        return onArriveBroadcastPriorityList;
    }

    public void setOnArriveBroadcastPriorityList(ArrayList<Integer> onArriveBroadcastPriorityList) {
        this.onArriveBroadcastPriorityList = onArriveBroadcastPriorityList;
    }

    public ArrayList<Integer> getAlterationBroadcastPriorityList() {
        return alterationBroadcastPriorityList;
    }

    public void setAlterationBroadcastPriorityList(ArrayList<Integer> alterationBroadcastPriorityList) {
        this.alterationBroadcastPriorityList = alterationBroadcastPriorityList;
    }

    public String getSystemState() {
        return systemState;
    }

    public void setSystemState(String systemState) {
        this.systemState = systemState;
    }

    public List<StationInfoDTO> getStationInfoList() {
        return stationInfoList;
    }

    public void setStationInfoList(List<StationInfoDTO> stationInfoList) {
        this.stationInfoList = stationInfoList;
    }

    public HashMap<String, LateEarlyReasonEnum> getLateEarlyReason() {
        return lateEarlyReason;
    }

    public void setLateEarlyReason(HashMap<String, LateEarlyReasonEnum> lateEarlyReason) {
        this.lateEarlyReason = lateEarlyReason;
    }

    public ArrayList<Integer> getManualBroadcastPriorityList() {
        return manualBroadcastPriorityList;
    }

    public void setManualBroadcastPriorityList(ArrayList<Integer> manualBroadcastPriorityList) {
        this.manualBroadcastPriorityList = manualBroadcastPriorityList;
    }
}
