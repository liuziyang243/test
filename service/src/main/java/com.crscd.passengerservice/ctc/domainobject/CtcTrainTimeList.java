package com.crscd.passengerservice.ctc.domainobject;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by: zs
 * Date: 2016/8/25
 * Time: 9:49
 */
public class CtcTrainTimeList {
    private LocalDateTime receiveTime;//接收时间
    private int bureauCode;//局码
    private Map<String, CtcTrainTimeUnit> ctcTrainTimeUnitMap;//列车报点信息列表  key为stationCode+"_"+trainNum

    public CtcTrainTimeList() {
        receiveTime = LocalDateTime.now();
        ctcTrainTimeUnitMap = new HashMap<>();
        //-1代表是组装数据;解析各ctc服务器的数据时会进行更新
        bureauCode = -1;
    }

    public LocalDateTime getReceiveTime() {

        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getBureauCode() {
        return bureauCode;
    }

    public void setBureauCode(int bureauCode) {
        this.bureauCode = bureauCode;
    }

    public Map<String, CtcTrainTimeUnit> getCtcTrainTimeUnitMap() {
        return ctcTrainTimeUnitMap;
    }

    public void setCtcTrainTimeUnitMap(Map<String, CtcTrainTimeUnit> ctcTrainTimeUnitMap) {
        this.ctcTrainTimeUnitMap = ctcTrainTimeUnitMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof CtcTrainTimeList) {
            CtcTrainTimeList u = (CtcTrainTimeList) obj;
            return this.bureauCode == u.bureauCode
                    && this.ctcTrainTimeUnitMap.equals(u.ctcTrainTimeUnitMap);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getBureauCode();
        result = 31 * result + this.getCtcTrainTimeUnitMap().hashCode();
        result = 31 * result + this.getReceiveTime().hashCode();
        return result;
    }


}
