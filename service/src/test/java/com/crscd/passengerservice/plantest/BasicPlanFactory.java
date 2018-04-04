package com.crscd.passengerservice.plantest;

import com.crscd.framework.util.base.ContextUtil;
import com.crscd.framework.util.number.RandomUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.plan.dto.BasicTrainStationDTO;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.plan.serviceinterface.BasicPlanInterface;
import com.crscd.passengerservice.plan.serviceinterface.implement.BasicPlanInterfaceImpl;
import com.crscd.passengerservice.result.base.ResultMessage;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/23
 * Time: 8:54
 */
public class BasicPlanFactory {
    public static BasicPlanDTO getBasicPlanDTO() {
        BasicPlanDTO dto = new BasicPlanDTO();
        dto.setValidPeriodEnd("--");
        dto.setValidPeriodStart("--");
        dto.setTrainType(TrainTypeEnum.HIGH_SPEED);
        dto.setTrainNum(RandomUtil.randomStringFixLength(4));
        dto.setStartStation("Nairobi");
        dto.setFinalStation("Mombasa");
        return dto;
    }

    public static BasicPlanDTO getBasicPlanDTO(String trainNum) {
        BasicPlanDTO dto = new BasicPlanDTO();
        dto.setValidPeriodEnd("--");
        dto.setValidPeriodStart("--");
        dto.setTrainType(TrainTypeEnum.HIGH_SPEED);
        dto.setTrainNum(trainNum);
        dto.setStartStation("Nairobi");
        dto.setFinalStation("Mombasa");
        return dto;
    }

    public static List<String> initBasicPlansIntoDB(int number) {
        List<String> trainNumList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            String trainNum = RandomUtil.randomStringFixLength(4);
            initBasicPlanIntoDB(trainNum);
            trainNumList.add(trainNum);
        }
        return trainNumList;
    }

    public static void initBasicPlanIntoDB(String trainNum) {
        ApplicationContext ctx = ContextUtil.getInstance();
        BasicPlanInterface bpi = ctx.getBean("basicPlanServiceImpl", BasicPlanInterfaceImpl.class);
        BasicPlanDTO bpdto = getBasicPlanDTO(trainNum);
        ResultMessage resultMessage = bpi.addBasicPlanMainInfo(bpdto);
        Assert.assertTrue(resultMessage.getResult());

        // insert station Athi River
        BasicTrainStationDTO dto = new BasicTrainStationDTO();
        String arriveTime = DateTimeUtil.getCurrentDatetimeString();
        String departureTime = DateTimeUtil.calcTimeWithMinute(arriveTime, 60);
        dto.setPlanedArriveTime(DateTimeFormatterUtil.convertStringDatetimeToStringTime(arriveTime));
        dto.setPlanedDepartureTime(DateTimeFormatterUtil.convertStringDatetimeToStringTime(departureTime));
        dto.setPlanedTrackNum(2);
        dto.setStationName("Athi River");
        dto.setArriveDelayDays(0);
        dto.setDepartureDelayDays(0);

        ResultMessage resultMessage1 = bpi.addBasicPlanStationInfo(trainNum, dto);
        Assert.assertTrue(resultMessage1.getResult());

        // insert station Nairobi
        dto = new BasicTrainStationDTO();
        dto.setPlanedArriveTime("--");
        dto.setPlanedDepartureTime("10:10:10");
        dto.setPlanedTrackNum(1);
        dto.setStationName("Nairobi");
        dto.setArriveDelayDays(0);
        dto.setDepartureDelayDays(0);

        ResultMessage resultMessage2 = bpi.addBasicPlanStationInfo(trainNum, dto);
        Assert.assertTrue(resultMessage2.getResult());

        // insert station Mombasa
        dto = new BasicTrainStationDTO();
        dto.setPlanedArriveTime("19:13:10");
        dto.setPlanedDepartureTime("--");
        dto.setPlanedTrackNum(1);
        dto.setStationName("Mombasa");
        dto.setArriveDelayDays(0);
        dto.setDepartureDelayDays(0);

        ResultMessage resultMessage3 = bpi.addBasicPlanStationInfo(trainNum, dto);
        Assert.assertTrue(resultMessage3.getResult());

        System.out.println("[Test init] Init Train-" + trainNum);
    }
}
