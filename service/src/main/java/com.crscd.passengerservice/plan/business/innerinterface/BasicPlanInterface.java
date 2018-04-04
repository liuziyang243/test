package com.crscd.passengerservice.plan.business.innerinterface;

import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/31
 * Time: 16:46
 */
public interface BasicPlanInterface {

    /**
     * 获取全部列车时刻表计划
     *
     * @return
     */
    List<BasicPlan> getBasicPlanList();

    /**
     * 根据车次号获取列车时刻表
     *
     * @param trainNum
     * @return
     */
    BasicPlan getBasicPlanByTrainNum(String trainNum);

    /**
     * 根据站名获取列车时刻表
     *
     * @param stationName
     * @return
     */
    List<BasicPlan> getBasicPlanListByStation(String stationName);

    /**
     * 根据车次号和列车类型获取列车时刻表列表，支持模糊查询
     *
     * @param trainNum
     * @param trainType
     * @param fuzzyFlag
     * @return
     */
    List<BasicPlan> getBasicPlanList(String trainNum, TrainTypeEnum trainType, boolean fuzzyFlag);

    /**
     * 获取指定车次的全部车站信息
     *
     * @param trainNum
     * @return
     */
    List<BasicTrainStationInfo> getBasicTrainStationInfoList(String trainNum);

    /**
     * 获取指定车次的指定车站信息
     *
     * @param trainNum
     * @param stationName
     * @return
     */
    BasicTrainStationInfo getTrainStationInfo(String trainNum, String stationName);

}
