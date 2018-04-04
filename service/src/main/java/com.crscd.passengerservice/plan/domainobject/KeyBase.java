package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.context.ContextHelper;

import java.time.LocalDate;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 10:26
 */
public class KeyBase {
    private String trainNum;
    private LocalDate planDate;
    private String stationName;
    private String bureauCode;

    private KeyBase() {
    }

    public KeyBase(BaseTrainPlan base) {
        this();
        this.trainNum = base.getTrainNum();
        this.planDate = base.getPlanDate();
        this.stationName = base.getPresentStation().getStationName();
        this.bureauCode = base.getPresentStation().getBureauCode();
    }

    public KeyBase(String trainNum, String planDate, String stationName) {
        this();
        this.trainNum = trainNum;
        this.planDate = DateTimeFormatterUtil.convertStringToDate(planDate);
        this.stationName = stationName;
        ConfigManager manager = ContextHelper.getConfigManager();
        this.bureauCode = manager.getBureauCodeByStationName(stationName);
    }

    public KeyBase(String trainNum, LocalDate planDate, String stationName) {
        this();
        this.trainNum = trainNum;
        this.planDate = planDate;
        this.stationName = stationName;
        ConfigManager manager = ContextHelper.getConfigManager();
        this.bureauCode = manager.getBureauCodeByStationName(stationName);
    }

    public KeyBase(String key) {
        this();
        String[] strings = StringUtil.splitString(key, "_");
        this.trainNum = strings[0];
        this.planDate = DateTimeFormatterUtil.convertStringToDate(strings[1]);
        this.stationName = strings[2];
        this.bureauCode = strings[3];
    }

    public String getTrainNum() {
        return trainNum;
    }

    public LocalDate getPlanDate() {
        return planDate;
    }

    public String getPlanDateString() {
        return DateTimeFormatterUtil.convertDateToString(getPlanDate());
    }

    public String getStationName() {
        return stationName;
    }

    public String getBureauCode() {
        return bureauCode;
    }

    @Override
    public String toString() {
        return this.trainNum + "_" + DateTimeFormatterUtil.convertDateToString(this.planDate) + "_" + this.stationName + "_" + this.bureauCode;
    }
}
