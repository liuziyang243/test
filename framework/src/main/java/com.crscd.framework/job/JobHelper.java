package com.crscd.framework.job;

/**
 * Created by lzy on 2016/1/12.
 * auth: lzy
 * desc: 本类主要提供对quzrtz的基本封装
 * 修改时间: 2016.2.16
 * 修改内容:
 * 1.对类进行了重构，增加了需要输入jobName和jobGroupName的重载函数，增加了修改jobtrigger，移除job等多个方法
 * 2.改进原有的匿名管理job和trigger的方法，增加了对jobkey的管理，完善了通过类索引关闭该类关联的全部job
 * 3.让class不可被实例化
 * 修改时间：2016.7.1
 * 修改人：lzy
 * 修改内容：
 * 1.简化了获取scheduler的单例模式，根据quartz文档，从stdSchedulerFactory获取的defaultScheduler是一个单例；
 * 2.去掉了没有被使用的静态方法，尤其去掉了不带有jobname和groupname参数的公有函数；
 * 3.根据配置文档，修改了quartz property文件中对于多线程并发的参数配置，去掉了提前时间30s的配置
 * 修改时间：2017.06.13  修改人：lzy
 * 修改内容：
 * 1. 将调度器工厂和获取调度器进行封装，使Jobhelper可以为多个独立的调度器使用
 * 2. 将Trigger定义部分独立出去，变成一个独立的工具类
 */

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.util.number.RandomUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JobHelper {
    private static final Logger logger = LoggerFactory.getLogger(JobHelper.class);
    private static final Map<Class<?>, List<JobKey>> JOB_KEY_MAP = new HashMap<Class<?>, List<JobKey>>();
    private static DefaultSchedulerProvider sf = new DefaultSchedulerProvider(FrameworkConstant.QUARTZ_PROPS);
    private static Scheduler stdScheduler = sf.getScheduler();

    /**
     * don't let anyone to instantiate this class
     */
    private JobHelper() {
        throw new Error("Can't instantiate JobHelper Class!");
    }

    /**
     * 采用调度器单例模式，可以通过关闭调度器关闭全部的调度任务
     */
    private static Scheduler getDefaultScheduler() {
        return stdScheduler;
    }

    /**
     * Trigger类型为simpleTrigger，Job无参，只执行一次Job
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    public static void startOnceJob(Class<?> jobClass, String start, String name, String group) {
        startRepeatedJob(jobClass, 0, 0, start, "", name, group);
    }

    /**
     * Trigger类型为simpleTrigger，Job有参，只执行一次Job
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    public static void startOnceJob(Class<?> jobClass, String start, Map<String, Object> params, String name, String group) {
        startJob(jobClass, 0, 0, start, "", params, name, group);
    }

    /**
     * Trigger类型为simpleTrigger，Job无参，执行有限次Job
     * 需要指定任务开始时间和结束时间
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    private static void startRepeatedJob(Class<?> jobClass, int second, int count, String start, String end, String name, String group) {
        JobDetail jobDetail = createJobDetail(jobClass, name, group);
        Trigger trigger = TriggerHelper.createTrigger(second, count, start, end, name, group);
        doStartJob(jobClass, jobDetail, trigger);
    }

    /**
     * Trigger类型为cron，Job无参，无限重复，立即执行
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    public static void startRepeatedJob(Class<?> jobClass, String cron, String name, String group) {
        JobDetail jobDetail = createJobDetail(jobClass, name, group);
        Trigger trigger = TriggerHelper.createTrigger(name, group, cron);
        doStartJob(jobClass, jobDetail, trigger);
    }

    /**
     * Trigger类型为simpleTrigger，Job无参，以秒为单位无限周期执行Job，立即执行
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    public static void startRepeatedJobNow(Class<?> jobClass, int second, String jobName, String groupName) {
        startRepeatedJob(jobClass, second, -1, "", "", jobName, groupName);
    }

    /**
     * Trigger类型为simpleTrigger，Job有参，以秒为单位无限周期执行Job，立即执行
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    public static void startRepeatedJobNow(Class<?> jobClass, int second, Map<String, Object> params, String jobName, String groupName) {
        String startTime = DateTimeUtil.getCurrentDatetimeString();
        startJob(jobClass, second, -1, startTime, "", params, jobName, groupName);
    }

    /**
     * Trigger类型为simpleTrigger，Job无参，以秒为单位无限周期执行Job，立即执行
     * 为job注册listener
     * 为了能够管理全部任务，必须传入jobName和jobgroupName
     */
    public static void startJobNow(Class<?> jobClass, JobListener listener, int second, String jobName, String groupName) {
        startJob(jobClass, listener, second, -1, "", "", jobName, groupName);
    }

    /**
     * Trigger类型为simpleTrigger，Job无参
     * 重复次数通过count配置，0：执行1次；正整数：多次；-1：无限次
     * 可以指定开始和结束时间，如果开始时间和结束时间配置为""则表示无指定时间
     * 为job注册listener
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    private static void startJob(Class<?> jobClass, JobListener listener, int second, int count, String start, String end, String name, String group) {
        JobDetail jobDetail = createJobDetail(jobClass, name, group);
        Trigger trigger = TriggerHelper.createTrigger(second, count, start, end, name, group);
        doStartJob(jobClass, listener, jobDetail, trigger);
    }

    /**
     * Trigger类型为simpleTrigger，Job有参
     * 重复次数通过count配置，0：执行1次；正整数：多次；-1：无限次
     * 可以指定开始和结束时间，如果开始时间和结束时间配置为""则表示无指定时间
     * 能够自己根据类名生成默认的jobName和jobGroupName
     */
    private static void startJob(Class<?> jobClass, int second, int count, String start, String end,
                                 Map<String, Object> params) {
        JobDetail jobDetail = createJobDetail(jobClass);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
        }
        Trigger trigger = TriggerHelper.createTrigger(jobClass, second, count, start, end);
        doStartJob(jobClass, jobDetail, trigger);
    }

    /**
     * Trigger类型为simpleTrigger，Job有参
     * 重复次数通过count配置，0：执行1次；正整数：多次；-1：无限次
     * 可以指定开始和结束时间，如果开始时间和结束时间配置为""则表示无指定时间
     * 为了能够管理全部任务，必须传入jobName和jobGroupName
     */
    public static void startJob(Class<?> jobClass, int second, int count, String start, String end,
                                Map<String, Object> params, String name, String group) {
        JobDetail jobDetail = createJobDetail(jobClass, name, group);
        if (!(params == null) && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
            }
        }
        Trigger trigger = TriggerHelper.createTrigger(second, count, start, end, name, group);
        doStartJob(jobClass, jobDetail, trigger);
    }

    /**
     * 通过将执行时间修改为当前，立刻执行一个计划中的job
     * 并在执行之后将job从调度器中清除
     */
    public static void executeJob(String jobName, String groupName) {
        //将计划执行时间调整为当前时间，立即执行任务
        modifyJobTime(jobName, groupName, DateTimeUtil.getCurrentDatetimeString());
        //将任务从调度器中清理掉，防止出现再次创建的时候任务仍然存在的情况
        removeJob(JobKey.jobKey(jobName, groupName));
        logger.warn("[Job] Execute job " + groupName + "-" + jobName + " immediately and remove the job from the scheduler.");
    }

    /**
     * 停止并移除一个计划中的job
     */
    public static void removeJob(JobKey jobkey) {
        String triggerName = jobkey.getName();
        String triggerGroup = jobkey.getGroup();
        if (hasJob(triggerName, triggerGroup)) {
            try {
                Scheduler scheduler = getDefaultScheduler();
                // 停止触发器
                scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
                // 移除触发器
                scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup));
                // 删除任务
                scheduler.deleteJob(JobKey.jobKey(triggerName, triggerGroup));
                logger.warn("[Job] remove the job " + triggerGroup + "-" + triggerName + " from scheduler ");
            } catch (SchedulerException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 停止并移除计划中一个jobGroup的任务
     */
    public static void removeJobs(String jobGroupName) {
        List<JobKey> jobKeyList = getJobkeybyGroupName(jobGroupName);
        if (jobKeyList.isEmpty()) {
            return;
        }
        jobKeyList.forEach(JobHelper::removeJob);
    }

    /**
     * 移除指定class对应的全部job
     */
    public static void removeJobs(Class<?> jobClass) {
        List<JobKey> jobKeyList = getJobKeylistbyClass(jobClass);
        jobKeyList.forEach(JobHelper::removeJob);
    }

    // 停止Defaultscheduler
    public static void stopScheduler() throws SchedulerException {
        Scheduler scheduler = getDefaultScheduler();
        if (!scheduler.isShutdown()) {
            scheduler.shutdown(true);
        }
    }

    /**
     * 更新已经存在的任务的定时器
     */
    public static void modifyJobTime(String jobName, String jobGroupName, String newstarttime) {
        try {
            Scheduler scheduler = getDefaultScheduler();
            Trigger oldTrigger = scheduler.getTrigger(new TriggerKey(jobName, jobGroupName));
            if (oldTrigger != null) {
                Date oldStartTime = oldTrigger.getStartTime();
                if (oldStartTime == null) {
                    logger.debug("trigger " + jobName + " " + jobGroupName + " has fired!");
                    return;
                }
                // 如果trigger中存储的执行起始时间与新的执行时间相同，则不再进行操作
                // 直接返回
                if (newstarttime.equals(DateTimeUtil.getDateTimeFromDateType(oldStartTime))) {
                    return;
                }
                //更新任务执行的时间
                TriggerBuilder tb = oldTrigger.getTriggerBuilder();
                Trigger newTrigger = tb.startAt(DateTimeUtil.getDateFromString(newstarttime)).build();
                scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
            } else {
                logger.debug("Old trigger:" + jobName + " " + jobGroupName + " is null");
            }
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Meet some error when updating start time for job-" + jobGroupName + "-" + jobName, e);
        }
    }

    /**
     * 更新已经存在的任务的定时器,调整的时间单位是秒
     */
    public static void modifyJobInterval(String jobName, String jobGroupName, int newInterval) {
        try {
            Scheduler scheduler = getDefaultScheduler();
            Trigger oldTrigger = scheduler.getTrigger(new TriggerKey(jobName, jobGroupName));
            String startTime = DateTimeUtil.getCurrentDatetimeString();
            Trigger trigger = TriggerHelper.createTrigger(newInterval, -1, startTime, "", jobName, jobGroupName);
            scheduler.rescheduleJob(oldTrigger.getKey(), trigger);

        } catch (SchedulerException e) {
            logger.error("[JobHelper] Meet some error when modifying job interval for job-" + jobGroupName + "-" + jobName, e);
        }
    }


    /**
     * 判断调度器中是否存在指定名字的任务
     */
    public static boolean hasJob(String jobName, String jobGroupName) {
        Scheduler scheduler;
        try {
            scheduler = getDefaultScheduler();
            Trigger oldTrigger = scheduler.getTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
            if (oldTrigger != null) {
                return true;
            }
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Judge job whether in scheduler got wrong.", e);
        }
        return false;
    }

    /**
     * 通过jobclass暂停指定的job
     */
    public static void pauseJob(Class<?> jobClass) {
        try {
            Scheduler scheduler = getDefaultScheduler();
            List<JobKey> jobkeylist = getJobKeylistbyClass(jobClass);
            for (JobKey jobKey : jobkeylist) {
                scheduler.pauseJob(jobKey);
            }
            logger.debug("[JobHelper] pause job: " + jobClass.getName());
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Pause job got wrong for class " + jobClass.getName(), e);
        }
    }

    /**
     * 通过jobName和jobgroupname暂停指定的job
     */
    public static void pauseJob(String jobName, String jobGroupName) {
        try {
            Scheduler scheduler = getDefaultScheduler();
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
            logger.debug("[JobHelper] pause job: " + jobName);
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Pause job got wrong for job-" + jobGroupName + "-" + jobName, e);
        }
    }

    /**
     * 通过jobclass恢复指定的job
     */
    public static void resumeJob(Class<?> jobClass) {
        try {
            Scheduler scheduler = getDefaultScheduler();
            List<JobKey> jobkeylist = getJobKeylistbyClass(jobClass);
            for (JobKey jobKey : jobkeylist) {
                scheduler.resumeJob(jobKey);
            }
            logger.debug("[JobHelper] Resume job: " + jobClass.getName());
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Resume job got wrong for class " + jobClass.getName(), e);
        }
    }

    /**
     * 通过jobName和jobgroupname恢复指定的job
     */
    public static void resumeJob(String jobName, String jobGroupName) {
        try {
            Scheduler scheduler = getDefaultScheduler();
            scheduler.resumeJob(new JobKey(jobName, jobGroupName));
            logger.debug("[JobHelper] Resume job: " + jobName);
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Resume job got wrong for job-" + jobGroupName + "-" + jobName, e);
        }
    }


    /**
     * 执行scheduler.start()方法，并将jobClass与scheduler存储入map中
     */
    @SuppressWarnings("serial")
    private static void doStartJob(Class<?> jobClass, JobDetail jobDetail, Trigger trigger) {
        try {
            Scheduler scheduler = getDefaultScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown() && !scheduler.isStarted()) {
                scheduler.start();
            }
            logger.debug("[JobHelper] start job: " + jobClass.getName());
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Start job got wrong for class " + jobClass.getName(), e);
        }
    }

    /**
     * 执行scheduler.start()方法，并将jobClass与scheduler存储入map中,并且在调度器中绑定监听器
     */
    @SuppressWarnings("serial")
    private static void doStartJob(Class<?> jobClass, JobListener listener, JobDetail jobDetail, Trigger trigger) {
        try {
            Scheduler scheduler = getDefaultScheduler();
            Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
            scheduler.getListenerManager().addJobListener(listener, matcher);
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown() && !scheduler.isStarted()) {
                scheduler.start();
            }
            logger.debug("[JobHelper] start job: " + jobClass.getName());
        } catch (SchedulerException e) {
            logger.error("[JobHelper] Start job got wrong for class " + jobClass.getName(), e);
        }
    }


    /**
     * 通过类获取调度器对应的jobkeylist
     */
    private static List<JobKey> getJobKeylistbyClass(Class<?> jobClass) {
        List<JobKey> keylist = new ArrayList<>();
        if (JOB_KEY_MAP.containsKey(jobClass)) {
            keylist = JOB_KEY_MAP.get(jobClass);
        }
        return keylist;
    }

    /**
     * 将随机数key存储到jobClass对应的键值中
     */
    private static void addJobKey(Class<?> jobClass, JobKey tag) {
        if (JOB_KEY_MAP.containsKey(jobClass)) {
            JOB_KEY_MAP.get(jobClass).add(tag);
        } else {
            JOB_KEY_MAP.put(jobClass, new ArrayList<JobKey>() {{
                add(tag);
            }});
        }
    }

    /**
     * 使用jobGroupName获取全部的jobKey
     */
    private static List<JobKey> getJobkeybyGroupName(String jobGroupName) {
        List<JobKey> jobKeyList = new ArrayList<>();
        try {
            Scheduler scheduler = getDefaultScheduler();
            jobKeyList.addAll(new ArrayList<>(scheduler.getJobKeys(GroupMatcher.groupEquals(jobGroupName))));
        } catch (SchedulerException e) {
            logger.error("[JobHelper] get all job key by group name meet wrong!", e);
        }
        return jobKeyList;
    }

    /**
     * 列出调度器的全部jobs
     */
    private static List<JobKey> getAllJobkey() {
        List<JobKey> jobKeyList = new ArrayList<>();
        try {
            Scheduler scheduler = getDefaultScheduler();
            for (String group : scheduler.getJobGroupNames()) {
                jobKeyList.addAll(new ArrayList<>(scheduler.getJobKeys(GroupMatcher.groupEquals(group))));
            }
        } catch (SchedulerException e) {
            logger.error("[JobHelper] get all job key meet wrong!", e);
        }
        return jobKeyList;
    }

    /**
     * 列出调度器中全部的trigger
     */
    private static List<TriggerKey> getAllTrigger() {
        List<TriggerKey> triggerlist = new ArrayList<>();
        Scheduler scheduler;
        try {
            scheduler = getDefaultScheduler();
            for (String group : scheduler.getTriggerGroupNames()) {
                triggerlist.addAll(new ArrayList<>(scheduler.getTriggerKeys(GroupMatcher.groupEquals(group))));
            }
        } catch (SchedulerException e) {
            logger.error("[JobHelper] get all trigger meet wrong!", e);
        }
        return triggerlist;
    }

    /**
     * 将实现job接口的类封装成jobDetail，该封装为无参方式封装
     */
    @SuppressWarnings("unchecked")
    private static JobDetail createJobDetail(Class<?> jobClass) {
        String tag = jobClass.getName() + RandomUtil.getUUID();
        String group = jobClass.getName();
        addJobKey(jobClass, JobKey.jobKey(tag, group));
        return JobBuilder.newJob((Class<? extends org.quartz.Job>) jobClass).withIdentity(tag).build();
    }

    /**
     * 将实现job接口的类封装成jobDetail，该封装为传入jobName和jobGroupName
     */
    @SuppressWarnings("unchecked")
    private static JobDetail createJobDetail(Class<?> jobClass, String jobName, String jobGroupName) {
        addJobKey(jobClass, JobKey.jobKey(jobName, jobGroupName));
        return JobBuilder.newJob((Class<? extends org.quartz.Job>) jobClass).withIdentity(jobName, jobGroupName).build();
    }
}
