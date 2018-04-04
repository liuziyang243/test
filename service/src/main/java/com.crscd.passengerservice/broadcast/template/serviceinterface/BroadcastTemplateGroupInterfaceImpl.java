package com.crscd.passengerservice.broadcast.template.serviceinterface;

import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.broadcast.template.business.BroadcastTemplateManager;
import com.crscd.passengerservice.broadcast.template.dao.BroadcastTemplateDao;
import com.crscd.passengerservice.broadcast.template.dao.BroadcastTemplateGroupDAO;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplate;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplateGroup;
import com.crscd.passengerservice.broadcast.template.dto.BroadcastTemplateDTO;
import com.crscd.passengerservice.broadcast.template.dto.BroadcastTemplateGroupDTO;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/2
 */
public class BroadcastTemplateGroupInterfaceImpl implements BroadcastTemplateGroupInterface {
    private BroadcastTemplateManager manager;
    private BroadcastTemplateDao templateDao;
    private BroadcastTemplateGroupDAO groupDAO;

    public void setManager(BroadcastTemplateManager manager) {
        this.manager = manager;
    }

    public void setTemplateDao(BroadcastTemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    public void setGroupDAO(BroadcastTemplateGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public List<String> getBroadcastGroupNameList(String stationName) {
        List<BroadcastTemplateGroup> groupList = manager.getBroadcastTemplateGroupByStation(stationName, BroadcastKindEnum.ARRIVE_DEPARTURE);
        List<String> groupNameList = new ArrayList<>();
        for (BroadcastTemplateGroup group : groupList) {
            groupNameList.add(group.getTemplateGroupName());
        }
        return groupNameList;
    }

    @Override
    public List<BroadcastTemplateGroupDTO> getBroadcastGroupList(String stationName, BroadcastKindEnum kind) {
        List<BroadcastTemplateGroup> groupList = manager.getBroadcastTemplateGroupByStation(stationName, kind);
        List<BroadcastTemplateGroupDTO> dtoList = new ArrayList<>();
        for (BroadcastTemplateGroup group : groupList) {
            BroadcastTemplateGroupDTO dto = getGroupDTOFromDO(group);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<BroadcastTemplateDTO> getBroadcastTemplateList(String stationName, String groupName) {
        BroadcastTemplateGroup templateGroup = manager.getBroadcastTemplateGroupByName(stationName, groupName);
        List<BroadcastTemplateDTO> templateDTOList = new ArrayList<>();
        for (BroadcastTemplate template : templateGroup.getBroadcastTemplateList()) {
            BroadcastTemplateDTO dto = new BroadcastTemplateDTO(template);
            templateDTOList.add(dto);
        }
        return templateDTOList;
    }

    @Override
    public ResultMessage addBroadcastGroup(BroadcastTemplateGroupDTO group) {
        if (groupDAO.insertGroup(group)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2001);
        }
    }

    @Override
    public ResultMessage addBroadcastTemplate(BroadcastTemplateDTO template) {
        if (templateDao.insertTemplate(template)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2001);
        }
    }

    @Override
    public ResultMessage modifyGroupName(String stationName, String oldGroupName, String newGroupName) {
        templateDao.updateTemplateGroupName(stationName, oldGroupName, newGroupName);
        if (groupDAO.updateGroup(stationName, oldGroupName, newGroupName)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2002);
        }
    }

    @Override
    public ResultMessage modifyBroadcastTemplate(BroadcastTemplateDTO template) {
        if (templateDao.updateTemplate(template)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2002);
        }
    }

    @Override
    public ResultMessage delBroadcastGroup(long groupID) {
        if (groupDAO.deleteGroup(groupID)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2003);
        }
    }

    @Override
    public ResultMessage delBroadcastTemplate(long templateID) {
        if (templateDao.deleteTemplate(templateID)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2003);
        }
    }

    private BroadcastTemplateGroupDTO getGroupDTOFromDO(BroadcastTemplateGroup group) {
        return MapperUtil.map(group, BroadcastTemplateGroupDTO.class);
    }

}
