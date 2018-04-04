package com.crscd.passengerservice.cctv.serviceinterface;

import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.cctv.dao.RoundRollingGroupDAO;
import com.crscd.passengerservice.cctv.domainobject.CCTVSystemUserInfo;
import com.crscd.passengerservice.cctv.domainobject.RoundRollingGroup;
import com.crscd.passengerservice.cctv.dto.CCTVSystemUserInfoDTO;
import com.crscd.passengerservice.cctv.dto.RoundRollingGroupDTO;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 11:21
 */
public class CCTVServiceInterfaceImpl implements CCTVServiceInterface {
    private RoundRollingGroupDAO dao;
    private CCTVSystemUserInfo userInfo;

    public void setDao(RoundRollingGroupDAO dao) {
        this.dao = dao;
    }

    public void setUserInfo(CCTVSystemUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public CCTVSystemUserInfoDTO getCCTVSystemUserInfo() {
        return MapperUtil.map(userInfo, CCTVSystemUserInfoDTO.class);
    }

    @Override
    public List<RoundRollingGroupDTO> getRoundRollingGroupList(String stationName) {
        List<RoundRollingGroupDTO> result = new ArrayList<>();
        List<RoundRollingGroup> groupList = dao.getRoundRollingGroupList(stationName);
        for (RoundRollingGroup group : groupList) {
            result.add(getDTOFromDO(group));
        }
        return result;
    }

    @Override
    public ResultMessage checkGroupNameExit(String stationName, String groupName) {
        if (dao.exitGroupName(groupName, stationName)) {
            return new ResultMessage(1201);
        }
        return new ResultMessage(1202);
    }

    @Override
    public ResultMessage addRoundRollingGroup(RoundRollingGroupDTO dto) {
        if (dao.insert(getDOFromDTO(dto))) {
            return new ResultMessage();
        }
        return new ResultMessage(2001);
    }

    @Override
    public ResultMessage modifyRoundRollingGroup(RoundRollingGroupDTO dto) {
        if (dao.update(getDOFromDTO(dto))) {
            return new ResultMessage(2002);
        }
        return new ResultMessage();
    }

    @Override
    public ResultMessage delRoundRollingGroup(long id) {
        if (dao.delete(id)) {
            return new ResultMessage(2003);
        }
        return new ResultMessage();
    }

    private RoundRollingGroupDTO getDTOFromDO(RoundRollingGroup group) {
        RoundRollingGroupDTO dto = new RoundRollingGroupDTO();
        dto.setId(group.getId());
        dto.setGroupName(group.getGroupName());
        dto.setRoundPollingInterval(group.getRoundPollingInterval());
        dto.setStationName(group.getStationName());
        HashMap<String, String> cameraMap = new HashMap<>();
        for (String camera : group.getCameraIDList()) {
            String[] keyValue = camera.split("_");
            cameraMap.put(keyValue[0], keyValue[1]);
        }
        dto.setCameraIdList(cameraMap);
        return dto;
    }

    private RoundRollingGroup getDOFromDTO(RoundRollingGroupDTO dto) {
        RoundRollingGroup group = new RoundRollingGroup();
        group.setId(dto.getId());
        group.setGroupName(dto.getGroupName());
        group.setRoundPollingInterval(dto.getRoundPollingInterval());
        group.setStationName(dto.getStationName());
        List<String> cameraList = new ArrayList<>();
        for (Map.Entry<String, String> entry : dto.getCameraIdList().entrySet()) {
            cameraList.add(entry.getKey() + "_" + entry.getValue());
        }
        group.setCameraIDList(cameraList);
        return group;
    }
}
