package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.passengerservice.plan.business.BasicPlanCompareManager;
import com.crscd.passengerservice.plan.domainobject.BasicMap;
import com.crscd.passengerservice.plan.domainobject.BasicTrainDetail;
import com.crscd.passengerservice.plan.dto.BasicMapDTO;
import com.crscd.passengerservice.plan.dto.BasicMapDetailDTO;
import com.crscd.passengerservice.plan.dto.BasicMapMergeDTO;
import com.crscd.passengerservice.plan.dto.BasicTrainDetailDTO;
import com.crscd.passengerservice.plan.enumtype.AnalyseEnum;
import com.crscd.passengerservice.plan.serviceinterface.BasicMapInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public class BasicMapInterfaceImpl implements BasicMapInterface {
    private BasicPlanCompareManager basicPlanCompareManager;

    public void setBasicPlanCompareManager(BasicPlanCompareManager basicPlanCompareManager) {
        this.basicPlanCompareManager = basicPlanCompareManager;
    }

    /*
     *获取历史基本图列表
     */
    @Override
    public List<BasicMapDTO> getBasicMapList(String startDate, String endDate) {
        List<BasicMapDTO> basicMapDTOList = new ArrayList<>();
        List<BasicMap> basicMaps = basicPlanCompareManager.getBasicMapList(startDate, endDate);
        if (basicMaps.size() != 0) {
            for (BasicMap basicMap : basicMaps) {
                BasicMapDTO basicMapDTO = packageBasicMapDTO(basicMap);
                basicMapDTOList.add(basicMapDTO);
            }

        }
        return basicMapDTOList;
    }


    //组装基本图的基本信息
    private BasicMapDTO packageBasicMapDTO(BasicMap basicMap) {
        Map<String, AnalyseEnum> analyseEnumMap = new HashMap<>();
        if (basicMap.isConfirmState()) {
            analyseEnumMap = basicPlanCompareManager.getAnalyseResult(basicMap);
        }
        BasicMapDTO basicMapDTO = new BasicMapDTO(basicMap, analyseEnumMap);
        return basicMapDTO;
    }

    /*
     *根据UUID获得某基本图的基本信息
     */
    @Override
    public BasicMapDTO getBasicMapByUuid(String uuid) {
        BasicMap basicMap = basicPlanCompareManager.getBasicMap(uuid);
        return packageBasicMapDTO(basicMap);
    }

    /*
     *根据UUID分析某基本图的基本信息
     */
    @Override
    public BasicMapDTO analyseBasicMapByUuid(String uuid) {
        basicPlanCompareManager.updateConfirmState(uuid);
        return getBasicMapByUuid(uuid);
    }


    /*
     *查看当前基本图的详细比较信息
     */
    @Override
    public BasicMapDetailDTO getCompareDetail(String uuid, String stationName) {
        BasicMap basicMap = basicPlanCompareManager.getBasicMap(uuid);
        BasicMapDetailDTO basicMapDetailDTO = new BasicMapDetailDTO();
        basicMapDetailDTO.setReceiveTime(basicMap.getReceiveTime());
        basicMapDetailDTO.setUuid(uuid);

        if (!basicMap.isConfirmState()) {
            return basicMapDetailDTO;
        }
        //分析结果
        Map<Integer, List<BasicTrainDetail>> basicTrainDetailMap = basicPlanCompareManager.getCompareDetail(basicMap, stationName);

        //组装DTO
        List<BasicTrainDetailDTO> ctcBasicTrainDetailDTOs = new ArrayList<>();
        if (basicTrainDetailMap.get(1).size() != 0) {
            for (BasicTrainDetail basicTrainDetail : basicTrainDetailMap.get(1)) {
                BasicTrainDetailDTO basicTrainDetailDTO = new BasicTrainDetailDTO(basicTrainDetail);
                ctcBasicTrainDetailDTOs.add(basicTrainDetailDTO);
            }
        }

        List<BasicTrainDetailDTO> basicTrainDetailDTOs = new ArrayList<>();
        if (basicTrainDetailMap.get(2).size() != 0) {
            for (BasicTrainDetail basicTrainDetail : basicTrainDetailMap.get(2)) {
                BasicTrainDetailDTO basicTrainDetailDTO = new BasicTrainDetailDTO(basicTrainDetail);
                basicTrainDetailDTOs.add(basicTrainDetailDTO);
            }
        }

        basicMapDetailDTO.setCtcTrainDetailList(ctcBasicTrainDetailDTOs);
        basicMapDetailDTO.setTrainDetailList(basicTrainDetailDTOs);
        return basicMapDetailDTO;

    }

    /*
     * 合并基本图
     * uuid为合并的基本图对象索引号，trainNum为合并的车次号，result为合并的方式
     */
    @Override
    public BasicMapMergeDTO mergeBasicMap(List<BasicTrainDetailDTO> mergeTrainList, String uuid, String stationName) {
        BasicMapMergeDTO basicMapMergeDTO = new BasicMapMergeDTO();
        BasicMap basicMap = basicPlanCompareManager.getBasicMap(uuid);
        //ctc 的基本图信息分析
        Map<String, BasicTrainDetail> ctcTrainDetailMap = basicPlanCompareManager.packageCtcTrainDetail(basicMap, stationName);
        //按车次逐条更新当前基本图
        if (null != mergeTrainList && mergeTrainList.size() != 0) {
            Map<String, Boolean> mergeResultMap = new HashMap<>();
            for (BasicTrainDetailDTO basicTrainDetailDTO : mergeTrainList) {
                String trainNum = basicTrainDetailDTO.getTrainNum();
                AnalyseEnum analyseResult = basicTrainDetailDTO.getAnalyseResult();
                BasicTrainDetail basicTrainDetail = ctcTrainDetailMap.get(trainNum);
                boolean mergeResult = basicPlanCompareManager.updateBasicPlanTrainInfo(trainNum, analyseResult, basicTrainDetail);
                mergeResultMap.put(trainNum, mergeResult);
            }
            basicMapMergeDTO.setMergeResult(mergeResultMap);
        }
        BasicMapDetailDTO basicMapDetailDTO = getCompareDetail(uuid, stationName);
        basicMapMergeDTO.setUuid(uuid);
        basicMapMergeDTO.setBasicMapDetailDTO(basicMapDetailDTO);
        return basicMapMergeDTO;
    }

    /*
     * 获取基本图更新时间
     */
    @Override
    public String getLastBasicMapRecTime() {
        return basicPlanCompareManager.getLastBasicMapRecTime();
    }

}
