package com.crscd.passengerservice.device.business;

import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.device.dto.BroadcastDeviceDTO;
import com.crscd.passengerservice.device.po.BroadcastDeviceBean;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/19
 * Time: 16:55
 * 主要用来合并设备的静态信息和动态信息，组成DTO数据
 */
public class BroadcastDeviceInfoManager extends AbstractDeviceInfoManager<BroadcastDeviceDTO, BroadcastDeviceBean> {

    @Override
    public List<BroadcastDeviceDTO> getDeviceDTOListByStation(String station) {
        List<BroadcastDeviceBean> beanList = deviceDAO.getDeviceInfoList(station);
        List<BroadcastDeviceDTO> deviceDTOList = new ArrayList<>();
        for (BroadcastDeviceBean bean : beanList) {
            BroadcastDeviceDTO dto = MapperUtil.map(bean, BroadcastDeviceDTO.class);
            dto.setDeviceState(getStateInfoInterface().getDeviceStateByDevID(dto.getBroadcastDevID()));
            deviceDTOList.add(dto);
        }
        return deviceDTOList;
    }

    @Override
    public Map<String, ResultMessage> modifyDeviceStaticInfo(BroadcastDeviceDTO broadcastDeviceDTO) {
        BroadcastDeviceBean bean = MapperUtil.map(broadcastDeviceDTO, BroadcastDeviceBean.class);
        return null;
    }

    @Override
    public Map<String, ResultMessage> modifyCustomDeviceInfo(List<Integer> idList, Map<String, String> customInfo) {
        return null;
    }
}
