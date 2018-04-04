package com.crscd.passengerservice.broadcast.record.domainobject;

import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.domainobject.BroadcastKey;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;

import java.time.LocalDateTime;

/**
 * Created by liuziyang
 * Create date: 2017/9/16
 * 考虑多次对一条计划执行广播的情况，record key值的设计应该在broadcastkey基础上
 * 再增加一个唯一标识，使recordkey能够单向找到broadcastkey。
 */
public class BroadcastRecordKey extends BroadcastKey {

    /**
     * 用时间戳作为补充
     **/
    private String recordTime;

    public BroadcastRecordKey(BroadcastStationPlan plan) {
        super(plan);
        String planKey = plan.getPlanKey();
        BroadcastKey broadcastKey = new BroadcastKey(planKey);
        this.randomId = broadcastKey.getRandomId();
        this.recordTime = LocalDateTime.now().toString();
    }

    public BroadcastRecordKey(String key) {
        super(key);
        String[] strings = StringUtil.splitString(key, "_");
        this.recordTime = strings[5];
    }

    public BroadcastRecordKey(BroadcastKey broadcastKey) {
        super(broadcastKey.toString());
        this.recordTime = LocalDateTime.now().toString();
    }

    public String getRecordTime() {
        return recordTime;
    }

    /**
     * 通过记录key，可以单向找到对应广播计划key值，这样可以有效的
     * 支持反复手动播放同一条广播计划的情况
     *
     * @return
     */
    public String getBroadcastKey() {
        String recordKey = this.toString();
        int index = recordKey.lastIndexOf("_");
        return recordKey.substring(0, index - 1);
    }

    @Override
    public String toString() {
        return this.getTrainNum() + "_" + DateTimeFormatterUtil.convertDateToString(this.getPlanDate()) + "_" + this.getStationName() + "_" + this.getBureauCode() + "_" + this.kind.toString() + "_" + this.getRandomId() + "_" + this.recordTime;
    }
}
