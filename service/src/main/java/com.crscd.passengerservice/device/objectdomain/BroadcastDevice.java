package com.crscd.passengerservice.device.objectdomain;

import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 16:47
 */
public class BroadcastDevice extends BaseDeviceInfo {
    private String broadcastID;

    public BroadcastDevice() {
        this.setDeviceTypeEnum(DeviceTypeEnum.BROADCAST_AMPLIFIER);
    }

    public String getBroadcastID() {
        return broadcastID;
    }

    public void setBroadcastID(String broadcastID) {
        this.broadcastID = broadcastID;
    }
}
