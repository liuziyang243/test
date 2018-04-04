package com.crscd.passengerservice.cctv.dao;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.cctv.domainobject.RoundRollingGroup;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 11:31
 */
public class RoundRollingGroupDAO {
    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public List<RoundRollingGroup> getRoundRollingGroupList(String stationName) {
        String condition = "stationName=?";
        return dataSet.selectListWithCondition(RoundRollingGroup.class, condition, stationName);
    }

    public boolean insert(RoundRollingGroup group) {
        return dataSet.insert(group);
    }

    public boolean update(RoundRollingGroup group) {
        return dataSet.update(group);
    }

    public boolean delete(long id) {
        return dataSet.delete(RoundRollingGroup.class, FrameworkConstant.DEL_BY_PK, id);
    }

    public boolean exitGroupName(String groupName, String stationName) {
        String condition = "stationName=? AND groupName=?";
        return dataSet.selectCount(RoundRollingGroup.class, condition, stationName, groupName) > 0;
    }
}
