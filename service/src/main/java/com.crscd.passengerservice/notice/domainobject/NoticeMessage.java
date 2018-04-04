package com.crscd.passengerservice.notice.domainobject;

import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.notice.dto.GenerateNoticeInfoDTO;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.enumtype.ProcessStateEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.notice.po.NoticeMessageBean;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/29
 * Time: 11:58
 */
public class NoticeMessage {
    /********** notice消息头 ***********/
    /* ID */
    private long id;
    /* 生成notice的源 */
    private SenderEnum sender;
    /* 接收notice消息的对象，即修改对象 */
    private ReceiverEnum receiver;
    /* 生成notice消息的用户，也可能是系统 */
    private String generateUser;
    /* 产生notice消息的时间戳 */
    private String generateTimeStamp;
    /* notice描述 */
    private String description;
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
    private LocalDate planDate;
    private Map<NoticeModifyEnum, String> modifiedDataMap;

    public NoticeMessage() {
        this.processState = ProcessStateEnum.UN_HANDLE;
        generateTimeStamp = DateTimeUtil.getCurrentDatetimeString();
        modifiedDataMap = new HashMap<>();
        // 初始化组号，一般认为是单notice
        this.noticeGroup = ServiceConstant.SINGLE_NOTICE;
    }

    public NoticeMessage(GenerateNoticeInfoDTO dto) {
        this();
        // 如果来自ctc或者客票系统，可能是一组修改，主要接受来自系统的组标识
        if (SenderEnum.CTC.equals(dto.getSender()) || SenderEnum.TICKET.equals(dto.getSender())) {
            this.noticeGroup = dto.getNoticeGroup();
        }
        this.sender = dto.getSender();
        this.receiver = dto.getReceiver();
        this.generateUser = dto.getGenerateUser();
        this.trainNum = dto.getTrainNum();
        this.stationName = dto.getStationName();
        this.planDate = DateTimeFormatterUtil.convertStringToDate(dto.getPlanDate());
        if (MapUtil.isNotEmpty(dto.getModifiedDataMap())) {
            this.modifiedDataMap = dto.getModifiedDataMap();
        }
    }

    public NoticeMessage(NoticeMessageBean bean) {
        this.id = bean.getId();
        this.sender = bean.getSender();
        this.receiver = bean.getReceiver();
        this.generateTimeStamp = bean.getGenerateTimeStamp();
        this.processState = bean.getProcessState();
        this.processUser = bean.getProcessUser();
        this.processTime = bean.getProcessTime();
        this.trainNum = bean.getTrainNum();
        this.stationName = bean.getStationName();
        this.planDate = DateTimeFormatterUtil.convertStringToDate(bean.getPlanDate());
        this.modifiedDataMap = new HashMap<>();
        this.noticeGroup = bean.getNoticeGroup();
        Map<String, String> stringMap = JsonUtil.jsonToMap(bean.getModifiedDataMap(), String.class, String.class);
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            this.modifiedDataMap.put(NoticeModifyEnum.fromString(entry.getKey()), entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "[notice id]:" + this.getId() + "\n" +
                "[notice sender]:" + this.getSender() + "\n" +
                "[notice receiver]:" + this.getReceiver() + "\n" +
                "[notice trainNum]:" + this.getTrainNum() + "\n" +
                "[notice station]:" + this.getStationName() + "\n" +
                "[notice planDate]:" + this.getPlanDate() + "\n";
    }

    public long getId() {
        return id;
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

    public String getDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<NoticeModifyEnum, String> entry : modifiedDataMap.entrySet()) {
            stringBuilder.append(entry.getKey().getModifyMessage()).append(" The new value after modify is ").append(entry.getValue()).append(". ");
        }
        return stringBuilder.toString();
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

    public LocalDate getPlanDate() {
        return planDate;
    }

    public void setPlanDate(LocalDate planDate) {
        this.planDate = planDate;
    }

    public Map<NoticeModifyEnum, String> getModifiedDataMap() {
        return modifiedDataMap;
    }

    public void setModifiedDataMap(Map<NoticeModifyEnum, String> modifiedDataMap) {
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
