package com.crscd.passengerservice.ticket.bussiness;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.display.format.util.DataGetOrderByTime;
import com.crscd.passengerservice.ticket.domainobject.TicketScreenDisplaySendInfo;
import com.crscd.passengerservice.ticket.po.TicketScreenDataBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/9/5.
 */
public class TicketDataForDisplayGeneration {
    //数据库
    private DataSet oracleDataSet;
    private ConfigManager configManager;
    private DataGetOrderByTime dataGetOrderByTime;

    public void setDataGetOrderByTime(DataGetOrderByTime dataGetOrderByTime) {
        this.dataGetOrderByTime = dataGetOrderByTime;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    /**
     * 售票屏数据生成
     *
     * @return
     */
    public List<TicketScreenDisplaySendInfo> dataForDisplayGeneration() {
        List<TicketScreenDisplaySendInfo> tsd = new ArrayList<>();
        List<ScreenConfig> screenList = oracleDataSet.selectListWithCondition(ScreenConfig.class, "ScreenType=?", ScreenTypeEnum.TICKET_INFO_SCREEN.toString());
        for (ScreenConfig screen : screenList) {
            int screenID = screen.getScreenID();
            tsd.add(dataForSingleScreen(screenID));
        }
        return tsd;
    }

    private TicketScreenDisplaySendInfo dataForSingleScreen(int screenID) {
        List<Map<String, String>> tableData = ticketScreenDataGeneration(screenID);
        TicketScreenDisplaySendInfo tsdInfo = new TicketScreenDisplaySendInfo();
        tsdInfo.setScreenID(screenID);
        tsdInfo.setTableData(tableData);
        return tsdInfo;
    }

    public List<Map<String, String>> ticketScreenDataGeneration(int screenID) {
        String stationName = configManager.getStationNameByScreenID(screenID);
        List<Map<String, String>> ticketDataList = new ArrayList<>();
        List<String> trainNumList = dataGetOrderByTime.TicketScreenDataOrderByDeparT(stationName);
        if (ListUtil.isEmpty(trainNumList)) {
            return ticketDataList;
        }
        trainNumList = ListUtil.removeDuplicateWithOrder(trainNumList);
        for (String trainNum : trainNumList) {
            Map<String, String> dateInfo = new HashMap<>();
            int days = ConfigHelper.getInt("TicketSeatDays");
            for (int i = 0; i < days; i++) {
                String date = DateTimeUtil.getDateByPlusDays(i);
                List<TicketScreenDataBean> dayTSBean = oracleDataSet.selectListWithCondition(
                        TicketScreenDataBean.class, "StationName=? AND TrainNum=? AND TicketDate=?", stationName, trainNum, date);

                String daySeatDateKey = "" + i + "day_date";
                int index = date.indexOf("-");
                String newDate = date.substring(index + 1, date.length());
                dateInfo.put(daySeatDateKey, "" + newDate);
                if (ListUtil.isEmpty(dayTSBean)) {
                    continue;
                }
                //车次信息默认值
                dateInfo.put("trainNum", "--");
                dateInfo.put("planArriveTime", "--");
                dateInfo.put("planDepartureTime", "--");
                dateInfo.put("startStation", "--");
                dateInfo.put("finalStation", "--");

                //遍历所有席别
                for (TicketScreenDataBean tsd : dayTSBean) {
                    if (!StringUtil.isEmpty(trainNum)) {
                        dateInfo.put("trainNum", trainNum);
                    }
                    if (!StringUtil.isEmpty(dayTSBean.get(0).getPlanArriveTime())) {
                        dateInfo.put("planArriveTime", dayTSBean.get(0).getPlanArriveTime());
                    }
                    if (!StringUtil.isEmpty(dayTSBean.get(0).getPlanDepartureTime())) {
                        dateInfo.put("planDepartureTime", dayTSBean.get(0).getPlanDepartureTime());
                    }
                    if (!StringUtil.isEmpty(dayTSBean.get(0).getStartStation())) {
                        dateInfo.put("startStation", dayTSBean.get(0).getStartStation());
                    }
                    if (!StringUtil.isEmpty(dayTSBean.get(0).getFinalStation())) {
                        dateInfo.put("finalStation", dayTSBean.get(0).getFinalStation());
                    }
                    String daySeatTypeKey = "" + i + "day_" + tsd.getSeatName();
                    dateInfo.put(daySeatTypeKey, "" + tsd.getTicketNum());
                }
            }
            ticketDataList.add(dateInfo);
        }
        return ticketDataList;
    }

}
