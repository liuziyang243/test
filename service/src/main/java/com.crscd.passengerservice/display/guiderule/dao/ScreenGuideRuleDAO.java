package com.crscd.passengerservice.display.guiderule.dao;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.display.guiderule.domainobject.ScreenGuideRule;
import com.crscd.passengerservice.plan.enumtype.StationTypeEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/10
 */
public class ScreenGuideRuleDAO {
    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    // 根据车站和屏幕类型获取导向规则
    public List<ScreenGuideRule> getScreenGuideRuleByTypeAndStation(String stationName, ScreenTypeEnum type) {
        String condition = "stationName=? AND screenType=?";
        return dataSet.selectListWithCondition(ScreenGuideRule.class, condition, stationName, type);
    }

    // 根据车站、列车类型和屏幕类型获取导向规则
    // 用于显示
    public ScreenGuideRule getScreenGuideRuleByStationTrainAndType(String stationName, ScreenTypeEnum screenType, TrainTypeEnum trainType, StationTypeEnum stationType) {
        String condition = "stationName=? AND screenType=? And trainType=? And stationType=?";
        return dataSet.select(ScreenGuideRule.class, condition, stationName, screenType, trainType, stationType);
    }

    // 增加导向规则
    public boolean insertScreenGuideRule(ScreenGuideRule rule) {
        return dataSet.insert(rule);
    }

    // 编辑导向规则
    public boolean updateScreenGuideRule(ScreenGuideRule rule) {
        return dataSet.update(rule);
    }

    // 删除导向规则
    public boolean delScreenGuideRule(long id) {
        return dataSet.delete(ScreenGuideRule.class, FrameworkConstant.DEL_BY_PK, id);
    }
}
