package com.crscd.passengerservice.notice.serviceinterface.implement;

import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.notice.business.NoticeGenObserver;
import com.crscd.passengerservice.notice.business.NoticeManager;
import com.crscd.passengerservice.notice.domainobject.NoticeMessage;
import com.crscd.passengerservice.notice.dto.NoticeMessageDTO;
import com.crscd.passengerservice.notice.dto.OperationLogDTO;
import com.crscd.passengerservice.notice.dto.SingleNoticeMessageDTO;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.notice.serviceinterface.NoticeServiceInterface;
import com.crscd.passengerservice.result.GroupResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * @author lzy
 * Date: 2017/8/29
 * Time: 14:19
 */
public class NoticeServiceInterfaceImpl implements NoticeServiceInterface {
    private NoticeManager manager;

    public void setManager(NoticeManager manager) {
        this.manager = manager;
    }

    @Override
    public List<NoticeMessageDTO> getUnhandledNoticeList() {
        List<NoticeMessage> messageList = manager.getUnhandledNoticeList();
        return getMessageDTOListFromDOList(messageList);
    }

    @Override
    public List<NoticeMessageDTO> getNoticeMessageByStation(String stationName, ReceiverEnum pageName, String startDate, String endDate) {
        List<NoticeMessage> messageList = manager.getUnhandledNoticeListByStationAndDate(null, pageName, stationName, startDate, endDate);
        return getMessageDTOListFromDOList(messageList);
    }

    @Override
    public ResultMessage startAutoProcessTicketNotice() {
        NoticeGenObserver.setAutoProcessTicketFlag(true);
        return new ResultMessage();
    }

    @Override
    public ResultMessage stopAutoProcessTicketNotice() {
        NoticeGenObserver.setAutoProcessTicketFlag(false);
        return new ResultMessage();
    }

    @Override
    public ResultMessage startAutoProcessCTCNotice() {
        NoticeGenObserver.setAutoProcessCTCFlag(true);
        return new ResultMessage();
    }

    @Override
    public ResultMessage stopAutoProcessCTCNotice() {
        NoticeGenObserver.setAutoProcessCTCFlag(false);
        return new ResultMessage();
    }

    @Override
    public List<OperationLogDTO> getOperationLogByStation(String stationName, SenderEnum senderPage, ReceiverEnum pageName, String startDate, String endDate) {
        List<NoticeMessage> messageList = manager.getHandledNoticeListByStationAndDate(senderPage, pageName, stationName, startDate, endDate);
        List<OperationLogDTO> dtoList = new ArrayList<>();
        for (NoticeMessage message : messageList) {
            OperationLogDTO dto = getLogDTOFromDO(message);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public GroupResultMessage acceptNotice(String processUser, ArrayList<Long> idList, ReceiverEnum pageName) {
        return manager.acceptNoticeList(processUser, idList, pageName);
    }

    @Override
    public GroupResultMessage rejectNotice(String processUser, ArrayList<Long> idList) {
        return manager.rejectNoticeList(processUser, idList);
    }

    private List<NoticeMessageDTO> getMessageDTOListFromDOList(List<NoticeMessage> messageList) {
        List<NoticeMessageDTO> dtoList = new ArrayList<>();
        Map<Long, List<NoticeMessage>> GroupList = new HashMap<>();
        // 先处理只含有信息的notice，并将属于不同组的notice筛选出来
        for (NoticeMessage message : messageList) {
            if (message.getNoticeGroup() == ServiceConstant.SINGLE_NOTICE) {
                dtoList.add(getMessageDTOFromDO(message));
            } else {
                if (GroupList.containsKey(message.getNoticeGroup())) {
                    GroupList.get(message.getNoticeGroup()).add(message);
                } else {
                    List<NoticeMessage> noticeMessageList = new ArrayList<>();
                    noticeMessageList.add(message);
                    GroupList.put(message.getNoticeGroup(), noticeMessageList);
                }
            }
        }
        // 处理组notice
        for (Map.Entry<Long, List<NoticeMessage>> entry : GroupList.entrySet()) {
            dtoList.add(getMessageDTOFromDO(entry.getValue()));
        }
        return dtoList;
    }

    private NoticeMessageDTO getMessageDTOFromDO(NoticeMessage message) {
        SingleNoticeMessageDTO singleMessage = new SingleNoticeMessageDTO();
        singleMessage.setId(message.getId());
        singleMessage.setOverTimeFlag(manager.checkNoticeValid(message));
        singleMessage.setTrainNum(message.getTrainNum());
        singleMessage.setDescription(message.getDescription());

        NoticeMessageDTO dto = new NoticeMessageDTO();
        dto.setGenerateTimeStamp(message.getGenerateTimeStamp());
        dto.setGenerateUser(message.getGenerateUser());
        dto.setSender(message.getSender());
        dto.getNoticeList().add(singleMessage);
        return dto;
    }

    private NoticeMessageDTO getMessageDTOFromDO(List<NoticeMessage> messageList) {
        NoticeMessageDTO dto = new NoticeMessageDTO();
        dto.setGenerateTimeStamp(messageList.get(0).getGenerateTimeStamp());
        dto.setGenerateUser(messageList.get(0).getGenerateUser());
        dto.setSender(messageList.get(0).getSender());

        for (NoticeMessage message : messageList) {
            SingleNoticeMessageDTO singleMessage = new SingleNoticeMessageDTO();
            singleMessage.setId(message.getId());
            singleMessage.setOverTimeFlag(manager.checkNoticeValid(message));
            singleMessage.setTrainNum(message.getTrainNum());
            singleMessage.setDescription(message.getDescription());
            dto.getNoticeList().add(singleMessage);
        }

        return dto;
    }

    private OperationLogDTO getLogDTOFromDO(NoticeMessage message) {
        return MapperUtil.map(message, OperationLogDTO.class);
    }
}
