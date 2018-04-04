package com.crscd.passengerservice.plan.dao;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.po.BroadcastStationPlanBean;
import com.crscd.passengerservice.plan.util.PlanHelper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 9:14
 */
public class BroadcastPlanDAO extends AbstractPlanDAO<BroadcastStationPlanBean, BroadcastStationPlan> {

    BroadcastPlanDAO(ConfigManager manager) {
        super(manager);
    }

    @Override
    public long selectPK(String planKey) {
        return selectPK(BroadcastStationPlanBean.class, planKey);
    }

    @Override
    public BroadcastStationPlanBean select(String planKey) {
        String condition = "planKey=?";
        return dataSet.select(BroadcastStationPlanBean.class, condition, planKey);
    }

    @Override
    public List<BroadcastStationPlanBean> selectList(String trainNum, String stationName, String date) {
        return selectBeanList(BroadcastStationPlanBean.class, trainNum, stationName, date);
    }

    @Override
    public List<BroadcastStationPlanBean> selectList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        return selectBeanList(BroadcastStationPlanBean.class, trainNum, stationName, startDay, endDay, fuzzyFlag);
    }

    @Override
    public List<BroadcastStationPlanBean> selectList(String stationName, int pageNumber, int pageSize) {
        return selectPagedPlanBeanList(BroadcastStationPlanBean.class, stationName, pageNumber, pageSize);
    }

    @Override
    public List<BroadcastStationPlanBean> getAll() {
        return dataSet.selectList(BroadcastStationPlanBean.class);
    }

    @Override
    public boolean del(long id) {
        return del(BroadcastStationPlanBean.class, id);
    }

    /**
     * 只在生成计划的时候用于检测是否存在到发广播计划使用
     *
     * @param planKey
     * @return
     */
    @Override
    public boolean existPlan(String planKey) {
        planKey += planKey + "_" + BroadcastKindEnum.ARRIVE_DEPARTURE + "_";
        String condition = "planKey like \'" + planKey + "%\'";
        return dataSet.selectCount(BroadcastStationPlanBean.class, condition) > 0;
    }

    @Override
    public BroadcastStationPlan selectPlan(String planKey) {
        BroadcastStationPlanBean planBean = select(planKey);
        if (null == planBean) {
            return null;
        } else {
            return new BroadcastStationPlan(planBean, manager);
        }
    }

    @Override
    public List<BroadcastStationPlan> selectPlanList(String trainNum, String stationName, String date) {
        List<BroadcastStationPlanBean> beanList = selectList(trainNum, stationName, date);
        List<BroadcastStationPlan> planList = new ArrayList<>();
        for (BroadcastStationPlanBean bean : beanList) {
            BroadcastStationPlan plan = new BroadcastStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public List<BroadcastStationPlan> selectPlanList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        List<BroadcastStationPlanBean> beanList = selectList(trainNum, stationName, startDay, endDay, fuzzyFlag);
        List<BroadcastStationPlan> planList = new ArrayList<>();
        for (BroadcastStationPlanBean bean : beanList) {
            BroadcastStationPlan plan = new BroadcastStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public long getRowCount() {
        return getRowCount(BroadcastStationPlanBean.class);
    }

    @Override
    public List<BroadcastStationPlan> selectPlanList(String stationName, int pageNumber, int pageSize) {
        List<BroadcastStationPlanBean> beanList = selectList(stationName, pageNumber, pageSize);
        List<BroadcastStationPlan> planList = new ArrayList<>();
        for (BroadcastStationPlanBean bean : beanList) {
            BroadcastStationPlan plan = new BroadcastStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public List<BroadcastStationPlan> getAllList() {
        List<BroadcastStationPlanBean> beanList = getAll();
        List<BroadcastStationPlan> planList = new ArrayList<>();
        for (BroadcastStationPlanBean bean : beanList) {
            BroadcastStationPlan plan = new BroadcastStationPlan(bean, manager);
            planList.add(plan);
        }
        return planList;
    }

    @Override
    public boolean insertPlan(BroadcastStationPlan broadcastStationPlan) {
        BroadcastStationPlanBean bean = getDOFromPO(broadcastStationPlan);
        boolean flag = insert(bean);
        if (flag) {
            long id = CastUtil.castLong(dataSet.selectPK(BroadcastStationPlanBean.class, bean));
            broadcastStationPlan.setId(id);
        }
        return flag;
    }

    @Override
    public boolean updatePlan(BroadcastStationPlan broadcastStationPlan) {
        BroadcastStationPlanBean bean = getDOFromPO(broadcastStationPlan);
        return update(bean);
    }

    @Override
    public boolean delPlan(String planKey) {
        BroadcastStationPlanBean bean = select(planKey);
        return del(bean.getId());
    }

    @Override
    public BroadcastStationPlanBean getDOFromPO(BroadcastStationPlan plan) {
        MapperFactory mapperFactory = PlanHelper.getMapperFactory();
        mapperFactory.classMap(BroadcastStationPlan.class, BroadcastStationPlanBean.class)
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
                .fieldMap("broadcastTime", "broadcastTime").converter("dateTimeConverter").add()
                .byDefault().register();

        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(plan, BroadcastStationPlanBean.class);
    }

}
