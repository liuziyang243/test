package com.crscd.passengerservice.display.guiderule.domainobject;

import com.crscd.framework.orm.annotation.EqualBean;
import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTimeBaseEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

/**
 * Created by cuishiqing on 2017/7/18.
 */
@EqualBean
public class ScreenGuideRule {
    /* 列车类型 */
    protected TrainTypeEnum trainType;
    /* 列车在当前车站是始发、通过还是终到 */
    protected StationTypeEnum stationType;
    // 自增长ID
    @OrmIgnore
    private long id;
    // 显示规则归属车站
    private String stationName;
    // 屏幕类型
    private ScreenTypeEnum screenType;
    // 上屏时间基准：arriveTime:到点 departureTime: 发点
    private TrainTimeBaseEnum upTimeReference;
    // 上屏相对时间（可是负值，时间单位：分钟）
    private int upTimeOffset;
    // 下屏时间基准 arriveTime: 到点 departureTime：发点
    private TrainTimeBaseEnum downTimeReference;
    // 下屏相对时间（可是负值，时间单位：分钟）
    private int downTimeOffset;

    public ScreenGuideRule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TrainTypeEnum getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainTypeEnum trainType) {
        this.trainType = trainType;
    }

    public StationTypeEnum getStationType() {
        return stationType;
    }

    public void setStationType(StationTypeEnum stationType) {
        this.stationType = stationType;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public ScreenTypeEnum getScreenType() {
        return screenType;
    }

    public void setScreenType(ScreenTypeEnum screenType) {
        this.screenType = screenType;
    }

    public TrainTimeBaseEnum getUpTimeReference() {
        return upTimeReference;
    }

    public void setUpTimeReference(TrainTimeBaseEnum upTimeReference) {
        this.upTimeReference = upTimeReference;
    }

    public int getUpTimeOffset() {
        return upTimeOffset;
    }

    public void setUpTimeOffset(int upTimeOffset) {
        this.upTimeOffset = upTimeOffset;
    }

    public TrainTimeBaseEnum getDownTimeReference() {
        return downTimeReference;
    }

    public void setDownTimeReference(TrainTimeBaseEnum downTimeReference) {
        this.downTimeReference = downTimeReference;
    }

    public int getDownTimeOffset() {
        return downTimeOffset;
    }

    public void setDownTimeOffset(int downTimeOffset) {
        this.downTimeOffset = downTimeOffset;
    }
}
