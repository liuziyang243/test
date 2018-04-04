package com.crscd.passengerservice.device.dao;

import com.crscd.passengerservice.device.objectdomain.BaseDeviceInfo;
import com.crscd.passengerservice.device.po.BroadcastDeviceBean;

import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 17:36
 */
public class ScreenDeviceDAO extends AbstractDeviceInfoDAO<BroadcastDeviceBean> {

    // 获取设备状态信息显示
    @Override
    public List<BroadcastDeviceBean> getDeviceInfoList(String stationName) {
        return getDeviceInfoList(stationName, BroadcastDeviceBean.class);
    }

    // 批量维护设备基本信息
    @Override
    public Map<String, Boolean> modifyDeviceListInfo(List<Integer> idList, BaseDeviceInfo info) {
        return modifyDeviceListInfo(idList, info, BroadcastDeviceBean.class);
    }

    // 专门维护自定义信息
    @Override
    public Map<String, Boolean> modifyCustomDeviceInfo(List<Integer> idList, Map<String, String> customInfo) {
        return modifyCustomDeviceInfo(idList, customInfo, BroadcastDeviceBean.class);
    }
}
