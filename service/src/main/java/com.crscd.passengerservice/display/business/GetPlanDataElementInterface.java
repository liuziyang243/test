package com.crscd.passengerservice.display.business;

import com.crscd.passengerservice.display.format.domainobject.PlanDataElement;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 17:38
 */
public interface GetPlanDataElementInterface {
    List<PlanDataElement> getTargetScreenDisplayDat(int screenID);
}
