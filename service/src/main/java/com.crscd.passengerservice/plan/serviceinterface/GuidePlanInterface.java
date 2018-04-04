package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.plan.dto.GuideStationPlanDTO;
import com.crscd.passengerservice.plan.enumtype.GuidePlanModifyEnum;
import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/19
 */
public interface GuidePlanInterface {
    /**
     * 获取导向计划信息
     * 对车次号支持模糊查询
     *
     * @param stationName
     * @param trainNum
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "getPeriodGuidePlanResult")
    List<GuideStationPlanDTO> getPeriodGuidePlan(@WebParam(name = "stationName") String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 修改导向计划信息
     *
     * @param planKey
     * @param modifyList
     * @param arriveTimeModifyReason
     * @param departureTimeModifyReason
     * @return
     */
    @WebResult(name = "updateGuidePlanResult")
    ResultMessage updateGuidePlan(@WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") HashMap<GuidePlanModifyEnum, String> modifyList, @WebParam(name = "arriveTimeModifyReason") LateEarlyReasonEnum arriveTimeModifyReason, @WebParam(name = "departureTimeModifyReason") LateEarlyReasonEnum departureTimeModifyReason);
}
