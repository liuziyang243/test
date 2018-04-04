package com.crscd.passengerservice.plan.pool;

import com.crscd.passengerservice.plan.dao.daointerface.PlanDAOInterface;
import com.crscd.passengerservice.plan.domainobject.DispatchStationPlan;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 10:11
 */
public class DispatchPlanDataPool extends AbstractPlanDataPool<DispatchStationPlan> {
    public DispatchPlanDataPool(int durationInDays, PlanDAOInterface<DispatchStationPlan> dao) {
        super(durationInDays, dao);
    }
}
