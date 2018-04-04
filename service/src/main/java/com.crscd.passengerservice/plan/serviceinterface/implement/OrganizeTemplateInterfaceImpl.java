package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.business.BasicPlanManager;
import com.crscd.passengerservice.plan.dao.OrganizeTemplateDAO;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.domainobject.OrganizeTemplate;
import com.crscd.passengerservice.plan.dto.OrganizeTemplateDTO;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.plan.serviceinterface.OrganizeTemplateInterface;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Create date: 2017/8/27
 */
public class OrganizeTemplateInterfaceImpl implements OrganizeTemplateInterface {
    private OrganizeTemplateDAO dao;
    private BasicPlanManager manager;
    private ConfigManager cfgManager;

    public OrganizeTemplateInterfaceImpl(BasicPlanManager manager, OrganizeTemplateDAO dao, ConfigManager cfgManager) {
        this.manager = manager;
        this.dao = dao;
        this.cfgManager = cfgManager;
    }

    @Override
    public List<OrganizeTemplateDTO> getOrganizeTemplate(String stationName, String trainNum, TrainTypeEnum trainType) {
        if (null == stationName) {
            return new ArrayList<>();
        }
        List<OrganizeTemplateDTO> dtoList = new ArrayList<>();
        List<BasicPlan> basicPlanList = manager.getBasicPlanList(trainNum, trainType, true);
        for (BasicPlan plan : basicPlanList) {
            if (plan.containSpecifiedTrainStation(stationName)) {
                BasicTrainStationInfo info = plan.getSpecifiedTrainStationInfo(stationName);
                OrganizeTemplate template = new OrganizeTemplate(info.getStationType());
                boolean flag = false;
                if (dao.existOrganizeTemplateInDB(plan.getTrainNum(), stationName)) {
                    template = dao.getOrganizeTemplate(plan.getTrainNum(), stationName);
                    flag = true;
                }
                OrganizeTemplateDTO dto = new OrganizeTemplateDTO(plan, info, template, cfgManager, flag);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    public ResultMessage modifyOrganizeTemplate(OrganizeTemplateDTO dto) {
        if (dao.existOrganizeTemplateInDB(dto.getTrainNum(), dto.getStationName())) {
            OrganizeTemplate template = dao.getOrganizeTemplate(dto.getTrainNum(), dto.getStationName());
            copyDTOtoDO(dto, template);
            if (!dao.updateTemplate(template)) {
                return new ResultMessage(2002);
            }

        } else {
            OrganizeTemplate template = new OrganizeTemplate();
            copyDTOtoDO(dto, template);
            if (!dao.insertTemplate(template)) {
                return new ResultMessage(2001);
            }
        }
        return new ResultMessage();
    }

    @Override
    public List<OrganizeTemplateDTO> getUnconfiguredOrganizeTemplate(String stationName) {
        List<OrganizeTemplateDTO> dtoList = new ArrayList<>();
        List<BasicPlan> basicPlanList = manager.getBasicPlanListByStation(stationName);
        for (BasicPlan plan : basicPlanList) {
            BasicTrainStationInfo info = plan.getSpecifiedTrainStationInfo(stationName);
            if (!dao.existOrganizeTemplateInDB(plan.getTrainNum(), stationName)) {
                OrganizeTemplate template = new OrganizeTemplate(info.getStationType());
                OrganizeTemplateDTO dto = new OrganizeTemplateDTO(plan, info, template, cfgManager, false);
                dtoList.add(dto);
            } else {
                OrganizeTemplate template = dao.getOrganizeTemplate(plan.getTrainNum(), stationName);
                if ("default".equals(template.getBroadcastTemplateGroupName())) {
                    OrganizeTemplateDTO dto = new OrganizeTemplateDTO(plan, info, template, cfgManager, false);
                    dtoList.add(dto);
                }
            }
        }
        return dtoList;
    }

    private void copyDTOtoDO(OrganizeTemplateDTO dto, OrganizeTemplate template) {
        template.setTrainNum(dto.getTrainNum());
        template.setStationName(dto.getStationName());
        template.setStartAboardCheckBase(dto.getStartAboardCheckBase());
        template.setStartAboardCheckTimeOffset(dto.getStartAboardCheckTimeOffset());
        template.setStopAboardCheckBase(dto.getStopAboardCheckBase());
        template.setStopAboardCheckTimeOffset(dto.getStopAboardCheckTimeOffset());
        if (ListUtil.isEmpty(dto.getWaitZoneList())) {
            template.setWaitZoneList(new ArrayList<>());
        } else {
            template.setWaitZoneList(dto.getWaitZoneList());
        }
        if (ListUtil.isEmpty(dto.getStationEntrancePort())) {
            template.setStationEntrancePort(new ArrayList<>());
        } else {
            template.setStationEntrancePort(dto.getStationEntrancePort());
        }
        if (ListUtil.isEmpty(dto.getStationExitPort())) {
            template.setStationExitPort(new ArrayList<>());
        } else {
            template.setStationExitPort(dto.getStationExitPort());
        }
        if (ListUtil.isEmpty(dto.getAboardCheckGate())) {
            template.setAboardCheckGate(new ArrayList<>());
        } else {
            template.setAboardCheckGate(dto.getAboardCheckGate());
        }
        if (ListUtil.isEmpty(dto.getExitCheckGate())) {
            template.setExitCheckGate(new ArrayList<>());
        } else {
            template.setExitCheckGate(dto.getExitCheckGate());
        }
        template.setBroadcastTemplateGroupName(dto.getBroadcastTemplateGroupName());
    }
}
