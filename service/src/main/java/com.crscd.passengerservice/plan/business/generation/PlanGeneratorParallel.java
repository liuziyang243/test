package com.crscd.passengerservice.plan.business.generation;

import com.crscd.passengerservice.plan.business.innerinterface.GeneratePeriodPlanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/15 14:56
 * @version v1.00
 */
public class PlanGeneratorParallel implements GeneratePeriodPlanInterface {
    private static final Logger logger = LoggerFactory.getLogger(PlanGeneratorParallel.class);
    private final static int MIN_TASK_NUM = 20;
    private PlanGenerateInMem planGenerateInMem;

    public PlanGeneratorParallel(PlanGenerateInMem planGenerateInMem) {
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
        // 首先在内存中生成计划
        planGenerateInMem.generateMultiPeriodPlan(trainNumList, stationName, startDate, endDate);
        List<PlanGenerateCell> cellList = new ArrayList<>(planGenerateInMem.getCellList());

        planGenerateInMem.clearCachedPlan();

        // 生成插入数据库任务，将内存中生成的计划加入数据库中
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 根据CPU核数确认任务数量
        int threshold = cellList.size();
        if (threshold >= MIN_TASK_NUM) {
            int CPUs = Runtime.getRuntime().availableProcessors();
            threshold = (int) Math.ceil((double) cellList.size() / CPUs);
        }

        PlanGenerateTask task = new PlanGenerateTask(cellList, threshold);

        Future<Map<String, String>> result = forkJoinPool.submit(task);

        try {
            return new HashMap<>(result.get());
        } catch (Exception e) {
            logger.error("[PlanGeneratorParallel]", e);
        }
        return new HashMap<>();
    }
}
