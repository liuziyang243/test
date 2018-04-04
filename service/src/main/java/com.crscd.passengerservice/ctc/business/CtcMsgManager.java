package com.crscd.passengerservice.ctc.business;

import com.alibaba.fastjson.JSON;
import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.restful.client.RestHttpClient;
import com.crscd.framework.util.number.RandomUtil;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.ctc.dao.CtcMessageDAO;
import com.crscd.passengerservice.ctc.domainobject.*;
import com.crscd.passengerservice.ctc.dto.CtcNoticeData;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.serviceinterface.CTCNoticeServiceInterface;
import com.crscd.passengerservice.plan.business.BasicPlanManager;
import com.crscd.passengerservice.plan.business.DispatchStationPlanManager;
import com.crscd.passengerservice.plan.business.innerinterface.BasicPlanInterface;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.domainobject.DispatchStationPlan;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.crscd.framework.util.time.DateTimeFormatterUtil.convertDatetimeToString;

/**
 * @author zs
 * Created on 2017/9/7.
 */
public class CtcMsgManager {
    private static final int CTC_INTERFACE_IP_ADDR = ConfigHelper.getInt("ctcInterfaceIpAddr");
    private static final int CTC_INTERFACE_PORT = ConfigHelper.getInt("ctcInterfacePort");
    private static final Logger logger = LoggerFactory.getLogger(CtcMsgManager.class);
    private static UUID lastBasicMapUuid = UUID.randomUUID();
    private static CtcPeriodPlanList lastCtcPeriodPlanList = new CtcPeriodPlanList();
    private static CtcTrainTimeList lastCtcTrainTimeList = new CtcTrainTimeList();
    private RestHttpClient client;
    private CtcMessageDAO ctcMessageDAO;
    private ConfigManager config;
    private BasicPlanInterface bpManager;
    private DispatchStationPlanManager dspManager;
    private CTCNoticeServiceInterface ctcNoticeService;

    private CtcMsgManager(CtcMessageDAO ctcMessageDAO) {
        this.ctcMessageDAO = ctcMessageDAO;
        String uuid = ctcMessageDAO.getLatestBasicMapUuid();
        if (null != uuid) {
            lastBasicMapUuid = UUID.fromString(uuid);
        }
    }

    public void setClient(RestHttpClient client) {
        this.client = client;
    }

    public void setConfig(ConfigManager config) {
        this.config = config;
    }

    public void setBpManager(BasicPlanManager bpManager) {
        this.bpManager = bpManager;
    }

    public void setDspManager(DispatchStationPlanManager dspManager) {
        this.dspManager = dspManager;
    }

    public void setCtcNoticeService(CTCNoticeServiceInterface ctcNoticeService) {
        this.ctcNoticeService = ctcNoticeService;
    }

    //获取基本图
    public void getCtcBasicMap() {
        String url = "http://" + CTC_INTERFACE_IP_ADDR + ":" + CTC_INTERFACE_PORT + "/ctcmsg/CtcBasicMapInfo";
        CtcBasicMapInfo newCtcBasicMapInfo = client.getObject(url, CtcBasicMapInfo.class);
        //比较接收到的基本图与上一次接收的是否相同，相同则不处理，不同则更新
        if (null != newCtcBasicMapInfo && !lastBasicMapUuid.equals(newCtcBasicMapInfo.getUuid())) {
            //剔除不符合条件的车站（比如货运车站）信息，注意是否需要重新组装始发，终到站
            Map<String, CtcBasicTrainInfo> planMap = newCtcBasicMapInfo.getPlanMap();
            if (null != planMap && 0 != planMap.size()) {
                for (Map.Entry<String, CtcBasicTrainInfo> entry : planMap.entrySet()) {
                    checkBasicTrainInfo(entry.getValue());
                }
                //保存到数据库
                UUID newBasicMapUuid = newCtcBasicMapInfo.getUuid();
                String receiveTime = convertDatetimeToString(newCtcBasicMapInfo.getReceiveTime());
                String basicMapString = JsonUtil.toJSON(newCtcBasicMapInfo);
                ctcMessageDAO.saveBasicMap(newBasicMapUuid, basicMapString, receiveTime);

                //更新保存的上一个基本图的UUID
                lastBasicMapUuid = newBasicMapUuid;
            }
        }
    }

    //检查车站（比如货运车站）信息
    private void checkBasicTrainInfo(CtcBasicTrainInfo ctcBasicTrainInfo) {
        List<String> stationCodeList = config.getAllStationCode();
        LinkedHashMap<String, CtcBasicTrainStationInfo> stationInfoList = ctcBasicTrainInfo.getStationInfoList();
        Iterator<Map.Entry<String, CtcBasicTrainStationInfo>> iterator = stationInfoList.entrySet().iterator();
        while (iterator.hasNext()) {
            String stationCode = iterator.next().getKey();
            if (!stationCodeList.contains(stationCode)) {
                iterator.remove();
            }
        }

        /*
        //重新排序
        //这里将map.entrySet()转换成list
        Set<Map.Entry<String, CtcBasicTrainStationInfo>> entries = stationInfoList.entrySet();
        List<Map.Entry<String,CtcBasicTrainStationInfo>> aList = new ArrayList<>(entries);
        //然后通过比较器来实现排序
        Collections.sort(aList,new Comparator<Map.Entry<String,CtcBasicTrainStationInfo>>() {
            //升序排序
            @Override
            public int compare(Map.Entry<String, CtcBasicTrainStationInfo> o1,
                               Map.Entry<String, CtcBasicTrainStationInfo> o2) {
                LocalDateTime o1Time = o1.getValue().getPlanedArriveTime();
                LocalDateTime o2Time = o2.getValue().getPlanedArriveTime();
                if(o1.getValue().getStationType().equals(StationTypeEnum.START)){
                    o1Time = o1.getValue().getPlanedDepartureTime();
                }
                if(o2.getValue().getStationType().equals(StationTypeEnum.START)){
                    o2Time = o2.getValue().getPlanedDepartureTime();
                }
                return o1Time.compareTo(o2Time);
            }

        });

        //更新始发，终到站
        int len = aList.size();
        CtcBasicTrainStationInfo startStationInfo = aList.get(0).getValue();
        CtcBasicTrainStationInfo finalStationInfo = aList.get(len - 1).getValue();
        startStationInfo.setStationType(StationTypeEnum.START);
        finalStationInfo.setStationType(StationTypeEnum.FINAL);
        ctcBasicTrainInfo.setStartStationCode(startStationInfo.getStationCode());
        ctcBasicTrainInfo.setFinalStationCode(finalStationInfo.getStationCode());
        LinkedHashMap<String, CtcBasicTrainStationInfo> aMap = new LinkedHashMap<>();
        for(Map.Entry<String, CtcBasicTrainStationInfo> entry : aList){
            aMap.put(entry.getKey(),entry.getValue());
        }
        ctcBasicTrainInfo.setStationInfoList(aMap);
        */
        //不需要重新排序,直接更新始发，终到站
        Set<Map.Entry<String, CtcBasicTrainStationInfo>> entries = stationInfoList.entrySet();
        List<Map.Entry<String, CtcBasicTrainStationInfo>> aList = new ArrayList<>(entries);
        int len = aList.size();
        CtcBasicTrainStationInfo startStationInfo = aList.get(0).getValue();
        CtcBasicTrainStationInfo finalStationInfo = aList.get(len - 1).getValue();
        startStationInfo.setStationType(StationTypeEnum.START);
        finalStationInfo.setStationType(StationTypeEnum.FINAL);
        ctcBasicTrainInfo.setStartStationCode(startStationInfo.getStationCode());
        ctcBasicTrainInfo.setFinalStationCode(finalStationInfo.getStationCode());
        ctcBasicTrainInfo.setStationInfoList(stationInfoList);
    }


    //获取阶段计划
    public void getCtcPeriodPlans() {
        String url = "http://" + CTC_INTERFACE_IP_ADDR + ":" + CTC_INTERFACE_PORT + "/ctcmsg/CtcPeriodPlanList";
        LocalDateTime time = LocalDateTime.now();
        String jsonString = JSON.toJSONString(time);
        CtcPeriodPlanList newCtcPeriodPlanList = client.getObject(url, jsonString, CtcPeriodPlanList.class);
        long noticeGroupID = RandomUtil.nextLong();
        //如果当前阶段计划与保存的阶段计划不同，则发送notice消息并更新保存的阶段计划
        if (null != newCtcPeriodPlanList && !newCtcPeriodPlanList.getReceiveTime().equals(lastCtcPeriodPlanList.getReceiveTime())) {
            Map<String, CtcPeriodPlanUnit> newCtcPlanUnitMap = newCtcPeriodPlanList.getCtcPlanUnitMap();
            Map<String, CtcPeriodPlanUnit> lastCtcPlanUnitMap = lastCtcPeriodPlanList.getCtcPlanUnitMap();
            for (String stationCode : newCtcPlanUnitMap.keySet()) {
                String stationName = config.getStationNameByCode(stationCode);
                //如果查询不到站码对应站名，认为不是客车站
                if (null != stationName) {
                    CtcPeriodPlanUnit newCtcPeriodPlanUnit = newCtcPlanUnitMap.get(stationCode);
                    CtcPeriodPlanUnit lastCtcPeriodPlanUnit = lastCtcPlanUnitMap.get(stationCode);
                    Map<String, CtcPeriodTrainUnit> newCtcTrainUnitMap = newCtcPeriodPlanUnit.getCtcTrainUnitMap();
                    if (null == lastCtcPeriodPlanUnit) {
                        //此stationCode内容均为新传递的，需要发送notice
                        for (String trainNum : newCtcTrainUnitMap.keySet()) {
                            CtcPeriodTrainUnit newCtcPeriodTrainUnit = newCtcTrainUnitMap.get(trainNum);
                            sendPeriodPlanNotice(noticeGroupID, newCtcPeriodTrainUnit, stationName);
                        }
                    } else {
                        Map<String, CtcPeriodTrainUnit> lastCtcTrainUnitMap = lastCtcPeriodPlanUnit.getCtcTrainUnitMap();
                        for (String trainNum : newCtcTrainUnitMap.keySet()) {
                            CtcPeriodTrainUnit newCtcPeriodTrainUnit = newCtcTrainUnitMap.get(trainNum);
                            CtcPeriodTrainUnit lastCtcPeriodTrainUnit = lastCtcTrainUnitMap.get(trainNum);
                            if (null == lastCtcPeriodTrainUnit || !newCtcPeriodTrainUnit.equals(lastCtcPeriodTrainUnit)) {
                                //此车次的阶段计划变更或者是新增的此车次阶段计划，需要发送notice
                                sendPeriodPlanNotice(noticeGroupID, newCtcPeriodTrainUnit, stationName);
                            }
                        }
                    }
                }
            }
            //更新保存的阶段计划
            lastCtcPeriodPlanList = newCtcPeriodPlanList;
        }

    }

    //阶段计划发送notice
    private void sendPeriodPlanNotice(long groupID, CtcPeriodTrainUnit ctcPeriodTrainUnit, String stationName) {
        String trainNum = ctcPeriodTrainUnit.getTrainNum();
        BasicTrainStationInfo basicTrainStationInfo = bpManager.getTrainStationInfo(trainNum, stationName);
        //计算此计划的日期,根据基本图和阶段计划的日期计算
        int delayDays = basicTrainStationInfo.getDepartureDelayDays();
        LocalDateTime mapDepartureTime = ctcPeriodTrainUnit.getMapDepartureTime();
        LocalDate mapDepartureDate = mapDepartureTime.toLocalDate();
        LocalDate planDate = mapDepartureDate.minusDays(delayDays);
        //如果有此计划，则组装CtcNoticeData消息，发送notice
        String planKey = new KeyBase(trainNum, planDate, stationName).toString();
        DispatchStationPlan dsp = dspManager.getPlan(planKey);
        if (null != dsp) {
            LocalDateTime planArriveTime = dsp.getActualArriveTime();
            LocalDateTime planDepartureTime = dsp.getActualDepartureTime();
            int planTrackNum = dsp.getActualTrackNum();

            //比较变更的信息
            LocalDateTime newArriveTime = ctcPeriodTrainUnit.getPlanArriveTime();
            LocalDateTime newDepartureTime = ctcPeriodTrainUnit.getPlanDepartureTime();
            int newTrackNum = ctcPeriodTrainUnit.getArriveTrack();

            HashMap<NoticeModifyEnum, String> modifiedDataMap = new HashMap<>();
            if (!planArriveTime.equals(newArriveTime)) {
                modifiedDataMap.put(NoticeModifyEnum.ACTUAL_ARRIVE_TIME, convertDatetimeToString(newArriveTime));
            }
            if (!planDepartureTime.equals(newDepartureTime)) {
                modifiedDataMap.put(NoticeModifyEnum.ACTUAL_DEPARTURE_TIME, convertDatetimeToString(newDepartureTime));
            }
            if (planTrackNum != newTrackNum) {
                modifiedDataMap.put(NoticeModifyEnum.ACTUAL_TRACK_NUM, String.valueOf(newTrackNum));
            }

            //组装CtcNoticeData消息
            if (modifiedDataMap.size() != 0) {
                CtcNoticeData ctcNoticeData = new CtcNoticeData(groupID, trainNum, stationName, planDate, modifiedDataMap);
                //发送notice
                ctcNoticeService.genCTCNotice(ctcNoticeData);

            }
        }

    }


    //获取报点信息
    public void getCtcTrainTimes() {
        String url = "http://" + CTC_INTERFACE_IP_ADDR + ":" + CTC_INTERFACE_PORT + "/ctcmsg/CtcTrainTimeList";
        LocalDateTime time = LocalDateTime.now();
        String jsonString = JSON.toJSONString(time);
        CtcTrainTimeList newCtcTrainTimeList = client.getObject(url, jsonString, CtcTrainTimeList.class);
        long noticeGroupID = RandomUtil.nextLong();
        if (null != newCtcTrainTimeList && !newCtcTrainTimeList.getReceiveTime().equals(lastCtcTrainTimeList.getReceiveTime())) {
            Map<String, CtcTrainTimeUnit> newCtcTrainTimeUnitMap = newCtcTrainTimeList.getCtcTrainTimeUnitMap();
            Map<String, CtcTrainTimeUnit> lastCtcTrainTimeUnitMap = lastCtcTrainTimeList.getCtcTrainTimeUnitMap();
            for (String key : newCtcTrainTimeUnitMap.keySet()) {
                CtcTrainTimeUnit newCtcTrainTimeUnit = newCtcTrainTimeUnitMap.get(key);
                String stationCode = newCtcTrainTimeUnit.getStationCode();
                String stationName = config.getStationNameByCode(stationCode);
                //如果查询不到站码对应站名，认为不是客车站
                if (null != stationName) {
                    if (!lastCtcTrainTimeUnitMap.containsKey(key)) {
                        //报点信息发送notice
                        sendTrainTimeNotice(noticeGroupID, newCtcTrainTimeUnit, stationName);
                    } else {
                        CtcTrainTimeUnit lastCtcTrainTimeUnit = lastCtcTrainTimeUnitMap.get(key);
                        if (!newCtcTrainTimeUnit.equals(lastCtcTrainTimeUnit)) {
                            //报点信息发送notice
                            sendTrainTimeNotice(noticeGroupID, newCtcTrainTimeUnit, stationName);
                        }
                    }
                }
            }

        }

    }

    //报点信息发送notice
    private void sendTrainTimeNotice(long groupID, CtcTrainTimeUnit ctcTrainTimeUnit, String stationName) {
        String trainNum = ctcTrainTimeUnit.getTrainNum();
        BasicTrainStationInfo basicTrainStationInfo = bpManager.getTrainStationInfo(trainNum, stationName);
        //计算此计划的日期,根据基本图和阶段计划的日期计算
        int delayDays = 0;
        LocalDateTime actualTime = ctcTrainTimeUnit.getTime();
        LocalDateTime planTime;
        LocalTime timeChange = ctcTrainTimeUnit.getTimeChange();

        switch (ctcTrainTimeUnit.getTimeType()) {
            //到达时间
            case 0x01:
                planTime = actualTime;
                delayDays = basicTrainStationInfo.getArriveDelayDays();
                break;
            //出发时间
            case 0x02:
                planTime = actualTime;
                delayDays = basicTrainStationInfo.getDepartureDelayDays();
                break;
            //到达早点
            case 0x04:
                planTime = DateTimeUtil.dateTimeCalculator(actualTime, timeChange, true);
                delayDays = basicTrainStationInfo.getArriveDelayDays();
                break;
            //出发早点
            case 0x05:
                planTime = DateTimeUtil.dateTimeCalculator(actualTime, timeChange, true);
                delayDays = basicTrainStationInfo.getDepartureDelayDays();
                break;
            //到达晚点
            case 0x07:
                planTime = DateTimeUtil.dateTimeCalculator(actualTime, timeChange, false);
                delayDays = basicTrainStationInfo.getArriveDelayDays();
                break;
            //出发晚点
            case 0x08:
                planTime = DateTimeUtil.dateTimeCalculator(actualTime, timeChange, false);
                delayDays = basicTrainStationInfo.getDepartureDelayDays();
                break;
            default:
                return;
        }

        LocalDate planStationDate = planTime.toLocalDate();
        LocalDate planDate = planStationDate.minusDays(delayDays);
        //如果有此计划，则组装CtcNoticeData消息，发送notice
        String planKey = new KeyBase(trainNum, planDate, stationName).toString();
        DispatchStationPlan dsp = dspManager.getPlan(planKey);
        if (null != dsp) {
            LocalDateTime planArriveTime = dsp.getActualArriveTime();
            LocalDateTime planDepartureTime = dsp.getActualDepartureTime();
            int planTrackNum = dsp.getActualTrackNum();
            int actualTrackNum = ctcTrainTimeUnit.getTrackNum();
            HashMap<NoticeModifyEnum, String> modifiedDataMap = new HashMap<>();

            switch (ctcTrainTimeUnit.getTimeType()) {
                //到达时间
                case 0x01:
                    //到达早点
                case 0x04:
                    //到达晚点
                case 0x07:
                    if (!actualTime.equals(planArriveTime)) {
                        modifiedDataMap.put(NoticeModifyEnum.ACTUAL_ARRIVE_TIME, convertDatetimeToString(actualTime));
                    }
                    break;
                //出发时间
                case 0x02:
                    //出发早点
                case 0x05:
                    //出发晚点
                case 0x08:
                    if (!actualTime.equals(planDepartureTime)) {
                        modifiedDataMap.put(NoticeModifyEnum.ACTUAL_DEPARTURE_TIME, convertDatetimeToString(actualTime));
                    }
                    break;
                default:
                    break;
            }
            if (actualTrackNum != planTrackNum) {
                modifiedDataMap.put(NoticeModifyEnum.ACTUAL_TRACK_NUM, String.valueOf(actualTrackNum));
            }

            //组装CtcNoticeData消息
            if (modifiedDataMap.size() != 0) {
                CtcNoticeData ctcNoticeData = new CtcNoticeData(groupID, trainNum, stationName, planDate, modifiedDataMap);
                //发送notice
                ctcNoticeService.genCTCNotice(ctcNoticeData);

            }
        }
    }


}
