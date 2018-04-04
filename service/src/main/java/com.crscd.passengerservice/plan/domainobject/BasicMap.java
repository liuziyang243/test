package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.text.JsonUtil;
import com.crscd.passengerservice.ctc.domainobject.CtcBasicMapInfo;
import com.crscd.passengerservice.ctc.domainobject.CtcBasicTrainInfo;
import com.crscd.passengerservice.ctc.po.BasicMapBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/14.
 */
public class BasicMap {
    private String uuid;
    private String receiveTime;
    private boolean confirmState;//true 代表已分析；false代表未分析
    private Map<String, CtcBasicTrainInfo> planMap;

    public BasicMap(BasicMapBean basicMapBean) {
        this.uuid = basicMapBean.getUuid();
        this.receiveTime = basicMapBean.getReceiveTime();
        int state = basicMapBean.getConfirmState();
        this.confirmState = state != 0;
        String basicMapJson = basicMapBean.getBasicMapJson();
        this.planMap = JsonUtil.fromJSON(basicMapJson, CtcBasicMapInfo.class).getPlanMap();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public boolean isConfirmState() {
        return confirmState;
    }

    public void setConfirmState(boolean confirmState) {
        this.confirmState = confirmState;
    }

    public Map<String, CtcBasicTrainInfo> getPlanMap() {
        return planMap;
    }

    public void setPlanMap(Map<String, CtcBasicTrainInfo> planMap) {
        this.planMap = planMap;
    }
}
