package com.crscd.passengerservice.plan.business.generation;

import com.crscd.framework.dao.DatabaseHelper;
import com.crscd.framework.dao.SqlHelper;
import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.reflect.ObjectUtil;
import com.crscd.passengerservice.broadcast.business.BroadcastSchedulerManager;
import com.crscd.passengerservice.broadcast.template.business.BroadcastTemplateManager;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.business.BroadcastStationPlanManager;
import com.crscd.passengerservice.plan.business.DispatchStationPlanManager;
import com.crscd.passengerservice.plan.business.GuideStationPlanManager;
import com.crscd.passengerservice.plan.business.PassengerStationPlanManager;
import com.crscd.passengerservice.plan.dao.*;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.domainobject.DispatchStationPlan;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;
import com.crscd.passengerservice.plan.domainobject.PassengerStationPlan;
import com.crscd.passengerservice.plan.po.BroadcastStationPlanBean;
import com.crscd.passengerservice.plan.po.DispatchStationPlanBean;
import com.crscd.passengerservice.plan.po.GuideStationPlanBean;
import com.crscd.passengerservice.plan.po.PassengerStationPlanBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/15 14:53
 * @version v1.00
 */
public class PlanGenerateInDB {

    private DatabaseHelper helper;
    private DispatchStationPlanManager dispatchManager;
    private PassengerStationPlanManager passengerManager;
    private GuideStationPlanManager guideManager;
    private BroadcastStationPlanManager broadcastManager;
    private DispatchPlanDAO dispatchPlanDAO;
    private PassengerPlanDAO passengerPlanDAO;
    private GuidePlanDAO guidePlanDAO;
    private BroadcastPlanDAO broadcastPlanDAO;
    private BroadcastSchedulerManager schedulerManager;
    private List<PlanGenerateCell> cellList;

    public PlanGenerateInDB() {
        this.cellList = new CopyOnWriteArrayList<>();
    }

    public void setCellList(List<PlanGenerateCell> cellList) {
        this.cellList = cellList;
    }

    public void setBasicPlanDAO(BasicPlanDAO basicPlanDAO) {
    }

    public void setBroadcastTemplateManager(BroadcastTemplateManager broadcastTemplateManager) {
    }

    public void setHelper(DatabaseHelper helper) {
        this.helper = helper;
    }

    public void setDispatchManager(DispatchStationPlanManager dispatchManager) {
        this.dispatchManager = dispatchManager;
    }

    public void setPassengerManager(PassengerStationPlanManager passengerManager) {
        this.passengerManager = passengerManager;
    }

    public void setGuideManager(GuideStationPlanManager guideManager) {
        this.guideManager = guideManager;
    }

    public void setBroadcastManager(BroadcastStationPlanManager broadcastManager) {
        this.broadcastManager = broadcastManager;
    }

    public void setManager(ConfigManager manager) {
    }

    public void setDispatchPlanDAO(DispatchPlanDAO dispatchPlanDAO) {
        this.dispatchPlanDAO = dispatchPlanDAO;
    }

    public void setPassengerPlanDAO(PassengerPlanDAO passengerPlanDAO) {
        this.passengerPlanDAO = passengerPlanDAO;
    }

    public void setGuidePlanDAO(GuidePlanDAO guidePlanDAO) {
        this.guidePlanDAO = guidePlanDAO;
    }

    public void setBroadcastPlanDAO(BroadcastPlanDAO broadcastPlanDAO) {
        this.broadcastPlanDAO = broadcastPlanDAO;
    }

    public void setSchedulerManager(BroadcastSchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public Map<String, String> getResultList() {
        Map<String, String> result = new HashMap<>();
        for (PlanGenerateCell cell : cellList) {
            result.put(cell.getPlanKey(), cell.getReason());
        }
        return result;
    }

    /**
     * 采用事务方式插入四种计划
     * 如果批量插入过程中出现错误，则直接回滚
     * 回滚只针对插入一个车次某日的计划，其他车次日期不受影响
     */
    void insertPlansIntoDB() {
        cellList.parallelStream().forEach(this::insertCellInDB);
    }

    private void insertCellInDB(PlanGenerateCell cell) {
        // 如果结果集中出现success，则说明成功生成了内存计划,
        // 至少成功生成了调度、客运和导向计划
        if (cell.getReason().contains("success")) {
            Map<String, Object[]> sqlList = new HashMap<>();
            // 调度计划
            DispatchStationPlan dispatchPlan = cell.getDispatchStationPlan();
            DispatchStationPlanBean dispatchStationPlanBean = dispatchPlanDAO.getDOFromPO(dispatchPlan);
            Map<String, Object[]> dispatchPlanSqlParamMap = getPlanSqlParamMap(DispatchStationPlanBean.class, dispatchStationPlanBean);
            sqlList.putAll(dispatchPlanSqlParamMap);
            // 客运计划
            PassengerStationPlan passengerPlan = cell.getPassengerStationPlan();
            PassengerStationPlanBean passengerStationPlanBean = passengerPlanDAO.getDOFromPO(passengerPlan);
            Map<String, Object[]> passengerPlanSqlParamMap = getPlanSqlParamMap(PassengerStationPlanBean.class, passengerStationPlanBean);
            sqlList.putAll(passengerPlanSqlParamMap);
            // 导向计划
            GuideStationPlan guidePlan = cell.getGuideStationPlan();
            GuideStationPlanBean guideStationPlanBean = guidePlanDAO.getDOFromPO(guidePlan);
            Map<String, Object[]> guidePlanSqlParamMap = getPlanSqlParamMap(GuideStationPlanBean.class, guideStationPlanBean);
            sqlList.putAll(guidePlanSqlParamMap);
            // 广播计划
            List<BroadcastStationPlan> broadcastStationPlans = cell.getBroadcastStationPlanList();
            for (BroadcastStationPlan plan : broadcastStationPlans) {
                BroadcastStationPlanBean broadcastStationPlanBean = broadcastPlanDAO.getDOFromPO(plan);
                Map<String, Object[]> broadcastPlanSqlParamMap = getPlanSqlParamMap(BroadcastStationPlanBean.class, broadcastStationPlanBean);
                sqlList.putAll(broadcastPlanSqlParamMap);
            }
            if (!helper.transactionUpdate(sqlList)) {
                cell.setGenResult(false);
                if ("Generate success".equals(cell.getReason())) {
                    cell.setReason("Generate plans failed because insert plans into DB failed!");

                } else {
                    String tempReason = cell.getReason() + "Generate plans failed because insert plans into DB failed!";
                    cell.setReason(tempReason);
                }
            } else {
                // 向内存pool中增加计划
                dispatchPlan.setId(dispatchPlanDAO.selectPK(dispatchPlan.getPlanKey()));
                dispatchManager.getPlanDataPool().addPlanToMem(dispatchPlan.getPlanKey(), dispatchPlan);
                passengerPlan.setId(passengerPlanDAO.selectPK(passengerPlan.getPlanKey()));
                passengerManager.getPlanDataPool().addPlanToMem(passengerPlan.getPlanKey(), passengerPlan);
                guidePlan.setId(guidePlanDAO.selectPK(guidePlan.getPlanKey()));
                guideManager.getPlanDataPool().addPlanToMem(guidePlan.getPlanKey(), guidePlan);
                for (BroadcastStationPlan plan : broadcastStationPlans) {
                    plan.setId(broadcastPlanDAO.selectPK(plan.getPlanKey()));
                    broadcastManager.getPlanDataPool().addPlanToMem(plan.getPlanKey(), plan);
                }
                // 将广播计划加入调度器中
                schedulerManager.addBroadcastPlanListToScheduler(broadcastStationPlans);
            }
        }
    }

    /**
     * 生成sqlhelper需要的执行语句及参数map
     * 将一个车次同一天的计划合成到一次数据库操作过程中
     * 以便于进行事务处理
     *
     * @param clazz
     * @param plan
     * @param <T>
     * @return
     */
    private <T> Map<String, Object[]> getPlanSqlParamMap(Class<T> clazz, T plan) {
        Map<String, Object[]> result = new HashMap<>();
        if (null != plan) {
            Map<String, Object> stdFieldMap = ObjectUtil.getFieldMap(plan);
            if (!MapUtil.isEmpty(stdFieldMap)) {
                String sql = SqlHelper.generateInsertSql(clazz, stdFieldMap.keySet());
                Map<String, Object> fieldMap = ObjectUtil.getFieldMap(plan);
                Object[] params = fieldMap.values().toArray();
                result.put(sql, params);
            }
        }
        return result;
    }

}
