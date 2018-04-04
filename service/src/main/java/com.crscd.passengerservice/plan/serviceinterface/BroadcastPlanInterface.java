package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.plan.dto.BroadcastStationPlanDTO;
import com.crscd.passengerservice.result.GroupResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * @author liuziyang
 * Create date: 2017/8/19
 */
public interface BroadcastPlanInterface {
    /**
     * 获取广播计划信息,分为到发广播计划和变更广播计划
     * 对车次号支持模糊查询
     *
     * @param stationName
     * @param trainNum
     * @param broadcastKind
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "getPeriodGuidePlanResult")
    List<BroadcastStationPlanDTO> getPeriodBroadcastPlan(@WebParam(name = "stationName") String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = "broadcastKind") BroadcastKindEnum broadcastKind, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 修改广播计划执行时间
     *
     * @param planKey
     * @param executeTime
     * @return
     */
    @WebResult(name = "modifyBroadcastPlanExecuteTimeResult")
    ResultMessage modifyBroadcastPlanExecuteTime(@WebParam(name = "planKey") String planKey, @WebParam(name = "executeTime") String executeTime);

    /**
     * 修改广播计划执行区域
     *
     * @param planKey
     * @param executeArea
     * @return
     */
    @WebResult(name = "modifyBroadcastPlanExecuteAreaResult")
    ResultMessage modifyBroadcastPlanExecuteArea(@WebParam(name = "planKey") String planKey, @WebParam(name = "executeArea") List<String> executeArea);

    /**
     * 将一个车站某日未执行的到发广播计划调整为手动模式
     *
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "changePeriodBroadcastPlanModeToManualModeResult")
    GroupResultMessage changePeriodBroadcastPlanModeToManualMode(@WebParam(name = "stationName") String stationName, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 将一个车站某日未执行的到发广播计划调整为自动模式
     *
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "changePeriodBroadcastPlanModeToAutoModeResult")
    GroupResultMessage changePeriodBroadcastPlanModeToAutoMode(@WebParam(name = "stationName") String stationName, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 将一个单条的到发广播计划执行模式调整为手动模式
     *
     * @param planKey
     * @return
     */
    @WebResult(name = "changePeriodBroadcastPlanModeToManualModeResult")
    ResultMessage changeSingleBroadcastPlanModeToManualMode(@WebParam(name = "planKey") String planKey);

    /**
     * 将一个单条的到发广播计划执行模式调制为手动模式
     *
     * @param planKey
     * @return
     */
    @WebResult(name = "changePeriodBroadcastPlanModeToAutoModeResult")
    ResultMessage changeSingleBroadcastPlanModeToAutoMode(@WebParam(name = "planKey") String planKey);

}
