package com.crscd.passengerservice.device.business;

import com.crscd.passengerservice.device.enumtype.CommStateEnum;
import com.crscd.passengerservice.device.enumtype.DeviceStateEnum;
import com.crscd.passengerservice.device.enumtype.PowerStateEnum;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 9:42
 * 提供给外部系统实现的接口，需要从外部系统获取到设备的各种状态
 * 外系统之需要实现能提供的接口即可
 */
public interface DeviceStateInfoInterface {
    // 返回一个设备的设备状态
    DeviceStateEnum getDeviceStateByDevID(String objectID);

    // 返回一个设备的通信状态
    CommStateEnum getCommStateByDevID(String objectID);

    // 返回一个备的电源状态
    PowerStateEnum getPowerStateByDevID(String objectID);
}
