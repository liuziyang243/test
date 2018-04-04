package com.crscd.passengerservice.plantest;

import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.dao.BasicPlanDAO;
import com.crscd.passengerservice.plan.dao.DispatchPlanDAO;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.DispatchStationPlan;
import com.crscd.passengerservice.plan.po.DispatchStationPlanBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/11
 * Time: 10:09
 */
public class PlanMapperTest {
    @Test
    public void testDispatchPlanMap() {
        ApplicationContext context = ContextUtil.getInstance();
        DispatchPlanDAO dao = context.getBean("dispatchPlanDAO", DispatchPlanDAO.class);
        BasicPlanDAO basicPlanDAO = context.getBean("basicPlanDAO", BasicPlanDAO.class);
        ConfigManager manager = context.getBean("configManager", ConfigManager.class);
        int trainNumCount = 1;
        List<String> trainNumList = OtConfigedBasicPlanFactory.getOtConfiguredBasicPlan(trainNumCount);
        String station = "Athi River";
        String planDate = "2017-10-01";
        BasicPlan basicPlan = basicPlanDAO.getBasicPlanByTrainNum(trainNumList.get(0));
        DispatchStationPlan dispatchStationPlan = new DispatchStationPlan(basicPlan, planDate, station, manager);

        DispatchStationPlanBean bean = dao.getDOFromPO(dispatchStationPlan);
        Assert.assertNotNull(bean);
    }
}
