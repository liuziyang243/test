package com.crscd.passengerservice.broadcast.serviceinterface;

import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/14
 *
 * @author lzy
 */
public interface BroadcastPlanExecuteInterface {
    /**
     * 手动播放广播计划
     *
     * @param planKey
     * @return
     */
    @WebResult(name = "makeManualBroadcastResult")
    ResultMessage makeManualBroadcast(@WebParam(name = "planKey") String planKey);

    /**
     * 停止单条广播计划的播放
     * 由于一条广播计划可能被执行多次，因此界面需要提醒用户相关广播都会停止
     *
     * @param planKey
     * @return
     */
    @WebResult(name = "stopSingleBroadcastResult")
    HashMap<String, ResultMessage> stopBroadcastPlan(@WebParam(name = "planKey") String planKey);

    /**
     * 针对广播记录，停止单条广播的播放
     *
     * @param recordKey
     * @return
     */
    @WebResult(name = "stopSingleBroadcastResult")
    ResultMessage stopSingleBroadcast(@WebParam(name = "recordKey") String recordKey);

    /**
     * 播放车次广播计划
     *
     * @param stationName
     * @param trainNum
     * @param contentName
     * @param localContent
     * @param EngContent
     * @param broadcastArea
     * @param priorityLevel
     * @return
     */
    @WebResult(name = "makeTrainManualBroadcastResult")
    ResultMessage makeTrainManualBroadcast(@WebParam(name = "stationName") String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = "contentName") String contentName, @WebParam(name = "localContent") String localContent, @WebParam(name = "EngContent") String EngContent, @WebParam(name = "broadcastArea") List<String> broadcastArea, @WebParam(name = "priorityLevel") int priorityLevel);

    /**
     * 获取车次广播界面下的列车车次列表
     *
     * @param stationName
     * @return
     */
    @WebResult(name = "getTrainNumListResult")
    List<String> getTrainNumList(@WebParam(name = "stationName") String stationName);

    /**
     * 获取车次广播的优先级配置列表
     *
     * @param stationName
     * @return
     */
    @WebResult(name = "getPriorityListResult")
    List<Integer> getPriorityList(@WebParam(name = "stationName") String stationName);
}
