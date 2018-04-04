package com.crscd.passengerservice.plan.business;

import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.plan.business.innerinterface.DeletePeriodPlanInterface;
import com.crscd.passengerservice.plan.business.innerinterface.GeneratePeriodPlanInterface;

import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/30
 * Time: 8:45
 * 为了能够实现多用户同时操作，应保证每个用户在操作的时候，
 * 都返回一个新的PlanGenAndDelManagemet实例
 */
public class PlanGenAndDelManager {

    public HashMap<String, String> generatePlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {
        GeneratePeriodPlanInterface generator = ContextHelper.getPlanGeneratorParallel();
        return generator.generateMultiPeriodPlan(trainNumList, stationName, startDate, endDate);
    }

    public HashMap<String, String> delPlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {
        DeletePeriodPlanInterface deleter = ContextHelper.getPlanDeleterStream();
        return deleter.delPlanList(trainNumList, stationName, startDate, endDate);
    }

}
