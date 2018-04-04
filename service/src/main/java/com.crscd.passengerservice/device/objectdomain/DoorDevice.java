package com.crscd.passengerservice.device.objectdomain;

import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 16:53
 */
public class DoorDevice extends BaseDeviceInfo {
    private String doorName;

    public DoorDevice() {
        this.setDeviceTypeEnum(DeviceTypeEnum.DOOR);
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }
}
