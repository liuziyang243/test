package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;

/**
 * @author lzy
 * Date: 2017/8/28
 * Time: 8:37
 * 用来为广播控制器提供广播计划状态回写
 */
public interface BroadcastStateInterface {
    /**
     * 更新广播计划状态
     * 实时上每次更新的是一个广播记录的状态，因为同一条广播计划可以同时手动执行多次
     *
     * @param recordKey
     * @param state
     */
    void setBroadcastPlanExecuteState(String recordKey, BroadcastStateEnum state);

    /**
     * 通过广播计划key获取广播计划的优先级
     */
    int getBroadcastPlanPriority(String recordKey);
}
