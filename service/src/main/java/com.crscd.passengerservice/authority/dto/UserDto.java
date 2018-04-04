package com.crscd.passengerservice.authority.dto;

import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.config.dto.StationInfoDTO;

import java.util.List;

/**
 * @author zs
 * @date 2017/8/3
 */
public class UserDto {
    // todo: 应该仅仅保留List<String>类型的车站列表
    private List<StationInfoDTO> stationInfoDTOS;
    private List<Action> actions;
    private int userLevel;//1 superAdmin 2 administrator 3 operator

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }


    public List<StationInfoDTO> getStationInfoDTOS() {
        return stationInfoDTOS;
    }

    public void setStationInfoDTOS(List<StationInfoDTO> stationInfoDTOS) {
        this.stationInfoDTOS = stationInfoDTOS;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
