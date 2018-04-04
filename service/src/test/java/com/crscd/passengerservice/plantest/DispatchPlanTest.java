package com.crscd.passengerservice.plantest;

import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.plan.pool.DispatchPlanDataPool;
import org.apache.shiro.util.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

/**
 * @author lzy
 * Date: 2017/8/25
 * Time: 11:37
 */
public class DispatchPlanTest {
    @Test
    public void dataPoolTest() {
        ApplicationContext ctx = ContextUtil.getInstance();
        DispatchPlanDataPool pool = ctx.getBean("dispatchPlanDataPool", DispatchPlanDataPool.class);
        Assert.notNull(pool);
    }
}
