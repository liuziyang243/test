package com.crscd.passengerservice.display.format.serviceinterface;

import com.crscd.passengerservice.display.format.domainobject.PlanDataElement;
import com.crscd.passengerservice.display.format.domainobject.TicketWinScreenContent;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cuishiqing on 2017/9/5.
 */
public interface FormatSendInterface {
    //客票余票信息自动上屏接口
    @WebResult(name = "ticketScreenFormatSendResult")
    void ticketScreenFormatSend(int screenID, ArrayList<HashMap<String, String>> ticketData);

    //计划类信息自动下发接口
    @WebResult(name = "formatSendResult")
    void formatSend(int screenID, List<PlanDataElement> planDataElements);

    //版式手动下发接口
    @WebResult(name = "realTimeFormatSendResult")
    boolean realTimeFormatSend(@WebParam(name = "screenID") int screenID, @WebParam(name = "currentOrStandby") int currentOrStandby);

    //版式+数据下发接口
    @WebResult(name = "screenMessageDisplayResult")
    boolean screenMessageDisplay(int screenID, String formatID, ArrayList<HashMap<String, String>> tableData);

    //售票窗口屏下发接口
    @WebResult(name = "ticketWinScreenFormatSendResult")
    HashMap<String, String> ticketWinScreenFormatSend(@WebParam(name = "stationName") String stationName, @WebParam(name = "tscList") List<TicketWinScreenContent> tscList, @WebParam(name = "type") String type);

}
