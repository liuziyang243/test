package com.crscd.passengerservice.display.guiderule.business;

import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.display.guiderule.dao.ScreenGuideRuleDAO;
import com.crscd.passengerservice.display.guiderule.domainobject.ScreenGuideRule;
import com.crscd.passengerservice.display.guiderule.enumtype.OnOffEnum;
import com.crscd.passengerservice.display.guiderule.enumtype.OnOffTimeStateEnum;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 15:07
 */
public class PlanOnOffTimeImpl implements PlanOnOffTimeInterface {
    private static final Logger logger = LoggerFactory.getLogger(PlanOnOffTimeImpl.class);
    private ScreenGuideRuleDAO ruleDAO;

    public void setRuleDAO(ScreenGuideRuleDAO ruleDAO) {
        this.ruleDAO = ruleDAO;
    }

    @Override
    public Map<OnOffEnum, LocalDateTime> getPlanOnAndOffScreenTime(ScreenTypeEnum screenType, GuideStationPlan trainPlan) {
        Map<OnOffEnum, LocalDateTime> upDownTimeMap = new HashMap<>();
        upDownTimeMap.put(OnOffEnum.UP_TIME, null);
        upDownTimeMap.put(OnOffEnum.DOWN_TIME, null);
        if (null == trainPlan) {
            logger.warn("[Guide rule] Get null guide plan!");
            return upDownTimeMap;
        }

        ScreenGuideRule rule = ruleDAO.getScreenGuideRuleByStationTrainAndType(trainPlan.getPresentStation().getStationName(), screenType, trainPlan.getTrainType(), trainPlan.getStationType());
        if (null == rule) {
            logger.warn("[Guide rule] Get null rule for " + screenType + "_" + trainPlan.getTrainType() + "_" + trainPlan.getStationType() + " at station " + trainPlan.getPresentStation().getStationName());
            return upDownTimeMap;
        }

        LocalDateTime upTime = getBaseTime(rule.getUpTimeReference(), trainPlan);
        if (null != upTime) {
            upDownTimeMap.put(OnOffEnum.UP_TIME, upTime.plusMinutes(rule.getUpTimeOffset()));
        }
        LocalDateTime downTime = getBaseTime(rule.getDownTimeReference(), trainPlan);
        if (null != downTime) {
            upDownTimeMap.put(OnOffEnum.DOWN_TIME, downTime.plusMinutes(rule.getDownTimeOffset()));
        }

        return upDownTimeMap;
    }

    @Override
    public OnOffTimeStateEnum getPlanOnAndOffScreenTimeState(Map<OnOffEnum, LocalDateTime> timeMap) {
        // 如果上屏时间为空；
        if (null == timeMap.get(OnOffEnum.UP_TIME)) {
            return OnOffTimeStateEnum.ON_SCREEN_TIME_EMPTY;
        }
        // 如果下屏时间为空；
        if (null == timeMap.get(OnOffEnum.DOWN_TIME)) {
            return OnOffTimeStateEnum.OFF_SCREEN_TIME_EMPTY;
        }
        // 如果下屏时间比当前时间早；
        if (LocalDateTime.now().isAfter(timeMap.get(OnOffEnum.DOWN_TIME))) {
            return OnOffTimeStateEnum.OFF_SCREEN_TIME_BEFORE_PRESENT;
        }
        // 如果下屏时间比上屏时间早；
        if (timeMap.get(OnOffEnum.UP_TIME).isAfter(timeMap.get(OnOffEnum.DOWN_TIME))) {
            return OnOffTimeStateEnum.UP_TIME_AFTER_DOWN_TIME;
        }
        // 上屏时间在当前时间之后
        if (timeMap.get(OnOffEnum.UP_TIME).isAfter(LocalDateTime.now())) {
            return OnOffTimeStateEnum.UP_TIME_BEFORE_PRESENT;
        }
        return OnOffTimeStateEnum.NEED_ON_SCREEN;
    }


    // 根据导向规则从计划中获取上下屏基准时间
    private LocalDateTime getBaseTime(TrainTimeBaseEnum base, GuideStationPlan plan) {
        switch (base) {
            case STOP_CHECK:
                return plan.getStopAboardCheckTime();
            case ARRIVE_TIME:
                return plan.getActualArriveTime();
            case START_CHECK:
                return plan.getStartAboardCheckTime();
            case DEPARTURE_TIME:
                return plan.getActualDepartureTime();
            default:
                throw new IllegalArgumentException("Wrong input for TrainTimeBaseEnum " + base.toString());
        }
    }
}
