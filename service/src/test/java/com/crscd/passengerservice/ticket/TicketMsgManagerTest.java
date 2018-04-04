package com.crscd.passengerservice.ticket;

import com.crscd.framework.util.base.ContextUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.plan.dto.BasicTrainStationDTO;
import com.crscd.passengerservice.plan.serviceinterface.BasicPlanInterface;
import com.crscd.passengerservice.plan.serviceinterface.implement.BasicPlanInterfaceImpl;
import com.crscd.passengerservice.plantest.BasicPlanFactory;
import com.crscd.passengerservice.ticket.bussiness.StructTransformer;
import com.crscd.passengerservice.ticket.domainobject.TrainTicketInfo;
import com.crscd.passengerservice.ticket.po.TicketScreenDataBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

/**
 * Created by cuishiqing on 2017/9/26.
 */
public class TicketMsgManagerTest {

    @Test
    public void trainTicketInfoToTicketScreenDataBeanTest() {
        planInit();
        TrainTicketInfo t = new TrainTicketInfo();
        t.setTrainNum("T122");
        t.setStationCode("32");
        t.setDate("2017-9-26");
        t.setSeatName("FirstClass");
        t.setTicketNum(24);
        t.setUpdateTime(DateTimeUtil.getCurrentDatetimeString());

        StructTransformer structTransformer = ContextHelper.getStructTransformer();
        TicketScreenDataBean ticketScreenDataBean = structTransformer.trainTicketInfoToTicketScreenDataBean(t);
    }

    private void planInit() {
        String trainNum = "T122";
        ApplicationContext ctx = ContextUtil.getInstance();
        BasicPlanInterface bpi = ctx.getBean("basicPlanServiceImpl", BasicPlanInterfaceImpl.class);

        BasicPlanDTO bpdto = BasicPlanFactory.getBasicPlanDTO(trainNum);
        bpi.addBasicPlanMainInfo(bpdto);

        BasicTrainStationDTO dto = new BasicTrainStationDTO();
        dto.setPlanedArriveTime("15:21:10");
        dto.setPlanedDepartureTime("15:22:10");
        dto.setPlanedTrackNum(2);
        dto.setStationName("Athi River");
        dto.setArriveDelayDays(0);
        dto.setDepartureDelayDays(0);

        // insert
        bpi.addBasicPlanStationInfo(trainNum, dto);
    }

}
