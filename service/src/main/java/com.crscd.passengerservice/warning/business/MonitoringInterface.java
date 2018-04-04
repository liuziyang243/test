package com.crscd.passengerservice.warning.business;

import com.crscd.passengerservice.warning.enumtype.MonitorStateEnum;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 13:42
 */
public interface MonitoringInterface {
    // 获取监控对象列表, 例如设备id
    List<String> getMonitorObjectList();

    // 获取对象监控指标列表
    List<MonitorProperty> getPropertyList();

    // 根据监控对象ID获取监控对象状态是正常还是异常
    MonitorStateEnum getMonitoringState(String objectID, String property);

    // 通知触发处理由正常变成异常的情况
    // 可以生成一条告警信息并插入到数据库中
    // 需要自行判断和实现是否需要在一定时间内生成相同的告警
    void notifyAbnormal(String objectID, String property);

    // 通知触发处理异常变为正常的情况
    void notifyRecover(String objectID, String property);
}
