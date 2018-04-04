package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.broadcast.content.dao.NormalBroadcastContentDAO;
import com.crscd.passengerservice.broadcast.content.domainobject.NormalBroadcastContent;
import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/8/27
 */
public class NormalContentManager implements BroadcastContentReplaceInterface {

    private NormalWildcardReplaceManager manager;
    private NormalBroadcastContentDAO dao;
    private String localLan = ConfigHelper.getString("travelService.localLan");

    public void setManager(NormalWildcardReplaceManager manager) {
        this.manager = manager;
    }

    public void setDao(NormalBroadcastContentDAO dao) {
        this.dao = dao;
    }

    @Override
    public String getLocalBroadcastContent(BroadcastStationPlan plan) {
        NormalBroadcastContent content = dao.getContentByNameForTemplate(plan.getBroadcastContentName(), plan.getBroadcastKind(), plan.getPresentStation().getStationName());
        return manager.makeReplaceForWildcard(content.getContentInLocalLan(), getPropertyListFromPlan(plan), localLan);
    }

    @Override
    public String getEngBroadcastContent(BroadcastStationPlan plan) {
        NormalBroadcastContent content = dao.getContentByNameForTemplate(plan.getBroadcastContentName(), plan.getBroadcastKind(), plan.getPresentStation().getStationName());
        return manager.makeReplaceForWildcard(content.getContentInEng(), getPropertyListFromPlan(plan), ServiceConstant.ENG);
    }

    @Override
    public String getBroadcastContent(String localContent, String EngContent, Map<NormalSubstitutePropertyEnum, Object> propertyMap) {
        String contentLoc = null;
        String contentEng = null;
        if (null != localContent) {
            contentLoc = manager.makeReplaceForWildcard(localContent, propertyMap, localLan);
        }
        if (null != EngContent) {
            contentEng = manager.makeReplaceForWildcard(EngContent, propertyMap, ServiceConstant.ENG);
        }
        return contentLoc + contentEng;
    }

    private Map<NormalSubstitutePropertyEnum, Object> getPropertyListFromPlan(BroadcastStationPlan plan) {
        Map<NormalSubstitutePropertyEnum, Object> propertyEnumObjectMap = new HashMap<>();
        for (NormalSubstitutePropertyEnum propertyEnum : NormalSubstitutePropertyEnum.values()) {
            switch (propertyEnum) {
                case PLATFORM:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.PLATFORM, plan.getDockingPlatform());
                    break;
                case EXIT_PORT:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.EXIT_PORT, plan.getStationExitPort());
                    break;
                case TRAIN_NUM:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.TRAIN_NUM, plan.getTrainNum());
                    break;
                case WAIT_ZONE:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.WAIT_ZONE, plan.getWaitZone());
                    break;
                case ENTRANCE_PORT:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ENTRANCE_PORT, plan.getStationEntrancePort());
                    break;
                case FINAL_STATION:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.FINAL_STATION, plan.getFinalStation().getStationName());
                    break;
                case START_STATION:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.START_STATION, plan.getStartStation().getStationName());
                    break;
                case EXIT_CHECK_GATE:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.EXIT_CHECK_GATE, plan.getExitCheckGate());
                    break;
                case STOP_CHECK_TIME:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.STOP_CHECK_TIME, DateTimeFormatterUtil.convertDatetimeToString(plan.getStopAboardCheckTime()));
                    break;
                case ACTUAL_TRACK_NUM:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ACTUAL_TRACK_NUM, plan.getActualTrackNum());
                    break;
                case START_CHECK_TIME:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.START_CHECK_TIME, DateTimeFormatterUtil.convertDatetimeToString(plan.getStartAboardCheckTime()));
                    break;
                case ABOARD_CHECK_GATE:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ABOARD_CHECK_GATE, plan.getAboardCheckGate());
                    break;
                case ACTUAL_ARRIVE_TIME:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ACTUAL_ARRIVE_TIME, DateTimeFormatterUtil.convertDatetimeToString(plan.getActualArriveTime()));
                    break;
                case ACTUAL_DEPARTURE_TIME:
                    propertyEnumObjectMap.put(NormalSubstitutePropertyEnum.ACTUAL_DEPARTURE_TIME, DateTimeFormatterUtil.convertDatetimeToString(plan.getActualDepartureTime()));
                    break;
                default:
                    throw new IllegalArgumentException("Wrong arg for broadcast plan property! " + propertyEnum);
            }
        }
        return propertyEnumObjectMap;
    }
}
