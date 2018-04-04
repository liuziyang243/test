package com.crscd.passengerservice.device.dao;

import com.crscd.passengerservice.device.objectdomain.BaseDeviceInfo;
import com.crscd.passengerservice.device.po.BroadcastDeviceBean;

import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/19
 * Time: 17:04
 */
public class BroadcastDeviceDAO extends AbstractDeviceInfoDAO<BroadcastDeviceBean> {
    @Override
    public List<BroadcastDeviceBean> getDeviceInfoList(String stationName) {
        return getDeviceInfoList(stationName, BroadcastDeviceBean.class);
    }

    @Override
    public Map<String, Boolean> modifyDeviceListInfo(List<Integer> idList, BaseDeviceInfo info) {
        return modifyDeviceListInfo(idList, info, BroadcastDeviceBean.class);
    }

    @Override
    public Map<String, Boolean> modifyCustomDeviceInfo(List<Integer> idList, Map<String, String> customInfo) {
        return modifyCustomDeviceInfo(idList, customInfo, BroadcastDeviceBean.class);
    }
}
