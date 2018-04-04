package com.crscd.passengerservice.notice.serviceinterface.implement;

import com.crscd.passengerservice.ctc.dto.CtcNoticeData;
import com.crscd.passengerservice.notice.business.NoticeManager;
import com.crscd.passengerservice.notice.dto.GenerateNoticeInfoDTO;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * @author lzy
 * Date: 2017/9/4
 * Time: 17:52
 */
public class CTCNoticeInterfaceImpl extends AbstractCTCNoticeInterfaceImpl {
    private NoticeManager manager;

    public void setManager(NoticeManager manager) {
        this.manager = manager;
    }

    @Override
    public void genCTCNotice(CtcNoticeData data) {
        GenerateNoticeInfoDTO dto = new GenerateNoticeInfoDTO();
        dto.setGenerateUser("CTC System");
        dto.setSender(SenderEnum.CTC);
        dto.setNoticeGroup(data.getNoticeGroup());
        dto.setPlanDate(data.getPlanDate());
        dto.setTrainNum(data.getTrainNum());
        dto.setStationName(data.getStationName());
        dto.setModifiedDataMap(data.getModifiedDataMap());

        List<Long> idList = new ArrayList<>();
        dto.setReceiver(ReceiverEnum.GUIDE_PLAN);
        Long uid = manager.generateMessageReturnID(dto);
        idList.add(uid);
        dto.setReceiver(ReceiverEnum.PASSENGER_PLAN);
        Long pid = manager.generateMessageReturnID(dto);
        idList.add(pid);
        dto.setReceiver(ReceiverEnum.GUIDE_PLAN);
        Long gid = manager.generateMessageReturnID(dto);
        idList.add(gid);
        dto.setReceiver(ReceiverEnum.BROADCAST_PLAN);
        Long bid = manager.generateMessageReturnID(dto);
        idList.add(bid);

        // 接收到来自CTC的新的notice
        setChanged();
        // 通知观察者,将3个notice的id发送给观察者便于继续处理
        notifyObservers(idList);
    }
}
