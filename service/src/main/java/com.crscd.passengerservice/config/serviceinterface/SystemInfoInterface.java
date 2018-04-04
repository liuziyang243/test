package com.crscd.passengerservice.config.serviceinterface;

import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;

import javax.jws.WebParam;
import javax.jws.WebResult;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 12:55
 */
public interface SystemInfoInterface {
    // 通过起始车站和终到站获取列车开行方向
    @WebResult(name = "getTrainDirectionResult")
    TrainDirectionEnum getTrainDirection(@WebParam(name = "startStation") String startStation, @WebParam(name = "finalStation") String finalStation);
}
