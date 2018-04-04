package com.crscd.passengerservice.plan.dao;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;
import com.crscd.passengerservice.plan.po.GuideStationPlanBean;
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
public class GuidePlanDAO extends AbstractPlanDAO<GuideStationPlanBean, GuideStationPlan> {

    GuidePlanDAO(ConfigManager manager) {
        super(manager);
    }

    @Override
    public long selectPK(String planKey) {
        return selectPK(GuideStationPlanBean.class, planKey);
    }

    @Override
    public GuideStationPlanBean select(String planKey) {
        String condition = "planKey=?";
        return dataSet.select(GuideStationPlanBean.class, condition, planKey);
    }

    @Override
    public List<GuideStationPlanBean> selectList(String trainNum, String stationName, String date) {
        return selectBeanList(GuideStationPlanBean.class, trainNum, stationName, date);
    }

    @Override
    public List<GuideStationPlanBean> selectList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        return selectBeanList(GuideStationPlanBean.class, trainNum, stationName, startDay, endDay, fuzzyFlag);
    }

    @Override
    public List<GuideStationPlanBean> selectList(String stationName, int pageNumber, int pageSize) {
        return selectPagedPlanBeanList(GuideStationPlanBean.class, stationName, pageNumber, pageSize);
    }

    @Override
    public List<GuideStationPlanBean> getAll() {
        return dataSet.selectList(GuideStationPlanBean.class);
    }

    @Override
    public boolean del(long id) {
        return del(GuideStationPlanBean.class, id);
    }

    @Override
    public boolean existPlan(String planKey) {
        String condition = "planKey=?";
        return dataSet.selectCount(GuideStationPlanBean.class, condition, planKey) > 0;
    }

    @Override
    public GuideStationPlan selectPlan(String planKey) {
        GuideStationPlanBean planBean = select(planKey);
        if (null == planBean) {
            return null;
        } else {
            return new GuideStationPlan(planBean, manager);
        }
    }

    @Override
    public List<GuideStationPlan> selectPlanList(String trainNum, String stationName, String date) {
        List<GuideStationPlanBean> beanList = selectList(trainNum, stationName, date);
        List<GuideStationPlan> planList = new ArrayList<>();
        for (GuideStationPlanBean bean : beanList) {
            GuideStationPlan plan = new GuideStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public List<GuideStationPlan> selectPlanList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        List<GuideStationPlanBean> beanList = selectList(trainNum, stationName, startDay, endDay, fuzzyFlag);
        List<GuideStationPlan> planList = new ArrayList<>();
        for (GuideStationPlanBean bean : beanList) {
            GuideStationPlan plan = new GuideStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public List<GuideStationPlan> selectPlanList(String stationName, int pageNumber, int pageSize) {
        List<GuideStationPlanBean> beanList = selectList(stationName, pageNumber, pageSize);
        List<GuideStationPlan> planList = new ArrayList<>();
        for (GuideStationPlanBean bean : beanList) {
            GuideStationPlan plan = new GuideStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public long getRowCount() {
        return getRowCount(GuideStationPlanBean.class);
    }

    @Override
    public List<GuideStationPlan> getAllList() {
        List<GuideStationPlanBean> beanList = getAll();
        List<GuideStationPlan> planList = new ArrayList<>();
        for (GuideStationPlanBean bean : beanList) {
            GuideStationPlan plan = new GuideStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public boolean insertPlan(GuideStationPlan guideStationPlan) {
        GuideStationPlanBean bean = getDOFromPO(guideStationPlan);
        boolean flag = insert(bean);
        if (flag) {
            long id = CastUtil.castLong(dataSet.selectPK(GuideStationPlanBean.class, bean));
            guideStationPlan.setId(id);
        }
        return flag;
    }

    @Override
    public boolean updatePlan(GuideStationPlan guideStationPlan) {
        GuideStationPlanBean bean = getDOFromPO(guideStationPlan);
        return update(bean);
    }

    @Override
    public boolean delPlan(String planKey) {
        GuideStationPlanBean bean = select(planKey);
        return del(bean.getId());
    }

    @Override
    public GuideStationPlanBean getDOFromPO(GuideStationPlan plan) {
        MapperFactory mapperFactory = PlanHelper.getMapperFactory();
        mapperFactory.classMap(GuideStationPlan.class, GuideStationPlanBean.class)
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
        return mapper.map(plan, GuideStationPlanBean.class);
    }
}
