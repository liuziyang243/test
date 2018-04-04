package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.passengerservice.plan.business.DispatchStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.DispatchStationPlan;
import com.crscd.passengerservice.plan.dto.DispatchPlanDTO;
import com.crscd.passengerservice.plan.enumtype.DispatchPlanModifyEnum;
import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;
import com.crscd.passengerservice.plan.serviceinterface.DispatchPlanInterface;
import com.crscd.passengerservice.plan.util.PlanHelper;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.result.page.PagedDispatchPlans;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/24
 * Time: 14:11
 */
public class DispatchPlanInterfaceImpl implements DispatchPlanInterface {

    private DispatchStationPlanManager manager;

    public void setManager(DispatchStationPlanManager manager) {
        this.manager = manager;
    }

    @Override
    public List<DispatchPlanDTO> getPeriodDispatchPlan(String stationName, String trainNum, String startDate, String endDate) {
        List<DispatchPlanDTO> dtoList = new ArrayList<>();
        // 如果站名为空，则直接返回空值，界面不允许站名空着
        if (null == stationName) {
            return dtoList;
        }
        List<DispatchStationPlan> planList = manager.getPlanList(stationName, trainNum, startDate, endDate, true);
        for (DispatchStationPlan plan : planList) {
            DispatchPlanDTO dto = getDTOFromDO(plan);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public PagedDispatchPlans getPagedDispatchPlan(String stationName, int pageNum, int pageSize) {
        if (null == stationName) {
            return new PagedDispatchPlans();
        } else {
            List<DispatchStationPlan> planList = manager.getPlanList(stationName, pageNum, pageSize);
            List<DispatchPlanDTO> dtoList = new ArrayList<>();
            for (DispatchStationPlan plan : planList) {
                DispatchPlanDTO dto = getDTOFromDO(plan);
                dtoList.add(dto);
            }
            long rows = manager.getPlanCount();
            return new PagedDispatchPlans(pageNum, pageSize, rows, dtoList);
        }
    }

    @Override
    public ResultMessage updateDispatchPlan(String planKey, HashMap<DispatchPlanModifyEnum, String> modifyList, LateEarlyReasonEnum arriveTimeModifyReason, LateEarlyReasonEnum departureTimeModifyReason) {
        if (!manager.updatePlan(planKey, modifyList, arriveTimeModifyReason, departureTimeModifyReason)) {
            return new ResultMessage(2002);
        }
        return new ResultMessage();
    }

    // 从plan向DTO拷贝属性
    private DispatchPlanDTO getDTOFromDO(DispatchStationPlan plan) {
        MapperFactory mapperFactory = PlanHelper.getMapperFactory();
        mapperFactory.classMap(DispatchStationPlan.class, DispatchPlanDTO.class)
                .fieldMap("startStation", "startStation").converter("stationConverter").add()
                .fieldMap("finalStation", "finalStation").converter("stationConverter").add()
                .fieldMap("actualArriveTime", "actualArriveTime").converter("dateTimeConverter").add()
                .fieldMap("actualDepartureTime", "actualDepartureTime").converter("dateTimeConverter").add()
                .fieldMap("planedArriveTime", "planedArriveTime").converter("dateTimeConverter").add()
                .fieldMap("planedDepartureTime", "planedDepartureTime").converter("dateTimeConverter").add()
                .fieldMap("planDate", "planDate").converter("dateConverter").add()
                .fieldMap("validPeriodStart", "validPeriodStart").converter("dateConverter").add()
                .fieldMap("validPeriodEnd", "validPeriodEnd").converter("dateConverter").add()
                .byDefault().register();

        MapperFacade mapper = mapperFactory.getMapperFacade();
        DispatchPlanDTO dto = mapper.map(plan, DispatchPlanDTO.class);
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
