package com.crscd.passengerservice.ctc.po;

import com.crscd.framework.util.base.ClobUtil;

import java.sql.Clob;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/11.
 */
public class BasicMapBean {
    private String uuid;
    private Clob basicMapJson;
    private String receiveTime;
    private int confirmState;

    public BasicMapBean() {
        this.confirmState = 0;
    }

    public BasicMapBean(UUID uuid, String basicMapJson, String receiveTime) {
        this.uuid = uuid.toString();
        this.basicMapJson = ClobUtil.str2Clob(basicMapJson);
        this.receiveTime = receiveTime;
        this.confirmState = 0;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBasicMapJson() {
        return ClobUtil.clob2Str(this.basicMapJson);
    }

    public void setBasicMapJson(String basicMapJson) {
        this.basicMapJson = ClobUtil.str2Clob(basicMapJson);
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(int confirmState) {
        this.confirmState = confirmState;
    }
}
