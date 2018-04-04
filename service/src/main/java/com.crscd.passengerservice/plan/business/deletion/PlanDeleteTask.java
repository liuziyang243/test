package com.crscd.passengerservice.plan.business.deletion;

import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.plan.business.generation.PlanGenerateTask;
import com.crscd.passengerservice.plan.business.innerinterface.DeletePeriodPlanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/31 10:49
 * @version v1.00
 */
public class PlanDeleteTask extends RecursiveTask<Map<String, String>> {
    private static final Logger logger = LoggerFactory.getLogger(PlanGenerateTask.class);

    private final int THRESHOLD;
    private List<String> trainNumList;
    private String stationName;
    private String startDate;
    private String endDate;

    public PlanDeleteTask(List<String> trainNumList, String stationName, String startDate, String endDate, int threshold) {
        this.trainNumList = trainNumList;
        this.THRESHOLD = threshold;
        this.stationName = stationName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    protected Map<String, String> compute() {
        Map<String, String> result = new ConcurrentHashMap<>();

        // 如果任务足够少，就进行计划的删除
        boolean canExecute = trainNumList.size() <= THRESHOLD;
        if (canExecute) {
            //从ioc中获取删除执行实例
            DeletePeriodPlanInterface deleter = ContextHelper.getPlanDeleter();
            result.putAll(deleter.delPlanList(trainNumList, stationName, startDate, endDate));
        } else {
            // 如果任务数大于阀值，就分裂成两个子任务进行处理
            int taskNum = trainNumList.size();
            int middle = taskNum / 2;

            logger.info("[PlanDeleteTask] Task threshold is " + THRESHOLD + ". Fork task from " + taskNum + " into two tasks with size " + middle + " and " + (taskNum - middle));

            List<String> leftTrainNumList = trainNumList.subList(0, middle);
            List<String> rightTrainNumList = trainNumList.subList(middle, taskNum);

            PlanDeleteTask leftTask = new PlanDeleteTask(leftTrainNumList, stationName, startDate, endDate, THRESHOLD);
            PlanDeleteTask rightTask = new PlanDeleteTask(rightTrainNumList, stationName, startDate, endDate, THRESHOLD);
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
