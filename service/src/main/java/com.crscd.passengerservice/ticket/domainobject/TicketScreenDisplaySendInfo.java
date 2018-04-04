package com.crscd.passengerservice.ticket.domainobject;

import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/9/5.
 */
public class TicketScreenDisplaySendInfo {
    private int screenID;
    private List<Map<String, String>> tableData;

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public List<Map<String, String>> getTableData() {
        return tableData;
    }

    public void setTableData(List<Map<String, String>> tableData) {
        this.tableData = tableData;
    }
}
