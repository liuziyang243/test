package com.crscd.passengerservice.plantest;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.plan.business.BasicPlanManager;
import com.crscd.passengerservice.plan.dao.BasicPlanDAO;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.plan.dto.BasicTrainStationDTO;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.plan.serviceinterface.BasicPlanInterface;
import com.crscd.passengerservice.plan.serviceinterface.implement.BasicPlanInterfaceImpl;
import com.crscd.passengerservice.result.base.ResultMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/22
 * Time: 10:44
 */
public class BasicPlanTest {

    @Before
    public void clearTable() {
        DataSet dataSet = ContextHelper.getTestDataSet();
        dataSet.getDbhelper().update("TRUNCATE TABLE BASICPLAN");
    }

    @Test
    public void AddModifyDelBasicPlanMainInfoTest() {
        ApplicationContext ctx = ContextUtil.getInstance();
        BasicPlanDAO dao = ctx.getBean("basicPlanDAO", BasicPlanDAO.class);
        BasicPlanInterface bpi = ctx.getBean("basicPlanServiceImpl", BasicPlanInterfaceImpl.class);

        BasicPlanDTO dto = BasicPlanFactory.getBasicPlanDTO();
        ResultMessage resultMessage = bpi.addBasicPlanMainInfo(dto);
        Assert.assertTrue(resultMessage.getResult());

        dto.setStartStation("Voi");

        ResultMessage resultMessage1 = bpi.updateBasicPlanMainInfo(dto);
        BasicPlan basicPlan = dao.getBasicPlanByTrainNum(dto.getTrainNum());
        Assert.assertTrue(resultMessage1.getResult());

        List<BasicPlanDTO> basicPlanList = bpi.getBasicPlanMainInfoList("Voi", null, null);
        Assert.assertEquals(basicPlanList.size(), 1);

        Assert.assertNotNull(basicPlan);
        Assert.assertEquals(basicPlan.getStartStation().getStationName(), dto.getStartStation());

        ResultMessage resultMessage2 = bpi.removeBasicPlanMainInfo(dto.getTrainNum());
        Assert.assertTrue(resultMessage2.getResult());
    }

    @Test
    public void AddModifyDelBasicPlanStationInfoTest() {
        String trainNum = "D1001";
        ApplicationContext ctx = ContextUtil.getInstance();
        BasicPlanManager manager = ctx.getBean("basicPlanManager", BasicPlanManager.class);
        BasicPlanInterface bpi = ctx.getBean("basicPlanServiceImpl", BasicPlanInterfaceImpl.class);

        BasicPlanDTO bpdto = BasicPlanFactory.getBasicPlanDTO(trainNum);
        ResultMessage resultMessage = bpi.addBasicPlanMainInfo(bpdto);
        Assert.assertTrue(resultMessage.getResult());

        BasicTrainStationDTO dto = new BasicTrainStationDTO();
        dto.setPlanedArriveTime("15:21:10");
        dto.setPlanedDepartureTime("15:22:10");
        dto.setPlanedTrackNum(2);
        dto.setStationName("Athi River");
        dto.setArriveDelayDays(0);
        dto.setDepartureDelayDays(0);

        // insert
        ResultMessage resultMessage1 = bpi.addBasicPlanStationInfo(trainNum, dto);
        Assert.assertTrue(resultMessage1.getResult());

        List<BasicTrainStationDTO> dtoList = bpi.getBasicStationInfoList(trainNum);
        Assert.assertEquals(dtoList.size(), 1);

        // update
        dto.setPlanedTrackNum(1);
        ResultMessage resultMessage2 = bpi.updateBasicPlanStationInfo(trainNum, dto);
        Assert.assertTrue(resultMessage2.getResult());

        dtoList = bpi.getBasicStationInfoList(trainNum);
        Assert.assertEquals(dtoList.get(0).getPlanedTrackNum(), 1);

        // update train type
        bpdto.setTrainType(TrainTypeEnum.NORMAL);
        ResultMessage resultMessage6 = bpi.updateBasicPlanMainInfo(bpdto);
        Assert.assertTrue(resultMessage6.getResult());
        BasicPlan basicPlan = manager.getBasicPlanByTrainNum(trainNum);
        Assert.assertEquals(TrainTypeEnum.NORMAL, basicPlan.getTrainType());

        // update start station in main info
        bpdto.setStartStation("Emali");
        ResultMessage resultMessage3 = bpi.updateBasicPlanMainInfo(bpdto);
        Assert.assertTrue(resultMessage3.getResult());

        List<BasicTrainStationInfo> infoList = manager.getBasicTrainStationInfoList(trainNum);
        Assert.assertEquals(infoList.get(0).getStartStation(), "Emali");

        // deletion
//        ResultMessage resultMessage4 = bpi.removeBasicPlanStationInfo(trainNum, "Athi River");
//        Assert.assertTrue(resultMessage4.getResult());
        ResultMessage resultMessage5 = bpi.removeBasicPlanMainInfo(trainNum);
        Assert.assertTrue(resultMessage5.getResult());
        List<BasicTrainStationInfo> infoList2 = manager.getBasicTrainStationInfoList(trainNum);
        Assert.assertTrue(infoList2.isEmpty());
    }
}
