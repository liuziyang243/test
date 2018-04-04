package com.crscd.passengerservice.plantest;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.plan.dao.OrganizeTemplateDAO;
import com.crscd.passengerservice.plan.domainobject.OrganizeTemplate;
import com.crscd.passengerservice.plan.dto.OrganizeTemplateDTO;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.plan.serviceinterface.implement.OrganizeTemplateInterfaceImpl;
import com.crscd.passengerservice.result.base.ResultMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/31
 */
public class OrgnizeTemplateTest {

    @Before
    public void clearTable() {
        DataSet dataSet = ContextHelper.getTestDataSet();
        dataSet.getDbhelper().update("DELETE FROM BASICPLAN");
        dataSet.getDbhelper().update("DELETE FROM BASICTrainStationInfo");
        dataSet.getDbhelper().update("TRUNCATE TABLE organizetemplate");
        System.out.println("Clear all test data in db.");
    }

    @Test
    public void otTest() {
        ApplicationContext ctx = ContextUtil.getInstance();
        OrganizeTemplateDAO dao = ctx.getBean("organizeTemplateDAO", OrganizeTemplateDAO.class);
        int trainNumCount = 4;
        List<String> trainNumList;
        trainNumList = BasicPlanFactory.initBasicPlansIntoDB(trainNumCount);
        String trainNum = trainNumList.get(0);
        String station = "Athi River";
        Assert.assertFalse(dao.existOrganizeTemplateInDB(trainNum, station));
        Assert.assertFalse(dao.existOrganizeTemplateInDB("D11", station));
        Assert.assertFalse(dao.existOrganizeTemplateInDB(trainNum, "beijing"));
        clearTable();
    }

    @Test
    public void otTestUnhandle() {
        ApplicationContext ctx = ContextUtil.getInstance();
        OrganizeTemplateInterfaceImpl impl = ctx.getBean("organizeTemplateInterfaceImpl", OrganizeTemplateInterfaceImpl.class);
        int trainNumCount = 4;
        List<String> trainNumList;
        trainNumList = BasicPlanFactory.initBasicPlansIntoDB(trainNumCount);
        String station = "Athi River";
        List<OrganizeTemplateDTO> dtoList = impl.getUnconfiguredOrganizeTemplate(station);
        Assert.assertEquals(trainNumCount, dtoList.size());
    }

    @Test
    public void otTestUnhandle2() {
        ApplicationContext ctx = ContextUtil.getInstance();
        OrganizeTemplateInterfaceImpl impl = ctx.getBean("organizeTemplateInterfaceImpl", OrganizeTemplateInterfaceImpl.class);
        int trainNumCount = 4;
        List<String> trainNumList;
        trainNumList = BasicPlanFactory.initBasicPlansIntoDB(trainNumCount);
        String station = "Athi River";
        List<OrganizeTemplateDTO> dtoList = impl.getOrganizeTemplate(station, null, TrainTypeEnum.ALL);
        Assert.assertEquals(trainNumCount, dtoList.size());
        Assert.assertNotNull(dtoList.get(0).getBroadcastTemplateGroupName());
    }

    @Test
    public void otTestModfiy() {
        ApplicationContext ctx = ContextUtil.getInstance();
        OrganizeTemplateDAO dao = ctx.getBean("organizeTemplateDAO", OrganizeTemplateDAO.class);
        OrganizeTemplateInterfaceImpl impl = ctx.getBean("organizeTemplateInterfaceImpl", OrganizeTemplateInterfaceImpl.class);
        int trainNumCount = 4;
        List<String> trainNumList;
        trainNumList = BasicPlanFactory.initBasicPlansIntoDB(trainNumCount);
        String station = "Athi River";
        List<OrganizeTemplateDTO> dtoList = impl.getOrganizeTemplate(station, null, TrainTypeEnum.ALL);

        OrganizeTemplateDTO dto = dtoList.get(0);
        String trainNum = dto.getTrainNum();

        List<String> areaList = new ArrayList<>();
        areaList.add("test1");
        areaList.add("test2");
        areaList.add("test3");
        dto.setAboardCheckGate(areaList);
        dto.setExitCheckGate(areaList);
        dto.setWaitZoneList(areaList);
        dto.setStationEntrancePort(areaList);
        dto.setStationExitPort(areaList);

        dto.setStartAboardCheckBase(TrainTimeBaseEnum.START_CHECK);
        dto.setStopAboardCheckBase(TrainTimeBaseEnum.STOP_CHECK);
        dto.setStartAboardCheckTimeOffset(-20);
        dto.setStopAboardCheckTimeOffset(0);

        dto.setBroadcastTemplateGroupName("test");

        ResultMessage result = impl.modifyOrganizeTemplate(dto);
        Assert.assertTrue(result.getResult());
        Assert.assertTrue(dao.existOrganizeTemplateInDB(trainNum, station));

        List<OrganizeTemplateDTO> dtoList1 = impl.getOrganizeTemplate(station, trainNum, TrainTypeEnum.ALL);

        Assert.assertNotNull(dtoList1);

        OrganizeTemplateDTO dto1 = dtoList1.get(0);

        Assert.assertEquals(areaList, dto1.getAboardCheckGate());
        Assert.assertEquals(areaList, dto1.getWaitZoneList());
        Assert.assertEquals(areaList, dto1.getExitCheckGate());
        Assert.assertEquals(areaList, dto1.getStationEntrancePort());
        Assert.assertEquals(areaList, dto1.getStationExitPort());

        Assert.assertEquals(TrainTimeBaseEnum.START_CHECK, dto1.getStartAboardCheckBase());
        Assert.assertEquals(TrainTimeBaseEnum.STOP_CHECK, dto1.getStopAboardCheckBase());
        Assert.assertEquals(-20, dto1.getStartAboardCheckTimeOffset());
        Assert.assertEquals(0, dto1.getStopAboardCheckTimeOffset());

        Assert.assertEquals("test", dto1.getBroadcastTemplateGroupName());
    }

    @Test
    public void otTestUnhandle3() {
        ApplicationContext ctx = ContextUtil.getInstance();
        OrganizeTemplateDAO dao = ctx.getBean("organizeTemplateDAO", OrganizeTemplateDAO.class);
        OrganizeTemplateInterfaceImpl impl = ctx.getBean("organizeTemplateInterfaceImpl", OrganizeTemplateInterfaceImpl.class);
        int trainNumCount = 4;
        List<String> trainNumList;
        trainNumList = BasicPlanFactory.initBasicPlansIntoDB(trainNumCount);
        String station = "Athi River";
        List<OrganizeTemplateDTO> dtoList = impl.getUnconfiguredOrganizeTemplate(station);
        String trainNum = dtoList.get(0).getTrainNum();
        impl.modifyOrganizeTemplate(dtoList.get(0));
        dtoList = impl.getUnconfiguredOrganizeTemplate(station);
        Assert.assertEquals(trainNumCount - 1, dtoList.size());
        OrganizeTemplate ot = dao.getOrganizeTemplate(trainNum, station);
        Assert.assertEquals(ot.getStationEntrancePort(), new ArrayList<>());
    }
}
