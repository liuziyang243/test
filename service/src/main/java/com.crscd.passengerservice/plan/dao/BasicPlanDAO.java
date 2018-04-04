package com.crscd.passengerservice.plan.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.CastUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.po.BasicPlanBean;
import com.crscd.passengerservice.plan.po.BasicTrainStationInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 11:23
 */
public class BasicPlanDAO {

    private DataSet dataSet;

    private ConfigManager manager;

    public BasicPlanDAO(DataSet dataSet, ConfigManager manager) {
        this.dataSet = dataSet;
        this.manager = manager;
    }

    // 通过车次号获取指定的列车时刻表信息
    public BasicPlan getBasicPlanByTrainNum(String trainNum) {
        String condition = "trainNum=?";
        BasicPlanBean bean = dataSet.select(BasicPlanBean.class, condition, trainNum);
        if (null != bean) {
            List<BasicTrainStationInfoBean> trainStationBeanList = dataSet.selectListWithCondition(BasicTrainStationInfoBean.class, condition, bean.getTrainNum());
            return new BasicPlan(bean, trainStationBeanList, manager);
        } else {
            return null;
        }
    }

    // 从数据库读取全部列车时刻表并返回
    public List<BasicPlan> getAllBasicPlanList() {
        List<BasicPlan> basicPlanList = new ArrayList<>();
        List<BasicPlanBean> basicPlanBeanList = dataSet.selectList(BasicPlanBean.class);
        for (BasicPlanBean bean : basicPlanBeanList) {
            String condition = "trainNum=?";
            List<BasicTrainStationInfoBean> trainStationBeanList = dataSet.selectListWithCondition(BasicTrainStationInfoBean.class, condition, bean.getTrainNum());
            BasicPlan plan = new BasicPlan(bean, trainStationBeanList, manager);
            basicPlanList.add(plan);
        }
        return basicPlanList;
    }

    // 向数据库中存储列车时刻表除了车站外的公共信息
    public boolean saveBasicPlanMainInfo(BasicPlan plan) {
        BasicPlanBean bean = new BasicPlanBean(plan);
        boolean flag = dataSet.insert(bean);
        if (flag) {
            long id = CastUtil.castLong(dataSet.selectPK(BasicPlanBean.class, bean));
            plan.setId(id);
        }
        return flag;
    }

    // 向数据库中存储列车时刻表车站信息
    public boolean saveBasicPlanStationInfo(String trainNum, BasicTrainStationInfo info) {
        BasicTrainStationInfoBean bean = new BasicTrainStationInfoBean(trainNum, info);
        boolean flag = dataSet.insert(bean);
        if (flag) {
            long id = CastUtil.castLong(dataSet.selectPK(BasicTrainStationInfoBean.class, bean));
            info.setId(id);
        }
        return flag;
    }

    // 更新列车时刻表公共信息
    public boolean updateBasicPlanMainInfo(BasicPlan plan) {
        BasicPlanBean bean = new BasicPlanBean(plan);
        return dataSet.update(bean);
    }

    // 根据车站信息获取id
    public long getStationInfoBeanId(String trainNum, BasicTrainStationInfo info) {
        String condition = "trainNum=? AND stationName=?";
        Object id = dataSet.selectColumn(BasicTrainStationInfoBean.class, "id", condition, trainNum, info.getStationInfo().getStationName());
        return CastUtil.castLong(id);
    }

    // 更新列车时刻表车站信息
    public boolean updateBasicPlanStationInfo(String trainNum, BasicTrainStationInfo info) {
        BasicTrainStationInfoBean bean = new BasicTrainStationInfoBean(trainNum, info);
        return dataSet.update(bean);
    }

    // 删除列车时刻表公共信息->会连带删除下属的全部车站信息
    public boolean deleteBasicPlanMainInfo(String trainNum) {
        String condition = "trainNum=?";
        dataSet.delete(BasicTrainStationInfoBean.class, condition, trainNum);
        return dataSet.delete(BasicPlanBean.class, condition, trainNum);
    }

    // 删除列车时刻表中指定的车站信息
    public boolean deleteBasicPlanStationInfo(String trainNum, String stationName) {
        String condition = "trainNum=? AND stationName=?";
        return dataSet.delete(BasicTrainStationInfoBean.class, condition, trainNum, stationName);
    }
}
