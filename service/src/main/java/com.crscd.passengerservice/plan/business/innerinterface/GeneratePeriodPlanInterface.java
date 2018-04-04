package com.crscd.passengerservice.plan.business.innerinterface;

import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/15 14:53
 * @version v1.00
 */
public interface GeneratePeriodPlanInterface {
    HashMap<String, String> generateMultiPeriodPlan(List<String> trainNumList, String stationName, String startDate, String endDate);
}
