package com.crscd.passengerservice.display.format.serviceinterface;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.display.business.GetPlanDataElementInterface;
import com.crscd.passengerservice.display.business.MakeGuidePlanOnScreen;
import com.crscd.passengerservice.display.format.business.DataXMLGeneration;
import com.crscd.passengerservice.display.format.business.FormatFileManager;
import com.crscd.passengerservice.display.format.business.TicketWinXmlGeneration;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;
import com.crscd.passengerservice.display.format.domainobject.PlanDataElement;
import com.crscd.passengerservice.display.format.domainobject.TicketWinScreenContent;
import com.crscd.passengerservice.display.format.po.ScreenManageBean;
import com.crscd.passengerservice.display.format.po.TicketWinScreenContentBean;
import com.crscd.passengerservice.display.format.util.DataGetOrderByTime;
import com.crscd.passengerservice.display.format.util.FormatSendHelper;
import com.crscd.passengerservice.ticket.bussiness.TicketDataForDisplayGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/9/5.
 */
public class FormatSendInterfaceImpl implements FormatSendInterface {
    //数据库
    private DataSet oracleDataSet;

    private TicketDataForDisplayGeneration ticketDataForDisplayGeneration;

    private TicketWinXmlGeneration ticketWinXmlGeneration;

    private DataGetOrderByTime dataGetOrderByTime;

    private DataXMLGeneration dataXMLGeneration;

    private GetPlanDataElementInterface getPlanDataElementInterface = new MakeGuidePlanOnScreen();

    private FormatFileManager formatFileManager;

    private FormatSendHelper formatSendHelper;

    public FormatSendInterfaceImpl() {
    }

    public void setFormatFileManager(FormatFileManager formatFileManager) {
        this.formatFileManager = formatFileManager;
    }

    public void setDataXMLGeneration(DataXMLGeneration dataXMLGeneration) {
        this.dataXMLGeneration = dataXMLGeneration;
    }

    public void setDataGetOrderByTime(DataGetOrderByTime dataGetOrderByTime) {
        this.dataGetOrderByTime = dataGetOrderByTime;
    }

    public void setFormatSendHelper(FormatSendHelper formatSendHelper) {
        this.formatSendHelper = formatSendHelper;
    }

    public void setTicketWinXmlGeneration(TicketWinXmlGeneration ticketWinXmlGeneration) {
        this.ticketWinXmlGeneration = ticketWinXmlGeneration;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setTicketDataForDisplayGeneration(TicketDataForDisplayGeneration ticketDataForDisplayGeneration) {
        this.ticketDataForDisplayGeneration = ticketDataForDisplayGeneration;
    }

    /**
     * 售票窗口屏版是下发（通知及火警模式）
     *
     * @param stationName
     * @param tscList     winNum或content为空时，需赋值为""
     * @param type        通知：Notice  火警：FAS
     * @return
     */
    @Override
    public HashMap<String, String> ticketWinScreenFormatSend(String stationName, List<TicketWinScreenContent> tscList, String type) {
        Map<String, String> result = new HashMap<>();
        String sendResult = "0";

        for (TicketWinScreenContent tsc : tscList) {
            int screenID = tsc.getScreenID();
            String formatData = ticketWinXmlGeneration.TicketWinScreenFormatGeneration(tsc, "Notice");
            if (StringUtil.isEmpty(formatData)) {
                sendResult = "0";
                result.put("" + screenID, sendResult);
                continue;
            }
            String formatID = "0000_" + tsc.getScreenID();
            String dataXml = dataXMLGeneration.TicketWinScreenDataXmlGeneration(formatID);
            boolean sendR = formatSendHelper.formatSender(formatID, formatData, screenID, dataXml);
            if (sendR) {
                TicketWinScreenContentBean tsdBean = new TicketWinScreenContentBean();
                tsdBean.setScreenID(screenID);
                tsdBean.setStationName(stationName);
                tsdBean.setWinNum(tsc.getWinNum());
                tsdBean.setContent(tsc.getContent());
                TicketWinScreenContentBean oldTsdBean = oracleDataSet.select(TicketWinScreenContentBean.class, "ScreenID=?", screenID);
                if (oldTsdBean != null) {
                    oracleDataSet.update(tsdBean, "ScreenID=?", screenID);
                } else {
                    oracleDataSet.insert(tsdBean);
                }
                sendResult = "1";
            }
            result.put("" + screenID, sendResult);
        }

        return (HashMap<String, String>) result;
    }

    @Override
    public boolean screenMessageDisplay(int screenID, String formatID, ArrayList<HashMap<String, String>> tableData) {
        FormatInfo formatInfo;
        formatInfo = formatFileManager.select(formatID);
        if (null == formatInfo) {
            return false;
        }
        String format = formatInfo.getFormatData();

        List<Map<String, String>> tableDataList = new ArrayList<>(tableData);

        String data = dataXMLGeneration.DataXmlGeneration(formatID, format, screenID, tableDataList);
        if (StringUtil.isEmpty(format) || StringUtil.isEmpty(data)) {
            return false;
        }
        return formatSendHelper.formatSender(formatID, format, screenID, data);
    }

    /**
     * 计划类版式自动下发接口
     *
     * @param screenID
     * @param planDataElements
     */
    @Override
    public void formatSend(int screenID, List<PlanDataElement> planDataElements) {
        String formatID;
        List<Map<String, String>> tableData;
        if (ListUtil.isEmpty(planDataElements)) {
            formatID = oracleDataSet.selectColumn(ScreenManageBean.class, "StandbyFormatNo", "ScreenID=?", screenID);
        } else {
            formatID = oracleDataSet.selectColumn(ScreenManageBean.class, "CurrentFormatNo", "ScreenID=?", screenID);
        }
        if (StringUtil.isEmpty(formatID)) {
            return;
        }
        //根据屏幕类型判断上屏数据的排序方式：出站屏按到点时间排序 其他屏幕按发点时间排序
        ScreenTypeEnum screenType = oracleDataSet.selectColumn(ScreenConfig.class, "ScreenType", "ScreenID=?", screenID);
        if (screenType.equals(ScreenTypeEnum.EXIT_STATION_SCREEN)) {
            tableData = dataGetOrderByTime.generatePlanTableDataMapArrive(planDataElements);
        } else {
            tableData = dataGetOrderByTime.generatePlanTableDataMapDeparture(planDataElements);
        }

        ArrayList<HashMap<String, String>> tableDataList = new ArrayList<>();
        for (Map<String, String> map : tableData) {
            tableDataList.add(new HashMap<>(map));
        }
        screenMessageDisplay(screenID, formatID, tableDataList);
    }

    /**
     * 版式手动下发接口
     *
     * @param screenID
     * @param formatType
     * @return
     */
    @Override
    public boolean realTimeFormatSend(int screenID, int formatType) {
        //获取版式编号
        String formatID = "";
        ScreenManageBean screenManageBean = oracleDataSet.select(ScreenManageBean.class, "ScreenID=?", screenID);
        if (screenManageBean == null) {
            return false;
        }
        //版式类型 1：当前版式 2：空闲版式 0：火灾版式
        if (formatType == 0) {
            formatID = screenManageBean.getFasFormatNo();
        } else if (formatType == 1) {
            formatID = screenManageBean.getCurrentFormatNo();
        } else {
            formatID = screenManageBean.getStandbyFormatNo();
        }

        //获取屏幕数据
        List<Map<String, String>> tableData;
        ScreenConfig screenConfigBean = oracleDataSet.select(ScreenConfig.class, "ScreenID=?", screenID);
        ScreenTypeEnum screenType = screenConfigBean.getScreenType();
        if (screenType.equals(ScreenTypeEnum.TICKET_INFO_SCREEN)) {
            tableData = ticketDataForDisplayGeneration.ticketScreenDataGeneration(screenID);
        } else {
            List<PlanDataElement> planDataList = getPlanDataElementInterface.getTargetScreenDisplayDat(screenID);
            if (screenType.equals(ScreenTypeEnum.EXIT_STATION_SCREEN)) {
                tableData = dataGetOrderByTime.generatePlanTableDataMapArrive(planDataList);
            } else {
                tableData = dataGetOrderByTime.generatePlanTableDataMapDeparture(planDataList);
            }
        }

        ArrayList<HashMap<String, String>> tableDataList = new ArrayList<>();
        for (Map<String, String> map : tableData) {
            tableDataList.add(new HashMap<>(map));
        }
        return screenMessageDisplay(screenID, formatID, tableDataList);
    }

    /**
     * 客票版式下发
     *
     * @param screenID
     * @param ticketData
     */
    @Override
    public void ticketScreenFormatSend(int screenID, ArrayList<HashMap<String, String>> ticketData) {
        String formatID;
        if (ListUtil.isEmpty(ticketData)) {
            formatID = oracleDataSet.selectColumn(ScreenManageBean.class, "StandbyFormatNo", "ScreenID=?", screenID);
        } else {
            formatID = oracleDataSet.selectColumn(ScreenManageBean.class, "CurrentFormatNo", "ScreenID=?", screenID);
        }
        if (StringUtil.isEmpty(formatID)) {
            return;
        }
        screenMessageDisplay(screenID, formatID, ticketData);
    }

}
