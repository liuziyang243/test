package com.crscd.passengerservice.device.objectdomain;

import com.crscd.passengerservice.device.enumtype.DeviceTypeEnum;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 16:42
 */
public class ScreenDevice extends BaseDeviceInfo {
    private String screenID;
    private String screenName;

    public ScreenDevice() {
        this.setDeviceTypeEnum(DeviceTypeEnum.SCREEN);
    }

    public String getScreenID() {
        return screenID;
    }

    public void setScreenID(String screenID) {
        this.screenID = screenID;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
