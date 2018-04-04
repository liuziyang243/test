package com.crscd.passengerservice.broadcast.record.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.record.domainobject.BroadcastRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/28
 * Time: 9:21
 */
public class BroadcastRecordDAO {
    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    // 通过planKey获取recordKey
    public List<BroadcastRecord> getBroadcastRecordListByPlanKey(String planKey) {
        String condition = "key like '" + planKey + "_%'";
        return dataSet.selectListWithCondition(BroadcastRecord.class, condition);
    }

    // 通过recordKey获取record
    public BroadcastRecord getBroadcastRecordByKey(String recordKey) {
        String condition = "key=?";
        return dataSet.select(BroadcastRecord.class, condition, recordKey);
    }

    // 根据站名和起止时间（精确时间）过滤广播记录列表
    // 起始和结束时间为yyyy-mm--dd hh:mi:ss
    public List<BroadcastRecord> getBroadcastRecordByStationAndTime(String stationName, String trainNum, String startTime, String endTime) {
        List<BroadcastRecord> beanList = new ArrayList<>();
        Map<String, String> conditionParamMap = new HashMap<>();
        if (null != stationName) {
            conditionParamMap.put("stationName=?", stationName);
        }
        if (null != trainNum) {
            conditionParamMap.put("trainNum like ?", "%" + trainNum + "%");
        }
        if (null != startTime) {
            conditionParamMap.put("to_date(generateTime,'yyyy-mm-dd hh24:mi:ss')>=to_date(?,'yyyy-mm-dd hh24:mi:ss')", startTime);
        }
        if (null != endTime) {
            conditionParamMap.put("to_date(generateTime,'yyyy-mm-dd hh24:mi:ss')<=to_date(?,'yyyy-mm-dd hh24:mi:ss')", startTime);
        }
        if (conditionParamMap.isEmpty()) {
            beanList = dataSet.selectList(BroadcastRecord.class);
        } else if (conditionParamMap.size() == 1) {
            for (Map.Entry<String, String> entry : conditionParamMap.entrySet()) {
                beanList = dataSet.selectListWithCondition(BroadcastRecord.class, entry.getKey(), entry.getValue());
            }
        } else {
            String[] parms = new String[conditionParamMap.size()];
            int index = 0;
            StringBuilder condition = new StringBuilder();
            for (Map.Entry<String, String> entry : conditionParamMap.entrySet()) {
                if (index == 0) {
                    condition.append(entry.getKey());
                } else {
                    condition.append(" AND ").append(entry.getKey());
                }
                parms[index] = entry.getValue();
                index += 1;
            }
            beanList = dataSet.selectListWithCondition(BroadcastRecord.class, condition.toString(), parms);
        }
        return beanList;
    }

    // 向数据库插入一条新的广播记录
    public boolean insertBroadcastRecord(BroadcastRecord record) {
        return dataSet.insert(record);
    }

    // 更新广播记录中的执行状态
    public boolean updateBroadcastRecord(String planKey, BroadcastStateEnum state) {
        String condition = "key=?";
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("operationState", state);
        return dataSet.update(BroadcastRecord.class, fieldMap, condition, planKey);
    }
}
