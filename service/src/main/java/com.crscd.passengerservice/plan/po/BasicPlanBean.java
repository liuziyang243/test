package com.crscd.passengerservice.plan.po;

import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 11:21
 */
public class BasicPlanBean {
    /**
     * ID
     */
    @OrmIgnore
    private long id;
    /** Generated time record */
    private String generateTimestamp;
    /** 列车车次号 */
    private String trainNum;
    /** 列车车次号，暂时备用 */
    private String uniqueTrainNum;
    /** 列车类型 */
    private TrainTypeEnum trainType;
    /** 始发站 */
    private String startStation;
    /** 终到站 */
    private String finalStation;
    /** 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    /** 停开起止 */
    private String trainSuspendStart;
    private String trainSuspendEnd;

    /**
     * 通过BasicPlan生成Bean
     */
    public BasicPlanBean(BasicPlan plan) {
        this.id = plan.getId();
        this.generateTimestamp = plan.getGenerateTimestamp();
        this.trainNum = plan.getTrainNum();
        this.uniqueTrainNum = plan.getUniqueTrainNum();
        this.trainType = plan.getTrainType();
        this.startStation = plan.getStartStation().getStationName();
        this.finalStation = plan.getFinalStation().getStationName();
        this.validPeriodStart = DateTimeFormatterUtil.convertDateToString(plan.getValidPeriodStart());
        this.validPeriodEnd = DateTimeFormatterUtil.convertDateToString(plan.getValidPeriodEnd());
        this.trainSuspendStart = DateTimeFormatterUtil.convertDateToString(plan.getTrainSuspendStart());
        this.trainSuspendEnd = DateTimeFormatterUtil.convertDateToString(plan.getTrainSuspendEnd());
    }

    public BasicPlanBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenerateTimestamp() {
        return generateTimestamp;
    }

    public void setGenerateTimestamp(String generateTimestamp) {
        this.generateTimestamp = generateTimestamp;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getUniqueTrainNum() {
        return uniqueTrainNum;
    }

    public void setUniqueTrainNum(String uniqueTrainNum) {
        this.uniqueTrainNum = uniqueTrainNum;
    }

    public TrainTypeEnum getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainTypeEnum trainType) {
        this.trainType = trainType;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(String finalStation) {
        this.finalStation = finalStation;
    }

    public String getValidPeriodStart() {
        return validPeriodStart;
    }

    public void setValidPeriodStart(String validPeriodStart) {
        this.validPeriodStart = validPeriodStart;
    }

    public String getValidPeriodEnd() {
        return validPeriodEnd;
    }

    public void setValidPeriodEnd(String validPeriodEnd) {
        this.validPeriodEnd = validPeriodEnd;
    }

    public String getTrainSuspendStart() {
        return trainSuspendStart;
    }

    public void setTrainSuspendStart(String trainSuspendStart) {
        this.trainSuspendStart = trainSuspendStart;
    }

    public String getTrainSuspendEnd() {
        return trainSuspendEnd;
    }

    public void setTrainSuspendEnd(String trainSuspendEnd) {
        this.trainSuspendEnd = trainSuspendEnd;
    }
}
