package com.crscd.passengerservice.plan.dao;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.domainobject.PassengerStationPlan;
import com.crscd.passengerservice.plan.po.PassengerStationPlanBean;
import com.crscd.passengerservice.plan.util.PlanHelper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 9:13
 */
public class PassengerPlanDAO extends AbstractPlanDAO<PassengerStationPlanBean, PassengerStationPlan> {
    PassengerPlanDAO(ConfigManager manager) {
        super(manager);
    }

    @Override
    public long selectPK(String planKey) {
        return selectPK(PassengerStationPlanBean.class, planKey);
    }

    @Override
    public PassengerStationPlanBean select(String planKey) {
        String condition = "planKey=?";
        return dataSet.select(PassengerStationPlanBean.class, condition, planKey);
    }

    @Override
    public List<PassengerStationPlanBean> selectList(String trainNum, String stationName, String date) {
        return selectBeanList(PassengerStationPlanBean.class, trainNum, stationName, date);
    }

    @Override
    public List<PassengerStationPlanBean> selectList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        return selectBeanList(PassengerStationPlanBean.class, trainNum, stationName, startDay, endDay, fuzzyFlag);
    }

    @Override
    public List<PassengerStationPlanBean> selectList(String stationName, int pageNumber, int pageSize) {
        return selectPagedPlanBeanList(PassengerStationPlanBean.class, stationName, pageNumber, pageSize);
    }

    @Override
    public List<PassengerStationPlanBean> getAll() {
        return dataSet.selectList(PassengerStationPlanBean.class);
    }

    @Override
    public boolean del(long id) {
        return del(PassengerStationPlanBean.class, id);
    }

    @Override
    public boolean existPlan(String planKey) {
        String condition = "planKey=?";
        return dataSet.selectCount(PassengerStationPlanBean.class, condition, planKey) > 0;
    }

    @Override
    public PassengerStationPlan selectPlan(String planKey) {
        PassengerStationPlanBean planBean = select(planKey);
        if (null == planBean) {
            return null;
        } else {
            return new PassengerStationPlan(planBean, manager);
        }
    }

    @Override
    public List<PassengerStationPlan> selectPlanList(String trainNum, String stationName, String date) {
        List<PassengerStationPlanBean> beanList = selectList(trainNum, stationName, date);
        List<PassengerStationPlan> planList = new ArrayList<>();
        for (PassengerStationPlanBean bean : beanList) {
            PassengerStationPlan plan = new PassengerStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public List<PassengerStationPlan> selectPlanList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        List<PassengerStationPlanBean> beanList = selectList(trainNum, stationName, startDay, endDay, fuzzyFlag);
        List<PassengerStationPlan> planList = new ArrayList<>();
        for (PassengerStationPlanBean bean : beanList) {
            PassengerStationPlan plan = new PassengerStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public long getRowCount() {
        return getRowCount(PassengerStationPlanBean.class);
    }

    @Override
    public List<PassengerStationPlan> selectPlanList(String stationName, int pageNumber, int pageSize) {
        List<PassengerStationPlanBean> beanList = selectList(stationName, pageNumber, pageSize);
        List<PassengerStationPlan> planList = new ArrayList<>();
        for (PassengerStationPlanBean bean : beanList) {
            PassengerStationPlan plan = new PassengerStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public List<PassengerStationPlan> getAllList() {
        List<PassengerStationPlanBean> beanList = getAll();
        List<PassengerStationPlan> planList = new ArrayList<>();
        for (PassengerStationPlanBean bean : beanList) {
            PassengerStationPlan plan = new PassengerStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public boolean insertPlan(PassengerStationPlan plan) {
        PassengerStationPlanBean bean = getDOFromPO(plan);
        boolean flag = insert(bean);
        if (flag) {
            long id = CastUtil.castLong(dataSet.selectPK(PassengerStationPlanBean.class, bean));
            plan.setId(id);
        }
        return flag;
    }

    @Override
    public boolean updatePlan(PassengerStationPlan plan) {
        PassengerStationPlanBean bean = getDOFromPO(plan);
        return update(bean);
    }

    @Override
    public boolean delPlan(String planKey) {
        PassengerStationPlanBean bean = select(planKey);
        return del(bean.getId());
    }

    @Override
    public PassengerStationPlanBean getDOFromPO(PassengerStationPlan plan) {
        MapperFactory mapperFactory = PlanHelper.getMapperFactory();
        mapperFactory.classMap(PassengerStationPlan.class, PassengerStationPlanBean.class)
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
                .fieldMap("manualSuspendFlag", "manualSuspendFlag").converter("booleanConverter").add()
                .fieldMap("startAboardCheckTime", "startAboardCheckTime").converter("dateTimeConverter").add()
                .fieldMap("stopAboardCheckTime", "stopAboardCheckTime").converter("dateTimeConverter").add()
                .byDefault().register();

        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(plan, PassengerStationPlanBean.class);
    }
}
