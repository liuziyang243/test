package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;

import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/8/27
 */
public interface BroadcastContentReplaceInterface {
    // 通过计划获取替换标签后的本地广播词
    String getLocalBroadcastContent(BroadcastStationPlan plan);

    // 通过计划获取替换标签后的英文广播词
    String getEngBroadcastContent(BroadcastStationPlan plan);

    // 通过属性列表直接获取替换标签的本地广播词+英文广播词
    // 专门用于车次广播
    String getBroadcastContent(String localContent, String EngContent, Map<NormalSubstitutePropertyEnum, Object> propertyMap);
}
