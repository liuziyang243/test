package com.crscd.passengerservice.plan.pool;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.dao.daointerface.PlanDAOInterface;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.domainobject.KeyBase;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 17:00
 */
public class BroadcastPlanDataPool extends AbstractPlanDataPool<BroadcastStationPlan> {
    public BroadcastPlanDataPool(int durationInDays, PlanDAOInterface<BroadcastStationPlan> dao) {
        super(durationInDays, dao);
    }

    /**
     * 仅从内存中删掉计划即可,对于广播计划需要删掉多组数据
     *
     * @param key
     */
    @Override
    public void delPlanFromMem(String key) {
        KeyBase keyBase = new KeyBase(key);
        if (containsInPool(DateTimeFormatterUtil.convertDateToString(keyBase.getPlanDate()))) {
            List<BroadcastStationPlan> planList = getDailyDataList(keyBase.getPlanDate());
            for (BroadcastStationPlan plan : planList) {
                if (plan.getPlanKey().contains(key)) {
                    delData(plan.getPlanKey());
                }
            }
        }
    }

    /**
     * 向内存和数据库加入计划, 用于生成变更广播计划使用
     *
     * @param plan
     * @return
     */
    public boolean addPlanToMemAndDB(BroadcastStationPlan plan) {
        addPlanToMem(plan.getPlanKey(), plan);
        return getDao().insertPlan(plan);
    }
}
