package com.crscd.passengerservice.plan.pool;

import com.crscd.passengerservice.plan.dao.daointerface.PlanDAOInterface;
import com.crscd.passengerservice.plan.domainobject.PassengerStationPlan;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 17:01
 */
public class PassengerPlanDataPool extends AbstractPlanDataPool<PassengerStationPlan> {
    public PassengerPlanDataPool(int durationInDays, PlanDAOInterface<PassengerStationPlan> dao) {
        super(durationInDays, dao);
    }
}
