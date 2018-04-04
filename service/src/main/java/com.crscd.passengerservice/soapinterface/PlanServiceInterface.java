package com.crscd.passengerservice.soapinterface;

import com.crscd.passengerservice.notice.serviceinterface.NoticeServiceInterface;
import com.crscd.passengerservice.notice.serviceinterface.PlanGenNoticeInterface;
import com.crscd.passengerservice.plan.serviceinterface.*;

import javax.jws.WebService;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 11:22
 * 列车时刻表、计划相关的接口放在这个文件下，包括notice信息等
 */
@WebService
public interface PlanServiceInterface extends
        BasicMapInterface,
        BasicPlanInterface,
        GenerateAndDelPlanInterface,
        DispatchPlanInterface,
        PassengerPlanInterface,
        GuidePlanInterface,
        BroadcastPlanInterface,
        OrganizeTemplateInterface,
        NoticeServiceInterface,
        PlanGenNoticeInterface {
}
