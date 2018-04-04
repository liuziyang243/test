package com.crscd.passengerservice.plan.business.generation;

import com.crscd.passengerservice.context.ContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/15 14:55
 * @version v1.00
 */
public class PlanGenerateTask extends RecursiveTask<Map<String, String>> {
    private static final Logger logger = LoggerFactory.getLogger(PlanGenerateTask.class);

    private final int THRESHOLD;
    private List<PlanGenerateCell> cellList;

    public PlanGenerateTask(List<PlanGenerateCell> cellList, int threshold) {
        this.cellList = cellList;
        this.THRESHOLD = threshold;
    }

    @Override
    protected Map<String, String> compute() {
        Map<String, String> result = new ConcurrentHashMap<>();

        // 如果任务足够少，就进行计划的插入和执行
        boolean canExecute = cellList.size() <= THRESHOLD;
        if (canExecute) {
            //从ioc中获取实例
            PlanGenerateInDB planGenerateInDB = ContextHelper.getPlanGenerateInDB();
            planGenerateInDB.setCellList(cellList);
            planGenerateInDB.insertPlansIntoDB();
            result.putAll(planGenerateInDB.getResultList());
        } else {
            // 如果任务数大于阀值，就分裂成两个子任务进行处理
            int taskNum = cellList.size();
            int middle = taskNum / 2;

            logger.info("[PlanGenerateTask] Task threshold is " + THRESHOLD + ". Fork task from " + taskNum + " into two tasks with size " + middle + " and " + (taskNum - middle));

            List<PlanGenerateCell> leftCellList = cellList.subList(0, middle);
            List<PlanGenerateCell> rightCellList = cellList.subList(middle, taskNum);

            PlanGenerateTask leftTask = new PlanGenerateTask(leftCellList, THRESHOLD);
            PlanGenerateTask rightTask = new PlanGenerateTask(rightCellList, THRESHOLD);
            // 执行子任务
            invokeAll(leftTask, rightTask);
            // 等待子任务执行完成，并得到其结果
            Map<String, String> leftResult = leftTask.join();
            Map<String, String> rightResult = rightTask.join();
            // 合并子任务
            result.putAll(leftResult);
            result.putAll(rightResult);
        }

        return result;
    }
}

