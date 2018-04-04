package com.crscd.passengerservice.soapinterface;

import com.crscd.passengerservice.display.device.serviceinterface.DeviceManagerInterface;
import com.crscd.passengerservice.display.format.serviceinterface.FormatManagerInterface;
import com.crscd.passengerservice.display.format.serviceinterface.FormatSendInterface;
import com.crscd.passengerservice.display.format.serviceinterface.FrameManagerInterface;
import com.crscd.passengerservice.display.guiderule.serviceinterface.ScreenGuideRuleInterface;
import com.crscd.passengerservice.multimedia.serviceinterface.MaterialManagerInterface;
import com.crscd.passengerservice.multimedia.serviceinterface.PlayListManagerInterface;

import javax.jws.WebService;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 14:22
 * 导向业务相关的接口放在这个下面，包括素材版式相关
 * 以及导向业务模版相关，窗口屏和客票屏管理相关
 */
@WebService
public interface GuideServiceInterface extends
        ScreenGuideRuleInterface,
        FormatManagerInterface,
        FrameManagerInterface,
        FormatSendInterface,
        DeviceManagerInterface,
        PlayListManagerInterface,
        MaterialManagerInterface {

}
