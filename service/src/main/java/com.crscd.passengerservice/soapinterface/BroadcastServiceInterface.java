package com.crscd.passengerservice.soapinterface;

import com.crscd.passengerservice.broadcast.content.serviceinterface.BroadcastContentInterface;
import com.crscd.passengerservice.broadcast.record.serviceinterface.BroadcastRecordServiceInterface;
import com.crscd.passengerservice.broadcast.serviceinterface.BroadcastPlanExecuteInterface;
import com.crscd.passengerservice.broadcast.template.serviceinterface.BroadcastTemplateGroupInterface;

import javax.jws.WebService;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 14:22
 * 广播相关的接口放在这个下面，包括广播模版管理、驱动调用接口
 */
@WebService
public interface BroadcastServiceInterface
        extends
        BroadcastTemplateGroupInterface,
        BroadcastContentInterface,
        BroadcastRecordServiceInterface,
        BroadcastPlanExecuteInterface {
}
