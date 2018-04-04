package com.crscd.passengerservice.ctc.domainobject;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zs
 * Date: 2017/8/25
 * Time: 9:49
 */
public class CtcPeriodPlanList {
    private int bureauCode;
    private LocalDateTime receiveTime;
    private Map<String, CtcPeriodPlanUnit> ctcPlanUnitMap;

    public CtcPeriodPlanList() {
        //-1代表是组装数据;解析各ctc服务器的数据时会进行更新
        bureauCode = -1;
        receiveTime = LocalDateTime.now();
        ctcPlanUnitMap = new HashMap<>();
    }

    public int getBureauCode() {
        return bureauCode;
    }

    public void setBureauCode(int bureauCode) {
        this.bureauCode = bureauCode;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Map<String, CtcPeriodPlanUnit> getCtcPlanUnitMap() {
        return ctcPlanUnitMap;
    }

    public void setCtcPlanUnitMap(Map<String, CtcPeriodPlanUnit> ctcPlanUnitMap) {
        this.ctcPlanUnitMap = ctcPlanUnitMap;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof CtcPeriodPlanList) {
            CtcPeriodPlanList u = (CtcPeriodPlanList) obj;
            return this.bureauCode == u.bureauCode
                    && this.ctcPlanUnitMap.equals(u.ctcPlanUnitMap);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getBureauCode();
        result = 31 * result + this.getCtcPlanUnitMap().hashCode();
        result = 31 * result + this.getReceiveTime().hashCode();
        return result;
    }


}
