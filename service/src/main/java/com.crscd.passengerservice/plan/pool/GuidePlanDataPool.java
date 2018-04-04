package com.crscd.passengerservice.plan.pool;

import com.crscd.passengerservice.plan.dao.daointerface.PlanDAOInterface;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 17:01
 */
public class GuidePlanDataPool extends AbstractPlanDataPool<GuideStationPlan> {
    public GuidePlanDataPool(int durationInDays, PlanDAOInterface<GuideStationPlan> dao) {
        super(durationInDays, dao);
    }
}
