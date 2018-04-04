package com.crscd.passengerservice.plan.business.deletion;

import com.crscd.passengerservice.plan.business.generation.PlanGeneratorParallel;
import com.crscd.passengerservice.plan.business.innerinterface.DeletePeriodPlanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/31 10:48
 * @version v1.00
 */
public class PlanDeleterParallel implements DeletePeriodPlanInterface {

    private static final Logger logger = LoggerFactory.getLogger(PlanGeneratorParallel.class);

    private final static int MIN_TASK_NUM = 2;

    public PlanDeleterParallel() {
    }

    @Override
    public HashMap<String, String> delPlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 根据CPU核数确认任务数量
        int threshold = MIN_TASK_NUM;
        int CPUs = Runtime.getRuntime().availableProcessors();
        if (trainNumList.size() >= CPUs) {
            threshold = (int) Math.ceil((double) trainNumList.size() / CPUs);
        }

        PlanDeleteTask task = new PlanDeleteTask(trainNumList, stationName, startDate, endDate, threshold);

        Future<Map<String, String>> result = forkJoinPool.submit(task);

        try {
            return new HashMap<>(result.get());
        } catch (Exception e) {
            logger.error("[PlanDeleterParallel]", e);
        }
        return new HashMap<>();
    }
}
