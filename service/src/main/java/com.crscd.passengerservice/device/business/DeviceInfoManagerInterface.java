package com.crscd.passengerservice.device.business;

import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 8:45
 */
public interface DeviceInfoManagerInterface<T> {
    // 根据车站获取指定设备的列表
    List<T> getDeviceDTOListByStation(String station);

    // 将设备信息更新到数据库中
    Map<String, ResultMessage> modifyDeviceStaticInfo(T t);

    // 将设备自定义信息更新到数据库中
    Map<String, ResultMessage> modifyCustomDeviceInfo(List<Integer> idList, Map<String, String> customInfo);
}
