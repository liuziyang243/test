package com.crscd.passengerservice.notice.serviceinterface;

import com.crscd.passengerservice.notice.dto.NoticeMessageDTO;
import com.crscd.passengerservice.notice.dto.OperationLogDTO;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.result.GroupResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/29
 * Time: 13:15
 */
public interface NoticeServiceInterface {
    /**
     * 获取全部未处理notice
     *
     * @return
     */
    @WebResult(name = "getUnhandledNoticeListResult")
    List<NoticeMessageDTO> getUnhandledNoticeList();

    /**
     * 获取指定计划的未处理notice
     *
     * @param stationName
     * @param pageName
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "getNoticeMessageByStationResult")
    List<NoticeMessageDTO> getNoticeMessageByStation(@WebParam(name = "stationName") String stationName, @WebParam(name = "pageName") ReceiverEnum pageName, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 获取指定计划notice处理记录和自身的操作记录
     * TODO: 未能考虑到guide plan和broadcast plan在操作的时候是没有生成message的
     *
     * @param stationName
     * @param senderPage
     * @param pageName
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "getOperationLogByStationResult")
    List<OperationLogDTO> getOperationLogByStation(@WebParam(name = "stationName") String stationName, @WebParam(name = "senderPage") SenderEnum senderPage, @WebParam(name = "pageName") ReceiverEnum pageName, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 接收noticce
     *
     * @param processUser
     * @param idList
     * @param pageName
     * @return
     */
    @WebResult(name = "acceptNoticeResult")
    GroupResultMessage acceptNotice(@WebParam(name = "info") String processUser, @WebParam(name = "messageId") ArrayList<Long> idList, @WebParam(name = "pageName") ReceiverEnum pageName);

    /**
     * 拒绝notice
     *
     * @param processUser
     * @param idList
     * @return
     */
    @WebResult(name = "rejectNoticeResult")
    GroupResultMessage rejectNotice(@WebParam(name = "info") String processUser, @WebParam(name = "messageId") ArrayList<Long> idList);

    /**
     * 开启自动接收客票系统修改
     *
     * @return
     */
    @WebResult(name = "startAutoProcessTicketNoticeResult")
    ResultMessage startAutoProcessTicketNotice();

    /**
     * 停止自动接收客票系统修改
     *
     * @return
     */
    @WebResult(name = "stopAutoProcessTicketNoticeResult")
    ResultMessage stopAutoProcessTicketNotice();

    /**
     * 开启自动接收CTC系统修改
     *
     * @return
     */
    @WebResult(name = "startAutoProcessCTCNoticeResult")
    ResultMessage startAutoProcessCTCNotice();

    /**
     * 停止自动接收CTC系统修改
     *
     * @return
     */
    @WebResult(name = "stopAutoProcessCTCNoticeResult")
    ResultMessage stopAutoProcessCTCNotice();
}
