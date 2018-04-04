package com.crscd.passengerservice.plan.business;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.plan.business.innerinterface.BasicPlanInterface;
import com.crscd.passengerservice.plan.dao.BasicPlanDAO;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 14:02
 * 直接对数据库进行操作
 */

public class BasicPlanManager implements BasicPlanInterface {
    private BasicPlanDAO dao;

    public BasicPlanManager(BasicPlanDAO dao) {
        this.dao = dao;
    }

    /**
     * 获取某趟列车的某个车站的信息
     *
     * @param trainNum
     * @param stationName
     * @return
     */
    @Override
    public BasicTrainStationInfo getTrainStationInfo(String trainNum, String stationName) {
        BasicPlan plan = getBasicPlanByTrainNum(trainNum);
        if (null != plan) {
            return plan.getSpecifiedTrainStationInfo(stationName);
        }
        return null;
    }

    /**
     * 根据车次号获取某趟车的列车时刻表计划
     *
     * @param trainNum
     * @return
     */
    @Override
    public BasicPlan getBasicPlanByTrainNum(String trainNum) {
        return dao.getBasicPlanByTrainNum(trainNum);
    }

    /**
     * 获取列车时刻表车站信息
     *
     * @param trainNum
     * @return
     */
    @Override
    public List<BasicTrainStationInfo> getBasicTrainStationInfoList(String trainNum) {
        List<BasicTrainStationInfo> basicTrainStationInfos = new ArrayList<>();
        BasicPlan basicPlan = getBasicPlanByTrainNum(trainNum);
        if (null == basicPlan) {
            return basicTrainStationInfos;
        }
        return basicPlan.getStationList();
    }

    /**
     * 无条件获取全部全部列车时刻表计划
     */
    @Override
    public List<BasicPlan> getBasicPlanList() {
        return dao.getAllBasicPlanList();
    }

    /**
     * 根据车站站名获取基本列车计划
     *
     * @param stationName
     */
    @Override
    public List<BasicPlan> getBasicPlanListByStation(String stationName) {
        return getBasicPlanList(stationName, null, TrainTypeEnum.ALL, false);
    }

    /**
     * 通过车次号或者列车类型获取列车时刻表
     * 车次号允许为空，当为空的时候表示没有这个过滤条件
     * 获取全部种类列车车次的时候使用TrainTypeEnum.ALL
     */
    @Override
    public List<BasicPlan> getBasicPlanList(String trainNum, TrainTypeEnum trainType, boolean fuzzyFlag) {
        return getBasicPlanList(null, trainNum, trainType, fuzzyFlag);
    }

    /**
     * 通过车站、车次号、列车类型获取列车时刻表信息
     *
     * @param stationName
     * @param trainNum
     * @param trainType
     * @param fuzzyFlag
     * @return
     */
    public List<BasicPlan> getBasicPlanList(String stationName, String trainNum, TrainTypeEnum trainType, boolean fuzzyFlag) {
        List<BasicPlan> result = new ArrayList<>();
        List<BasicPlan> basicPlans = getBasicPlanList();
        // 如果列车时刻表为空则直接返回
        if (ListUtil.isEmpty(basicPlans)) {
            return new ArrayList<>();
        }
        basicPlans = filterBasicPlanListByTrainNum(trainNum, basicPlans, fuzzyFlag);
        basicPlans = filterBasicPlanListByTrainType(trainType, basicPlans);
        // 如果车站为空，则表示不需要过滤包含某个车站的列车时刻表
        if (StringUtil.isEmpty(stationName)) {
            return basicPlans;
        }
        // 否则还需要根据站名，将包含该车站的车次信息取出
        else {
            for (BasicPlan plan : basicPlans) {
                if (plan.containSpecifiedTrainStation(stationName)) {
                    result.add(plan);
                }
            }
            return result;
        }
    }

    /**
     * 通过车次号过滤列车时刻表计划
     * 允许通过车次号进行模糊查询
     *
     * @param trainNum
     * @param basicPlans
     * @param fuzzyFlag
     * @return
     */
    private List<BasicPlan> filterBasicPlanListByTrainNum(String trainNum, List<BasicPlan> basicPlans, boolean fuzzyFlag) {
        if (StringUtil.isEmpty(trainNum)) {
            return basicPlans;
        }
        List<BasicPlan> basicPlanList = new ArrayList<>();
        for (BasicPlan plan : basicPlans) {
            if (fuzzyFlag) {
                if (plan.getTrainNum().contains(trainNum)) {
                    basicPlanList.add(plan);
                }
            } else {
                if (plan.getTrainNum().equals(trainNum)) {
                    basicPlanList.add(plan);
                }
            }
        }
        return basicPlanList;
    }

    /**
     * 通过列车类型过滤列车时刻表计划
     *
     * @param trainType
     * @param basicPlans
     * @return
     */
    private List<BasicPlan> filterBasicPlanListByTrainType(TrainTypeEnum trainType, List<BasicPlan> basicPlans) {
        if (TrainTypeEnum.ALL == trainType) {
            return basicPlans;
        }
        List<BasicPlan> basicPlanList = new ArrayList<>();
        for (BasicPlan plan : basicPlans) {
            if (plan.getTrainType().equals(trainType)) {
                basicPlanList.add(plan);
            }
        }
        return basicPlanList;
    }

    /************** 数据库操作 ****************/

    // 增加同一趟列车的基本信息
    public boolean insertBasicTrainInfo(BasicPlan plan) {
        return dao.saveBasicPlanMainInfo(plan);
    }

    // 插入一趟列车的车站信息
    public boolean insertBasicStation(String trainNum, BasicTrainStationInfo info) {
        // 获取该趟列车公共信息，为车站信息添加额外的列车始发终到车站信息
        BasicPlan plan = getBasicPlanByTrainNum(trainNum);
        info.setStartStation(plan.getStartStation().getStationName());
        info.setFinalStation(plan.getFinalStation().getStationName());
        // 执行插入操作
        return dao.saveBasicPlanStationInfo(trainNum, info);
    }

    // 修改一趟列车时刻表信息的基本信息
    public boolean updateBasicTrainInfo(BasicPlan plan) {
        BasicPlan tempPlan = getBasicPlanByTrainNum(plan.getTrainNum());
        plan.setId(tempPlan.getId());
        if (dao.updateBasicPlanMainInfo(plan)) {
            // 如果起始站和终到站有修改，需要更新全部车站的数据库信息
            boolean flag = false;
            if (!tempPlan.getStartStation().equals(plan.getStartStation())) {
                flag = true;
                tempPlan.setStartStation(plan.getStartStation());
                for (BasicTrainStationInfo info : tempPlan.getStationList()) {
                    info.setStartStation(plan.getStartStation().getStationName());
                }
            }
            if (!tempPlan.getFinalStation().equals(plan.getFinalStation())) {
                flag = true;
                tempPlan.setFinalStation(plan.getFinalStation());
                for (BasicTrainStationInfo info : tempPlan.getStationList()) {
                    info.setStartStation(plan.getFinalStation().getStationName());
                }
            }
            if (flag) {
                for (BasicTrainStationInfo info : tempPlan.getStationList()) {
                    dao.updateBasicPlanStationInfo(tempPlan.getTrainNum(), info);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // 修改一趟列车时刻表中的车站信息
    public boolean updateBasicStationInfo(String trainNum, BasicTrainStationInfo info) {
        long id = dao.getStationInfoBeanId(trainNum, info);
        info.setId(id);
        return dao.updateBasicPlanStationInfo(trainNum, info);
    }

    // 删除一趟列车的全部信息
    public boolean delBasicPlanFromDB(String trainNum) {
        return dao.deleteBasicPlanMainInfo(trainNum);
    }

    // 删除一趟车列车时刻表的某个车站
    public boolean delBasicTrainStationFromMemAndDB(String trainNum, String stationName) {
        return dao.deleteBasicPlanStationInfo(trainNum, stationName);
    }
}
