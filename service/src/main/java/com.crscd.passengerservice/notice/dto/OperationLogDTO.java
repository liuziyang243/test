package com.crscd.passengerservice.notice.dto;

import com.crscd.passengerservice.notice.enumtype.ProcessStateEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;

/**
 * @author lzy
 * Date: 2017/8/29
 * Time: 12:57
 */
public class OperationLogDTO {
    /********** notice消息头 ***********/
    /* ID */
    private long id;
    /* 生成notice的源 */
    private SenderEnum sender;
    /* 生成notice消息的用户，也可能是系统 */
    private String generateUser;
    /* 产生notice消息的时间戳 */
    private String generateTimeStamp;
    /* notice描述 */
    private String description;
    /* 消息处理人（ip地址或者用户） */
    private String processUser;
    /* 消息处理的时间 */
    private String processTime;
    /* notice消息处理装态 */
    private ProcessStateEnum processState;

    /*********** notice消息体 ************/
    private String trainNum;

    public OperationLogDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ProcessStateEnum getProcessState() {
        return processState;
    }

    public void setProcessState(ProcessStateEnum processState) {
        this.processState = processState;
    }
}
