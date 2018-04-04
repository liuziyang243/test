package com.crscd.passengerservice.notice.business;

import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * @author lzy
 * Date: 2017/9/4
 * Time: 16:13
 */
public class AutoProcessManager {
    private NoticeManager manager;

    public void setManager(NoticeManager manager) {
        this.manager = manager;
    }

    void autoAcceptTicketNotice(List<Long> idList) {
        ArrayList<Long> idlist = new ArrayList<>(idList);
        manager.acceptNoticeList("System", idlist, ReceiverEnum.PASSENGER_PLAN);
        manager.acceptNoticeList("System", idlist, ReceiverEnum.GUIDE_PLAN);
        manager.acceptNoticeList("System", idlist, ReceiverEnum.BROADCAST_PLAN);
    }

    void autoAcceptCTCNotice(List<Long> idList) {
        ArrayList<Long> idlist = new ArrayList<>(idList);
        manager.acceptNoticeList("System", idlist, ReceiverEnum.PASSENGER_PLAN);
        manager.acceptNoticeList("System", idlist, ReceiverEnum.GUIDE_PLAN);
        manager.acceptNoticeList("System", idlist, ReceiverEnum.BROADCAST_PLAN);
        manager.acceptNoticeList("System", idlist, ReceiverEnum.DISPATCH_PLAN);
    }
}
