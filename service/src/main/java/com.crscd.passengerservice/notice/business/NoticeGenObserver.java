package com.crscd.passengerservice.notice.business;

import com.crscd.passengerservice.notice.serviceinterface.implement.AbstractCTCNoticeInterfaceImpl;
import com.crscd.passengerservice.notice.serviceinterface.implement.AbstractTicketNoticeInterfaceImpl;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author lzy
 * @author lzy
 * Date: 2017/9/4
 * Time: 16:08
 */
public class NoticeGenObserver implements Observer {
    private static boolean autoProcessTicketFlag;
    private static boolean autoProcessCTCFlag;

    private AutoProcessManager autoProcessManager;

    public NoticeGenObserver(AbstractTicketNoticeInterfaceImpl ticketNoticeInterface, AbstractCTCNoticeInterfaceImpl ctcNoticeInterface) {
        super();
        ticketNoticeInterface.addObserver(this);
        ctcNoticeInterface.addObserver(this);
    }

    public static void setAutoProcessTicketFlag(boolean autoProcessTicketFlag) {
        NoticeGenObserver.autoProcessTicketFlag = autoProcessTicketFlag;
    }

    public static void setAutoProcessCTCFlag(boolean autoProcessCTCFlag) {
        NoticeGenObserver.autoProcessCTCFlag = autoProcessCTCFlag;
    }

    public void setAutoProcessManager(AutoProcessManager autoProcessManager) {
        this.autoProcessManager = autoProcessManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg) {
        List<Long> idList = (List<Long>) arg;
        if (o instanceof AbstractCTCNoticeInterfaceImpl && autoProcessCTCFlag) {
            autoProcessManager.autoAcceptCTCNotice(idList);
        }
        if (o instanceof AbstractTicketNoticeInterfaceImpl && autoProcessTicketFlag) {
            autoProcessManager.autoAcceptTicketNotice(idList);
        }
    }
}