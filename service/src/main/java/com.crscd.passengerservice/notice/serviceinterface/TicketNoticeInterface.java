package com.crscd.passengerservice.notice.serviceinterface;

import com.crscd.passengerservice.ticket.dto.TicketCheckNoticeData;

/**
 * @author lzy
 * Date: 2017/8/31
 * Time: 16:21
 */
public interface TicketNoticeInterface {
    /**
     * 由客票系统生成Notice
     *
     * @param data
     */
    void genTicketNotice(TicketCheckNoticeData data);
}
