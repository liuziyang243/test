package com.crscd.passengerservice.plantest;

import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.plan.dto.OrganizeTemplateDTO;
import com.crscd.passengerservice.plan.serviceinterface.implement.OrganizeTemplateInterfaceImpl;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/31
 */
public class OtConfigedBasicPlanFactory {

    static List<String> getOtConfiguredBasicPlan(int number) {
        return getOtConfiguredBasicPlan(number, "default");
    }

    static List<String> getOtConfiguredBasicPlan(int number, String broadcastTemplateGroup) {
        List<String> trainNumList = BasicPlanFactory.initBasicPlansIntoDB(number);
        ApplicationContext ctx = ContextUtil.getInstance();
        OrganizeTemplateInterfaceImpl impl = ctx.getBean("organizeTemplateInterfaceImpl", OrganizeTemplateInterfaceImpl.class);
        String station = "Athi River";
        List<OrganizeTemplateDTO> dtoList = impl.getUnconfiguredOrganizeTemplate(station);
        for (OrganizeTemplateDTO dto : dtoList) {
            dto.setBroadcastTemplateGroupName(broadcastTemplateGroup);
            impl.modifyOrganizeTemplate(dto);
        }
        return trainNumList;
    }
}
