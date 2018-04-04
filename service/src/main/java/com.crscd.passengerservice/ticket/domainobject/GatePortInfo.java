package com.crscd.passengerservice.ticket.domainobject;

import com.crscd.framework.util.collection.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public class GatePortInfo {
    //闸机id
    private int gatePortId;
    //可检席别名称列表
    private List<String> seatNameList;
    //可检票种名称列表
    private List<String> ticketTypeList;

    public GatePortInfo() {
        seatNameList = new ArrayList<>();
        ticketTypeList = new ArrayList<>();
    }

    public int getGatePortId() {
        return gatePortId;
    }

    public void setGatePortId(int gatePortId) {
        this.gatePortId = gatePortId;
    }

    public List<String> getSeatNameList() {
        return seatNameList;
    }

    public void setSeatNameList(List<String> seatNameList) {
        this.seatNameList = seatNameList;
    }

    public List<String> getTicketTypeList() {
        return ticketTypeList;
    }

    public void setTicketTypeList(List<String> ticketTypeList) {
        this.ticketTypeList = ticketTypeList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof GatePortInfo) {
            GatePortInfo u = (GatePortInfo) obj;
            return this.gatePortId == u.gatePortId
                    && ListUtil.isSameList(this.seatNameList, u.seatNameList)
                    && ListUtil.isSameList(this.ticketTypeList, u.ticketTypeList);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getGatePortId();
        result = 31 * result + this.getSeatNameList().hashCode();
        result = 31 * result + this.getTicketTypeList().hashCode();
        return result;
    }
}
