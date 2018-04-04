package com.crscd.passengerservice.notice.po;

import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.passengerservice.notice.enumtype.ProcessStateEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;

/**
 * @author lzy
 * Date: 2017/8/29
 * Time: 13:04
 */
public class NoticeMessageBean {
    /********** notice消息头 ***********/
    /* ID */
    @OrmIgnore
    private long id;
    /* 生成notice的源 */
    private SenderEnum sender;
    /* 接收notice消息的对象，即修改对象 */
    private ReceiverEnum receiver;
    /* 生成notice消息的用户，也可能是系统 */
    private String generateUser;
    /* 产生notice消息的时间戳 */
    private String generateTimeStamp;
    /* notice消息处理装态 */
    private ProcessStateEnum processState;
    /* 消息处理人（ip地址或者用户） */
    private String processUser;
    /* 消息处理的时间 */
    private String processTime;
    /* notice组号, 用来标识notice是否是同一来源, 如果为-1表示是一个单一消息 */
    private long noticeGroup;

    /*********** notice消息体 ************/
    private String trainNum;
    private String stationName;
    private String planDate;
    private String modifiedDataMap;

    public NoticeMessageBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SenderEnum getSender() {
        return sender;
    }

    public void setSender(SenderEnum sender) {
        this.sender = sender;
    }

    public ReceiverEnum getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiverEnum receiver) {
        this.receiver = receiver;
    }

    public String getGenerateTimeStamp() {
        return generateTimeStamp;
    }

    public void setGenerateTimeStamp(String generateTimeStamp) {
        this.generateTimeStamp = generateTimeStamp;
    }

    public ProcessStateEnum getProcessState() {
        return processState;
    }

    public void setProcessState(ProcessStateEnum processState) {
        this.processState = processState;
    }

    public String getProcessUser() {
        return processUser;
    }

    public void setProcessUser(String processUser) {
        this.processUser = processUser;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getModifiedDataMap() {
        return modifiedDataMap;
    }

    public void setModifiedDataMap(String modifiedDataMap) {
        this.modifiedDataMap = modifiedDataMap;
    }

    public String getGenerateUser() {
        return generateUser;
    }

    public void setGenerateUser(String generateUser) {
        this.generateUser = generateUser;
    }

    public long getNoticeGroup() {
        return noticeGroup;
    }

    public void setNoticeGroup(long noticeGroup) {
        this.noticeGroup = noticeGroup;
    }
}
