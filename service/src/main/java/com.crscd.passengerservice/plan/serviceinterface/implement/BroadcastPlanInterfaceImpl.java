package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.passengerservice.broadcast.content.business.BroadcastContentReplaceInterface;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastModeEnum;
import com.crscd.passengerservice.plan.business.BroadcastStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.dto.BroadcastStationPlanDTO;
import com.crscd.passengerservice.plan.serviceinterface.BroadcastPlanInterface;
import com.crscd.passengerservice.plan.util.PlanHelper;
import com.crscd.passengerservice.result.GroupResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/25
 * Time: 16:39
 */
public class BroadcastPlanInterfaceImpl implements BroadcastPlanInterface {
    private BroadcastStationPlanManager manager;
    private BroadcastContentReplaceInterface contentReplaceInterface;

    public void setContentReplaceInterface(BroadcastContentReplaceInterface contentReplaceInterface) {
        this.contentReplaceInterface = contentReplaceInterface;
    }

    public void setManager(BroadcastStationPlanManager manager) {
        this.manager = manager;
    }

    @Override
    public List<BroadcastStationPlanDTO> getPeriodBroadcastPlan(String stationName, String trainNum, BroadcastKindEnum broadcastKind, String startDate, String endDate) {
        List<BroadcastStationPlanDTO> dtoList = new ArrayList<>();
        List<BroadcastStationPlan> planList = manager.getPlanList(stationName, trainNum, broadcastKind, startDate, endDate, true);
        for (BroadcastStationPlan plan : planList) {
            BroadcastStationPlanDTO dto = getDTOFromPO(plan);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public ResultMessage modifyBroadcastPlanExecuteTime(String planKey, String executeTime) {
        if (!manager.modifyPlanExecuteTime(planKey, executeTime)) {
            return new ResultMessage(2002);
        }
        return new ResultMessage();
    }

    @Override
    public ResultMessage modifyBroadcastPlanExecuteArea(String planKey, List<String> executeArea) {
        if (!manager.modifyPlanExecuteArea(planKey, executeArea)) {
            return new ResultMessage(2002);
        }
        return new ResultMessage();
    }

    @Override
    public GroupResultMessage changePeriodBroadcastPlanModeToManualMode(String stationName, String startDate, String endDate) {
        HashMap<String, Boolean> result = new HashMap<>(manager.modifyPeriodPlanOfStationExecuteMode(stationName, BroadcastModeEnum.MANUAL, startDate, endDate));
        return new GroupResultMessage(result);
    }

    @Override
    public GroupResultMessage changePeriodBroadcastPlanModeToAutoMode(String stationName, String startDate, String endDate) {
        HashMap<String, Boolean> result = new HashMap<>(manager.modifyPeriodPlanOfStationExecuteMode(stationName, BroadcastModeEnum.AUTO, startDate, endDate));
        return new GroupResultMessage(result);
    }

    @Override
    public ResultMessage changeSingleBroadcastPlanModeToManualMode(String planKey) {
        if (!manager.modifyPlanExecuteMode(planKey, BroadcastModeEnum.MANUAL)) {
            return new ResultMessage(2002);
        }
        return new ResultMessage();
    }

    @Override
    public ResultMessage changeSingleBroadcastPlanModeToAutoMode(String planKey) {
        if (!manager.modifyPlanExecuteMode(planKey, BroadcastModeEnum.AUTO)) {
            return new ResultMessage(2002);
        }
        return new ResultMessage();
    }

    // 将DO的公共信息拷贝到DTO中
    private BroadcastStationPlanDTO getDTOFromPO(BroadcastStationPlan plan) {
        MapperFactory mapperFactory = PlanHelper.getMapperFactory();
        mapperFactory.classMap(BroadcastStationPlan.class, BroadcastStationPlanDTO.class)
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
                .fieldMap("broadcastTime", "broadcastTime").converter("dateTimeConverter").add()
                .fieldMap("validPeriodEnd", "validPeriodEnd").converter("dateConverter").add()
                .byDefault().register();

        MapperFacade mapper = mapperFactory.getMapperFacade();
        BroadcastStationPlanDTO dto = mapper.map(plan, BroadcastStationPlanDTO.class);
        dto.setBroadcastContentInEng(contentReplaceInterface.getEngBroadcastContent(plan));
        dto.setBroadcastContentInLocalLan(contentReplaceInterface.getLocalBroadcastContent(plan));
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
