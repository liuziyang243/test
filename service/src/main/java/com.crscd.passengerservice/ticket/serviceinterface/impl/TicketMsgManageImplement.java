package com.crscd.passengerservice.ticket.serviceinterface.impl;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.number.RandomUtil;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.format.serviceinterface.FormatSendInterface;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.notice.serviceinterface.TicketNoticeInterface;
import com.crscd.passengerservice.ticket.bussiness.StructTransformer;
import com.crscd.passengerservice.ticket.bussiness.TicketDataForDisplayGeneration;
import com.crscd.passengerservice.ticket.domainobject.*;
import com.crscd.passengerservice.ticket.dto.TicketCheckNoticeData;
import com.crscd.passengerservice.ticket.po.PassengerFlowInfoBean;
import com.crscd.passengerservice.ticket.po.TicketScreenDataBean;
import com.crscd.passengerservice.ticket.serviceinterface.TicketMsgManageInterface;
import com.crscd.passengerservice.warning.business.MonitoringInterface;
import com.crscd.passengerservice.warning.business.TicketMonitorImpl;

import java.util.*;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public class TicketMsgManageImplement implements TicketMsgManageInterface {
    //完整检票信息缓存
    private static Map<String, TicketCheckInfo> allTicketCheckInfos = new Hashtable<>();
    //余票信息更新时间
    private String trainTicketMsgUpdateTime = "--";
    //检票信息更新时间
    private String ticketCheckMsgUpdateTime = "--";
    //客流量信息更新时间
    private String passengerFlowMsgUpdateTime = "--";
    //数据库
    private DataSet oracleDataSet;
    //ConfigManager
    private ConfigManager config;
    //检票信息Notice接口
    private TicketNoticeInterface noticeInterface;
    //告警接口
    private MonitoringInterface monitoringInterface = new TicketMonitorImpl();

    private StructTransformer structTransformer;

    private TicketDataForDisplayGeneration ticketDataForDisplayGeneration;

    public void setTicketDataForDisplayGeneration(TicketDataForDisplayGeneration ticketDataForDisplayGeneration) {
        this.ticketDataForDisplayGeneration = ticketDataForDisplayGeneration;
    }

    public void setStructTransformer(StructTransformer structTransformer) {
        this.structTransformer = structTransformer;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setConfig(ConfigManager config) {
        this.config = config;
    }

    public void setNoticeInterface(TicketNoticeInterface noticeInterface) {
        this.noticeInterface = noticeInterface;
    }

    /**
     * 获取完整余票信息的处理：删除当前数据库中的余票信息，并插入新的完整余票信息
     *
     * @param rcvMsg
     */
    @Override
    public void dealWithTrainTicketMsgWhole(String rcvMsg) {
        if (StringUtil.isEmpty(rcvMsg)) {
            return;
        }
        if ("No data or timeout!".equals(rcvMsg)) {
            //客票外系统获取数据故障
            monitoringInterface.notifyAbnormal("Ticket externol system", "externolSystem");
            return;
        }
        List<TicketScreenDataBean> ticketScreenDataBeenList = new ArrayList<>();
        List<TrainTicketInfo> ticketInfoList = JsonUtil.jsonToList(rcvMsg, TrainTicketInfo.class);
        //票额信息中增加计划中的到发信息
        for (TrainTicketInfo t : ticketInfoList) {
            TicketScreenDataBean ticketScreenDataBean = structTransformer.trainTicketInfoToTicketScreenDataBean(t);
            ticketScreenDataBeenList.add(ticketScreenDataBean);
        }
        if (oracleDataSet.delete(TicketScreenDataBean.class) && oracleDataSet.insertList(ticketScreenDataBeenList)) {
            trainTicketMsgUpdateTime = ticketInfoList.get(0).getUpdateTime();
        }
        //票额信息上屏
        List<TicketScreenDisplaySendInfo> ticketDataList = ticketDataForDisplayGeneration.dataForDisplayGeneration();
        FormatSendInterface fsi = ContextHelper.getFormatSendInterface();
        for (TicketScreenDisplaySendInfo t : ticketDataList) {
            ArrayList<HashMap<String, String>> tableDataList = new ArrayList<>();
            for (Map<String, String> map : t.getTableData()) {
                tableDataList.add(new HashMap<>(map));
            }
            fsi.ticketScreenFormatSend(t.getScreenID(), tableDataList);
        }
    }

    @Override
    public void dealWithTrainTicketMsgChange(String rcvMsg) {
        if (StringUtil.isEmpty(rcvMsg)) {
            return;
        }
        if ("No data or timeout!".equals(rcvMsg)) {
            //客票外系统获取数据故障
            monitoringInterface.notifyAbnormal("Ticket externol system", "externolSystem");
            return;
        }
        List<TrainTicketInfo> ticketInfoList = JsonUtil.jsonToList(rcvMsg, TrainTicketInfo.class);
        List<TicketScreenDataBean> dataBeanList = oracleDataSet.selectList(TicketScreenDataBean.class);
        Map<String, TrainTicketInfo> infoMap = structTransformer.ticketListToMap(ticketInfoList);
        Map<String, TicketScreenDataBean> beanMap = structTransformer.ticketListToMapBean(dataBeanList);
        for (Map.Entry<String, TrainTicketInfo> entry : infoMap.entrySet()) {
            TicketScreenDataBean bean = structTransformer.trainTicketInfoToTicketScreenDataBean(entry.getValue());
            if (beanMap.containsKey(entry.getKey())) {
                oracleDataSet.update(bean, "id");
            } else {
                oracleDataSet.insert(bean);
            }
        }
        trainTicketMsgUpdateTime = ticketInfoList.get(0).getUpdateTime();
        //票额信息上屏
        List<TicketScreenDisplaySendInfo> ticketDataList = ticketDataForDisplayGeneration.dataForDisplayGeneration();
        FormatSendInterface fsi = ContextHelper.getFormatSendInterface();
        for (TicketScreenDisplaySendInfo t : ticketDataList) {
            ArrayList<HashMap<String, String>> tableDataList = new ArrayList<>();
            for (Map<String, String> map : t.getTableData()) {
                tableDataList.add(new HashMap<>(map));
            }
            fsi.ticketScreenFormatSend(t.getScreenID(), tableDataList);
        }
    }

    @Override
    public void dealWithTicketCheckMsgWhole(String rcvMsg) {
        if (StringUtil.isEmpty(rcvMsg)) {
            return;
        }
        if ("No data or timeout!".equals(rcvMsg)) {
            //客票外系统获取数据故障
            monitoringInterface.notifyAbnormal("Ticket externol system", "externolSystem");
            return;
        }
        long noticeGroupID = RandomUtil.nextLong();
        List<TicketCheckInfo> ticketCheckInfoList = JsonUtil.jsonToList(rcvMsg, TicketCheckInfo.class);
        Map<String, TicketCheckInfo> ticketCheckInfoMap = structTransformer.ticketCheckInfoListToMap(ticketCheckInfoList);
        for (Map.Entry<String, TicketCheckInfo> entry : ticketCheckInfoMap.entrySet()) {
            ticketCheckNoticeGeneration(noticeGroupID, entry.getValue());
        }
        allTicketCheckInfos.clear();
        allTicketCheckInfos.putAll(ticketCheckInfoMap);
        ticketCheckMsgUpdateTime = DateTimeUtil.getCurrentDatetimeString();
    }

    @Override
    public void dealWithTicketCheckMsgChange(String rcvMsg) {
        if (StringUtil.isEmpty(rcvMsg)) {
            return;
        }
        if ("No data or timeout!".equals(rcvMsg)) {
            //客票外系统获取数据故障
            monitoringInterface.notifyAbnormal("Ticket externol system", "externolSystem");
            return;
        }
        long noticeGroupID = RandomUtil.nextLong();
        List<TicketCheckInfo> ticketCheckInfoList = JsonUtil.jsonToList(rcvMsg, TicketCheckInfo.class);
        Map<String, TicketCheckInfo> ticketCheckInfoMap = structTransformer.ticketCheckInfoListToMap(ticketCheckInfoList);
        for (Map.Entry<String, TicketCheckInfo> entry : ticketCheckInfoMap.entrySet()) {
            if (ticketCheckNoticeGeneration(noticeGroupID, entry.getValue())) {
                allTicketCheckInfos.put(entry.getKey(), entry.getValue());
            }
        }
        ticketCheckMsgUpdateTime = DateTimeUtil.getCurrentDatetimeString();
    }

    @Override
    public void dealWithPassengerFlowMsgWhole(String rcvMsg) {
        if (StringUtil.isEmpty(rcvMsg)) {
            return;
        }
        if ("No data or timeout!".equals(rcvMsg)) {
            //客票外系统获取数据故障
            monitoringInterface.notifyAbnormal("Ticket externol system", "externolSystem");
            return;
        }
        List<PassengerFlowInfo> passengerFlowInfoList = JsonUtil.jsonToList(rcvMsg, PassengerFlowInfo.class);
        List<PassengerFlowInfoBean> beanList = new ArrayList<>();
        for (PassengerFlowInfo p : passengerFlowInfoList) {
            PassengerFlowInfoBean bean = structTransformer.passengerFlowInfoToBean(p);
            beanList.add(bean);
        }
        if (oracleDataSet.delete(PassengerFlowInfoBean.class) && oracleDataSet.insertList(beanList)) {
            trainTicketMsgUpdateTime = passengerFlowInfoList.get(0).getUpdateTime();
        }
    }

    @Override
    public void dealWithPassengerFlowMsgChange(String rcvMsg) {
        if (StringUtil.isEmpty(rcvMsg)) {
            return;
        }
        if ("No data or timeout!".equals(rcvMsg)) {
            //客票外系统获取数据故障
            monitoringInterface.notifyAbnormal("Ticket externol system", "externolSystem");
            return;
        }
        List<PassengerFlowInfo> passengerFlowInfoList = JsonUtil.jsonToList(rcvMsg, PassengerFlowInfo.class);
        List<PassengerFlowInfoBean> beanList = oracleDataSet.selectList(PassengerFlowInfoBean.class);
        Map<String, PassengerFlowInfo> infoMap = structTransformer.passengerFlowListToMap(passengerFlowInfoList);
        Map<String, PassengerFlowInfoBean> beanMap = structTransformer.passengerFlowListToMapBean(beanList);
        for (Map.Entry<String, PassengerFlowInfo> entry : infoMap.entrySet()) {
            PassengerFlowInfoBean bean = structTransformer.passengerFlowInfoToBean(entry.getValue());
            if (beanMap.containsKey(entry.getKey())) {
                oracleDataSet.update(bean, "id");
            } else {
                oracleDataSet.insert(bean);
            }
        }
        passengerFlowMsgUpdateTime = passengerFlowInfoList.get(0).getUpdateTime();
    }

    @Override
    public List<PassengerFlowInfo> getPassengerFlowInfo() {
        List<PassengerFlowInfoBean> beanList = oracleDataSet.selectList(PassengerFlowInfoBean.class);
        List<PassengerFlowInfo> passengerFlowInfoList = new ArrayList<>();
        for (PassengerFlowInfoBean p : beanList) {
            PassengerFlowInfo passengerFlowInfo = new PassengerFlowInfo();
            passengerFlowInfo.setTrainNum(p.getTrainNum());
            passengerFlowInfo.setStationCode(config.getStationCodeByName(p.getStationName()));
            passengerFlowInfo.setDate(p.getTicketDate());
            passengerFlowInfo.setPassengerGetOn(p.getPassengerGetOn());
            passengerFlowInfo.setPassengerGetOff(p.getPassengerGetOff());
            passengerFlowInfo.setUpdateTime(p.getUpdateTime());
            passengerFlowInfoList.add(passengerFlowInfo);
        }
        return passengerFlowInfoList;
    }

    private boolean ticketCheckNoticeGeneration(long noticeGroup, TicketCheckInfo ticketCheckInfo) {
        String key = ticketCheckInfo.getUniqueCheckPlanKey();
        if (allTicketCheckInfos.containsKey(key)) {
            if (!allTicketCheckInfos.get(key).equals(ticketCheckInfo)) {
                TicketCheckNoticeData ticketCheckNoticeData =
                        ticketCheckInfoChangeGeneration(allTicketCheckInfos.get(key), ticketCheckInfo);
                ticketCheckNoticeData.setNoticeGroup(noticeGroup);
                noticeInterface.genTicketNotice(ticketCheckNoticeData);
                return true;
            }
        } else {
            TicketCheckNoticeData ticketCheckNoticeData =
                    ticketCheckInfoChangeGeneration(allTicketCheckInfos.get(key), ticketCheckInfo);
            ticketCheckNoticeData.setNoticeGroup(noticeGroup);
            noticeInterface.genTicketNotice(ticketCheckNoticeData);
            return true;
        }

        return false;
    }

    private TicketCheckNoticeData ticketCheckInfoChangeGeneration(TicketCheckInfo oldInfo, TicketCheckInfo newInfo) {
        TicketCheckNoticeData noticeData = new TicketCheckNoticeData();
        List<String> aboardCheckPort = new ArrayList<>();
        List<String> exitCheckPort = new ArrayList<>();
        HashMap<NoticeModifyEnum, String> modifiedDatas = new HashMap<>();
        Map<Integer, TicketBarrierInfo> newEntranceBarrierMap = newInfo.getTicketEntranceBarrierInfoMap();
        Map<Integer, TicketBarrierInfo> newDepartureBarrierMap = newInfo.getTicketDepartureBarrierInfoMap();
        noticeData.setTrainNum(newInfo.getTrainNum());
        noticeData.setStationName(config.getStationNameByCode(newInfo.getStationCode()));
        noticeData.setPlanDate(newInfo.getDepartureDate());

        if (oldInfo == null) {
            modifiedDatas.put(NoticeModifyEnum.CHECK_STATUS, newInfo.getCheckStatus());
            modifiedDatas.put(NoticeModifyEnum.START_CHECK_TIME, newInfo.getCheckTime());
            modifiedDatas.put(NoticeModifyEnum.STOP_CHECK_TIME, newInfo.getStopCheckTime());
            for (Map.Entry<Integer, TicketBarrierInfo> entry : newEntranceBarrierMap.entrySet()) {
                aboardCheckPort.add(config.getTicketBarrierNameByBarrierNum(entry.getKey()));
            }
            for (Map.Entry<Integer, TicketBarrierInfo> entry : newDepartureBarrierMap.entrySet()) {
                exitCheckPort.add(config.getTicketBarrierNameByBarrierNum(entry.getKey()));
            }
        } else {
            if (!oldInfo.getCheckStatus().equals(newInfo.getCheckStatus())) {
                modifiedDatas.put(NoticeModifyEnum.CHECK_STATUS, newInfo.getCheckStatus());
            }
            if (!oldInfo.getCheckTime().equals(newInfo.getCheckTime())) {
                modifiedDatas.put(NoticeModifyEnum.START_CHECK_TIME, newInfo.getCheckTime());
            }
            if (!oldInfo.getStopCheckTime().equals(newInfo.getStopCheckTime())) {
                modifiedDatas.put(NoticeModifyEnum.STOP_CHECK_TIME, newInfo.getStopCheckTime());
            }

            Map<Integer, TicketBarrierInfo> oldEntranceBarrierMap = oldInfo.getTicketEntranceBarrierInfoMap();
            if (!oldEntranceBarrierMap.equals(newEntranceBarrierMap)) {
                for (Map.Entry<Integer, TicketBarrierInfo> entry : newEntranceBarrierMap.entrySet()) {
                    aboardCheckPort.add(config.getTicketBarrierNameByBarrierNum(entry.getKey()));
                }
            }
            Map<Integer, TicketBarrierInfo> oldDepartureBarrierMap = oldInfo.getTicketDepartureBarrierInfoMap();
            if (!oldDepartureBarrierMap.equals(newDepartureBarrierMap)) {
                for (Map.Entry<Integer, TicketBarrierInfo> entry : newDepartureBarrierMap.entrySet()) {
                    exitCheckPort.add(config.getTicketBarrierNameByBarrierNum(entry.getKey()));
                }
            }
        }

        if (!ListUtil.isEmpty(aboardCheckPort)) {
            String aboardCheckPortString = JsonUtil.toJSON(aboardCheckPort);
            modifiedDatas.put(NoticeModifyEnum.ABOARD_CHECK_PORT, aboardCheckPortString);
        }

        if (!ListUtil.isEmpty(exitCheckPort)) {
            String exitCheckPortString = JsonUtil.toJSON(exitCheckPort);
            modifiedDatas.put(NoticeModifyEnum.EXIT_CHECK_PORT, exitCheckPortString);
        }

        noticeData.setModifiedDataMap(modifiedDatas);
        return noticeData;
    }
}
