package com.crscd.passengerservice.config.dto;

import com.crscd.passengerservice.config.domainobject.StationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
public class StationInfoDTO {
    /**
     * 站名,由站码确定
     */
    private String stationName;
    /**
     * 站码
     */
    private String stationCode;
    /**
     * 地理区域配置
     */
    private ArrayList<SecondaryRegionDTO> geographicalRegionList;
    /**
     * 广播区域配置
     */
    private ArrayList<BroadcastAreaDTO> broadcastAreaList;
    /**
     * 股道列表
     */
    private List<Integer> trackList;

    public StationInfoDTO() {
    }

    public StationInfoDTO(StationInfo info) {
        this.stationCode = info.getStationCode();
        this.stationName = info.getStationName();
    }

    public List<Integer> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Integer> trackList) {
        this.trackList = trackList;
    }

    public ArrayList<SecondaryRegionDTO> getGeographicalRegionList() {
        return geographicalRegionList;
    }

    public void setGeographicalRegionList(ArrayList<SecondaryRegionDTO> geographicalRegionList) {
        this.geographicalRegionList = geographicalRegionList;
    }

    public ArrayList<BroadcastAreaDTO> getBroadcastAreaList() {
        return broadcastAreaList;
    }

    public void setBroadcastAreaList(ArrayList<BroadcastAreaDTO> broadcastAreaList) {
        this.broadcastAreaList = broadcastAreaList;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }
}