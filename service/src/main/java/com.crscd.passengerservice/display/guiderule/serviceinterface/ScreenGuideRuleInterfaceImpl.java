package com.crscd.passengerservice.display.guiderule.serviceinterface;

import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.display.guiderule.dao.ScreenGuideRuleDAO;
import com.crscd.passengerservice.display.guiderule.domainobject.ScreenGuideRule;
import com.crscd.passengerservice.display.guiderule.dto.ScreenGuideRuleDTO;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/10
 */
public class ScreenGuideRuleInterfaceImpl implements ScreenGuideRuleInterface {
    private ScreenGuideRuleDAO screenGuideRuleDAO;

    public void setScreenGuideRuleDAO(ScreenGuideRuleDAO screenGuideRuleDAO) {
        this.screenGuideRuleDAO = screenGuideRuleDAO;
    }

    @Override
    public List<ScreenGuideRuleDTO> getScreenGuideRuleList(String stationName, ScreenTypeEnum type) {
        List<ScreenGuideRule> guideRules = screenGuideRuleDAO.getScreenGuideRuleByTypeAndStation(stationName, type);
        List<ScreenGuideRuleDTO> dtos = new ArrayList<>();
        for (ScreenGuideRule rule : guideRules) {
            ScreenGuideRuleDTO dto = getDTOFromDO(rule);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public ResultMessage modifyScreenGuideRule(ScreenGuideRuleDTO dto) {
        if (screenGuideRuleDAO.updateScreenGuideRule(getDOFromDTO(dto))) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2002);
        }
    }

    @Override
    public ResultMessage addScreenGuideRule(ScreenGuideRuleDTO dto) {
        if (screenGuideRuleDAO.insertScreenGuideRule(getDOFromDTO(dto))) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2001);
        }
    }

    @Override
    public ResultMessage delScreenGuideRule(long id) {
        if (screenGuideRuleDAO.delScreenGuideRule(id)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2003);
        }
    }

    private ScreenGuideRuleDTO getDTOFromDO(ScreenGuideRule rule) {
        return MapperUtil.map(rule, ScreenGuideRuleDTO.class);
    }

    private ScreenGuideRule getDOFromDTO(ScreenGuideRuleDTO dto) {
        return MapperUtil.map(dto, ScreenGuideRule.class);
    }
}
