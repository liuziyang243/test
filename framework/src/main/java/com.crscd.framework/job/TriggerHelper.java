package com.crscd.framework.job;

import com.crscd.framework.util.number.RandomUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import org.quartz.*;

/**
 * Create by: lzy
 * Date: 2017/6/13
 * Time: 16:02
 */
public class TriggerHelper {
    /*
     * 封装cronTrigger触发器构造器,自行成成随机数作为tag
     */
    static CronTrigger createTrigger(Class<?> jobClass, String cron) {
        String tag = jobClass.getName() + RandomUtil.getUUID();
        return TriggerBuilder.newTrigger().withIdentity(tag)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
    }

    /*
     * 封装cronTrigger触发器构造器,带有triggerName和triggerGroupName
     */
    @SuppressWarnings("unused")
    static CronTrigger createTrigger(String triggerName, String triggerGroupName, String cron) {
        return TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
    }

    /*
     * 封装simpleTrigger触发器构造器，触发方式为每隔数秒进行重复，重复次数为无限次
     */
    @SuppressWarnings("unused")
    static SimpleTrigger createTrigger(Class<?> jobClass, int second) {
        String tag = jobClass.getName() + RandomUtil.getUUID();
        return TriggerBuilder.newTrigger().withIdentity(tag)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(second)).build();
    }

    /*
     * 封装simpleTrigger触发器构造器，触发方式为每隔数秒进行重复，重复次数为指定次数
     */
    @SuppressWarnings("unused")
    static SimpleTrigger createTrigger(Class<?> jobClass, int second, int count) {
        return createTrigger(jobClass, second, count, null, null);
    }

    /*
     * 封装simpleTrigger触发器构造器，触发方式为从指定的起始时间开始，每隔数秒进行重复，重复次数为指定次数，到指定的结束时间停止
     */
    static SimpleTrigger createTrigger(Class<?> jobClass, int second, int count, String start, String end) {
        TriggerBuilder<SimpleTrigger> triggerBuilder;
        String tag = jobClass.getName() + RandomUtil.getUUID();
        if (count > 0) { //重复次数大于0表示需要重复有限次
            triggerBuilder = TriggerBuilder.newTrigger().withIdentity(tag)
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(count, second));
        } else if (count == 0) { //重复次数等于0表示不需要重复，之需要执行一次
            triggerBuilder = TriggerBuilder.newTrigger().withIdentity(tag)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule());
        } else { //重复次数小于0表示需要执行无数次，一般为-1
            triggerBuilder = TriggerBuilder.newTrigger().withIdentity(tag)
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(second));
        }
        return doCreateTrigger(triggerBuilder, start, end);
    }

    /*
     * 封装simpleTrigger触发器构造器，触发方式为从指定的起始时间开始，每隔数秒进行重复，重复次数为指定次数，到指定的结束时间停止
     * 并且支持输入TriggerName和TriggerGroupName
     */
    static SimpleTrigger createTrigger(int second, int count, String start, String end, String triggerName, String triggerGroupName) {
        TriggerBuilder<SimpleTrigger> triggerBuilder;
        if (count > 0) { //重复次数大于0表示需要重复有限次
            triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(count, second));
        } else if (count == 0) { //重复次数等于0表示不需要重复，只需要执行一次
            triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule());
        } else { //重复次数小于0表示需要执行无数次，一般为-1
            triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(second));
        }
        return doCreateTrigger(triggerBuilder, start, end);
    }

    /*
     * 在simpleTrigger的基础上增加起始时间和结束时间
     */
    private static SimpleTrigger doCreateTrigger(TriggerBuilder<SimpleTrigger> triggerBuilder, String start,
                                                 String end) {
        if (StringUtil.isNotEmpty(start)) {
            triggerBuilder.startAt(DateTimeUtil.getDateFromString(start));
        } else {
            triggerBuilder.startNow();   //如果执行起始时间为空则默认采用立即执行
        }
        if (StringUtil.isNotEmpty(end)) {
            triggerBuilder.endAt(DateTimeUtil.getDateFromString(end));
        }
        return triggerBuilder.build();
    }
}
