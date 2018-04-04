package com.crscd.passengerservice.plan.dao;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.domainobject.DispatchStationPlan;
import com.crscd.passengerservice.plan.po.DispatchStationPlanBean;
import com.crscd.passengerservice.plan.util.PlanHelper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/22
 */
public class DispatchPlanDAO extends AbstractPlanDAO<DispatchStationPlanBean, DispatchStationPlan> {

    public DispatchPlanDAO(ConfigManager manager) {
        super(manager);
    }

    @Override
    public long selectPK(String planKey) {
        return selectPK(DispatchStationPlanBean.class, planKey);
    }

    @Override
    public DispatchStationPlanBean select(String planKey) {
        String condition = "planKey=?";
        return dataSet.select(DispatchStationPlanBean.class, condition, planKey);
    }

    @Override
    public List<DispatchStationPlanBean> selectList(String trainNum, String stationName, String date) {
        return selectBeanList(DispatchStationPlanBean.class, trainNum, stationName, date);
    }

    @Override
    public List<DispatchStationPlanBean> selectList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        return selectBeanList(DispatchStationPlanBean.class, trainNum, stationName, startDay, endDay, fuzzyFlag);
    }

    @Override
    public List<DispatchStationPlanBean> selectList(String stationName, int pageNumber, int pageSize) {
        return selectPagedPlanBeanList(DispatchStationPlanBean.class, stationName, pageNumber, pageSize);
    }

    @Override
    public List<DispatchStationPlanBean> getAll() {
        return dataSet.selectList(DispatchStationPlanBean.class);
    }

    @Override
    public boolean del(long id) {
        return del(DispatchStationPlanBean.class, id);
    }

    @Override
    public boolean existPlan(String planKey) {
        String condition = "planKey=?";
        return dataSet.selectCount(DispatchStationPlanBean.class, condition, planKey) > 0;
    }

    @Override
    public DispatchStationPlan selectPlan(String planKey) {
        DispatchStationPlanBean planBean = select(planKey);
        if (null == planBean) {
            return null;
        } else {
            return new DispatchStationPlan(planBean, manager);
        }
    }

    @Override
    public List<DispatchStationPlan> selectPlanList(String trainNum, String stationName, String date) {
        List<DispatchStationPlanBean> beanList = selectList(trainNum, stationName, date);
        List<DispatchStationPlan> planList = new ArrayList<>();
        for (DispatchStationPlanBean bean : beanList) {
            DispatchStationPlan plan = new DispatchStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public List<DispatchStationPlan> selectPlanList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        List<DispatchStationPlanBean> beanList = selectList(trainNum, stationName, startDay, endDay, fuzzyFlag);
        List<DispatchStationPlan> planList = new ArrayList<>();
        for (DispatchStationPlanBean bean : beanList) {
            DispatchStationPlan plan = new DispatchStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public long getRowCount() {
        return getRowCount(DispatchStationPlanBean.class);
    }

    @Override
    public List<DispatchStationPlan> selectPlanList(String stationName, int pageNumber, int pageSize) {
        List<DispatchStationPlanBean> beanList = selectList(stationName, pageNumber, pageSize);
        List<DispatchStationPlan> planList = new ArrayList<>();
        for (DispatchStationPlanBean bean : beanList) {
            DispatchStationPlan plan = new DispatchStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public List<DispatchStationPlan> getAllList() {
        List<DispatchStationPlanBean> beanList = getAll();
        List<DispatchStationPlan> planList = new ArrayList<>();
        for (DispatchStationPlanBean bean : beanList) {
            DispatchStationPlan plan = new DispatchStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public boolean insertPlan(DispatchStationPlan plan) {
        DispatchStationPlanBean bean = getDOFromPO(plan);
        boolean flag = insert(bean);
        if (flag) {
            long id = CastUtil.castLong(dataSet.selectPK(DispatchStationPlanBean.class, bean));
            plan.setId(id);
        }
        return flag;
    }

    @Override
    public boolean updatePlan(DispatchStationPlan dispatchStationPlan) {
        DispatchStationPlanBean bean = getDOFromPO(dispatchStationPlan);
        return update(bean);
    }

    @Override
    public boolean delPlan(String planKey) {
        DispatchStationPlanBean bean = select(planKey);
        return del(bean.getId());
    }

    // 将DO的信息拷贝到PO中，时间相关的信息需要转换后赋值
    @Override
    public DispatchStationPlanBean getDOFromPO(DispatchStationPlan plan) {
        MapperFactory mapperFactory = PlanHelper.getMapperFactory();
        mapperFactory.classMap(DispatchStationPlan.class, DispatchStationPlanBean.class)
                .fieldMap("startStation", "startStation").converter("stationConverter").add()
                .fieldMap("finalStation", "finalStation").converter("stationConverter").add()
                .fieldMap("presentStation", "presentStation").converter("stationConverter").add()
                .fieldMap("actualArriveTime", "actualArriveTime").converter("dateTimeConverter").add()
                .fieldMap("actualDepartureTime", "actualDepartureTime").converter("dateTimeConverter").add()
                .fieldMap("planedArriveTime", "planedArriveTime").converter("dateTimeConverter").add()
                .fieldMap("planedDepartureTime", "planedDepartureTime").converter("dateTimeConverter").add()
                .fieldMap("planDate", "planDate").converter("dateConverter").add()
                .fieldMap("trainSuspendStart", "trainSuspendStart").converter("dateConverter").add()
                .fieldMap("trainSuspendEnd", "trainSuspendEnd").converter("dateConverter").add()
                .fieldMap("validPeriodStart", "validPeriodStart").converter("dateConverter").add()
                .fieldMap("validPeriodEnd", "validPeriodEnd").converter("dateConverter").add()
                .fieldMap("manualSuspendFlag", "manualSuspendFlag").converter("booleanConverter").add().byDefault().register();

        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(plan, DispatchStationPlanBean.class);
    }

}
