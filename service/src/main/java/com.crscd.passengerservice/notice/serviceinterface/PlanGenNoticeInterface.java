package com.crscd.passengerservice.notice.serviceinterface;

import com.crscd.passengerservice.notice.dto.NoticeMessageDTO;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.plan.enumtype.DispatchPlanModifyEnum;
import com.crscd.passengerservice.plan.enumtype.PassengerPlanModifyEnum;
import com.crscd.passengerservice.result.PlanGenNoticeResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/11
 * Time: 14:10
 */
public interface PlanGenNoticeInterface {

    /**
     * 将导向页面生成的修改作为notice发送到其他页面
     *
     * @param user
     * @param planKey
     * @param modifyList
     * @param receiverList
     * @return
     */
    @WebResult(name = "generateDispatchPlanModifyNoticeResult")
    PlanGenNoticeResultMessage generateDispatchPlanModifyNotice(@WebParam(name = "user") String user, @WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") HashMap<DispatchPlanModifyEnum, String> modifyList, @WebParam(name = "receiverList") List<ReceiverEnum> receiverList);

    /**
     * 将客运页面生成的修改作为notice发送到其他页面
     *
     * @param user
     * @param planKey
     * @param modifyList
     * @param receiverList
     * @return
     */
    @WebResult(name = "generatePassengerPlanModifyNoticeResult")
    PlanGenNoticeResultMessage generatePassengerPlanModifyNotice(@WebParam(name = "user") String user, @WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") HashMap<PassengerPlanModifyEnum, String> modifyList, @WebParam(name = "receiverList") List<ReceiverEnum> receiverList);

    /**
     * 将客运计划页面的notice转发给导向计划和广播计划
     *
     * @param user
     * @param dto
     * @param receiverList
     * @return
     */
    @WebResult(name = "forwardPassengerPlanNoticeResult")
    PlanGenNoticeResultMessage forwardPassengerPlanNotice(@WebParam(name = "user") String user, NoticeMessageDTO dto, @WebParam(name = "receiverList") List<ReceiverEnum> receiverList);
}
