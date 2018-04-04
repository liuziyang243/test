package com.crscd.framework.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Create by: lzy
 * Date: 2017/6/13
 * Time: 9:11
 * 用于生成独立的调度器，拥有独立的job线程池
 */
public class DefaultSchedulerProvider {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSchedulerProvider.class);
    // 调度器工厂
    private StdSchedulerFactory ssf;

    /*
     * 对于简单配置的调度器工厂，之需要指定调度器名称和线程池大小
     */
    public DefaultSchedulerProvider(String schedulerName, String threadPoolCapacity) {
        ssf = new StdSchedulerFactory();
        Properties properties = new Properties();
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.put("org.quartz.scheduler.instanceName", schedulerName);
        properties.put("org.quartz.threadPool.threadCount", threadPoolCapacity);
        try {
            ssf.initialize(properties);
        } catch (SchedulerException e) {
            logger.error("[DefaultSchedulerProvider] init error.", e);
        }
    }

    /*
     * 对于复杂的调度器工厂，需要采用配置文件的方式进行配置
     */
    DefaultSchedulerProvider(String config) {
        ssf = new StdSchedulerFactory();
        try {
            ssf.initialize(config);
        } catch (SchedulerException e) {
            logger.error("[DefaultSchedulerProvider] init error.", e);
        }
    }

    // 获取调度器
    Scheduler getScheduler() {
        try {
            return ssf.getScheduler();
        } catch (SchedulerException e) {
            logger.error("[DefaultSchedulerProvider] Get scheduler error.", e);
            throw new RuntimeException(e);
        }
    }
}
