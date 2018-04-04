package com.crscd.passengerservice.plan.enumtype;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 10:08
 */
public enum TrainTimeBaseEnum {
    ARRIVE_TIME,
    DEPARTURE_TIME,
    START_CHECK,
    STOP_CHECK,
    NOT_VALID  // 专门用于无需计算时间的情况，例如变更广播计划
}
