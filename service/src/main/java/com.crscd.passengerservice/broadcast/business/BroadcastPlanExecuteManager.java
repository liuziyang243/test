package com.crscd.passengerservice.broadcast.business;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.broadcast.content.business.BroadcastContentReplaceInterface;
import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.drivers.serviceinterface.BroadcastDriverInterface;
import com.crscd.passengerservice.broadcast.record.business.BroadcastRecordManager;
import com.crscd.passengerservice.broadcast.record.domainobject.BroadcastRecord;
import com.crscd.passengerservice.broadcast.record.domainobject.BroadcastRecordKey;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.business.BroadcastStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.BroadcastKey;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.serviceinterface.BroadcastStateInterface;
import com.crscd.passengerservice.result.base.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理自动广播和手动广播（到发、变更）
 *
 * @author lzy
 * Date: 2017/8/28
 * Time: 9:22
 */

public class BroadcastPlanExecuteManager {
    private static final Logger logger = LoggerFactory.getLogger(BroadcastPlanExecuteManager.class);
    // todo: 需要从spring注入
    private BroadcastDriverInterface driverInterface;
    private BroadcastStationPlanManager planManager;
    private ConfigManager configManager;
    private BroadcastRecordManager recordManager;
    private BroadcastContentReplaceInterface contentReplaceInterface;
    private BroadcastStateInterface stateInterface;
    private BroadcastSchedulerManager schedulerManager;

    public void setSchedulerManager(BroadcastSchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public void setStateInterface(BroadcastStateInterface stateInterface) {
        this.stateInterface = stateInterface;
    }

    public void setContentReplaceInterface(BroadcastContentReplaceInterface contentReplaceInterface) {
        this.contentReplaceInterface = contentReplaceInterface;
    }

    public void setDriverInterface(BroadcastDriverInterface driverInterface) {
        this.driverInterface = driverInterface;
    }

    public void setPlanManager(BroadcastStationPlanManager planManager) {
        this.planManager = planManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setRecordManager(BroadcastRecordManager recordManager) {
        this.recordManager = recordManager;
    }

    /************************************************************
     * 调用广播系统驱动播放广播计划
     ***********************************************************/
    /**
     * 执行自动化的广播下发
     */
    void makeAutoBroadcastForSinglePlan(String planKey) {
        BroadcastStationPlan plan = planManager.getPlan(planKey);
        StringBuilder stringBuilder = new StringBuilder();
        if (plan == null) {
            logger.warn("[Broadcast] Broadcast plan-" + planKey + "is null.");
        } else if (plan.getManualSuspendFlag()) {
            // 如果列车已经停开，则不执行该计划并进行提示打印
            logger.info("[Broadcast] The train with Broadcast plan-" + planKey + " has been terminated and does not need to execute the broadcast.");
        } else if (plan.getBroadcastOperationMode().equals(BroadcastModeEnum.MANUAL)) {
            // 如果计划是手动执行，则不执行该计划并进行提示打印
            logger.info("[Broadcast] The train with Broadcast plan-" + planKey + " is in manual mode and does not need to execute the broadcast.");
        } else {
            makeSingleBroadcastByDriver(plan);
            stringBuilder.append("[broadcast] make auto broadcast ").append(plan.getBroadcastContentName()).append("\n")
                    .append("execute time: ").append(DateTimeUtil.getCurrentDatetimeString()).append("\n");
            logger.debug(stringBuilder.toString());
        }
    }

    /**
     * 调用驱动接口播放单条广播
     */
    public ResultMessage makeSingleBroadcastByDriver(String planKey) {
        BroadcastStationPlan plan = planManager.getPlan(planKey);
        if (null == plan) {
            return new ResultMessage(1101);
        }
        return makeSingleBroadcastByDriver(plan);
    }


    /**
     * 调用驱动接口播放单条广播
     */
    private ResultMessage makeSingleBroadcastByDriver(BroadcastStationPlan plan) {
        if (null == plan) {
            return new ResultMessage(1101);
        }
        // 根据广播业务内容和广播计划产生实际的广播文字内容
        String broadcastContent = contentReplaceInterface.getLocalBroadcastContent(plan) + contentReplaceInterface.getEngBroadcastContent(plan);

        // 如果广播内容为空，则没有必要调用广播接口
        if (StringUtil.isEmpty(broadcastContent)) {
            stateInterface.setBroadcastPlanExecuteState(plan.getPlanKey(), BroadcastStateEnum.CONTENT_EMPTY_QUIT);
            return new ResultMessage(1102);
        }
        // 需要根据系统配置信息接口将广播区转换成广播区编号
        List<Integer> areaList = configManager.getBroadcastAreaIDList(plan.getBroadcastArea(), plan.getPresentStation().getStationName());

        // 打印广播区信息
        // 如果广播区为空，则没有必要调用广播接口
        if (ListUtil.isEmpty(areaList)) {
            logger.warn("[Broadcast] Broadcast area list in [" + plan.getPlanKey() + "] is empty!");
            stateInterface.setBroadcastPlanExecuteState(plan.getPlanKey(), BroadcastStateEnum.BROADCAST_AREA_EMPTY_QUIT);
            return new ResultMessage(1103);
        } else {
            logger.debug("[Broadcast] " + plan.getPlanKey() + " broadcast zone id: " + areaList.toString());
        }
        // 将计划加入到任务记录中
        recordManager.makeBroadcastRecord(plan, broadcastContent);
        // 调用驱动接口进行广播
        return driverInterface.SyntheticBroadcastSend(plan.getPresentStation().getStationName(), areaList, broadcastContent, plan.getBroadcastPriorityLevel(), plan.getPlanKey());
    }

    /**
     * 调用驱动接口播放单条广播
     */
    public ResultMessage makeSingleBroadcastByDriver(String planKey, String contentName, String localContent, String EngContent, List<String> broadcastArea, int priorityLevel, Map<NormalSubstitutePropertyEnum, Object> propertyMap) {
        BroadcastKey broadcastKey = new BroadcastKey(planKey);
        // 根据广播业务内容和广播计划产生实际的广播文字内容
        String broadcastContent = contentReplaceInterface.getBroadcastContent(localContent, EngContent, propertyMap);

        // 如果广播内容为空，则没有必要调用广播接口
        if (StringUtil.isEmpty(broadcastContent)) {
            stateInterface.setBroadcastPlanExecuteState(planKey, BroadcastStateEnum.CONTENT_EMPTY_QUIT);
            return new ResultMessage(1102);
        }
        // 需要根据系统配置信息接口将广播区转换成广播区编号
        List<Integer> areaList = configManager.getBroadcastAreaIDList(broadcastArea, broadcastKey.getStationName());

        // 打印广播区信息
        // 如果广播区为空，则没有必要调用广播接口
        if (ListUtil.isEmpty(areaList)) {
            logger.warn("[Broadcast] Broadcast area list in train manual broadcast [" + planKey + "] is empty!");
            stateInterface.setBroadcastPlanExecuteState(planKey, BroadcastStateEnum.BROADCAST_AREA_EMPTY_QUIT);
            return new ResultMessage(1103);
        } else {
            logger.debug("[Broadcast] Train manual broadcast [" + planKey + "] broadcast zone id: " + areaList.toString());
        }
        // 将计划加入到任务记录中
        recordManager.makeBroadcastRecord(planKey, contentName, broadcastContent, broadcastArea, priorityLevel);
        // 调用驱动接口进行广播
        return driverInterface.SyntheticBroadcastSend(broadcastKey.getStationName(), areaList, broadcastContent, priorityLevel, planKey);
    }

    /************************************************************
     * 调用广播系统驱动停止播放广播计划
     ***********************************************************/
    /**
     * 根据前台提供的信息停止某条广播计划--根据recordKey停止
     */
    public ResultMessage stopSingleBroadcastByDriver(String recordKey) {
        BroadcastRecordKey key = new BroadcastRecordKey(recordKey);
        String stationName = key.getStationName();
        BroadcastRecord record = recordManager.getBroadcastRecordByKey(recordKey);
        // 如果查不到record，则直接返回
        if (null == record) {
            return new ResultMessage(1106);
        }
        // 如果record对应的状态不是播放中或者队列中，则返回
        if (!checkBroadcastState(record.getOperationState())) {
            return new ResultMessage(1104);
        }

        // 通过广播区获取广播设备id列表
        List<String> broadcastArea = record.getBroadcastArea();
        // 需要根据系统配置信息接口将广播区转换成广播区编号
        List<Integer> areaList = configManager.getBroadcastAreaIDList(broadcastArea, stationName);

        // 将广播区信息打印出来
        // 如果广播区为空，则没有必要调用广播接口
        if (ListUtil.isEmpty(areaList)) {
            logger.warn("[Broadcast] Broadcast area list in broadcast record [" + recordKey + "] is empty!");
            stateInterface.setBroadcastPlanExecuteState(recordKey, BroadcastStateEnum.MANUAL_STOP);
            return new ResultMessage(1103);
        } else {
            logger.debug("[Broadcast] Stop broadcast record [" + recordKey + "] broadcast zone id: " + areaList.toString());
        }
        return driverInterface.SyntheticBroadcastStop(stationName, areaList, recordKey);
    }

    /**
     * 根据前台提供的信息停止某条广播计划--根据broadcastKey停止
     * 当状态为待执行时，说明计划是自动广播计划，需要从调度器中去除并转换为手动模式
     */
    public HashMap<String, ResultMessage> stopBroadcastPlanByDriver(String planKey) {
        HashMap<String, ResultMessage> result = new HashMap<>();
        BroadcastStationPlan plan = planManager.getPlan(planKey);
        if (null == plan) {
            result.put(planKey, new ResultMessage(1101));
            return result;
        }
        BroadcastKey key = new BroadcastKey(planKey);
        // 对于未执行的自动计划，之需要从调度器中去除即可
        if (key.getKind().equals(BroadcastKindEnum.ARRIVE_DEPARTURE)
                || plan.getBroadcastOperationMode().equals(BroadcastModeEnum.AUTO)
                || plan.getBroadcastState().equals(BroadcastStateEnum.WAIT_EXECUTE)) {
            schedulerManager.delSinglePlanFromScheduler(plan);
            result.put(planKey, new ResultMessage());
        }

        // 对于正在执行的record，需要逐一停止执行
        List<BroadcastRecord> broadcastRecordList = recordManager.getBroadcastRecordListByPlanKey(planKey);
        for (BroadcastRecord record : broadcastRecordList) {
            if (checkBroadcastState(record.getOperationState())) {
                result.put(record.getRecordKey(), stopSingleBroadcastByDriver(record.getRecordKey()));
            }
        }
        return result;
    }

    // todo: 注意，从机制上record中都是调用广播驱动接口产生
    // 不应该出现WAIT_EXECUTE状态，但是不保证驱动没有及时回写导致问题
    // 这里需要和驱动封装者讨论
    private boolean checkBroadcastState(BroadcastStateEnum state) {
        return state.equals(BroadcastStateEnum.IN_QUEUE)
                || state.equals(BroadcastStateEnum.WAIT_EXECUTE)
                || state.equals(BroadcastStateEnum.BROADCASTING);
    }
}
