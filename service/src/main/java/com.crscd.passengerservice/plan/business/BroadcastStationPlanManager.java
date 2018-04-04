package com.crscd.passengerservice.plan.business;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.broadcast.business.BroadcastSchedulerManager;
import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.enumtype.BroadcastPlanExecuteStateEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.notice.domainobject.NoticeMessage;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.plan.domainobject.BroadcastKey;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.plan.pool.BroadcastPlanDataPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:35
 */
public class BroadcastStationPlanManager extends AbstractPlanManager<BroadcastStationPlan, BroadcastPlanDataPool> {
    private final static Logger logger = LoggerFactory.getLogger(BroadcastStationPlanManager.class);
    private BroadcastSchedulerManager schedulerManager;
    private AlterBroadcastPlanGenerator alterBroadcastPlanGenerator;

    public BroadcastStationPlanManager() {
    }

    public void setAlterBroadcastPlanGenerator(AlterBroadcastPlanGenerator alterBroadcastPlanGenerator) {
        this.alterBroadcastPlanGenerator = alterBroadcastPlanGenerator;
    }

    public void setSchedulerManager(BroadcastSchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    /**
     * 将全部计划都更新到调度器中
     * 刷新内存中的广播计划到调度器中
     */
    public Map<String, BroadcastPlanExecuteStateEnum> loadAllBroadcastPlansToScheduler() {
        List<BroadcastStationPlan> planList = getAllPlanListInDataPool();
        return schedulerManager.addBroadcastPlanListToScheduler(planList);
    }

    /**
     * 设置某个广播计划的执行状态
     *
     * @param planKey
     * @param state
     */
    public void setPlanExecuteState(String planKey, BroadcastStateEnum state) {
        BroadcastKey broadcastKey = new BroadcastKey(planKey);
        // 只有到发和变更广播才存在计划，其他广播属于临时性，没有计划对象
        if (broadcastKey.getKind().equals(BroadcastKindEnum.ARRIVE_DEPARTURE) || broadcastKey.getKind().equals(BroadcastKindEnum.ALTERATION)) {
            BroadcastStationPlan plan = getPlanDataPool().getPlan(planKey);
            if (null != plan) {
                plan.setBroadcastState(state);
                getPlanDataPool().updatePlan(planKey, plan);
            }
        }
    }

    /**
     * 修改某条广播计划的执行时间
     *
     * @param planKey
     * @param newTime
     * @return
     */
    public boolean modifyPlanExecuteTime(String planKey, String newTime) {
        BroadcastStationPlan plan = getPlanDataPool().getPlan(planKey);
        if (null != plan) {
            plan.setBroadcastTime(DateTimeFormatterUtil.convertStringToDatetime(newTime));
            if (getPlanDataPool().updatePlan(planKey, plan)) {
                // 修改广播执行时间需要重新调整调度器
                schedulerManager.modifySinglePlanInScheduler(plan);
            }
        }
        return false;
    }

    /**
     * 修改某条广播计划的执行区域
     *
     * @param planKey
     * @param newAreaList
     * @return
     */
    public boolean modifyPlanExecuteArea(String planKey, List<String> newAreaList) {
        BroadcastStationPlan plan = getPlanDataPool().getPlan(planKey);
        if (null != plan) {
            plan.setBroadcastArea(newAreaList);
            return getPlanDataPool().updatePlan(planKey, plan);
        }
        return true;
    }

    /**
     * 修改单条广播计划的作业模式
     *
     * @param planKey
     * @param mode
     * @return
     */
    public boolean modifyPlanExecuteMode(String planKey, BroadcastModeEnum mode) {
        BroadcastStationPlan plan = getPlanDataPool().getPlan(planKey);
        if (null != plan) {
            plan.setBroadcastOperationMode(mode);
            if (getPlanDataPool().updatePlan(planKey, plan)) {
                // 对于模式切换，需要调整调度器
                if (mode.equals(BroadcastModeEnum.MANUAL)) {
                    schedulerManager.delSinglePlanFromScheduler(plan);
                } else if (mode.equals(BroadcastModeEnum.AUTO)) {
                    schedulerManager.modifySinglePlanInScheduler(plan);
                }
            } else {
                return false;
            }
        } else {
            logger.warn("[Broadcast] The broadcast plan-%s is null when modify execute mode to %s", mode, mode.toString());
        }
        return true;
    }

    /**
     * 修改某个车站一段时间内的广播计划的作业模式
     *
     * @param stationName
     * @param mode
     * @param startDate
     * @param endDate
     * @return
     */
    public Map<String, Boolean> modifyPeriodPlanOfStationExecuteMode(String stationName, BroadcastModeEnum mode, String startDate, String endDate) {
        Map<String, Boolean> resultList = new HashMap<>();
        List<BroadcastStationPlan> planList = getPlanList(stationName, null, BroadcastKindEnum.ARRIVE_DEPARTURE, startDate, endDate, true);
        for (BroadcastStationPlan plan : planList) {
            resultList.put(plan.getPlanKey(), modifyPlanExecuteMode(plan.getPlanKey(), mode));
        }
        return resultList;
    }

    /**
     * 在原有的过滤基础上增加了广播类型的过滤
     * 如果广播类型为空或者为ALL，则返回全部广播
     * 推荐使用BroadcastKindEnum.ALL
     *
     * @param stationName
     * @param trainNum
     * @param broadcastKind
     * @param startDate
     * @param endDate
     * @param fuzzyQueryFlag
     * @return
     */
    public List<BroadcastStationPlan> getPlanList(String stationName, String trainNum, BroadcastKindEnum broadcastKind, String startDate, String endDate, boolean fuzzyQueryFlag) {
        List<BroadcastStationPlan> result = new ArrayList<>();
        List<BroadcastStationPlan> planList = getPlanList(stationName, trainNum, startDate, endDate, fuzzyQueryFlag);
        if (broadcastKind == null || BroadcastKindEnum.ALL.equals(broadcastKind)) {
            return planList;
        }
        for (BroadcastStationPlan plan : planList) {
            if (broadcastKind.equals(plan.getBroadcastKind())) {
                result.add(plan);
            }
        }
        return result;
    }

    /**
     * 获取DataPool中的全部广播计划，以便于加载到调度器中
     *
     * @return
     */
    private List<BroadcastStationPlan> getAllPlanListInDataPool() {
        return getPlanDataPool().getAllPlan();
    }


    /**
     * TODO: 是否影响广播执行时间，如果影响，则需要调整调度器，等待测试
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean modifyPlan(NoticeMessage notice) {
        if (!notice.getReceiver().equals(ReceiverEnum.BROADCAST_PLAN)) {
            throw new IllegalArgumentException("Get wrong notice from system!" + notice.toString());
        }
        String planKey = new KeyBase(notice.getTrainNum(), notice.getPlanDate(), notice.getStationName()).toString();
        List<BroadcastStationPlan> planList = getPlanDataPool().getDao().selectPlanList(notice.getTrainNum(), notice.getStationName(), DateTimeFormatterUtil.convertDateToString(notice.getPlanDate()));
        // 如果修改对象-广播计划已经不存在，则直接返回
        if (ListUtil.isEmpty(planList)) {
            return true;
        }
        boolean flag = true;
        //用于生成变更广播计划
        Map<NoticeModifyEnum, String> alternationBroadcastPlanGenReasons = new HashMap<>();
        Map<NoticeModifyEnum, String> modifyMap = notice.getModifiedDataMap();

        for (BroadcastStationPlan plan : planList) {
            for (Map.Entry<NoticeModifyEnum, String> entry : modifyMap.entrySet()) {
                switch (entry.getKey()) {
                    case ACTUAL_TRACK_NUM:
                        // 在实际设置前判断一下修改值是否与原值一致
                        int modifiedTrackNum = CastUtil.castInt(entry.getValue());
                        if (modifiedTrackNum != plan.getActualTrackNum()) {
                            plan.setActualTrackNum(modifiedTrackNum);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case ACTUAL_ARRIVE_TIME:
                        // 在实际设置前判断一下修改值是否与原值一致
                        LocalDateTime modifiedArrTime = DateTimeFormatterUtil.convertStringToDatetime(entry.getValue());
                        if (!plan.getActualArriveTime().equals(modifiedArrTime)) {
                            plan.setActualArriveTime(modifiedArrTime);
                            plan.setArriveStateReason(getReason(notice.getSender()));
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case ACTUAL_DEPARTURE_TIME:
                        // 在实际设置前判断一下修改值是否与原值一致
                        LocalDateTime modifiedDepTime = DateTimeFormatterUtil.convertStringToDatetime(entry.getValue());
                        if (!plan.getActualDepartureTime().equals(modifiedDepTime)) {
                            plan.setActualDepartureTime(modifiedDepTime);
                            plan.setDepartureStateReason(getReason(notice.getSender()));
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case MANUAL_SUSPEND:
                        // 在实际设置前判断一下修改值是否与原值一致
                        boolean suspendFlag = CastUtil.castBoolean(entry.getValue());
                        if (suspendFlag != plan.getManualSuspendFlag()) {
                            plan.setManualSuspendFlag(suspendFlag);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }

                        }
                        break;
                    case START_CHECK_TIME:
                        // 在实际设置前判断一下修改值是否与原值一致
                        LocalDateTime startTime = DateTimeFormatterUtil.convertStringToDatetime(entry.getValue());
                        if (!plan.getStartAboardCheckTime().equals(startTime)) {
                            plan.setStartAboardCheckTime(startTime);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case STOP_CHECK_TIME:
                        // 在实际设置前判断一下修改值是否与原值一致
                        LocalDateTime stopTime = DateTimeFormatterUtil.convertStringToDatetime(entry.getValue());
                        if (!plan.getStopAboardCheckTime().equals(stopTime)) {
                            plan.setStopAboardCheckTime(stopTime);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case WAIT_ZONE:
                        // 在实际设置前判断一下修改值是否与原值一致
                        List<String> waitZone = getZoneListFromJson(entry.getValue());
                        if (!ListUtil.isSameList(waitZone, plan.getWaitZone())) {
                            plan.setWaitZone(waitZone);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case ENTRANCE_PORT:
                        // 在实际设置前判断一下修改值是否与原值一致
                        List<String> entrancePort = getZoneListFromJson(entry.getValue());
                        if (!ListUtil.isSameList(entrancePort, plan.getStationEntrancePort())) {
                            plan.setStationEntrancePort(entrancePort);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case EXIT_PORT:
                        // 在实际设置前判断一下修改值是否与原值一致
                        List<String> exitPort = getZoneListFromJson(entry.getValue());
                        if (!ListUtil.isSameList(exitPort, plan.getStationExitPort())) {
                            plan.setStationExitPort(exitPort);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case ABOARD_CHECK_PORT:
                        // 在实际设置前判断一下修改值是否与原值一致
                        List<String> checkGate = getZoneListFromJson(entry.getValue());
                        if (!ListUtil.isSameList(checkGate, plan.getAboardCheckGate())) {
                            plan.setAboardCheckGate(checkGate);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case EXIT_CHECK_PORT:
                        // 在实际设置前判断一下修改值是否与原值一致
                        List<String> exitGate = getZoneListFromJson(entry.getValue());
                        if (!ListUtil.isSameList(exitGate, plan.getExitCheckGate())) {
                            plan.setExitCheckGate(exitGate);
                            // 由于对于多条broadcast plan的修改是一致的，因此只需要加入一个即可
                            if (!alternationBroadcastPlanGenReasons.containsKey(entry.getKey())) {
                                alternationBroadcastPlanGenReasons.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                    case CHECK_STATUS:
                        // take check status from ticket system, not to process yet
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong input value " + entry.getKey());
                }
            }
            // 刷新广播执行时间，调度器会自行处理时间变更的问题
            plan.setBroadcastTime(plan.getAutoCalcBroadcastTime());
            schedulerManager.modifySinglePlanInScheduler(plan);
            // 更新flag
            flag = flag & getPlanDataPool().updatePlan(planKey, plan);
        }
        // 生成变更广播计划
        alterBroadcastPlanGenerator.genAlternationBroadcastPlan(planList.get(0), modifyMap, getPlanDataPool());
        return flag;
    }

    @Override
    public boolean checkModifyValid(NoticeMessage notice) {
        if (!notice.getReceiver().equals(ReceiverEnum.BROADCAST_PLAN)) {
            throw new IllegalArgumentException("Get wrong notice from system!" + notice.toString());
        }
        String planKey = new KeyBase(notice.getTrainNum(), notice.getPlanDate(), notice.getStationName()).toString();
        BroadcastStationPlan plan = getPlanDataPool().getPlan(planKey);
        Map<NoticeModifyEnum, String> modfiyMap = notice.getModifiedDataMap();
        for (Map.Entry<NoticeModifyEnum, String> entry : modfiyMap.entrySet()) {
            switch (entry.getKey()) {
                case ACTUAL_TRACK_NUM:
                    if (CastUtil.castInt(entry.getValue()) != plan.getActualTrackNum()) {
                        return true;
                    }
                    break;
                case ACTUAL_ARRIVE_TIME:
                    if (!plan.getActualArriveTime()
                            .equals(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()))) {
                        return true;
                    }
                    break;
                case ACTUAL_DEPARTURE_TIME:
                    if (!plan.getActualDepartureTime()
                            .equals(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()))) {
                        return true;
                    }
                    break;
                case MANUAL_SUSPEND:
                    if (plan.getManualSuspendFlag() != CastUtil.castBoolean(entry.getValue())) {
                        return true;
                    }
                    break;
                case START_CHECK_TIME:
                    if (!plan.getStartAboardCheckTime().equals(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()))) {
                        return true;
                    }
                    break;
                case STOP_CHECK_TIME:
                    if (!plan.getStopAboardCheckTime().equals(DateTimeFormatterUtil.convertStringToDatetime(entry.getValue()))) {
                        return true;
                    }
                    break;
                case WAIT_ZONE:
                    if (!ListUtil.isNotEmptySameList(plan.getWaitZone(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case ENTRANCE_PORT:
                    if (!ListUtil.isNotEmptySameList(plan.getStationEntrancePort(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case EXIT_PORT:
                    if (!ListUtil.isNotEmptySameList(plan.getStationExitPort(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case ABOARD_CHECK_PORT:
                    if (!ListUtil.isNotEmptySameList(plan.getAboardCheckGate(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case EXIT_CHECK_PORT:
                    if (!ListUtil.isNotEmptySameList(plan.getExitCheckGate(), getZoneListFromJson(entry.getValue()))) {
                        return true;
                    }
                    break;
                case CHECK_STATUS:
                    // take check status from ticket system, not to process yet
                    break;
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return false;
    }
}
