package com.crscd.passengerservice.device.dao;

import com.crscd.passengerservice.device.objectdomain.BaseDeviceInfo;

import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 17:07
 */
public interface DeviceInfoDAO<T> {
    // 根据设备类型和站名查询设备列表
    List<T> getDeviceInfoList(String stationName);

    // 修改一组设备信息, id是指数据库信息id，不是设备id
    Map<String, Boolean> modifyDeviceListInfo(List<Integer> idList, BaseDeviceInfo info);

    // 专门修改设备自定义信息
    Map<String, Boolean> modifyCustomDeviceInfo(List<Integer> idList, Map<String, String> customInfo);
}
