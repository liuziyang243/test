package com.crscd.passengerservice.notice.serviceinterface.implement;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.passengerservice.notice.business.NoticeManager;
import com.crscd.passengerservice.notice.dto.GenerateNoticeInfoDTO;
import com.crscd.passengerservice.notice.dto.NoticeMessageDTO;
import com.crscd.passengerservice.notice.dto.SingleNoticeMessageDTO;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.notice.serviceinterface.PlanGenNoticeInterface;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.plan.enumtype.DispatchPlanModifyEnum;
import com.crscd.passengerservice.plan.enumtype.PassengerPlanModifyEnum;
import com.crscd.passengerservice.result.PlanGenNoticeResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * @author lzy
 * Date: 2017/9/11
 * Time: 14:13
 */
public class PlanGenNoticeInterfaceImpl implements PlanGenNoticeInterface {
    private NoticeManager manager;

    public void setManager(NoticeManager manager) {
        this.manager = manager;
    }

    @Override
    public PlanGenNoticeResultMessage generateDispatchPlanModifyNotice(String user, String planKey, HashMap<DispatchPlanModifyEnum, String> modifyList, List<ReceiverEnum> receiverList) {
        Map<ReceiverEnum, List<ResultMessage>> result = new HashMap<>();
        GenerateNoticeInfoDTO dto = new GenerateNoticeInfoDTO();
        dto.setSender(SenderEnum.DISPATCH_PLAN);
        dto.setGenerateUser(user);
        KeyBase keyBase = new KeyBase(planKey);
        dto.setPlanDate(keyBase.getPlanDateString());
        dto.setTrainNum(keyBase.getTrainNum());
        dto.setStationName(keyBase.getStationName());
        dto.setModifiedDataMap(convertDispatchModifyMap(modifyList));
        for (ReceiverEnum receiver : receiverList) {
            dto.setReceiver(receiver);
            List<ResultMessage> messageList = new ArrayList<>();
            if (manager.generateMessage(dto)) {
                messageList.add(new ResultMessage());
            } else {
                messageList.add(new ResultMessage(2001));
            }
            result.put(receiver, messageList);
        }
        return new PlanGenNoticeResultMessage(result);
    }

    @Override
    public PlanGenNoticeResultMessage generatePassengerPlanModifyNotice(String user, String planKey, HashMap<PassengerPlanModifyEnum, String> modifyList, List<ReceiverEnum> receiverList) {
        Map<ReceiverEnum, List<ResultMessage>> result = new HashMap<>();
        GenerateNoticeInfoDTO dto = new GenerateNoticeInfoDTO();
        dto.setSender(SenderEnum.PASSENGER_PLAN);
        dto.setGenerateUser(user);
        KeyBase keyBase = new KeyBase(planKey);
        dto.setPlanDate(keyBase.getPlanDateString());
        dto.setTrainNum(keyBase.getTrainNum());
        dto.setStationName(keyBase.getStationName());
        dto.setModifiedDataMap(convertPassengerModifyMap(modifyList));
        for (ReceiverEnum receiver : receiverList) {
            dto.setReceiver(receiver);
            List<ResultMessage> messageList = new ArrayList<>();
            if (manager.generateMessage(dto)) {
                messageList.add(new ResultMessage());
            } else {
                messageList.add(new ResultMessage(2001));
            }
            result.put(receiver, messageList);
        }
        return new PlanGenNoticeResultMessage(result);
    }

    @Override
    public PlanGenNoticeResultMessage forwardPassengerPlanNotice(String user, NoticeMessageDTO dto, List<ReceiverEnum> receiverList) {
        HashMap<ReceiverEnum, List<ResultMessage>> result = new HashMap<>();
        if (ListUtil.isEmpty(dto.getNoticeList())) {
            List<ResultMessage> messageList = new ArrayList<>();
            messageList.add(new ResultMessage(305));
            for (ReceiverEnum receiver : receiverList) {
                result.put(receiver, messageList);
            }
            return new PlanGenNoticeResultMessage(result);
        } else {
            for (ReceiverEnum receiver : receiverList) {
                List<ResultMessage> messageList = new ArrayList<>();
                for (SingleNoticeMessageDTO sDto : dto.getNoticeList()) {
                    ResultMessage message = manager.forwardSingleNotice(sDto.getId(), receiver, user);
                    messageList.add(message);
                }

                result.put(receiver, messageList);
            }
        }
        return new PlanGenNoticeResultMessage(result);
    }

    //将调度计划修改列表转换为notice修改列表
    private HashMap<NoticeModifyEnum, String> convertDispatchModifyMap(HashMap<DispatchPlanModifyEnum, String> modifyList) {
        HashMap<NoticeModifyEnum, String> result = new HashMap<>();
        for (Map.Entry<DispatchPlanModifyEnum, String> entry : modifyList.entrySet()) {
            switch (entry.getKey()) {
                case ACTUAL_TRACK_NUM:
                    result.put(NoticeModifyEnum.ACTUAL_TRACK_NUM, entry.getValue());
                    break;
                case MANUAL_SUSPEND:
                    result.put(NoticeModifyEnum.MANUAL_SUSPEND, entry.getValue());
                    break;
                case ACTUAL_ARRIVE_TIME:
                    result.put(NoticeModifyEnum.ACTUAL_ARRIVE_TIME, entry.getValue());
                    break;
                case ACTUAL_DEPARTURE_TIME:
                    result.put(NoticeModifyEnum.ACTUAL_DEPARTURE_TIME, entry.getValue());
                    break;
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return result;
    }

    // 将客运计划修改列表转换为notice修改列表
    private HashMap<NoticeModifyEnum, String> convertPassengerModifyMap(HashMap<PassengerPlanModifyEnum, String> modifyList) {
        HashMap<NoticeModifyEnum, String> result = new HashMap<>();
        for (Map.Entry<PassengerPlanModifyEnum, String> entry : modifyList.entrySet()) {
            switch (entry.getKey()) {
                case START_CHECK_TIME:
                    result.put(NoticeModifyEnum.START_CHECK_TIME, entry.getValue());
                    break;
                case STOP_CHECK_TIME:
                    result.put(NoticeModifyEnum.STOP_CHECK_TIME, entry.getValue());
                    break;
                case WAIT_ZONE:
                    result.put(NoticeModifyEnum.WAIT_ZONE, entry.getValue());
                    break;
                case ENTRANCE_PORT:
                    result.put(NoticeModifyEnum.ENTRANCE_PORT, entry.getValue());
                    break;
                case EXIT_PORT:
                    result.put(NoticeModifyEnum.EXIT_PORT, entry.getValue());
                    break;
                case ABOARD_CHECK_GATE:
                    result.put(NoticeModifyEnum.ABOARD_CHECK_PORT, entry.getValue());
                    break;
                case EXIT_CHECK_GATE:
                    result.put(NoticeModifyEnum.EXIT_CHECK_PORT, entry.getValue());
                    break;
                default:
                    throw new IllegalArgumentException("Wrong input value " + entry.getKey());
            }
        }
        return result;
    }
}
