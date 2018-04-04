package com.crscd.passengerservice.broadcast.record.business;

import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.record.dao.BroadcastRecordDAO;
import com.crscd.passengerservice.broadcast.record.domainobject.BroadcastRecord;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 16:41
 */
public class BroadcastRecordManager {
    private BroadcastRecordDAO recordDAO;

    public void setRecordDAO(BroadcastRecordDAO recordDAO) {
        this.recordDAO = recordDAO;
    }

    // 通过recordKey获取record
    public BroadcastRecord getBroadcastRecordByKey(String recordKey) {
        return recordDAO.getBroadcastRecordByKey(recordKey);
    }

    // 通过广播计划key获取一组record
    public List<BroadcastRecord> getBroadcastRecordListByPlanKey(String planKey) {
        return recordDAO.getBroadcastRecordListByPlanKey(planKey);
    }

    // 记录到发/变更广播计划播放
    public void makeBroadcastRecord(BroadcastStationPlan plan, String content) {
        recordDAO.insertBroadcastRecord(new BroadcastRecord(plan, content));
    }

    // 记录车次广播
    public void makeBroadcastRecord(String planKey, String contentName, String content, List<String> broadcastArea, int priorityLevel) {
        recordDAO.insertBroadcastRecord(new BroadcastRecord(planKey, contentName, content, broadcastArea, priorityLevel));
    }

    // 根据站名和起止时间(日期)过滤广播记录列表
    // 起始和结束时间为yyyy-mm--dd hh:mi:ss
    public List<BroadcastRecord> getBroadcastRecordByStationAndDate(String stationName, String trainNum, String startDate, String endDate) {
        String startTime = null;
        String endTime = null;
        if (null != startDate) {
            startTime = startDate + " 00:00:00";
        }
        if (null != endDate) {
            endTime = endDate + " 00:00:00";
        }

        return recordDAO.getBroadcastRecordByStationAndTime(stationName, trainNum, startTime, endTime);
    }

    // 更新广播记录状态
    public void updateBroadcastRecord(String recordKey, BroadcastStateEnum state) {
        recordDAO.updateBroadcastRecord(recordKey, state);
    }
}
