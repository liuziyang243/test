package com.crscd.passengerservice.ctc.domainobject;


import java.util.Map;

/**
 * Create by: zs
 * Date: 2017/8/25
 * Time: 9:49
 */
public class CtcPeriodPlanUnit {
    private String stationCode;
    private Map<String, CtcPeriodTrainUnit> ctcTrainUnitMap;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Map<String, CtcPeriodTrainUnit> getCtcTrainUnitMap() {
        return ctcTrainUnitMap;
    }

    public void setCtcTrainUnitMap(Map<String, CtcPeriodTrainUnit> ctcTrainUnitMap) {
        this.ctcTrainUnitMap = ctcTrainUnitMap;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof CtcPeriodPlanUnit) {
            CtcPeriodPlanUnit u = (CtcPeriodPlanUnit) obj;
            return this.stationCode.equals(u.stationCode)
                    && this.ctcTrainUnitMap.equals(u.ctcTrainUnitMap);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getStationCode().hashCode();
        result = 31 * result + this.getCtcTrainUnitMap().hashCode();
        return result;
    }


}
