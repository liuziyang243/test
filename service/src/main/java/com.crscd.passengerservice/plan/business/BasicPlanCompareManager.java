package com.crscd.passengerservice.plan.business;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.ctc.dao.CtcMessageDAO;
import com.crscd.passengerservice.ctc.domainobject.CtcBasicTrainInfo;
import com.crscd.passengerservice.ctc.domainobject.CtcBasicTrainStationInfo;
import com.crscd.passengerservice.ctc.po.BasicMapBean;
import com.crscd.passengerservice.plan.domainobject.*;
import com.crscd.passengerservice.plan.enumtype.AnalyseEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.crscd.passengerservice.plan.enumtype.AnalyseEnum.SAME;

/**
 * @author zs
 * @date 2017/9/14
 */
public class BasicPlanCompareManager {

    private CtcMessageDAO ctcMessageDAO;
    private BasicPlanManager basicPlanManager;
    private ConfigManager configManager;

    public void setCtcMessageDAO(CtcMessageDAO ctcMessageDAO) {
        this.ctcMessageDAO = ctcMessageDAO;
    }

    public void setBasicPlanManager(BasicPlanManager basicPlanManager) {
        this.basicPlanManager = basicPlanManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }


    /**
     * 获取基本图列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<BasicMap> getBasicMapList(String startTime, String endTime) {
        List<BasicMapBean> basicMapBeanList = ctcMessageDAO.getBasicMapList(startTime, endTime);
        List<BasicMap> basicMapList = new ArrayList<>();
        for (BasicMapBean basicMapBean : basicMapBeanList) {
            BasicMap basicMap = new BasicMap(basicMapBean);
            basicMapList.add(basicMap);
        }
        return basicMapList;
    }

    /**
     * 获取单条基本图
     *
     * @param uuid
     * @return
     */
    public BasicMap getBasicMap(String uuid) {
        BasicMapBean basicMapBean = ctcMessageDAO.getSingleBasicMap(uuid);
        return new BasicMap(basicMapBean);
    }

    /**
     * 更新审核状态
     *
     * @param uuid
     * @return
     */
    public boolean updateConfirmState(String uuid) {
        return ctcMessageDAO.updateConfirmState(uuid);
    }

    /**
     * 根据基本图获取分析结果
     *
     * @param basicMap
     * @return
     */
    public Map<String, AnalyseEnum> getAnalyseResult(BasicMap basicMap) {

        //获取当前的车次信息并转化为map格式<trainNum,basicPlan>  调用lzy接口
        List<BasicPlan> currentBasicMapList = basicPlanManager.getBasicPlanList();
        Map<String, BasicPlan> currentBasicMapMap = new HashMap<>();
        for (BasicPlan basicPlan : currentBasicMapList) {
            currentBasicMapMap.put(basicPlan.getTrainNum(), basicPlan);
        }
        //获取ctc基本图中的车次信息
        Map<String, CtcBasicTrainInfo> planMap = basicMap.getPlanMap();
        //比较两个基本图
        Map<String, AnalyseEnum> analyseResult = compareBasicMap(planMap, currentBasicMapMap);
        return analyseResult;
    }

    /**
     * 遍历基本图进行比较，分析结果
     *
     * @param planMap
     * @param currentBasicMapMap
     * @return
     */
    private Map<String, AnalyseEnum> compareBasicMap(Map<String, CtcBasicTrainInfo> planMap, Map<String, BasicPlan> currentBasicMapMap) {
        Map<String, AnalyseEnum> analyseResult = new HashMap<>();
        for (String trainNum : planMap.keySet()) {
            if (currentBasicMapMap.containsKey(trainNum)) {
                BasicPlan currentTrainInfo = currentBasicMapMap.get(trainNum);
                CtcBasicTrainInfo ctcBasicTrainInfo = planMap.get(trainNum);
                if (compareBasicInfo(currentTrainInfo, ctcBasicTrainInfo)) {
                    //一致
                    analyseResult.put(trainNum, SAME);
                } else {
                    //替换
                    analyseResult.put(trainNum, AnalyseEnum.REPLACE);
                }
            } else {//新增
                analyseResult.put(trainNum, AnalyseEnum.ADD);
            }
        }
        for (String trainNum : currentBasicMapMap.keySet()) {
            if (!analyseResult.containsKey(trainNum)) {
                //删除
                analyseResult.put(trainNum, AnalyseEnum.DELETE);
            }
        }
        return analyseResult;
    }

    /**
     * 比较当前基本图和CTC基本图中同一车次的信息是否一致
     *
     * @param currentTrainInfo
     * @param ctcBasicTrainInfo
     * @return
     */
    private boolean compareBasicInfo(BasicPlan currentTrainInfo, CtcBasicTrainInfo ctcBasicTrainInfo) {
        //基本信息比较
        if (!currentTrainInfo.getTrainNum().equals(ctcBasicTrainInfo.getTrainNum())) {
            return false;
        }
        if (!currentTrainInfo.getTrainType().equals(ctcBasicTrainInfo.getTrainType())) {
            return false;
        }
        //if(!currentTrainInfo.getTrainDirection().equals(ctcBasicTrainInfo.getTrainDirection()))
        //    return false;
        if (!currentTrainInfo.getStartStation().getStationCode().equals(ctcBasicTrainInfo.getStartStationCode())) {
            return false;
        }
        if (!currentTrainInfo.getFinalStation().getStationCode().equals(ctcBasicTrainInfo.getFinalStationCode())) {
            return false;
        }
        //车站信息比较
        List<BasicTrainStationInfo> basicTrainStationInfos = currentTrainInfo.getStationList();
        Map<String, CtcBasicTrainStationInfo> stationInfos = ctcBasicTrainInfo.getStationInfoList();
        //车站数量不匹配，返回false
        if (basicTrainStationInfos.size() != stationInfos.size()) {
            return false;
        }
        List<String> currentStationCodes = new ArrayList<>();
        for (BasicTrainStationInfo basicTrainStationInfo : basicTrainStationInfos) {
            String stationCode = basicTrainStationInfo.getStationInfo().getStationCode();
            currentStationCodes.add(stationCode);
        }
        List<String> stationCodes = new ArrayList<>(stationInfos.keySet());
        //车站站码不同，返回false
        if (!currentStationCodes.containsAll(stationCodes) || !stationCodes.containsAll(currentStationCodes)) {
            return false;
        }
        //比较车站信息
        for (BasicTrainStationInfo basicTrainStationInfo : basicTrainStationInfos) {
            String stationCode = basicTrainStationInfo.getStationInfo().getStationCode();
            CtcBasicTrainStationInfo ctcBasicTrainStationInfo = stationInfos.get(stationCode);
            if (basicTrainStationInfo.getPlanedTrackNum() != ctcBasicTrainStationInfo.getPlanedTrackNum()) {
                return false;
            }
            if (!DateTimeUtil.compareTime(ctcBasicTrainStationInfo.getPlanedArriveTime(), basicTrainStationInfo.getPlanedArriveTime())) {
                return false;
            }
            if (!DateTimeUtil.compareTime(ctcBasicTrainStationInfo.getPlanedDepartureTime(), basicTrainStationInfo.getPlanedDepartureTime())) {
                return false;
            }
        }
        return true;
    }


    /**
     * 根据基本图组装BasicMapDetail
     *
     * @param basicMap
     * @param stationName
     * @return
     */
    public Map<Integer, List<BasicTrainDetail>> getCompareDetail(BasicMap basicMap, String stationName) {
        Map<Integer, List<BasicTrainDetail>> result = new HashMap<>();
        List<BasicTrainDetail> ctcBasicTrainDetails = new ArrayList<>();
        List<BasicTrainDetail> basicTrainDetails = new ArrayList<>();

        //ctc 的基本图信息分析
        Map<String, BasicTrainDetail> ctcTrainDetailMap = packageCtcTrainDetail(basicMap, stationName);
        //平台 基本图信息分析
        Map<String, BasicTrainDetail> trainDetailMap = packageLocalTrainDetail(stationName);
        //添加比较结果
        for (String trainNum : ctcTrainDetailMap.keySet()) {
            BasicTrainDetail ctcBasicTrainDetail = ctcTrainDetailMap.get(trainNum);
            if (trainDetailMap.containsKey(trainNum)) {
                BasicTrainDetail basicTrainDetail = trainDetailMap.get(trainNum);
                if (ctcBasicTrainDetail.equals(basicTrainDetail)) {
                    ctcBasicTrainDetail.setAnalyseResult(SAME);
                    basicTrainDetail.setAnalyseResult(SAME);
                } else {
                    ctcBasicTrainDetail.setAnalyseResult(AnalyseEnum.REPLACE);
                    basicTrainDetail.setAnalyseResult(AnalyseEnum.REPLACE);
                }
            } else {
                ctcBasicTrainDetail.setAnalyseResult(AnalyseEnum.ADD);
            }
            ctcBasicTrainDetails.add(ctcBasicTrainDetail);
        }
        for (String trainNum : trainDetailMap.keySet()) {
            BasicTrainDetail basicTrainDetail = trainDetailMap.get(trainNum);
            if (null == basicTrainDetail.getAnalyseResult()) {
                basicTrainDetail.setAnalyseResult(AnalyseEnum.DELETE);
            }
            basicTrainDetails.add(basicTrainDetail);
        }

        result.put(1, ctcBasicTrainDetails);
        result.put(2, basicTrainDetails);
        return result;
    }


    /**
     * ctc的基本图信息分析 (比较结果未更新)
     *
     * @param basicMap
     * @param stationName
     * @return
     */
    public Map<String, BasicTrainDetail> packageCtcTrainDetail(BasicMap basicMap, String stationName) {
        Map<String, BasicTrainDetail> ctcTrainDetailMap = new HashMap<>();
        String stationCodeThisStation = configManager.getStationCodeByName(stationName);
        //遍历CTC基本图进行数据组装
        Map<String, CtcBasicTrainInfo> planMap = basicMap.getPlanMap();
        for (String trainNum : planMap.keySet()) {
            CtcBasicTrainInfo ctcBasicTrainInfo = planMap.get(trainNum);
            BasicTrainDetail basicTrainDetail = new BasicTrainDetail();

            String startStation = configManager.getStationNameByCode(ctcBasicTrainInfo.getStartStationCode());
            String finalStation = configManager.getStationNameByCode(ctcBasicTrainInfo.getFinalStationCode());
            basicTrainDetail.setTrainNum(trainNum);
            basicTrainDetail.setTrainType(ctcBasicTrainInfo.getTrainType());
            basicTrainDetail.setStartStation(startStation);
            basicTrainDetail.setFinalStation(finalStation);
            //分析车站信息
            Map<String, CtcBasicTrainStationInfo> stationInfoList = ctcBasicTrainInfo.getStationInfoList();
            List<BasicTrainStationDetail> basicTrainStationDetailList = new ArrayList<>();
            LocalDate startDate = DateTimeFormatterUtil.convertDatetimeToDate(stationInfoList.get(ctcBasicTrainInfo.getStartStationCode()).getPlanedDepartureTime());
            for (String stationCode : stationInfoList.keySet()) {
                BasicTrainStationDetail basicTrainStationDetail = new BasicTrainStationDetail(stationInfoList.get(stationCode), startDate, startStation, finalStation);
                //填充stationName
                basicTrainStationDetail.setStationName(configManager.getStationNameByCode(stationCode));
                basicTrainStationDetailList.add(basicTrainStationDetail);
            }

            //填充当前车次本站相关信息:到点，发点，股道号
            CtcBasicTrainStationInfo trainThisStationInfo = stationInfoList.get(stationCodeThisStation);
            if (null != trainThisStationInfo) {
                basicTrainDetail.setArriveTimeTheStation(trainThisStationInfo.getPlanedArriveTime());
                basicTrainDetail.setDepartureTimeTheStation(trainThisStationInfo.getPlanedDepartureTime());
                basicTrainDetail.setTrackNumThisStation(trainThisStationInfo.getPlanedTrackNum());
            }
            basicTrainDetail.setBasicTrainStationDetailList(basicTrainStationDetailList);
            ctcTrainDetailMap.put(trainNum, basicTrainDetail);
        }
        return ctcTrainDetailMap;
    }


    /**
     * 平台 基本图信息分析 (比较结果未更新)
     *
     * @param stationName
     * @return
     */
    private Map<String, BasicTrainDetail> packageLocalTrainDetail(String stationName) {
        Map<String, BasicTrainDetail> TrainDetailMap = new HashMap<>();
        //获取当前的车次信息  调用lzy接口
        List<BasicPlan> currentBasicMapList = basicPlanManager.getBasicPlanList();
        //遍历本地基本图进行数据组装
        for (BasicPlan basicPlan : currentBasicMapList) {
            BasicTrainDetail basicTrainDetail = new BasicTrainDetail();
            basicTrainDetail.setTrainNum(basicPlan.getTrainNum());
            basicTrainDetail.setTrainType(basicPlan.getTrainType());
            basicTrainDetail.setStartStation(basicPlan.getStartStation().getStationName());
            basicTrainDetail.setFinalStation(basicPlan.getFinalStation().getStationName());
            //分析车站信息
            List<BasicTrainStationInfo> stationInfoList = basicPlan.getStationList();
            List<BasicTrainStationDetail> basicTrainStationDetailList = new ArrayList<>();
            for (BasicTrainStationInfo basicTrainStationInfo : stationInfoList) {
                BasicTrainStationDetail basicTrainStationDetail = new BasicTrainStationDetail(basicTrainStationInfo);
                String currentStationName = basicTrainStationInfo.getStationInfo().getStationName();
                if (currentStationName.equals(stationName)) {
                    basicTrainDetail.setArriveTimeTheStation(basicTrainStationInfo.getPlanedArriveTime());
                    basicTrainDetail.setDepartureTimeTheStation(basicTrainStationInfo.getPlanedDepartureTime());
                    basicTrainDetail.setTrackNumThisStation(basicTrainStationInfo.getPlanedTrackNum());
                }

                basicTrainStationDetailList.add(basicTrainStationDetail);
            }
            basicTrainDetail.setBasicTrainStationDetailList(basicTrainStationDetailList);
            TrainDetailMap.put(basicPlan.getTrainNum(), basicTrainDetail);
        }
        return TrainDetailMap;
    }


    /**
     * 单条数据更新基本图中的车次信息
     *
     * @param trainNum
     * @param analyseResult
     * @param basicTrainDetail
     * @return
     */
    public boolean updateBasicPlanTrainInfo(String trainNum, AnalyseEnum analyseResult, BasicTrainDetail basicTrainDetail) {
        switch (analyseResult) {
            case ADD:
                if (null == basicTrainDetail) {
                    return false;
                } else {
                    return addTrainInfoWithStation(basicTrainDetail);
                }
            case DELETE:
                return delTrainInfo(trainNum);
            case REPLACE:
                if (null == basicTrainDetail) {
                    return false;
                } else {
                    return updateTrainInfoWithStation(basicTrainDetail);
                }
            case SAME:
                return true;
            default:
                return false;
        }
    }


    /**
     * 删除基本计划中的某个车次
     *
     * @param trainNum
     * @return
     */
    private boolean delTrainInfo(String trainNum) {
        return basicPlanManager.delBasicPlanFromDB(trainNum);
    }

    /**
     * 新增基本计划中的某个车次
     *
     * @param basicTrainDetail
     * @return
     */
    private boolean addTrainInfoWithStation(BasicTrainDetail basicTrainDetail) {
        String trainNum = basicTrainDetail.getTrainNum();
        BasicPlan basicPlan = new BasicPlan(basicTrainDetail, configManager);
        boolean result = basicPlanManager.insertBasicTrainInfo(basicPlan);
        if (result) {
            List<BasicTrainStationDetail> basicTrainStationDetailList = basicTrainDetail.getBasicTrainStationDetailList();
            for (BasicTrainStationDetail basicTrainStationDetail : basicTrainStationDetailList) {
                BasicTrainStationInfo info = new BasicTrainStationInfo(basicTrainStationDetail, configManager);
                result = basicPlanManager.insertBasicStation(trainNum, info) && result;
            }

        }
        return result;
    }

    /**
     * 更新基本计划中的某个车次
     *
     * @param basicTrainDetail
     * @return
     */
    private boolean updateTrainInfoWithStation(BasicTrainDetail basicTrainDetail) {
        boolean result = true;
        String trainNum = basicTrainDetail.getTrainNum();
        BasicPlan localBasicPlan = basicPlanManager.getBasicPlanByTrainNum(trainNum);
        //更新车次基本信息
        if (!localBasicPlan.isSameWithCtcTrainInfo(basicTrainDetail)) {
            BasicPlan basicPlan = new BasicPlan(basicTrainDetail, configManager);
            localBasicPlan.setStartStation(basicPlan.getStartStation());
            localBasicPlan.setFinalStation(basicPlan.getFinalStation());
            localBasicPlan.setTrainType(basicPlan.getTrainType());
            result = basicPlanManager.updateBasicTrainInfo(localBasicPlan);
        }
        //更新车次车站信息
        List<BasicTrainStationDetail> basicTrainStationDetailList = basicTrainDetail.getBasicTrainStationDetailList();
        List<String> newStationNames = new ArrayList<>();
        Map<String, BasicTrainStationDetail> basicTrainStationDetailMap = new HashMap<>();
        for (BasicTrainStationDetail basicTrainStationDetail : basicTrainStationDetailList) {
            String stationName = basicTrainStationDetail.getStationName();
            newStationNames.add(stationName);
            basicTrainStationDetailMap.put(stationName, basicTrainStationDetail);
        }

        List<BasicTrainStationInfo> stationInfoList = localBasicPlan.getStationList();
        List<String> localStationNames = new ArrayList<>();
        Map<String, BasicTrainStationInfo> basicTrainStationInfoMap = new HashMap<>();
        for (BasicTrainStationInfo basicTrainStationInfo : stationInfoList) {
            String stationName = basicTrainStationInfo.getStationInfo().getStationName();
            localStationNames.add(stationName);
            basicTrainStationInfoMap.put(stationName, basicTrainStationInfo);
        }

        List<String> addStationNames = new ArrayList<>();
        List<String> delStationNames = new ArrayList<>();
        List<String> sameStationNames = new ArrayList<>();
        addStationNames.addAll(newStationNames);
        addStationNames.removeAll(localStationNames);
        delStationNames.addAll(localStationNames);
        delStationNames.removeAll(newStationNames);
        sameStationNames.addAll(newStationNames);
        sameStationNames.retainAll(localStationNames);

        //需要删除的车站信息
        if (delStationNames.size() != 0) {
            for (String stationName : delStationNames) {
                result = basicPlanManager.delBasicTrainStationFromMemAndDB(trainNum, stationName) && result;
            }
        }
        //需要新增的车站信息
        if (addStationNames.size() != 0) {
            for (String stationName : addStationNames) {
                BasicTrainStationInfo info = new BasicTrainStationInfo(basicTrainStationDetailMap.get(stationName), configManager);
                result = basicPlanManager.insertBasicStation(trainNum, info) && result;
            }
        }
        //需要更新的车站信息
        if (sameStationNames.size() != 0) {
            for (String stationName : sameStationNames) {
                BasicTrainStationInfo newInfo = new BasicTrainStationInfo(basicTrainStationDetailMap.get(stationName), configManager);
                BasicTrainStationInfo localInfo = basicTrainStationInfoMap.get(stationName);
                newInfo.setId(localInfo.getId());
                result = basicPlanManager.updateBasicStationInfo(trainNum, newInfo) && result;
            }
        }
        return result;
    }


    /**
     * 获取最新的接收时间
     *
     * @return
     */
    public String getLastBasicMapRecTime() {
        List<String> receiveTimeList = ctcMessageDAO.getBasicMapRecTime();
        if (null != receiveTimeList && receiveTimeList.size() != 0) {
            return receiveTimeList.get(0);
        } else {
            return null;
        }
    }

}
