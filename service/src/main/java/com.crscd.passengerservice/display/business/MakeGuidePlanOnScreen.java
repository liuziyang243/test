package com.crscd.passengerservice.display.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.format.domainobject.PlanDataElement;
import com.crscd.passengerservice.display.format.serviceinterface.FormatSendInterface;
import com.crscd.passengerservice.display.guiderule.business.PlanOnOffTimeInterface;
import com.crscd.passengerservice.display.guiderule.enumtype.OnOffEnum;
import com.crscd.passengerservice.display.guiderule.enumtype.OnOffTimeStateEnum;
import com.crscd.passengerservice.plan.business.GuideStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 11:39
 */
public class MakeGuidePlanOnScreen implements GetPlanDataElementInterface {
    private static final Logger logger = LoggerFactory.getLogger(MakeGuidePlanOnScreen.class);
    // 缓存当前已经刷新过的数据
    // key-screen_id, value: planDataElement
    private static Map<String, List<PlanDataElement>> snapshotDatMap = new ConcurrentHashMap<>();
    private final int guidePlanInDays = ConfigHelper.getInt("travelService.guidePlanInDays");
    private ConfigManager configManager;
    private GuideStationPlanManager guideStationPlanManager;

    public MakeGuidePlanOnScreen() {
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setGuideStationPlanManager(GuideStationPlanManager guideStationPlanManager) {
        this.guideStationPlanManager = guideStationPlanManager;
    }

    /*
     * 通过屏幕ID返回该屏幕上显示的当前信息
     */
    @Override
    public List<PlanDataElement> getTargetScreenDisplayDat(int screenID) {
        return snapshotDatMap.get(String.valueOf(screenID));
    }

    /*
     * 根据站码刷新一个车站的全部屏幕
     */
    void makeScreenDataRefreshForStation(String stationName) {
        List<GuideStationPlan> guideStationPlanList = getGuideStationPlanInDays(stationName);
        makeEntranceScreenSnapshot(guideStationPlanList, stationName);
        makeExitScreenSnapshot(guideStationPlanList, stationName);
        makeCheckPortScreenSnapshot(guideStationPlanList, stationName);
        makePacketScreenSnapshot(guideStationPlanList, stationName);
        makePlatformScreenSnapshot(guideStationPlanList, stationName);
    }


    /*
     * 进站屏刷新函数
     * 参数的导向规则列表为一个车站的符合规则的导向计划列表
     */
    private void makeEntranceScreenSnapshot(List<GuideStationPlan> guideStationPlanList, String station) {
        makeScreenSnapshot(guideStationPlanList, ScreenTypeEnum.ENTRANCE_SCREEN, station);
    }

    /*
     * 出站屏刷新函数
     */
    private void makeExitScreenSnapshot(List<GuideStationPlan> guideStationPlanList, String station) {
        makeScreenSnapshot(guideStationPlanList, ScreenTypeEnum.EXIT_STATION_SCREEN, station);
    }

    /*
     * 检票屏刷新函数
     */
    private void makeCheckPortScreenSnapshot(List<GuideStationPlan> guideStationPlanList, String station) {
        makeScreenSnapshot(guideStationPlanList, ScreenTypeEnum.ABOARD_CHECK_SCREEN, station);
    }

    /*
     * 行包屏刷新函数
     */
    private void makePacketScreenSnapshot(List<GuideStationPlan> guideStationPlanList, String station) {
        makeScreenSnapshot(guideStationPlanList, ScreenTypeEnum.PACKAGE_SCREEN, station);
    }

    /*
     * 站台屏刷新函数
     */
    private void makePlatformScreenSnapshot(List<GuideStationPlan> guideStationPlanList, String station) {
        makeScreenSnapshot(guideStationPlanList, ScreenTypeEnum.PLATFORM_SCREEN, station);
    }


    /*
     * 根据屏幕类型产生每一次的显示数据
     */
    private void makeScreenSnapshot(List<GuideStationPlan> guideStationPlanList, ScreenTypeEnum screenType, String stationName) {
        List<ScreenConfig> screenConfigList = configManager.getScreenListByStation(stationName, screenType);
        if (ListUtil.isEmpty(screenConfigList)) {
            logger.debug("[Display] Screen list for " + screenType + " in station " + stationName + " is empty.");
        }
        for (ScreenConfig screenCfg : screenConfigList) {
            // 通过screen类型过滤导向计划列表，由于全部导向计划都需要显示在全部的进站大屏上
            // 因此不需要通过屏幕id对导向计划过滤
            List<GuideStationPlan> planList = new ArrayList<>();
            // 根据不同屏幕类型和ID对导向计划进行过滤
            switch (screenType) {
                // 站台屏幕需要根据plan中包含的股道对应到屏幕列表，并判断当前屏幕是否在列表中
                case PLATFORM_SCREEN: {
                    for (GuideStationPlan guidePlan : guideStationPlanList) {
                        List<ScreenConfig> screenCfgList = configManager.getPlatformScreenListByStation(stationName, guidePlan.getActualTrackNum());
                        if (screenCfgList.contains(screenCfg)) {
                            planList.add(guidePlan);
                        }
                    }
                    break;
                }
                //　其他屏幕列表默认全部屏幕
                default: {
                    planList = guideStationPlanList;
                }
            }
            //　获取显示信息
            List<PlanDataElement> planDataElementsList = getDataElementListFromGuideRuleList(planList, screenType);
            // 判断显示信息元素是否与原有的相同，如果有变化，则进行刷新操作
            // 如果map中不包含屏幕,则在Map中增加一个元素
            // key值采用stationCode+ScreenID的方式构成
            FormatSendInterface fsi = ContextHelper.getFormatSendInterface();
            String key = String.valueOf(screenCfg.getScreenID());
            if (!snapshotDatMap.containsKey(key)) {
                snapshotDatMap.put(key, planDataElementsList);
                // 调用接口函数
                fsi.formatSend(screenCfg.getScreenID(), planDataElementsList);

                logger.debug("[Display] Send display data on screen-" + screenCfg.getScreenID() + " in station " + screenCfg.getStationName() + " first time.");
            } else {
                //对比次此显示元素是否与上次上一次显示元素相同，如果不相同则更新
                if (!ListUtil.isSameList(planDataElementsList, snapshotDatMap.get(key))) {
                    // 更新数据
                    snapshotDatMap.remove(key);
                    snapshotDatMap.put(key, planDataElementsList);
                    // 调用接口函数
                    try {
                        fsi.formatSend(screenCfg.getScreenID(), new ArrayList<>(planDataElementsList));
                        logger.debug("[Display] Send display data on screen-" + screenCfg.getScreenID() + " in station " + screenCfg.getStationName());
                    } catch (Exception ex) {
                        logger.error("[Display] Format send failure on screen " + screenCfg.getScreenID(), ex);
                    }
                } else {
                    logger.debug("[Display] Display data on screen-" + screenCfg.getScreenID() + " in station " + screenCfg.getStationName() + " is not changed.");
                }
            }
        }
    }

    // 根据配置返回指定天数的guideplan，用于刷新显示
    private List<GuideStationPlan> getGuideStationPlanInDays(String stationName) {
        String startDay = DateTimeUtil.getDateByPlusDays(-guidePlanInDays);
        String endDay = DateTimeUtil.getDateByPlusDays(guidePlanInDays);
        return guideStationPlanManager.getPlanList(stationName, null, startDay, endDay, false);
    }

    /*
     * 根据一种指定的导向计划，过滤出需要上屏的计划，并转换成屏幕显示的数据类型
     */
    private List<PlanDataElement> getDataElementListFromGuideRuleList(List<GuideStationPlan> stationPlanList, ScreenTypeEnum screenType) {
        // 根据到点和发点过滤应该显示在屏幕上的导向计划
        List<GuideStationPlan> planList = new ArrayList<>();
        for (GuideStationPlan plan : stationPlanList) {
            // 计算上屏和下屏时间
            PlanOnOffTimeInterface onOffTimeInterface = ContextHelper.getPlanOnOffTimeInterface();
            Map<OnOffEnum, LocalDateTime> timeMap = onOffTimeInterface.getPlanOnAndOffScreenTime(screenType, plan);
            OnOffTimeStateEnum checkState = onOffTimeInterface.getPlanOnAndOffScreenTimeState(timeMap);
            // 判断上屏和下屏时间是否符合逻辑，如果不符合，则选择继续执行
            // 具体时间上屏和下屏时间违反逻辑的原因会打印在log中
            // 如果当前时间介于上屏和下屏时间之间，则将计划加入上屏计划列表中
            if (checkState.equals(OnOffTimeStateEnum.NEED_ON_SCREEN)) {
                planList.add(plan);
            } else {
                logger.debug(plan.getTrainNum() + checkState.getState());
            }
        }

        // 将应该上屏的导向计划转换为显示需要的信息
        List<PlanDataElement> planDataElementsList = new ArrayList<>();
        for (GuideStationPlan plan : planList) {
            PlanDataElement dataElements = getPlanDataElementFromGuidePlan(plan);
            if (dataElements != null) {
                planDataElementsList.add(dataElements);
            }
        }
        return planDataElementsList;
    }

    /*
     * 根据导向计划信息生成一条上屏数据信息
     */
    private PlanDataElement getPlanDataElementFromGuidePlan(GuideStationPlan plan) {
        if (plan == null) {
            logger.warn("[Display] Guide plan is null when get plan data element from guide-station plan");
            return null;
        }
        PlanDataElement planDataElement = new PlanDataElement();

        // 设置车次号，始发站，终到站信息
        planDataElement.setTrainNum(plan.getTrainNum());
        planDataElement.setStartStation(plan.getStartStation().getStationName());
        planDataElement.setFinalStation(plan.getFinalStation().getStationName());

        // 当日时间只取时分
        String actualArriveTime = DateTimeFormatterUtil.convertTimeToStringNoSecond(plan.getActualArriveTime().toLocalTime());
        String actualDepartureTime = DateTimeFormatterUtil.convertTimeToStringNoSecond(plan.getActualDepartureTime().toLocalTime());

        // 设置到点和到点状态
        // 如果是始发车站，则将车次到点直接设置为"--"
        if (plan.getStationType().equals(StationTypeEnum.START)) {
            planDataElement.setActualArriveTime("--");
            planDataElement.setArriveState("--");

        } else {
            planDataElement.setActualArriveTime(actualArriveTime);
            planDataElement.setArriveState(plan.getArriveTimeState().getState());
        }

        // 设置发点和发点状态
        // 如果是终到车站，则将车次发点直接设置为"--"
        if (plan.getStationType().equals(StationTypeEnum.FINAL)) {
            planDataElement.setActualDepartureTime("--");
            planDataElement.setDepartureState("--");

        } else {
            planDataElement.setActualDepartureTime(actualDepartureTime);
            planDataElement.setDepartureState(plan.getDepartureTimeState().getState());
        }

        // 设置检票状态
        if (plan.getStationType().equals(StationTypeEnum.FINAL)) {
            planDataElement.setCheckInState("--");
        } else {
            planDataElement.setCheckInState(plan.getCheckState().getState());
        }

        // 对股道号和站台赋值
        planDataElement.setTrackNumber(String.valueOf(plan.getActualTrackNum()));
        planDataElement.setPlatform(plan.getDockingPlatform());

        // 设置候车去和检票口
        planDataElement.setWaitZone(new ArrayList<>(plan.getWaitZone()));
        planDataElement.setEntrancePort(new ArrayList<>(plan.getAboardCheckGate()));

        // 设置列车是否停开
        if (plan.getManualSuspendFlag()) {
            planDataElement.setTerminated("terminated");
        } else {
            planDataElement.setTerminated("normal");
        }
        return planDataElement;
    }
}
