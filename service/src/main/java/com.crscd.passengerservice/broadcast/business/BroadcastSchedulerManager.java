package com.crscd.passengerservice.broadcast.business;

import com.crscd.framework.job.JobHelper;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.broadcast.enumtype.BroadcastPlanExecuteStateEnum;
import com.crscd.passengerservice.broadcast.enumtype.BroadcastTimeStateEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.plan.domainobject.BroadcastKey;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 16:57
 */
public class BroadcastSchedulerManager {
    private static final Logger logger = LoggerFactory.getLogger(BroadcastSchedulerManager.class);

    /**
     * 在调度器中增加一组广播计划
     * 用于初始化
     */
    public Map<String, BroadcastPlanExecuteStateEnum> addBroadcastPlanListToScheduler(List<BroadcastStationPlan> planList) {
        Map<String, BroadcastPlanExecuteStateEnum> result = new HashMap<>();
        for (BroadcastStationPlan plan : planList) {
            result.put(plan.getPlanKey(), makeSinglePlanToScheduler(plan));
        }
        return result;
    }

    /**
     * 在调度器中删除多条广播计划
     */
    public Map<String, BroadcastPlanExecuteStateEnum> delBroadcastPlanListFromScheduler(List<BroadcastStationPlan> planList) {
        Map<String, BroadcastPlanExecuteStateEnum> result = new HashMap<>();
        for (BroadcastStationPlan plan : planList) {
            result.put(plan.getPlanKey(), delSinglePlanFromScheduler(plan));
        }
        return result;
    }

    /**
     * 将单条列车广播计划部署到调度器中
     */
    private BroadcastPlanExecuteStateEnum makeSinglePlanToScheduler(BroadcastStationPlan plan) {
        if (plan == null) {
            logger.debug("[Broadcast] Broadcast plan is null!");
            return BroadcastPlanExecuteStateEnum.PLAN_NULL;
        }
        // 如果该条计划是手动执行，则不需要将该条计划加入到调度器中
        if (plan.getBroadcastOperationMode().equals(BroadcastModeEnum.MANUAL)) {
            logger.debug("[Broadcast] Broadcast plan [" + plan.getPlanKey() + "] is in manual mode!");
            return BroadcastPlanExecuteStateEnum.PLAN_IN_MANUAL_MODE;
        }
        // 检查广播计划的时间是否合法
        LocalDateTime executeTime = plan.getBroadcastTime();
        if (!isNeedSchedule(executeTime, plan.getPlanKey()).equals(BroadcastTimeStateEnum.TIME_OK)) {
            return BroadcastPlanExecuteStateEnum.PLAN_TIME_ILLEGAL;
        }
        BroadcastKey key = new BroadcastKey(plan.getPlanKey());
        String stationName = key.getStationName();
        String planKey = key.toString();
        String trainNum = key.getTrainNum();
        //jobName采用站名statoinCode, plankey组成
        String jobName = stationName + "_" + planKey;
        //jobGruopName采用单次列车计划标识符key+"_broadcast"
        String jobGroupName = trainNum + "_broadcast";
        // 如果有任务存在调度器中，则提示后直接返回
        if (JobHelper.hasJob(jobName, jobGroupName)) {
            logger.debug("[Broadcast] Scheduler has already had job [" + planKey + "]");
            return BroadcastPlanExecuteStateEnum.PLAN_IN_SCHEDULER;
        }
        // 将站码和广播计划的plan key作为参数传给job
        Map<String, Object> params = new HashMap<>();
        params.put(ServiceConstant.KEY, planKey);
        // 将广播任务部署到quartz调度器中
        logger.debug("[Broadcast] Register broadcast job [" + planKey + "] to quartz with execute time " + executeTime);
        JobHelper.startOnceJob(MakeBroadcastJob.class, DateTimeFormatterUtil.convertDatetimeToString(executeTime), params, jobName, jobGroupName);
        return BroadcastPlanExecuteStateEnum.PLAN_OK;
    }

    /**
     * 修改单条的列车广播计划
     * 当计划不存在于调度器内的时候，增加一条广播计划到调度器
     * 主要用于外部接口使用
     */
    public BroadcastPlanExecuteStateEnum modifySinglePlanInScheduler(BroadcastStationPlan plan) {
        if (plan == null) {
            logger.debug("[Broadcast] Broadcast plan is null!");
            return BroadcastPlanExecuteStateEnum.PLAN_NULL;
        }
        // 如果该条计划是手动执行，则不需要将该条计划加入到调度器中
        if (plan.getBroadcastOperationMode().equals(BroadcastModeEnum.MANUAL)) {
            logger.debug("[Broadcast] Broadcast Job [" + plan.getPlanKey() + "] is in manual mode!");
            return BroadcastPlanExecuteStateEnum.PLAN_IN_MANUAL_MODE;
        }
        // 检查任务执行时间是否符合要求
        LocalDateTime executeTime = plan.getBroadcastTime();
        if (!isNeedSchedule(executeTime, plan.getPlanKey()).equals(BroadcastTimeStateEnum.TIME_OK)) {
            return BroadcastPlanExecuteStateEnum.PLAN_TIME_ILLEGAL;
        }
        BroadcastKey key = new BroadcastKey(plan.getPlanKey());
        String stationName = key.getStationName();
        String planKey = key.toString();
        String trainNum = key.getTrainNum();
        //jobName采用站名statoinCode, plankey组成
        String jobName = stationName + "_" + planKey;
        //jobGruopName采用单次列车计划标识符key+"_broadcast"
        String jobGroupName = trainNum + "_broadcast";

        String executeTimeString = DateTimeFormatterUtil.convertDatetimeToString(executeTime);
        // 采用如果任务存在，则进行修改；如果不存在，则进行创建的方式处理
        if (JobHelper.hasJob(jobName, jobGroupName)) {
            JobHelper.modifyJobTime(jobName, jobGroupName, executeTimeString);
            logger.debug("[Broadcast] Modify broadcast job [%s] to quartz:%s", planKey, executeTimeString);
            return BroadcastPlanExecuteStateEnum.PLAN_IN_SCHEDULER;
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put(ServiceConstant.KEY, planKey);
            logger.debug("[Broadcast] Create broadcast job [%s] to quartz:%s", planKey, executeTimeString);
            JobHelper.startOnceJob(MakeBroadcastJob.class, executeTimeString, params, jobName, jobGroupName);
            return BroadcastPlanExecuteStateEnum.PLAN_NOT_IN_SCHEDULER;
        }
    }

    /**
     * 将单条广播计划从调度器中删除
     */
    public BroadcastPlanExecuteStateEnum delSinglePlanFromScheduler(BroadcastStationPlan plan) {
        if (plan == null) {
            logger.warn("[Broadcast] Broadcast plan is null when deleting it from scheduler!");
            return BroadcastPlanExecuteStateEnum.PLAN_NULL;
        }
        BroadcastKey key = new BroadcastKey(plan.getPlanKey());
        String stationName = key.getStationName();
        String planKey = key.toString();
        String trainNum = key.getTrainNum();
        //jobName采用站名statoinCode, plankey组成
        String jobName = stationName + "_" + planKey;
        //jobGruopName采用单次列车计划标识符key+"_broadcast"
        String jobGroupName = trainNum + "_broadcast";
        // 如果在调度器中存在任务，则将job从调度器中停止

        LocalDateTime executeTime = plan.getBroadcastTime();
        logger.debug("[Broadcast] Prepare to deletion broadcast job [" + planKey + "] from quartz.");
        if (!isNeedSchedule(executeTime, plan.getPlanKey()).equals(BroadcastTimeStateEnum.TIME_OK)) {
            return BroadcastPlanExecuteStateEnum.PLAN_TIME_ILLEGAL;
        }

        if (JobHelper.hasJob(jobName, jobGroupName)) {
            JobHelper.removeJob(JobKey.jobKey(jobName, jobGroupName));
            logger.debug("[Broadcast] Deleted broadcast job [" + planKey + "] from scheduler.");
            return BroadcastPlanExecuteStateEnum.PLAN_IN_SCHEDULER;
        }
        return BroadcastPlanExecuteStateEnum.PLAN_NOT_IN_SCHEDULER;
    }

    /**
     * 将全部广播计划从调度器中删除
     */
    public void delAllPlan() {
        JobHelper.removeJobs(MakeBroadcastJob.class);
    }

    /**
     * 将给定时间与当前服务器时间进行比较，
     * 如果给定时间在服务器时间之后，则返回true，否则返回false
     * 如果时间格式非法或者为空，返回false
     */
    private BroadcastTimeStateEnum isNeedSchedule(LocalDateTime time, String planKey) {
        if (null == time) {
            // 如果时间是空的
            logger.debug("[Broadcast] Broadcast Job [" + planKey + "] execute time is null!");
            return BroadcastTimeStateEnum.TIME_NULL;
        } else if (LocalDateTime.now().isAfter(time)) {
            // 如果执行时间在当前时间之前，说明已经过了执行时间
            logger.debug("[Broadcast] Broadcast Job [" + planKey + "] execute time: " + time + " is earlier than present!");
            return BroadcastTimeStateEnum.TIME_OVERDUE;
        } else {
            // 如果执行时间无误，则返回0
            return BroadcastTimeStateEnum.TIME_OK;
        }
    }
}
