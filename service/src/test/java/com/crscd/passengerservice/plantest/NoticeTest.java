package com.crscd.passengerservice.plantest;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.base.ContextUtil;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.notice.business.NoticeManager;
import com.crscd.passengerservice.notice.dto.GenerateNoticeInfoDTO;
import com.crscd.passengerservice.notice.dto.NoticeMessageDTO;
import com.crscd.passengerservice.notice.dto.OperationLogDTO;
import com.crscd.passengerservice.notice.dto.SingleNoticeMessageDTO;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.enumtype.ProcessStateEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.notice.serviceinterface.NoticeServiceInterface;
import com.crscd.passengerservice.plan.business.GuideStationPlanManager;
import com.crscd.passengerservice.plan.business.PlanGenAndDelManager;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.result.base.ResultMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/4
 * Time: 10:45
 */
public class NoticeTest {
    @Before
    public void clearTable() {
        DataSet dataSet = ContextHelper.getTestDataSet();
        dataSet.getDbhelper().update("TRUNCATE TABLE BASICPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE BASICTrainStationInfo");
        dataSet.getDbhelper().update("TRUNCATE TABLE DISPATCHSTATIONPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE PassengerSTATIONPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE GuideSTATIONPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE BroadcastSTATIONPLAN");
        dataSet.getDbhelper().update("TRUNCATE TABLE NoticeMessage");
    }

    @Test
    public void genNoticeAcceptTest() {
        int trainNumCount = 1;
        List<String> trainNumList = OtConfigedBasicPlanFactory.getOtConfiguredBasicPlan(trainNumCount);

        String station = "Athi River";
        String startDate = "2017-10-01";
        String endDate = "2017-10-01";
        String trainNum = trainNumList.get(0);

        ApplicationContext ctx = ContextUtil.getInstance();
        PlanGenAndDelManager manager = ctx.getBean("planGenAndDelManager", PlanGenAndDelManager.class);
        NoticeServiceInterface noticeServiceInterface = ctx.getBean("noticeServiceImpl", NoticeServiceInterface.class);
        NoticeManager noticeManager = ctx.getBean("noticeManager", NoticeManager.class);

        HashMap<String, String> result = manager.generatePlanList(trainNumList, station, startDate, endDate);

        Assert.assertEquals(result.size(), trainNumCount);

        GenerateNoticeInfoDTO dto = new GenerateNoticeInfoDTO();
        dto.setGenerateUser("test");
        dto.setStationName(station);
        dto.setTrainNum(trainNum);
        dto.setPlanDate(startDate);
        dto.setReceiver(ReceiverEnum.GUIDE_PLAN);
        dto.setSender(SenderEnum.DISPATCH_PLAN);
        HashMap<NoticeModifyEnum, String> modifyEnumStringMap = new HashMap<>();

        String newArriveTime = "2017-10-01 15:00:00";
        modifyEnumStringMap.put(NoticeModifyEnum.ACTUAL_ARRIVE_TIME, newArriveTime);
        String newDepartureTime = "2017-10-01 15:10:00";
        modifyEnumStringMap.put(NoticeModifyEnum.ACTUAL_DEPARTURE_TIME, newDepartureTime);
        int newTrackNum = 10;
        modifyEnumStringMap.put(NoticeModifyEnum.ACTUAL_TRACK_NUM, CastUtil.castString(newTrackNum));
        boolean trainSuspendFlag = true;
        modifyEnumStringMap.put(NoticeModifyEnum.MANUAL_SUSPEND, CastUtil.castString(trainSuspendFlag));
        String startCheckTime = "2017-10-01 14:50:00";
        String stopCheckTime = "2017-10-01 15:05:00";
        modifyEnumStringMap.put(NoticeModifyEnum.START_CHECK_TIME, startCheckTime);
        modifyEnumStringMap.put(NoticeModifyEnum.STOP_CHECK_TIME, stopCheckTime);

        List<String> waitZone = new ArrayList<>();
        waitZone.add("waitZone1");
        waitZone.add("waitZone2");
        modifyEnumStringMap.put(NoticeModifyEnum.WAIT_ZONE, JsonUtil.toJSON(waitZone));

        List<String> entrancePort = new ArrayList<>();
        entrancePort.add("ep1");
        entrancePort.add("ep2");
        modifyEnumStringMap.put(NoticeModifyEnum.ENTRANCE_PORT, JsonUtil.toJSON(entrancePort));

        List<String> exitPort = new ArrayList<>();
        exitPort.add("exit1");
        exitPort.add("exit2");
        modifyEnumStringMap.put(NoticeModifyEnum.EXIT_PORT, JsonUtil.toJSON(exitPort));

        List<String> checkPort = new ArrayList<>();
        checkPort.add("cp1");
        checkPort.add("cp2");
        modifyEnumStringMap.put(NoticeModifyEnum.ABOARD_CHECK_PORT, JsonUtil.toJSON(checkPort));

        List<String> exitCheckPort = new ArrayList<>();
        exitCheckPort.add("ecp1");
        exitCheckPort.add("ecp2");
        modifyEnumStringMap.put(NoticeModifyEnum.EXIT_CHECK_PORT, JsonUtil.toJSON(exitCheckPort));

        dto.setModifiedDataMap(modifyEnumStringMap);

        boolean message = noticeManager.generateMessage(dto);
        Assert.assertTrue(message);

        List<NoticeMessageDTO> noticeMessageDTOList = noticeServiceInterface.getUnhandledNoticeList();
        Assert.assertEquals(1, noticeMessageDTOList.size());
        Assert.assertFalse(noticeMessageDTOList.get(0).getNoticeList().get(0).isOverTimeFlag());

        ArrayList<Long> idList = new ArrayList<>();
        for (SingleNoticeMessageDTO singleNotice : noticeMessageDTOList.get(0).getNoticeList()) {
            idList.add(singleNotice.getId());
        }
        ResultMessage message2 = noticeServiceInterface.acceptNotice("system", idList, ReceiverEnum.GUIDE_PLAN);
        Assert.assertTrue(message2.getResult());

        GuideStationPlanManager guideStationPlanManager = ctx.getBean("guidePlanManager", GuideStationPlanManager.class);
        String planKey = new KeyBase(trainNum, startDate, station).toString();
        GuideStationPlan plan = guideStationPlanManager.getPlan(planKey);

        Assert.assertEquals(newArriveTime, DateTimeFormatterUtil.convertDatetimeToString(plan.getActualArriveTime()));
        Assert.assertEquals(newDepartureTime, DateTimeFormatterUtil.convertDatetimeToString(plan.getActualDepartureTime()));
        Assert.assertEquals(newTrackNum, plan.getActualTrackNum());
        Assert.assertTrue(plan.getSuspendFlag());
        Assert.assertEquals(startCheckTime, DateTimeFormatterUtil.convertDatetimeToString(plan.getStartAboardCheckTime()));
        Assert.assertEquals(stopCheckTime, DateTimeFormatterUtil.convertDatetimeToString(plan.getStopAboardCheckTime()));
        Assert.assertEquals(waitZone, plan.getWaitZone());
        Assert.assertEquals(entrancePort, plan.getStationEntrancePort());
        Assert.assertEquals(exitPort, plan.getStationExitPort());
        Assert.assertEquals(checkPort, plan.getAboardCheckGate());
        Assert.assertEquals(exitCheckPort, plan.getExitCheckGate());
    }

    @Test
    public void genNoticeRejectTest() {
        int trainNumCount = 1;
        List<String> trainNumList = OtConfigedBasicPlanFactory.getOtConfiguredBasicPlan(trainNumCount);

        String station = "Athi River";
        String startDate = "2017-10-01";
        String endDate = "2017-10-01";
        String trainNum = trainNumList.get(0);

        ApplicationContext ctx = ContextUtil.getInstance();
        PlanGenAndDelManager manager = ctx.getBean("planGenAndDelManager", PlanGenAndDelManager.class);
        NoticeServiceInterface noticeServiceInterface = ctx.getBean("noticeServiceImpl", NoticeServiceInterface.class);
        NoticeManager noticeManager = ctx.getBean("noticeManager", NoticeManager.class);
        HashMap<String, String> result = manager.generatePlanList(trainNumList, station, startDate, endDate);

        Assert.assertEquals(result.size(), trainNumCount);

        GenerateNoticeInfoDTO dto = new GenerateNoticeInfoDTO();
        dto.setGenerateUser("test");
        dto.setStationName(station);
        dto.setTrainNum(trainNum);
        dto.setPlanDate(startDate);
        dto.setReceiver(ReceiverEnum.GUIDE_PLAN);
        dto.setSender(SenderEnum.DISPATCH_PLAN);
        HashMap<NoticeModifyEnum, String> modifyEnumStringMap = new HashMap<>();

        String newArriveTime = "2017-10-01 15:00:00";
        modifyEnumStringMap.put(NoticeModifyEnum.ACTUAL_ARRIVE_TIME, newArriveTime);
        dto.setModifiedDataMap(modifyEnumStringMap);

        boolean message = noticeManager.generateMessage(dto);
        Assert.assertTrue(message);

        List<NoticeMessageDTO> noticeMessageDTOList = noticeServiceInterface.getUnhandledNoticeList();
        Assert.assertEquals(1, noticeMessageDTOList.size());
        Assert.assertFalse(noticeMessageDTOList.get(0).getNoticeList().get(0).isOverTimeFlag());

        ArrayList<Long> idList = new ArrayList<>();
        for (SingleNoticeMessageDTO singleNotice : noticeMessageDTOList.get(0).getNoticeList()) {
            idList.add(singleNotice.getId());
        }
        ResultMessage message2 = noticeServiceInterface.rejectNotice("system", idList);
        Assert.assertTrue(message2.getResult());

        List<OperationLogDTO> logDTOList = noticeServiceInterface.getOperationLogByStation(station, null, ReceiverEnum.GUIDE_PLAN, startDate, endDate);
        Assert.assertEquals(1, logDTOList.size());
        Assert.assertEquals(ProcessStateEnum.REJECT, logDTOList.get(0).getProcessState());

        List<NoticeMessageDTO> noticeMessageDTOList2 = noticeServiceInterface.getNoticeMessageByStation(station, ReceiverEnum.GUIDE_PLAN, startDate, endDate);
        Assert.assertEquals(0, noticeMessageDTOList2.size());

        List<NoticeMessageDTO> noticeMessageDTOList3 = noticeServiceInterface.getUnhandledNoticeList();
        Assert.assertEquals(0, noticeMessageDTOList3.size());
    }
}
