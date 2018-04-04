package com.crscd.passengerservice.device.business;

import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.device.dto.ScreenDeviceDTO;
import com.crscd.passengerservice.device.po.ScreenDeviceBean;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/19
 * Time: 16:56
 * 主要用来合并设备的静态信息和动态信息，组成DTO数据
 */
public class ScreenDeviceInfoManager extends AbstractDeviceInfoManager<ScreenDeviceDTO, ScreenDeviceBean> {

    @Override
    public List<ScreenDeviceDTO> getDeviceDTOListByStation(String station) {
        List<ScreenDeviceBean> beanList = deviceDAO.getDeviceInfoList(station);
        List<ScreenDeviceDTO> dtoList = new ArrayList<>();
        for (ScreenDeviceBean bean : beanList) {
            ScreenDeviceDTO dto = MapperUtil.map(bean, ScreenDeviceDTO.class);
            dto.setDeviceState(getStateInfoInterface().getDeviceStateByDevID(dto.getScreenID()));
            dto.setPowerState(getStateInfoInterface().getPowerStateByDevID(dto.getScreenID()));
        }
        return dtoList;
    }

    @Override
    public Map<String, ResultMessage> modifyDeviceStaticInfo(ScreenDeviceDTO screenDeviceDTO) {
        return null;
    }

    @Override
    public Map<String, ResultMessage> modifyCustomDeviceInfo(List<Integer> idList, Map<String, String> customInfo) {
        return null;
    }
}
