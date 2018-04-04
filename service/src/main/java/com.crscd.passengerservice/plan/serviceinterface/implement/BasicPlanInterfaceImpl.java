package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.business.BasicPlanManager;
import com.crscd.passengerservice.plan.dao.OrganizeTemplateDAO;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.plan.dto.BasicTrainStationDTO;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.plan.serviceinterface.BasicPlanInterface;
import com.crscd.passengerservice.plan.util.PlanHelper;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 15:48
 */
public class BasicPlanInterfaceImpl implements BasicPlanInterface {
    private BasicPlanManager manager;
    private ConfigManager cfgManager;
    private OrganizeTemplateDAO dao;

    public BasicPlanInterfaceImpl(BasicPlanManager manager, ConfigManager cfgManager) {
        this.manager = manager;
        this.cfgManager = cfgManager;
    }

    public void setDao(OrganizeTemplateDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<BasicPlanDTO> getBasicPlanMainInfoList(String stationName, String trainNum, TrainTypeEnum trainType) {
        List<BasicPlan> basicPlanList = manager.getBasicPlanList(trainNum, trainType, true);
        List<BasicPlanDTO> basicPlanDTOList = new ArrayList<>();
        for (BasicPlan plan : basicPlanList) {
            BasicPlanDTO dto = new BasicPlanDTO(plan);
            BasicTrainStationInfo info = plan.getSpecifiedTrainStationInfo(stationName);
            PlanHelper.setBasicPlanDTOTime(info, dto);
            basicPlanDTOList.add(dto);
        }
        return basicPlanDTOList;
    }

    @Override
    public List<BasicTrainStationDTO> getBasicStationInfoList(String trainNum) {
        List<BasicTrainStationDTO> dtoList = new ArrayList<>();
        List<BasicTrainStationInfo> infoList = manager.getBasicTrainStationInfoList(trainNum);
        if (ListUtil.isEmpty(infoList)) {
            return dtoList;
        } else {
            for (BasicTrainStationInfo info : infoList) {
                BasicTrainStationDTO dto = new BasicTrainStationDTO(info);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    public ResultMessage addBasicPlanMainInfo(BasicPlanDTO dto) {
        BasicPlan plan = new BasicPlan(dto, cfgManager);
        if (!manager.insertBasicTrainInfo(plan)) {
            return new ResultMessage(2001);
        }
        return new ResultMessage();
    }

    @Override
    public ResultMessage updateBasicPlanMainInfo(BasicPlanDTO dto) {
        BasicPlan plan = new BasicPlan(dto, cfgManager);
        // 如果无法查到已有的基本计划，则直接向数据库插入新的时刻表计划
        if (null == manager.getBasicPlanByTrainNum(dto.getTrainNum())) {
            if (!manager.insertBasicTrainInfo(plan)) {
                return new ResultMessage(2001);
            }
        }
        // 如果能够查到已有的时刻表计划，则可以更新计划
        else {
            if (!manager.updateBasicTrainInfo(plan)) {
                return new ResultMessage(2002);
            }
        }
        return new ResultMessage();
    }

    @Override
    public ResultMessage removeBasicPlanMainInfo(String trainNum) {
        if (!manager.delBasicPlanFromDB(trainNum)) {
            return new ResultMessage(2003);
        }
        return new ResultMessage();
    }

    @Override
    public ResultMessage addBasicPlanStationInfo(String trainNum, BasicTrainStationDTO dto) {
        BasicTrainStationInfo info = new BasicTrainStationInfo(dto, cfgManager);
        if (!manager.insertBasicStation(trainNum, info)) {
            return new ResultMessage(2001);
        }
        return new ResultMessage();
    }

    @Override
    public ResultMessage updateBasicPlanStationInfo(String trainNum, BasicTrainStationDTO dto) {
        BasicTrainStationInfo info = new BasicTrainStationInfo(dto, cfgManager);
        // 如果查找车站是空的，说明需要插入新的车站信息
        if (null == manager.getTrainStationInfo(trainNum, dto.getStationName())) {
            if (!manager.insertBasicStation(trainNum, info)) {
                return new ResultMessage(2001);
            }
        }
        // 如果查找车站非空，则要更新信息
        else {
            if (!manager.updateBasicStationInfo(trainNum, info)) {
                return new ResultMessage(2002);
            }
        }
        return new ResultMessage();
    }

    @Override
    public ResultMessage removeBasicPlanStationInfo(String trainNum, String stationName) {
        if (!manager.delBasicTrainStationFromMemAndDB(trainNum, stationName)) {
            return new ResultMessage(2003);
        }
        // 删除车站的同时要将客运组织业务模版一起删除
        // 否则会出现业务模版和列车时刻表信息不匹配
        dao.deleteTemplate(trainNum, stationName);
        return new ResultMessage();
    }

    @Override
    public float calculateMileage(String startStation, String presentStation) {
        return PlanHelper.getMileageFromStartStation(startStation, presentStation);
    }
}
