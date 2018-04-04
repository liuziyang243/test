package com.crscd.passengerservice.plantest;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.plan.business.BroadcastStationPlanManager;
import com.crscd.passengerservice.plan.business.PlanGenAndDelManager;
import com.crscd.passengerservice.plan.business.deletion.PlanDeleter;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.plan.pool.BroadcastPlanDataPool;
import com.crscd.passengerservice.plan.serviceinterface.implement.BroadcastPlanInterfaceImpl;
import com.crscd.passengerservice.plan.serviceinterface.implement.DispatchPlanInterfaceImpl;
import com.crscd.passengerservice.plan.serviceinterface.implement.GuidePlanInterfaceImpl;
import com.crscd.passengerservice.plan.serviceinterface.implement.PassengerPlanInterfaceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Created by liuziyang
 * Create date: 2017/8/31
 */
public class GenPlanTest {
    public void clearTable() {
        DataSet dataSet = ContextHelper.getTestDataSet();
        dataSet.getDbhelper().update("TRUNCATE TABLE BASICPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE BASICTrainStationInfo");
        dataSet.getDbhelper().update("TRUNCATE TABLE organizetemplate");
        dataSet.getDbhelper().update("TRUNCATE TABLE DISPATCHSTATIONPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE PassengerSTATIONPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE GuideSTATIONPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE BroadcastSTATIONPLAN");
    }

    @Test
    public void broadcastPlanGenerateTest() {
        int trainNumCount = 5;
        List<String> trainNumList = OtConfigedBasicPlanFactory.getOtConfiguredBasicPlan(trainNumCount);

        String station = "Athi River";
        String startDate = "2017-10-01";
        String endDate = "2017-10-01";

        ApplicationContext ctx = ContextUtil.getInstance();
        PlanGenAndDelManager manager = ctx.getBean("planGenAndDelManager", PlanGenAndDelManager.class);
        DispatchPlanInterfaceImpl dispatchPlanInterface = ctx.getBean("dispatchPlanServiceImpl", DispatchPlanInterfaceImpl.class);
        PassengerPlanInterfaceImpl passengerPlanInterface = ctx.getBean("passengerPlanServiceImpl", PassengerPlanInterfaceImpl.class);
        GuidePlanInterfaceImpl guidePlanInterface = ctx.getBean("guidePlanServiceImpl", GuidePlanInterfaceImpl.class);
        BroadcastPlanInterfaceImpl broadcastPlanInterface = ctx.getBean("broadcastPlanServiceImpl", BroadcastPlanInterfaceImpl.class);

        HashMap<String, String> result = manager.generatePlanList(trainNumList, station, startDate, endDate);

        Assert.assertEquals(result.size(), trainNumCount);

        String trainNum = trainNumList.get(0);
        String key = new KeyBase(trainNum, startDate, station).toString();
        Assert.assertTrue(result.get(key).contains("Can't find broadcast template group"));

        Assert.assertEquals(dispatchPlanInterface.getPeriodDispatchPlan(station, null, startDate, endDate).size(), trainNumCount);
        Assert.assertEquals(passengerPlanInterface.getPeriodPassengerPlan(station, null, startDate, endDate).size(), trainNumCount);
        Assert.assertEquals(guidePlanInterface.getPeriodGuidePlan(station, null, startDate, endDate).size(), trainNumCount);
        Assert.assertEquals(broadcastPlanInterface.getPeriodBroadcastPlan(station, null, BroadcastKindEnum.ARRIVE_DEPARTURE, startDate, endDate).size(), trainNumCount);

        HashMap<String, String> delResult = manager.delPlanList(trainNumList, station, startDate, endDate);

        Assert.assertTrue(delResult.get(key).contains("Broadcast plan missing in the data base"));
    }

    @Test
    public void planGenerateTest() {
        int trainNumCount = 5;
        String broadcastTemplateGroup = "pass";
        List<String> trainNumList = OtConfigedBasicPlanFactory.getOtConfiguredBasicPlan(trainNumCount, broadcastTemplateGroup);

        String station = "Athi River";
        String startDate = "2017-10-01";
        String endDate = "2017-10-01";

        ApplicationContext ctx = ContextUtil.getInstance();
        PlanGenAndDelManager manager = ctx.getBean("planGenAndDelManager", PlanGenAndDelManager.class);
        DispatchPlanInterfaceImpl dispatchPlanInterface = ctx.getBean("dispatchPlanServiceImpl", DispatchPlanInterfaceImpl.class);
        PassengerPlanInterfaceImpl passengerPlanInterface = ctx.getBean("passengerPlanServiceImpl", PassengerPlanInterfaceImpl.class);
        GuidePlanInterfaceImpl guidePlanInterface = ctx.getBean("guidePlanServiceImpl", GuidePlanInterfaceImpl.class);
        BroadcastPlanInterfaceImpl broadcastPlanInterface = ctx.getBean("broadcastPlanServiceImpl", BroadcastPlanInterfaceImpl.class);

        HashMap<String, String> result = manager.generatePlanList(trainNumList, station, startDate, endDate);

        Assert.assertEquals(result.size(), trainNumCount);

        String trainNum = trainNumList.get(0);
        String key = new KeyBase(trainNum, startDate, station).toString();
        Assert.assertEquals(result.get(key), "Generate success");

        Assert.assertEquals(dispatchPlanInterface.getPeriodDispatchPlan(station, null, startDate, endDate).size(), trainNumCount);
        Assert.assertEquals(passengerPlanInterface.getPeriodPassengerPlan(station, null, startDate, endDate).size(), trainNumCount);
        Assert.assertEquals(guidePlanInterface.getPeriodGuidePlan(station, null, startDate, endDate).size(), trainNumCount);
        Assert.assertEquals(broadcastPlanInterface.getPeriodBroadcastPlan(station, null, BroadcastKindEnum.ARRIVE_DEPARTURE, startDate, endDate).size(), trainNumCount);

        HashMap<String, String> delResult = manager.delPlanList(trainNumList, station, startDate, endDate);

        Assert.assertTrue(delResult.get(key).contains("Delete successful"));
    }

    @Test
    public void planDelTest() {
        String station = "Athi River";
        String startDate = "2017-10-01";
        String endDate = "2017-10-01";
        List<String> trainNumList = new ArrayList<>();
        trainNumList.add("D001");
        trainNumList.add("D002");
        String key = new KeyBase("D001", "2017-12-10", "Athi River").toString();

        ApplicationContext ctx = ContextUtil.getInstance();
        PlanDeleter deleter = ctx.getBean("planDeleter", PlanDeleter.class);
        BroadcastPlanDataPool planDataPool = ctx.getBean("broadcastPlanDataPool", BroadcastPlanDataPool.class);

        planDataPool.delPlanFromMem(key);

        BroadcastStationPlanManager manager = ctx.getBean("broadcastPlanManager", BroadcastStationPlanManager.class);
        manager.getPlanDataPool().delPlanFromMem(key);

        HashMap<String, String> delResult = deleter.delPlanList(trainNumList, station, startDate, endDate);

        for (Map.Entry<String, String> entry : delResult.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    @Test
    public void guidePlanGenerateTest() {
        int trainNumCount = 5;
        List<String> trainNumList = OtConfigedBasicPlanFactory.getOtConfiguredBasicPlan(trainNumCount);

        String station = "Athi River";
        String startDate = "2017-11-01";
        String endDate = "2017-11-02";

        ApplicationContext ctx = ContextUtil.getInstance();
        PlanGenAndDelManager manager = ctx.getBean("planGenAndDelManager", PlanGenAndDelManager.class);
        GuidePlanInterfaceImpl guidePlanInterface = ctx.getBean("guidePlanServiceImpl", GuidePlanInterfaceImpl.class);

        HashMap<String, String> result = manager.generatePlanList(trainNumList, station, startDate, endDate);
//        Assert.assertEquals(result.size(), trainNumCount);
//        Assert.assertEquals(guidePlanInterface.getPeriodGuidePlan(station, null, startDate, endDate).size(), trainNumCount);
    }

}
