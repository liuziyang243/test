package com.crscd.passengerservice.device.objectdomain;

import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 16:57
 */
public class IntrusionAlarmDevice extends BaseDeviceInfo {
    private String deviceName;

    public IntrusionAlarmDevice() {
        this.setDeviceTypeEnum(DeviceTypeEnum.INTRUSION_ALARM_DEVICE);
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
