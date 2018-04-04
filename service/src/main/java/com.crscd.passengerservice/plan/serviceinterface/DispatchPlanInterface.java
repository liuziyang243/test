package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.plan.dto.DispatchPlanDTO;
import com.crscd.passengerservice.plan.enumtype.DispatchPlanModifyEnum;
import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.result.page.PagedDispatchPlans;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/*******************************************************
 * Created by liuziyang
 * Create date: 2017/8/19
 * 调度计划相关接口
 ******************************************************/
public interface DispatchPlanInterface {

    /**
     * 获取调度计划信息
     * 对车次号支持模糊查询
     *
     * @param stationName
     * @param trainNum
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "getPeriodDispatchPlanResult")
    List<DispatchPlanDTO> getPeriodDispatchPlan(@WebParam(name = "stationName") String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 获取调度计划分页信息
     *
     * @param stationName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @WebResult(name = "getPagedDispatchPlanResult")
    PagedDispatchPlans getPagedDispatchPlan(@WebParam(name = "stationName") String stationName, @WebParam(name = "pageNum") int pageNum, @WebParam(name = "pageSize") int pageSize);

    /**
     * 修改调度计划信息
     *
     * @param planKey
     * @param modifyList
     * @param arriveTimeModifyReason
     * @param departureTimeModifyReason
     * @return
     */
    @WebResult(name = "updateDispatchPlanResult")
    ResultMessage updateDispatchPlan(@WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") HashMap<DispatchPlanModifyEnum, String> modifyList, @WebParam(name = "arriveTimeModifyReason") LateEarlyReasonEnum arriveTimeModifyReason, @WebParam(name = "departureTimeModifyReason") LateEarlyReasonEnum departureTimeModifyReason);
}
