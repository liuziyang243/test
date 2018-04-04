package com.crscd.passengerservice.plan.business.deletion;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.broadcast.business.BroadcastSchedulerManager;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.plan.business.BroadcastStationPlanManager;
import com.crscd.passengerservice.plan.business.DispatchStationPlanManager;
import com.crscd.passengerservice.plan.business.GuideStationPlanManager;
import com.crscd.passengerservice.plan.business.PassengerStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.po.BroadcastStationPlanBean;

import java.util.List;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/2/1 9:13
 * @version v1.00
 */
public abstract class AbstractPlanDeleter {
    DataSet dataSet;
    private DispatchStationPlanManager dispatchManager;
    private PassengerStationPlanManager passengerManager;
    private GuideStationPlanManager guideManager;
    private BroadcastStationPlanManager broadcastManager;
    private BroadcastSchedulerManager schedulerManager;

    public AbstractPlanDeleter(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void setSchedulerManager(BroadcastSchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
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

    /**
     * 从调度器中删除广播计划
     *
     * @param key
     */
    void deleteBroadcastPlanFromScheduler(String key) {
        BroadcastStationPlan plan = broadcastManager.getPlan(key);
        schedulerManager.delSinglePlanFromScheduler(plan);
    }

    /**
     * 删除内存中的计划
     *
     * @param key
     */
    void deletePlansInMem(String key) {
        dispatchManager.getPlanDataPool().delPlanFromMem(key);
        passengerManager.getPlanDataPool().delPlanFromMem(key);
        guideManager.getPlanDataPool().delPlanFromMem(key);
        broadcastManager.getPlanDataPool().delPlanFromMem(key);
    }

    /**
     * 删除数据库中的计划
     *
     * @param clazz
     * @param parms
     * @return
     */
    boolean[] deletePlans(Class<?> clazz, List<String> parms) {
        String condition = "planKey=?";
        return dataSet.delete(clazz, condition, parms);
    }


    /**
     * 通过广播计划key的前一部分获取全部相关的key List
     *
     * @param key
     * @return
     */
    List<String> getBroadcastKeyList(String key) {
        String condition = "planKey like ?";
        String broadPlanKey = key + "_" + BroadcastKindEnum.ARRIVE_DEPARTURE + "_" + "%";
        List<String> keyList = dataSet.selectColumnList(BroadcastStationPlanBean.class, "planKey", condition, "", broadPlanKey);
        return keyList;
    }
}
