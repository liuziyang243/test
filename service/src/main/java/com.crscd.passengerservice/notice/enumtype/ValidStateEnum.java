package com.crscd.passengerservice.notice.enumtype;

/**
 * Created by liuziyang
 * Create date: 2017/9/2
 *
 * @author lzy
 */
public enum ValidStateEnum {
    // notice有效
    VALID,
    // 修改的计划已经过期
    PLAN_OUT_OF_DATE,
    // 计划已经不存在
    PLAN_NO_EXIT,
    // notice修改内容与plan内容一样
    SAME_WITH_PLAN
}
