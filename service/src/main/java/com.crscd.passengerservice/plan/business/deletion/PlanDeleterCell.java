package com.crscd.passengerservice.plan.business.deletion;

import com.crscd.passengerservice.plan.domainobject.KeyBase;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/2/1 8:52
 * @version v1.00
 * <p>
 * 用于删除一组计划的时候使用，同一个PDC(PlanDeleterCell)包含列车车次号、站名和计划日期
 * 用于标定一组调度计划、客运计划、导向计划和广播计划
 */
public class PlanDeleterCell {
    private String trainNum;
    private String stationName;
    private String date;

    public PlanDeleterCell() {
    }

    public PlanDeleterCell(String trainNum, String stationName, String date) {
        this.trainNum = trainNum;
        this.stationName = stationName;
        this.date = date;
    }

    public String getPlanKey() {
        return new KeyBase(trainNum, date, stationName).toString();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
