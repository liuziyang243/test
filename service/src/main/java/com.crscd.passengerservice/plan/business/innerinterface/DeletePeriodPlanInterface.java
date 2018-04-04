package com.crscd.passengerservice.plan.business.innerinterface;

import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/31 10:47
 * @version v1.00
 */
public interface DeletePeriodPlanInterface {
    /**
     * 删除计划接口
     *
     * @param trainNumList
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    HashMap<String, String> delPlanList(List<String> trainNumList, String stationName, String startDate, String endDate);
}
