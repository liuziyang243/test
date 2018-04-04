package com.crscd.passengerservice.warning.business;

import com.crscd.passengerservice.warning.enumtype.WarningLevelEnum;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 16:38
 */
public interface GenInterfaceMachineWarningInterface {
    // identification:告警对象标识
    // message：告警消息
    // station：对象所在车站
    // interval：相同告警抑制时间，单位分钟
    // warningLevel：告警级别
    void genSingleTicketWarning(String identification, String message, WarningLevelEnum warningLevel, String station, int interval);
}
