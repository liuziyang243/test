package com.crscd.passengerservice.notice.serviceinterface;

import com.crscd.passengerservice.ctc.dto.CtcNoticeData;

/**
 * @author lzy
 * Date: 2017/9/4
 * Time: 16:18
 */
public interface CTCNoticeServiceInterface {

    /**
     * 由CTC生成Noitce信息
     *
     * @param data
     */
    void genCTCNotice(CtcNoticeData data);
}
