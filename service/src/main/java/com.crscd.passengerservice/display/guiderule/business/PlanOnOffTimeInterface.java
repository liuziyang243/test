package com.crscd.passengerservice.display.guiderule.business;

import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.display.guiderule.enumtype.OnOffEnum;
import com.crscd.passengerservice.display.guiderule.enumtype.OnOffTimeStateEnum;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 15:01
 */
public interface PlanOnOffTimeInterface {
    // 根据屏幕类型查找导向规则计算上下屏时间
    Map<OnOffEnum, LocalDateTime> getPlanOnAndOffScreenTime(ScreenTypeEnum screenType, GuideStationPlan trainPlan);

    // 判断当前计划上下屏时间是否有效
    OnOffTimeStateEnum getPlanOnAndOffScreenTimeState(Map<OnOffEnum, LocalDateTime> timeMap);
}
