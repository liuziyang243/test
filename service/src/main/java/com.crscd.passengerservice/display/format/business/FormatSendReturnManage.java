package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.config.po.ScreenCtrlServerConfigBean;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.business.GetPlanDataElementInterface;
import com.crscd.passengerservice.display.format.domainobject.PlanDataElement;
import com.crscd.passengerservice.display.format.po.ScreenManageBean;
import com.crscd.passengerservice.display.format.serviceinterface.FormatSendInterface;
import com.crscd.passengerservice.display.format.util.DataGetOrderByTime;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.FormatSendReturnInfo;
import com.crscd.passengerservice.display.util.ScreenInfoUtil;
import com.crscd.passengerservice.multimedia.serviceinterface.PlayListManagerInterface;
import com.crscd.passengerservice.ticket.bussiness.TicketDataForDisplayGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/12/13.
 */
public class FormatSendReturnManage {
    private DataSet oracleDataSet;
    private TicketDataForDisplayGeneration ticketDataForDisplayGeneration;
    private DataGetOrderByTime dataGetOrderByTime;

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setTicketDataForDisplayGeneration(TicketDataForDisplayGeneration ticketDataForDisplayGeneration) {
        this.ticketDataForDisplayGeneration = ticketDataForDisplayGeneration;
    }

    public void setDataGetOrderByTime(DataGetOrderByTime dataGetOrderByTime) {
        this.dataGetOrderByTime = dataGetOrderByTime;
    }

    /**
     * 版式缺失
     *
     * @param formatSendReturnInfo
     */
    public void FormatMissing(FormatSendReturnInfo formatSendReturnInfo) {
        if (null == formatSendReturnInfo) {
            return;
        }
        int screenId = ScreenInfoUtil.getScreenIdByIp(formatSendReturnInfo.getIp());
        ScreenManageBean screenManageBean = oracleDataSet.select(ScreenManageBean.class, "screenID = ?", screenId);
        screenManageBean.setFormatSendStatus("0");
        oracleDataSet.update(screenManageBean);

        //获取屏幕数据
        List<Map<String, String>> tableData;
        ScreenConfig screenConfigBean = oracleDataSet.select(ScreenConfig.class, "ScreenID=?", screenId);
        ScreenTypeEnum screenType = screenConfigBean.getScreenType();
        GetPlanDataElementInterface getPlanDataElementInterface = ContextHelper.getPlanDataElementInterface();
        if (screenType.equals(ScreenTypeEnum.TICKET_INFO_SCREEN)) {
            tableData = ticketDataForDisplayGeneration.ticketScreenDataGeneration(screenId);
        } else {
            List<PlanDataElement> planDataList = getPlanDataElementInterface.getTargetScreenDisplayDat(screenId);
            if (screenType.equals(ScreenTypeEnum.EXIT_STATION_SCREEN)) {
                tableData = dataGetOrderByTime.generatePlanTableDataMapArrive(planDataList);
            } else {
                tableData = dataGetOrderByTime.generatePlanTableDataMapDeparture(planDataList);
            }
        }

        FormatSendInterface formatSendInterface = ContextHelper.getFormatSendInterface();
        ArrayList<HashMap<String, String>> tableDataList = new ArrayList<>();
        for (Map<String, String> map : tableData) {
            tableDataList.add(new HashMap<>(map));
        }
        formatSendInterface.screenMessageDisplay(screenId, formatSendReturnInfo.getId(), tableDataList);
    }

    /**
     * 播放列表缺失
     *
     * @param formatSendReturnInfo
     */
    public void PlayListMissing(FormatSendReturnInfo formatSendReturnInfo) {
        //需要下发的播放列表编号
        List<String> playListIds = StringUtil.stringToList(formatSendReturnInfo.getId(), ",");
        //需要下发播放列表的站名
        String screenServerIp = formatSendReturnInfo.getIp();
        ScreenCtrlServerConfigBean serverConfigBean = oracleDataSet.select(ScreenCtrlServerConfigBean.class, "ip = ?", screenServerIp);
        String stationName = serverConfigBean.getStationName();
        PlayListManagerInterface playListManagerInterface = ContextHelper.getPlaylistManager();
        playListManagerInterface.playListSendSingleStation(stationName, playListIds);
    }
}
