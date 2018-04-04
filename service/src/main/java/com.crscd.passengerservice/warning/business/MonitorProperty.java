package com.crscd.passengerservice.warning.business;

import com.crscd.passengerservice.warning.enumtype.MonitorModeEnum;
import com.crscd.passengerservice.warning.enumtype.ThresholdCompareModeEnum;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 15:13
 */
public class MonitorProperty {
    // 属性名称
    private String propertyName;
    // 属性监控模式
    private MonitorModeEnum monitorModeEnum;
    // 周期模式下的阀值,统计正常值百分比
    private float threshold;
    // 如果是周期模式，需要统计周期长度，单位是秒
    private int periodLength;
    // 统计计算模式
    private ThresholdCompareModeEnum thresholdCompareMode;

    public MonitorProperty(String propertyName, MonitorModeEnum monitorModeEnum, float threshold, int periodLength, ThresholdCompareModeEnum thresholdCompareMode) {
        this.propertyName = propertyName;
        this.monitorModeEnum = monitorModeEnum;
        this.threshold = threshold;
        this.periodLength = periodLength;
        this.thresholdCompareMode = thresholdCompareMode;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public MonitorModeEnum getMonitorModeEnum() {
        return monitorModeEnum;
    }

    public float getThreshold() {
        return threshold;
    }

    public int getPeriodLength() {
        return periodLength;
    }

    public ThresholdCompareModeEnum getThresholdCompareMode() {
        return thresholdCompareMode;
    }
}
