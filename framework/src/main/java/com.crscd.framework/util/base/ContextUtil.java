package com.crscd.framework.util.base;

import com.crscd.framework.core.ConfigHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liuziyang
 * Create date: 2017/7/13
 */
public class ContextUtil {

    public static ApplicationContext getInstance() {
        return SingletonHolder.ctx;
    }

    private static class SingletonHolder {

        private static String context = ConfigHelper.getString("travelService.contextInUse");
        private static ApplicationContext ctx = new ClassPathXmlApplicationContext(context);
    }

}
