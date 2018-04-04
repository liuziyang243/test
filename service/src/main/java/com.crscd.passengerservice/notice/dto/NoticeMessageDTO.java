package com.crscd.passengerservice.notice.dto;

import com.crscd.passengerservice.notice.enumtype.SenderEnum;

import java.util.ArrayList;

/**
 * @author lzy
 * Date: 2017/8/29
 * Time: 12:58
 */
public class NoticeMessageDTO {
    /********** notice消息头 ***********/
    /* 生成notice的源 */
    private SenderEnum sender;
    /* 生成notice消息的用户，也可能是系统 */
    private String generateUser;
    /* 产生notice消息的时间戳 */
    private String generateTimeStamp;

    private ArrayList<SingleNoticeMessageDTO> noticeList;

    public NoticeMessageDTO() {
        noticeList = new ArrayList<>();
    }

    public SenderEnum getSender() {
        return sender;
    }

    public void setSender(SenderEnum sender) {
        this.sender = sender;
    }

    public String getGenerateUser() {
        return generateUser;
    }

    public void setGenerateUser(String generateUser) {
        this.generateUser = generateUser;
    }

    public String getGenerateTimeStamp() {
        return generateTimeStamp;
    }

    public void setGenerateTimeStamp(String generateTimeStamp) {
        this.generateTimeStamp = generateTimeStamp;
    }

    public ArrayList<SingleNoticeMessageDTO> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(ArrayList<SingleNoticeMessageDTO> noticeList) {
        this.noticeList = noticeList;
    }
}
