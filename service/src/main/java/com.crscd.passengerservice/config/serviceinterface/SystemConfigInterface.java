package com.crscd.passengerservice.config.serviceinterface;

import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.dto.SystemInfoDTO;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.ArrayList;

/**
 * @author lzy
 * Date: 2017/9/4
 * Time: 10:28
 */
public interface SystemConfigInterface {
    // 获取车站信息列表
    @WebResult(name = "getStationInfoListResult")
    SystemInfoDTO getSystemInfo(@WebParam(name = "lan") String lan);

    // 获取车站指定类型的屏幕信息
    @WebResult(name = "getScreenConfigInfoByStationAndTypeResult")
    ArrayList<ScreenConfig> getScreenConfigInfoByStationAndType(@WebParam(name = "station") String station, @WebParam(name = "type") ScreenTypeEnum type);

}
