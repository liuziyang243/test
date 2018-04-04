package com.crscd.passengerservice.plantest;

import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.display.business.MakeGuidePlanOnScreenManager;
import com.crscd.passengerservice.plan.business.PlanGenAndDelManager;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by cuishiqing on 2017/9/14.
 */
public class RefreshGuidePlanTest {
    @Test
    public void testRefreshGuidePlan() {
        int trainNumCount = 5;
        List<String> trainNumList = OtConfigedBasicPlanFactory.getOtConfiguredBasicPlan(trainNumCount);

        String station = "Athi River";
        String startDate = "2017-10-01";
        String endDate = "2017-10-01";

        ApplicationContext ctx = ContextUtil.getInstance();
        PlanGenAndDelManager manager = ctx.getBean("planGenAndDelManager", PlanGenAndDelManager.class);

        MakeGuidePlanOnScreenManager manager1 = ctx.getBean("makeGuidePlanOnScreenManager", MakeGuidePlanOnScreenManager.class);
        manager1.startRefreshGuidePlanOnScreen();
    }
}
