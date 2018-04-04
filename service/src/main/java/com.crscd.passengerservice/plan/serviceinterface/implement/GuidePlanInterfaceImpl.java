package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.passengerservice.plan.business.GuideStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;
import com.crscd.passengerservice.plan.dto.GuideStationPlanDTO;
import com.crscd.passengerservice.plan.enumtype.GuidePlanModifyEnum;
import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;
import com.crscd.passengerservice.plan.serviceinterface.GuidePlanInterface;
import com.crscd.passengerservice.plan.util.PlanHelper;
import com.crscd.passengerservice.result.base.ResultMessage;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/25
 * Time: 16:38
 */
public class GuidePlanInterfaceImpl implements GuidePlanInterface {
    private GuideStationPlanManager manager;

    public void setManager(GuideStationPlanManager manager) {
        this.manager = manager;
    }

    @Override
    public List<GuideStationPlanDTO> getPeriodGuidePlan(String stationName, String trainNum, String startDate, String endDate) {
        List<GuideStationPlanDTO> dtoList = new ArrayList<>();
        List<GuideStationPlan> planList = manager.getPlanList(stationName, trainNum, startDate, endDate, true);
        for (GuideStationPlan plan : planList) {
            GuideStationPlanDTO dto = getDTOFromPO(plan);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public ResultMessage updateGuidePlan(String planKey, HashMap<GuidePlanModifyEnum, String> modifyList, LateEarlyReasonEnum arriveTimeModifyReason, LateEarlyReasonEnum departureTimeModifyReason) {
        if (!manager.updatePlan(planKey, modifyList, arriveTimeModifyReason, departureTimeModifyReason)) {
            return new ResultMessage(2002);
        }
        return new ResultMessage();
    }

    // 将DO的公共信息拷贝到DTO中
    private GuideStationPlanDTO getDTOFromPO(GuideStationPlan plan) {
        MapperFactory mapperFactory = PlanHelper.getMapperFactory();
        mapperFactory.classMap(GuideStationPlan.class, GuideStationPlanDTO.class)
                .fieldMap("startStation", "startStation").converter("stationConverter").add()
                .fieldMap("finalStation", "finalStation").converter("stationConverter").add()
                .fieldMap("actualArriveTime", "actualArriveTime").converter("dateTimeConverter").add()
                .fieldMap("actualDepartureTime", "actualDepartureTime").converter("dateTimeConverter").add()
                .fieldMap("planedArriveTime", "planedArriveTime").converter("dateTimeConverter").add()
                .fieldMap("planedDepartureTime", "planedDepartureTime").converter("dateTimeConverter").add()
                .fieldMap("planDate", "planDate").converter("dateConverter").add()
                .fieldMap("startAboardCheckTime", "startAboardCheckTime").converter("dateTimeConverter").add()
                .fieldMap("stopAboardCheckTime", "stopAboardCheckTime").converter("dateTimeConverter").add()
                .fieldMap("validPeriodStart", "validPeriodStart").converter("dateConverter").add()
                .fieldMap("validPeriodEnd", "validPeriodEnd").converter("dateConverter").add()
                .byDefault().register();

        MapperFacade mapper = mapperFactory.getMapperFacade();
        GuideStationPlanDTO dto = mapper.map(plan, GuideStationPlanDTO.class);
        if (null == dto.getPlanedArriveTime()) {
            dto.setPlanedArriveTime("--");
        }
        if (null == dto.getActualArriveTime()) {
            dto.setActualArriveTime("--");
        }
        if (null == dto.getPlanedDepartureTime()) {
            dto.setPlanedDepartureTime("--");
        }
        if (null == dto.getActualDepartureTime()) {
            dto.setActualDepartureTime("--");
        }
        if (null == dto.getValidPeriodStart()) {
            dto.setValidPeriodStart("--");
        }
        if (null == dto.getValidPeriodEnd()) {
            dto.setValidPeriodEnd("--");
        }
        return dto;
    }
}
