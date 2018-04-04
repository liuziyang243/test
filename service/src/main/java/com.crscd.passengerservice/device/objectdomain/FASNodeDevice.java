package com.crscd.passengerservice.device.objectdomain;

import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 16:52
 */
public class FASNodeDevice extends BaseDeviceInfo {
    public FASNodeDevice() {
        this.setDeviceTypeEnum(DeviceTypeEnum.INTRUSION_ALARM_DEVICE);
    }
}
