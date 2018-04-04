package com.crscd.passengerservice.warning.serviceinterface;

import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.warning.dao.DeviceWarningDAO;
import com.crscd.passengerservice.warning.dto.DeviceWarningDTO;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.objectdomain.DeviceWarningInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 13:58
 */
public class WarningServiceInterfaceImpl implements WarningServiceInterface {
    private DeviceWarningDAO deviceWarningDAO;

    public void setDeviceWarningDAO(DeviceWarningDAO deviceWarningDAO) {
        this.deviceWarningDAO = deviceWarningDAO;
    }

    @Override
    public List<DeviceWarningDTO> getDeviceWarningListByStation(String station, SystemEnum system, String area, String startTime, String endTime) {
        List<DeviceWarningInfo> infoList = deviceWarningDAO.getDeviceWarningListByStation(station, system, area, startTime, endTime);
        List<DeviceWarningDTO> dtoList = new ArrayList<>();
        for (DeviceWarningInfo info : infoList) {
            dtoList.add(getDTOFromDO(info));
        }
        return dtoList;
    }

    @Override
    public ResultMessage confirmWarningMessage(long id, String user) {
        if (deviceWarningDAO.confirm(id, user)) {
            return new ResultMessage();
        }
        return new ResultMessage(1301);
    }

    private DeviceWarningDTO getDTOFromDO(DeviceWarningInfo info) {
        return MapperUtil.map(info, DeviceWarningDTO.class);
    }
}
