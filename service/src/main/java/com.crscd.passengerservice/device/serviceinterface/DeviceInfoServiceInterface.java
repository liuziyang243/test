package com.crscd.passengerservice.device.serviceinterface;

import com.crscd.passengerservice.device.dto.BroadcastDeviceDTO;
import com.crscd.passengerservice.device.dto.ScreenDeviceDTO;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/19
 * Time: 16:51
 */
public interface DeviceInfoServiceInterface {

    // 提供导向设备信息列表
    @WebResult(name = "getScreenInfoByStationResult")
    List<ScreenDeviceDTO> getScreenInfoByStation(String station);

    // 提供广播设备信息列表
    @WebResult(name = "getBroadcastInfoByStationResult")
    List<BroadcastDeviceDTO> getBroadcastInfoByStation(String station);

    // 提供FAS设备信息列表

    // 提供门禁设备信息列表

    // 维护导向设备基本信息
    @WebResult(name = "modifyScreenDevBasicInfoResult")
    HashMap<String, ResultMessage> modifyScreenDevBasicInfo();

    // 维护广播设备基本信息
    @WebResult(name = "modifyBroadcastDevBasicInfoResult")
    HashMap<String, ResultMessage> modifyBroadcastDevBasicInfo();

    // 维护FAS设备基本信息

    // 维护门禁设备基本信息

    // 维护导向设备自定义信息
    @WebResult(name = "modifyScreenDevCustomInfoResult")
    HashMap<String, ResultMessage> modifyScreenDevCustomInfo();

    // 维护广播设备自定义信息
    @WebResult(name = "modifyBroadcastDevCustomInfoResult")
    HashMap<String, ResultMessage> modifyBroadcastDevCustomInfo();
}
