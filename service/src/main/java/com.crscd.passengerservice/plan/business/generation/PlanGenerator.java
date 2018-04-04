package com.crscd.passengerservice.plan.business.generation;

import com.crscd.passengerservice.plan.business.innerinterface.GeneratePeriodPlanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/30
 * Time: 9:38
 */
public class PlanGenerator implements GeneratePeriodPlanInterface {

    private static final Logger logger = LoggerFactory.getLogger(PlanGenerator.class);

    private PlanGenerateInDB planGenerateInDB;
    private PlanGenerateInMem planGenerateInMem;

    public PlanGenerator(PlanGenerateInDB planGenerateInDB, PlanGenerateInMem planGenerateInMem) {
        this.planGenerateInDB = planGenerateInDB;
        this.planGenerateInMem = planGenerateInMem;
    }

    /**
     * 生成多趟列车多日的计划
     *
     * @param trainNumList
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public HashMap<String, String> generateMultiPeriodPlan(List<String> trainNumList, String stationName, String startDate, String endDate) {
        planGenerateInMem.generateMultiPeriodPlan(trainNumList, stationName, startDate, endDate);
        planGenerateInDB.setCellList(planGenerateInMem.getCellList());
        planGenerateInDB.insertPlansIntoDB();
        return new HashMap<>(planGenerateInDB.getResultList());
    }
}
