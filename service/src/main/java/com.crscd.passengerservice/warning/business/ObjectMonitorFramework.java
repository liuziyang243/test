package com.crscd.passengerservice.warning.business;

import com.crscd.framework.job.IndependentJobHelper;
import com.crscd.passengerservice.warning.enumtype.MonitorModeEnum;
import com.crscd.passengerservice.warning.enumtype.MonitorStateEnum;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 14:27
 */
public class ObjectMonitorFramework {
    // 用于存储状态的队列，如果是比例模式，queue的长度为计算值；
    // 如果是立即模式，则queue的长度为1，仅需要存储上次的值即可
    // key1: deviceID
    // key2: property value: state queue
    private Map<String, Map<String, Queue<MonitorStateEnum>>> stateQueueMap;

    private MonitoringInterface stateInterface;
    private String SchedulerName;
    private int refreshTime;

    public ObjectMonitorFramework(String SchedulerName, int refreshTime, MonitoringInterface stateInterface) {
        this.stateInterface = stateInterface;
        this.SchedulerName = SchedulerName;
        this.refreshTime = refreshTime;

        stateQueueMap = new HashMap<>();
        // 初始化queue
        for (String id : stateInterface.getMonitorObjectList()) {
            // 初始化propertyMap
            Map<String, Queue<MonitorStateEnum>> propertyMap = new HashMap<>();
            for (MonitorProperty property : stateInterface.getPropertyList()) {
                if (property.getMonitorModeEnum().equals(MonitorModeEnum.IMMEDIATE)) {
                    propertyMap.put(property.getPropertyName(), new ArrayBlockingQueue<>(1));
                } else {
                    // 向上取整计算队列长度
                    double interval = (double) property.getPeriodLength();
                    int capacity = (int) Math.ceil(interval / refreshTime);
                    propertyMap.put(property.getPropertyName(), new ArrayBlockingQueue<>(capacity));
                }
            }
            stateQueueMap.put(id, propertyMap);
        }
    }

    public void start() {
        IndependentJobHelper refreshDataJobScheduler = new IndependentJobHelper(SchedulerName, "2");
        refreshDataJobScheduler.startRepeatedJobNow(refreshJob.class, refreshTime);
    }

    // 处理立即模式的情况
    private void processPeriodCase(String id, MonitorProperty property, String pName, Queue<MonitorStateEnum> queue) {
        MonitorStateEnum pState = stateInterface.getMonitoringState(id, pName);
        // 计算原来的值
        boolean oldFlag = checkStateNormal(calcStaticsValue(queue), property);
        // 更新队列，如果无法加入说明队列已经满了
        if (!queue.offer(pState)) {
            queue.poll();
            queue.offer(pState);
        }
        // 计算新值
        float value = calcStaticsValue(queue);
        // 如果当前值为异常，则触发处理
        if (!checkStateNormal(value, property)) {
            stateInterface.notifyAbnormal(id, property.getPropertyName());
        } else {
            if (!oldFlag) {
                //如果由异常转为正常，则触发处理
                stateInterface.notifyRecover(id, pName);
            }
        }
    }

    // 处理周期模式的情况
    private void processImmediateCase(String id, MonitorProperty property, String pName, MonitorStateEnum presentState, Queue<MonitorStateEnum> queue) {
        //如果当前值为异常，则触发处理
        if (presentState.equals(MonitorStateEnum.ABNORMAL)) {
            stateInterface.notifyAbnormal(id, property.getPropertyName());
        }
        //如果由异常转为正常，则触发处理
        else if (presentState.equals(MonitorStateEnum.NORMAL)) {
            if (!queue.isEmpty()) {
                MonitorStateEnum oldState = queue.peek();
                if (oldState.equals(MonitorStateEnum.ABNORMAL)) {
                    stateInterface.notifyRecover(id, pName);
                }
            }
        }
        if (!queue.isEmpty()) {
            queue.poll();
        }
        queue.offer(presentState);
    }

    // 判断统计值
    private boolean checkStateNormal(float value, MonitorProperty property) {
        if (value < 0) {
            return true;
        }
        switch (property.getThresholdCompareMode()) {
            case LESS:
                return property.getThreshold() - value >= 0;
            case MORE:
                return value - property.getThreshold() >= 0;
            default:
                throw new IllegalArgumentException("wrong input for ThresholdCompareMode " + property.getThresholdCompareMode());
        }
    }

    // 计算统计值 正常量的百分比
    private float calcStaticsValue(Queue<MonitorStateEnum> queue) {
        if (queue.isEmpty()) {
            return -1;
        }
        int normalCount = 0;
        for (MonitorStateEnum state : queue) {
            if (state.equals(MonitorStateEnum.NORMAL)) {
                normalCount += 1;
            }
        }
        return (float) normalCount / queue.size();
    }

    /* refresh and gen warning message */
    @DisallowConcurrentExecution
    private class refreshJob implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            for (String id : stateInterface.getMonitorObjectList()) {
                Map<String, Queue<MonitorStateEnum>> propertyMap = stateQueueMap.get(id);
                for (MonitorProperty property : stateInterface.getPropertyList()) {
                    String pName = property.getPropertyName();
                    MonitorStateEnum presentState = stateInterface.getMonitoringState(id, pName);
                    Queue<MonitorStateEnum> queue = propertyMap.get(pName);
                    switch (property.getMonitorModeEnum()) {
                        case IMMEDIATE:
                            processImmediateCase(id, property, pName, presentState, queue);
                            break;
                        case PERIOD_STATISTICS:
                            processPeriodCase(id, property, pName, queue);
                            break;
                        default:
                            throw new IllegalArgumentException("System has not supported mode " + property.getMonitorModeEnum());
                    }
                }
            }
        }
    }
}
