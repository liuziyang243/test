package com.crscd.passengerservice.ctc.domainobject;

import java.time.LocalDateTime;

/**
 * Create by: zs
 * Date: 2017/8/25
 * Time: 9:49
 */
public class CtcPeriodTrainUnit {
    private String trainId;//唯一id
    private int runState;//运行状态标志	0－通过　1－到开 2－始发 3－终到 4－临时终止
    private String trainNum;
    //后方站
    private String backStationCode;//后方站
    private LocalDateTime backPlanTime;//后方站计划出发时间
    private int backIsDeparture;//后方站是否实际出发标志

    //前方站
    private String frontStationCode;//前方站
    private LocalDateTime frontPlanTime;//前方站计划到达时间
    private int frontIsDeparture;//前方站是否实际到达标志

    private String arriveTrainNum;//到达车次号
    private String departureTrainNum;//出发车次号

    private LocalDateTime mapArriveTime;//图定到达时间
    private LocalDateTime planArriveTime;//计划到达时间
    private int isArrive;//是否实际到达

    private LocalDateTime mapDepartureTime;//图定出发时间
    private LocalDateTime planDepartureTime;//计划出发时间
    private int isDeparture;//是否实际出发

    private int arriveTrack;//到达股道编号
    private int departureTrack;//出发股道编号

    private int isWorking;//是否作业	  0：没有作业 1：有作业
    private int trainProperty;//列车属性
    private int trainType;//列车类型

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public int getRunState() {
        return runState;
    }

    public void setRunState(int runState) {
        this.runState = runState;
    }

    public String getBackStationCode() {
        return backStationCode;
    }

    public void setBackStationCode(String backStationCode) {
        this.backStationCode = backStationCode;
    }

    public LocalDateTime getBackPlanTime() {
        return backPlanTime;
    }

    public void setBackPlanTime(LocalDateTime backPlanTime) {
        this.backPlanTime = backPlanTime;
    }

    public int getBackIsDeparture() {
        return backIsDeparture;
    }

    public void setBackIsDeparture(int backIsDeparture) {
        this.backIsDeparture = backIsDeparture;
    }

    public String getFrontStationCode() {
        return frontStationCode;
    }

    public void setFrontStationCode(String frontStationCode) {
        this.frontStationCode = frontStationCode;
    }

    public LocalDateTime getFrontPlanTime() {
        return frontPlanTime;
    }

    public void setFrontPlanTime(LocalDateTime frontPlanTime) {
        this.frontPlanTime = frontPlanTime;
    }

    public int getFrontIsDeparture() {
        return frontIsDeparture;
    }

    public void setFrontIsDeparture(int frontIsDeparture) {
        this.frontIsDeparture = frontIsDeparture;
    }

    public String getArriveTrainNum() {
        return arriveTrainNum;
    }

    public void setArriveTrainNum(String arriveTrainNum) {
        this.arriveTrainNum = arriveTrainNum;
    }

    public String getDepartureTrainNum() {
        return departureTrainNum;
    }

    public void setDepartureTrainNum(String departureTrainNum) {
        this.departureTrainNum = departureTrainNum;
    }

    public LocalDateTime getMapArriveTime() {
        return mapArriveTime;
    }

    public void setMapArriveTime(LocalDateTime mapArriveTime) {
        this.mapArriveTime = mapArriveTime;
    }

    public LocalDateTime getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(LocalDateTime planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    public int getIsArrive() {
        return isArrive;
    }

    public void setIsArrive(int isArrive) {
        this.isArrive = isArrive;
    }

    public LocalDateTime getMapDepartureTime() {
        return mapDepartureTime;
    }

    public void setMapDepartureTime(LocalDateTime mapDepartureTime) {
        this.mapDepartureTime = mapDepartureTime;
    }

    public LocalDateTime getPlanDepartureTime() {
        return planDepartureTime;
    }

    public void setPlanDepartureTime(LocalDateTime planDepartureTime) {
        this.planDepartureTime = planDepartureTime;
    }

    public int getIsDeparture() {
        return isDeparture;
    }

    public void setIsDeparture(int isDeparture) {
        this.isDeparture = isDeparture;
    }

    public int getArriveTrack() {
        return arriveTrack;
    }

    public void setArriveTrack(int arriveTrack) {
        this.arriveTrack = arriveTrack;
    }

    public int getDepartureTrack() {
        return departureTrack;
    }

    public void setDepartureTrack(int departureTrack) {
        this.departureTrack = departureTrack;
    }

    public int getIsWorking() {
        return isWorking;
    }

    public void setIsWorking(int isWorking) {
        this.isWorking = isWorking;
    }

    public int getTrainProperty() {
        return trainProperty;
    }

    public void setTrainProperty(int trainProperty) {
        this.trainProperty = trainProperty;
    }

    public int getTrainType() {
        return trainType;
    }

    public void setTrainType(int trainType) {
        this.trainType = trainType;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof CtcPeriodTrainUnit) {
            CtcPeriodTrainUnit u = (CtcPeriodTrainUnit) obj;
            return this.trainId.equals(u.trainId)
                    && this.runState == u.runState
                    && this.trainNum.equals(u.trainNum)
                    && this.backStationCode.equals(u.backStationCode)
                    && this.backPlanTime.equals(u.backPlanTime)
                    && this.backIsDeparture == u.backIsDeparture
                    && this.frontStationCode.equals(u.frontStationCode)
                    && this.frontPlanTime.equals(u.frontPlanTime)
                    && this.frontIsDeparture == u.frontIsDeparture
                    && this.arriveTrainNum.equals(u.arriveTrainNum)
                    && this.departureTrainNum.equals(u.departureTrainNum)
                    && this.mapArriveTime.equals(u.mapArriveTime)
                    && this.planArriveTime.equals(u.planArriveTime)
                    && this.isArrive == u.isArrive
                    && this.mapDepartureTime.equals(u.mapDepartureTime)
                    && this.planDepartureTime.equals(u.planDepartureTime)
                    && this.isDeparture == u.isDeparture
                    && this.arriveTrack == u.arriveTrack
                    && this.departureTrack == u.departureTrack
                    && this.isWorking == u.isWorking
                    && this.trainProperty == u.trainProperty
                    && this.trainType == u.trainType;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getTrainId().hashCode();
        result = 31 * result + this.getRunState();
        result = 31 * result + this.getTrainNum().hashCode();
        result = 31 * result + this.getBackStationCode().hashCode();
        result = 31 * result + this.getBackPlanTime().hashCode();
        result = 31 * result + this.getBackIsDeparture();
        result = 31 * result + this.getFrontStationCode().hashCode();
        result = 31 * result + this.getFrontPlanTime().hashCode();
        result = 31 * result + this.getFrontIsDeparture();
        result = 31 * result + this.getArriveTrainNum().hashCode();
        result = 31 * result + this.getDepartureTrainNum().hashCode();
        result = 31 * result + this.getMapArriveTime().hashCode();
        result = 31 * result + this.getPlanArriveTime().hashCode();
        result = 31 * result + this.getIsArrive();
        result = 31 * result + this.getMapDepartureTime().hashCode();
        result = 31 * result + this.getPlanDepartureTime().hashCode();
        result = 31 * result + this.getIsDeparture();
        result = 31 * result + this.getArriveTrack();
        result = 31 * result + this.getDepartureTrack();
        result = 31 * result + this.getIsWorking();
        result = 31 * result + this.getTrainProperty();
        result = 31 * result + this.getTrainType();
        return result;
    }

}
