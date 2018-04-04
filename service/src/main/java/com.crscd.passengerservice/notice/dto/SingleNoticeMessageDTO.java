package com.crscd.passengerservice.notice.dto;

/**
 * @author lzy
 * <p>
 * Create Time: 2017/11/8 12:24
 * @version v1.00
 */
public class SingleNoticeMessageDTO {
    /* ID */
    private long id;
    /* notice描述 */
    private String description;
    /*********** notice消息体 ************/
    private String trainNum;
    /************ notice状态  ************/
    // 如果notice携带的修改内容已经与plan相同，则认为修改无效
    // 如果修改对象已经不存在，则认为notice已经失效
    // 如果修改对象（计划）已经无效，则认为notice也随之失效
    private boolean overTimeFlag;

    public SingleNoticeMessageDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public boolean isOverTimeFlag() {
        return overTimeFlag;
    }

    public void setOverTimeFlag(boolean overTimeFlag) {
        this.overTimeFlag = overTimeFlag;
    }
}
