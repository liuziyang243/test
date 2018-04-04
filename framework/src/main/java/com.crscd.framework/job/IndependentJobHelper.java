package com.crscd.framework.job;

import com.crscd.framework.util.number.RandomUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Create by: lzy
 * Date: 2017/6/15
 * Time: 11:19
 */
public class IndependentJobHelper {
    private static final Logger logger = LoggerFactory.getLogger(IndependentJobHelper.class);
    private DefaultSchedulerProvider sf;

    public IndependentJobHelper(String name, String count) {
        sf = new DefaultSchedulerProvider(name, count);
    }

    /*
     * 采用调度器单例模式，可以通过关闭调度器关闭全部的调度任务
     */
    private Scheduler getDefaultScheduler() throws SchedulerException {
        return sf.getScheduler();
    }

    /*
     * Trigger类型为cron，Job无参，无限重复，立即执行
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    public void startCronRepeatedJob(Class<?> jobClass, String cron) {
        JobDetail jobDetail = createJobDetail(jobClass);
        String tag = jobClass.getName() + RandomUtil.getUUID();
        String group = jobClass.getName();
        Trigger trigger = TriggerHelper.createTrigger(tag, group, cron);
        try {
            Scheduler scheduler = getDefaultScheduler();
            doStartJob(jobClass, jobDetail, trigger, scheduler);
        } catch (SchedulerException e) {
            logger.error("[IndependJob] create independent job wrong.", e);
        }
    }

    /*
     * Trigger类型为simpleTrigger，Job无参，以秒为单位无限周期执行Job，立即执行
     * 能够管理全部任务，无需传入jobName和jobGroupName
     */
    public void startRepeatedJobNow(Class<?> jobClass, int second) {
        startJob(jobClass, second, -1, "", "");
    }

    /*
     * Trigger类型为simpleTrigger，Job有参，以秒为单位无限周期执行Job，立即执行
     * 能够管理全部任务，无需传入jobName和jobGroupName
     */
    public void startRepeatedJobNow(Class<?> jobClass, int second, Map<String, Object> params) {
        startJob(jobClass, second, -1, "", "", params);
    }

    /*
     * Trigger类型为simpleTrigger，Job有参
     * 重复次数通过count配置，0：执行1次；正整数：多次；-1：无限次
     * 可以指定开始和结束时间，如果开始时间和结束时间配置为""则表示无指定时间
     * 能够自己根据类名生成默认的jobName和jobGroupName
     */
    private void startJob(Class<?> jobClass, int second, int count, String start, String end,
                          Map<String, Object> params) {
        JobDetail jobDetail = createJobDetail(jobClass);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
        }
        Trigger trigger = TriggerHelper.createTrigger(jobClass, second, count, start, end);
        try {
            Scheduler scheduler = getDefaultScheduler();
            doStartJob(jobClass, jobDetail, trigger, scheduler);
        } catch (SchedulerException e) {
            logger.error("[IndependJob] create independent job wrong.", e);
        }
    }

    /*
     * Trigger类型为simpleTrigger，Job有参
     * 重复次数通过count配置，0：执行1次；正整数：多次；-1：无限次
     * 可以指定开始和结束时间，如果开始时间和结束时间配置为""则表示无指定时间
     * 能够自己根据类名生成默认的jobName和jobGroupName
     */
    private void startJob(Class<?> jobClass, int second, int count, String start, String end) {
        JobDetail jobDetail = createJobDetail(jobClass);
        Trigger trigger = TriggerHelper.createTrigger(jobClass, second, count, start, end);
        try {
            Scheduler scheduler = getDefaultScheduler();
            doStartJob(jobClass, jobDetail, trigger, scheduler);
        } catch (SchedulerException e) {
            logger.error("[IndependJob] create independent job wrong.", e);
        }
    }

    /*
     * 将实现job接口的类封装成jobDetail，该封装为无参方式封装
     */
    @SuppressWarnings("unchecked")
    private JobDetail createJobDetail(Class<?> jobClass) {
        String tag = jobClass.getName() + RandomUtil.getUUID();
        String group = jobClass.getName();
        return JobBuilder.newJob((Class<? extends org.quartz.Job>) jobClass).withIdentity(tag, group).build();
    }

    /*
     * 执行scheduler.start()方法，并将jobClass与scheduler存储入map中
     */
    @SuppressWarnings("serial")
    private void doStartJob(Class<?> jobClass, JobDetail jobDetail, Trigger trigger, Scheduler scheduler) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown() && !scheduler.isStarted()) {
                scheduler.start();
            }
            logger.debug("[JobHelper] start job: " + jobClass.getName());
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Start job got wrong for class " + jobClass.getName(), e);
        }
    }

}
