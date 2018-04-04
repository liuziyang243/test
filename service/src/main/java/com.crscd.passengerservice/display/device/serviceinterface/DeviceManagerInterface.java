package com.crscd.passengerservice.display.device.serviceinterface;

import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusAskInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusInfo;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cuishiqing on 2018/1/10.
 */
public interface DeviceManagerInterface {
    @WebResult(name = "deviceControlLine")
    HashMap<String, Boolean> deviceControlLine(@WebParam(name = "action") String action);

    @WebResult(name = "deviceControlStation")
    boolean deviceControlStation(@WebParam(name = "action") String action, @WebParam(name = "stationName") String stationName);

    @WebResult(name = "deviceControlByIps")
    boolean deviceControlByIps(@WebParam(name = "action") String action, @WebParam(name = "stationName") String stationName, @WebParam(name = "screenIps") List<String> screenIps);

    @WebResult(name = "getScreenStatusByIp")
    List<ScreenStatusInfo> getScreenStatusByIp(@WebParam(name = "statusAskInfos") List<ScreenStatusAskInfo> statusAskInfos, @WebParam(name = "stationName") String stationName);

    @WebResult(name = "getScreenStatusStation")
    List<ScreenStatusInfo> getScreenStatusStation(@WebParam(name = "stationName") String stationName);
}
