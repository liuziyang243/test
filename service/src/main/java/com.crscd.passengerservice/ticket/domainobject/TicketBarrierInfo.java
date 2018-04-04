package com.crscd.passengerservice.ticket.domainobject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public class TicketBarrierInfo {
    //检票口id
    private int ticketBarrierId;
    //检票口名称
    private String ticketBarrierName;
    //闸机相关
    private Map<Integer, GatePortInfo> gatePortInfoMap;

    public TicketBarrierInfo() {
        gatePortInfoMap = new HashMap<>();
    }

    public int getTicketBarrierId() {
        return ticketBarrierId;
    }

    public void setTicketBarrierId(int ticketBarrierId) {
        this.ticketBarrierId = ticketBarrierId;
    }

    public String getTicketBarrierName() {
        return ticketBarrierName;
    }

    public void setTicketBarrierName(String ticketBarrierName) {
        this.ticketBarrierName = ticketBarrierName;
    }

    public Map<Integer, GatePortInfo> getGatePortInfoMap() {
        return gatePortInfoMap;
    }

    public void setGatePortInfoMap(Map<Integer, GatePortInfo> gatePortInfoMap) {
        this.gatePortInfoMap = gatePortInfoMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof TicketBarrierInfo) {
            TicketBarrierInfo u = (TicketBarrierInfo) obj;
            return this.ticketBarrierId == u.ticketBarrierId;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getTicketBarrierId();
        return result;
    }
}
