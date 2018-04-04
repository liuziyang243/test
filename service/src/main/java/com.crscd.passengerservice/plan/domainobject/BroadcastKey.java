package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.number.RandomUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;

/**
 * Created by liuziyang
 * Create date: 2017/8/27
 */
public class BroadcastKey extends KeyBase {

    protected BroadcastKindEnum kind;

    protected int randomId;

    public BroadcastKey(BroadcastStationPlan plan) {
        super(plan);
        this.kind = plan.getBroadcastKind();
        this.randomId = RandomUtil.nextInt();
    }

    public BroadcastKey(String trainNum, String planDate, String stationName, BroadcastKindEnum kind) {
        super(trainNum, planDate, stationName);
        this.kind = kind;
        this.randomId = RandomUtil.nextInt();
    }

    public BroadcastKey(String key) {
        super(key);
        String[] strings = StringUtil.splitString(key, "_");
        StringBuilder kind = new StringBuilder();
        for (int i = 4; i < strings.length - 1; i++) {
            if (i == 4) {
                kind.append(strings[4]);
            } else {
                kind.append("_");
                kind.append(strings[i]);
            }
        }
        this.kind = BroadcastKindEnum.fromString(kind.toString());
        this.randomId = CastUtil.castInt(strings[strings.length - 1]);
    }

    public int getRandomId() {
        return randomId;
    }

    public BroadcastKindEnum getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return this.getTrainNum() + "_" + DateTimeFormatterUtil.convertDateToString(this.getPlanDate()) + "_" + this.getStationName() + "_" + this.getBureauCode() + "_" + this.kind.toString() + "_" + this.randomId;
    }
}
