package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.passengerservice.plan.business.PassengerStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.PassengerStationPlan;
import com.crscd.passengerservice.plan.dto.PassengerStationPlanDTO;
import com.crscd.passengerservice.plan.enumtype.PassengerPlanModifyEnum;
import com.crscd.passengerservice.plan.serviceinterface.PassengerPlanInterface;
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
 * Time: 16:36
 */
public class PassengerPlanInterfaceImpl implements PassengerPlanInterface {
    private PassengerStationPlanManager manager;

    public void setManager(PassengerStationPlanManager manager) {
        this.manager = manager;
    }

    @Override
    public List<PassengerStationPlanDTO> getPeriodPassengerPlan(String stationName, String trainNum, String startDate, String endDate) {
        List<PassengerStationPlanDTO> dtoList = new ArrayList<>();
        List<PassengerStationPlan> planList = manager.getPlanList(stationName, trainNum, startDate, endDate, true);
        for (PassengerStationPlan plan : planList) {
            PassengerStationPlanDTO dto = getDTOFromPO(plan);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public ResultMessage updatePassengerPlan(String planKey, HashMap<PassengerPlanModifyEnum, String> modifyList) {
        if (!manager.updatePlan(planKey, modifyList)) {
            return new ResultMessage(2002);
        }
        return new ResultMessage();
    }


    // 将DO的公共信息拷贝到DTO中
    private PassengerStationPlanDTO getDTOFromPO(PassengerStationPlan plan) {
        MapperFactory mapperFactory = PlanHelper.getMapperFactory();
        mapperFactory.classMap(PassengerStationPlan.class, PassengerStationPlanDTO.class)
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
        PassengerStationPlanDTO dto = mapper.map(plan, PassengerStationPlanDTO.class);
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
