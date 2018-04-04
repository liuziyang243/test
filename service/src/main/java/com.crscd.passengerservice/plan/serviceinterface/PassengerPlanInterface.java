package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.plan.dto.PassengerStationPlanDTO;
import com.crscd.passengerservice.plan.enumtype.PassengerPlanModifyEnum;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Create date: 2017/8/19
 * 客运计划相关接口
 ******************************************************/

public interface PassengerPlanInterface {

    /**
     * 获取客运计划信息
     * 对车次号支持模糊查询
     *
     * @param stationName
     * @param trainNum
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "getPeriodPassengerPlanResult")
    List<PassengerStationPlanDTO> getPeriodPassengerPlan(@WebParam(name = "stationName") String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 对客运计划进行修改
     * 修改调度计划信息
     *
     * @param planKey
     * @param modifyList
     * @return
     */
    @WebResult(name = "updatePassengerPlanResult")
    ResultMessage updatePassengerPlan(@WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") HashMap<PassengerPlanModifyEnum, String> modifyList);

}
