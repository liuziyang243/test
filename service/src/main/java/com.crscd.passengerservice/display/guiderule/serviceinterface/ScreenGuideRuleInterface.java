package com.crscd.passengerservice.display.guiderule.serviceinterface;

import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.display.guiderule.dto.ScreenGuideRuleDTO;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/10
 */
public interface ScreenGuideRuleInterface {
    // 获取导向规则列表
    @WebResult(name = "getScreenGuideRuleListResult")
    List<ScreenGuideRuleDTO> getScreenGuideRuleList(@WebParam(name = "stationName") String stationName, @WebParam(name = "type") ScreenTypeEnum type);

    // 编辑导向规则
    @WebResult(name = "modifyScreenGuideRuleResult")
    ResultMessage modifyScreenGuideRule(@WebParam(name = "dto") ScreenGuideRuleDTO dto);

    // 增加导向规则
    @WebResult(name = "addScreenGuideRuleResult")
    ResultMessage addScreenGuideRule(@WebParam(name = "dto") ScreenGuideRuleDTO dto);

    // 删除导向规则
    @WebResult(name = "delScreenGuideRuleResult")
    ResultMessage delScreenGuideRule(@WebParam(name = "id") long id);
}
