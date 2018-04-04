package com.crscd.passengerservice.ticket.serviceinterface;

import com.crscd.passengerservice.ticket.domainobject.PassengerFlowInfo;

import java.util.List;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public interface TicketMsgManageInterface {
    //客票余额完整信息处理
    void dealWithTrainTicketMsgWhole(String rcvMsg);

    //客票余额变更信息处理
    void dealWithTrainTicketMsgChange(String rcvMsg);

    //检票完整信息处理
    void dealWithTicketCheckMsgWhole(String rcvMsg);

    //检票变更信息处理
    void dealWithTicketCheckMsgChange(String rcvMsg);

    //客流量完整信息处理
    void dealWithPassengerFlowMsgWhole(String rcvMsg);

    //客流量变更信息处理
    void dealWithPassengerFlowMsgChange(String rcvMsg);

    //获取客流量信息
    List<PassengerFlowInfo> getPassengerFlowInfo();
}
