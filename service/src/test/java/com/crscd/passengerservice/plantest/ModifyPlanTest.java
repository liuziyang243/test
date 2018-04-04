package com.crscd.passengerservice.plantest;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.plan.business.PlanGenAndDelManager;
import com.crscd.passengerservice.plan.domainobject.OrganizeTemplate;
import com.crscd.passengerservice.plan.dto.GuideStationPlanDTO;
import com.crscd.passengerservice.plan.enumtype.GuidePlanModifyEnum;
import com.crscd.passengerservice.plan.po.*;
import com.crscd.passengerservice.plan.serviceinterface.implement.GuidePlanInterfaceImpl;
import com.crscd.passengerservice.result.base.ResultMessage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.00
 * Date: 2017/10/25
 * Time: 14:11
 * @auther lzy
 */
public class ModifyPlanTest {
    private List<String> trainNumList;
    private String station = "Athi River";
    private String startDate = "2017-11-01";
    private String endDate = "2017-11-01";

    @Before
    public void createTestDate() {
        int trainNumCount = 1;
        String broadcastTemplateGroup = "pass";
        trainNumList = OtConfigedBasicPlanFactory.getOtConfiguredBasicPlan(trainNumCount, broadcastTemplateGroup);

        ApplicationContext ctx = ContextUtil.getInstance();
        PlanGenAndDelManager manager = ctx.getBean("planGenAndDelManager", PlanGenAndDelManager.class);
        HashMap<String, String> result = manager.generatePlanList(trainNumList, station, startDate, endDate);
        for (Map.Entry<String, String> entry : result.entrySet()) {
            trainNumList.add(entry.getKey());
        }
    }

    @After
    public void clearTestData() {
        String condition = "trainNum=?";
        DataSet dataSet = ContextHelper.getTestDataSet();
        for (String var : trainNumList) {
            dataSet.delete(BasicPlanBean.class, condition, var);
            dataSet.delete(BasicTrainStationInfoBean.class, condition, var);
            dataSet.delete(OrganizeTemplate.class, condition, var);
            dataSet.delete(DispatchStationPlanBean.class, condition, var);
            dataSet.delete(PassengerStationPlanBean.class, condition, var);
            dataSet.delete(GuideStationPlanBean.class, condition, var);
            dataSet.delete(BroadcastStationPlanBean.class, condition, var);
        }
    }

    @Test
    public void modifySinglePropertyTest() {
        ApplicationContext ctx = ContextUtil.getInstance();
        GuidePlanInterfaceImpl guidePlanInterface = ctx.getBean("guidePlanServiceImpl", GuidePlanInterfaceImpl.class);
        for (String trainNum : trainNumList) {
            List<GuideStationPlanDTO> planList = guidePlanInterface.getPeriodGuidePlan(station, trainNum, startDate, endDate);
            for (GuideStationPlanDTO dto : planList) {
                HashMap<GuidePlanModifyEnum, String> modify = new HashMap<>();
                modify.put(GuidePlanModifyEnum.START_CHECK_TIME, "2017-11-01 12:12:00");
                ResultMessage message = guidePlanInterface.updateGuidePlan(dto.getPlanKey(), modify, null, null);
                Assert.assertEquals(200, message.getStatusCode());
            }
        }
    }
}
