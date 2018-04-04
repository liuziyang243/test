package com.crscd.passengerservice.ticket.bussiness;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.business.innerinterface.BasicPlanInterface;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.ticket.domainobject.PassengerFlowInfo;
import com.crscd.passengerservice.ticket.domainobject.TicketCheckInfo;
import com.crscd.passengerservice.ticket.domainobject.TrainTicketInfo;
import com.crscd.passengerservice.ticket.po.PassengerFlowInfoBean;
import com.crscd.passengerservice.ticket.po.TicketScreenDataBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/8/23.
 */
public class StructTransformer {
    //数据库
    private DataSet oracleDataSet;
    private ConfigManager config;
    private BasicPlanInterface bpi;

    public StructTransformer() {
    }

    public void setBpi(BasicPlanInterface bpi) {
        this.bpi = bpi;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setConfig(ConfigManager config) {
        this.config = config;
    }

    public TicketScreenDataBean trainTicketInfoToTicketScreenDataBean(TrainTicketInfo ticketInfo) {

        String stationName = config.getStationNameByCode(ticketInfo.getStationCode());
        BasicTrainStationInfo bts = bpi.getTrainStationInfo(ticketInfo.getTrainNum(), stationName);
        String startStation = bts.getStartStation();
        String finalStation = bts.getFinalStation();
        String planArriveTime = DateTimeFormatterUtil.convertTimeToStringNoSecond(bts.getPlanedArriveTime());
        String planDepartureTime = DateTimeFormatterUtil.convertTimeToStringNoSecond(bts.getPlanedDepartureTime());

        TicketScreenDataBean dataBean = new TicketScreenDataBean();
        dataBean.setTrainNum(ticketInfo.getTrainNum());
        dataBean.setStationName(config.getStationNameByCode(ticketInfo.getStationCode()));
        dataBean.setSeatName(ticketInfo.getSeatName());
        dataBean.setTicketNum(ticketInfo.getTicketNum());
        dataBean.setStartStation(startStation);
        dataBean.setFinalStation(finalStation);
        dataBean.setPlanArriveTime(planArriveTime);
        dataBean.setPlanDepartureTime(planDepartureTime);
        dataBean.setTicketDate(ticketInfo.getDate());

        return dataBean;
    }

    public Map<String, TrainTicketInfo> ticketListToMap(List<TrainTicketInfo> ticketInfoList) {
        Map<String, TrainTicketInfo> ticketInfoMap = new HashMap<>();
        for (TrainTicketInfo t : ticketInfoList) {
            String key = t.getTrainNum() + t.getStationCode() + t.getDate() + t.getSeatName();
            ticketInfoMap.put(key, t);
        }
        return ticketInfoMap;
    }

    public Map<String, TicketScreenDataBean> ticketListToMapBean(List<TicketScreenDataBean> beanList) {
        Map<String, TicketScreenDataBean> beanMap = new HashMap<>();
        for (TicketScreenDataBean b : beanList) {
            String key = b.getTrainNum() + config.getStationCodeByName(b.getStationName()) + b.getTicketDate() + b.getSeatName();
            beanMap.put(key, b);
        }
        return beanMap;
    }

    public PassengerFlowInfoBean passengerFlowInfoToBean(PassengerFlowInfo passengerFlowInfo) {
        PassengerFlowInfoBean bean = new PassengerFlowInfoBean();
        bean.setTrainNum(passengerFlowInfo.getTrainNum());
        bean.setStationName(config.getStationNameByCode(passengerFlowInfo.getStationCode()));
        bean.setTicketDate(passengerFlowInfo.getDate());
        bean.setPassengerGetOn(passengerFlowInfo.getPassengerGetOn());
        bean.setPassengerGetOff(passengerFlowInfo.getPassengerGetOff());
        bean.setUpdateTime(passengerFlowInfo.getUpdateTime());
        return bean;
    }

    public Map<String, PassengerFlowInfo> passengerFlowListToMap(List<PassengerFlowInfo> infos) {
        Map<String, PassengerFlowInfo> infoMap = new HashMap<>();
        for (PassengerFlowInfo p : infos) {
            String key = p.getTrainNum() + p.getStationCode() + p.getDate();
            infoMap.put(key, p);
        }
        return infoMap;
    }

    public Map<String, PassengerFlowInfoBean> passengerFlowListToMapBean(List<PassengerFlowInfoBean> beans) {
        Map<String, PassengerFlowInfoBean> beanMap = new HashMap<>();
        for (PassengerFlowInfoBean b : beans) {
            String key = b.getTrainNum() + b.getStationName() + b.getTicketDate();
            beanMap.put(key, b);
        }
        return beanMap;
    }

    public Map<String, TicketCheckInfo> ticketCheckInfoListToMap(List<TicketCheckInfo> infoList) {
        Map<String, TicketCheckInfo> ticketCheckInfoMap = new HashMap<>();
        for (TicketCheckInfo t : infoList) {
            ticketCheckInfoMap.put(t.getUniqueCheckPlanKey(), t);
        }
        return ticketCheckInfoMap;
    }
}
