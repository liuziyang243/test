package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.domainobject.StationInfo;
import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.plan.po.BasicPlanBean;
import com.crscd.passengerservice.plan.po.BasicTrainStationInfoBean;
import com.crscd.passengerservice.plan.util.PlanHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 8:17
 */
public class BasicPlan extends BasePlan {
    /* 列车车次号 */
    private String trainNum;
    /* 列车车次号，暂时备用 */
    private String uniqueTrainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private StationInfo startStation;
    /* 终到站 */
    private StationInfo finalStation;
    /* 有效期起止 */
    private LocalDate validPeriodStart;
    private LocalDate validPeriodEnd;
    /* 停开起止 */
    private LocalDate trainSuspendStart;
    private LocalDate trainSuspendEnd;
    /* 车站列表 key: stationName */
    private Map<String, BasicTrainStationInfo> stationInfoList;

    /**
     * 从前台创建一趟车的列车时刻表
     * 前台是分步生成列车计划的，即首先生成BasicPlan中不带车站列表信息的部分
     * 然后逐个添加车站信息
     */
    public BasicPlan(BasicPlanDTO dto, ConfigManager manager) {
        super();
        this.trainNum = dto.getTrainNum();
        this.uniqueTrainNum = trainNum;
        this.trainType = dto.getTrainType();

        this.startStation = manager.getStationInfoByStationName(dto.getStartStation());
        this.finalStation = manager.getStationInfoByStationName(dto.getFinalStation());
        this.validPeriodStart = DateTimeFormatterUtil.convertStringToDate(dto.getValidPeriodStart());
        this.validPeriodEnd = DateTimeFormatterUtil.convertStringToDate(dto.getValidPeriodEnd());
        this.stationInfoList = new ConcurrentHashMap<>();
    }

    /**
     * 从数据库读取po生成列车时刻表
     *
     * @param bean
     * @param infoList
     * @param manager
     */
    public BasicPlan(BasicPlanBean bean, List<BasicTrainStationInfoBean> infoList, ConfigManager manager) {
        this.id = bean.getId();
        this.trainNum = bean.getTrainNum();
        this.uniqueTrainNum = trainNum;
        this.trainType = bean.getTrainType();
        this.setGenerateTimestamp(bean.getGenerateTimestamp());
        this.startStation = manager.getStationInfoByStationName(bean.getStartStation());
        this.finalStation = manager.getStationInfoByStationName(bean.getFinalStation());
        this.validPeriodStart = DateTimeFormatterUtil.convertStringToDate(bean.getValidPeriodStart());
        this.validPeriodEnd = DateTimeFormatterUtil.convertStringToDate(bean.getValidPeriodEnd());
        this.trainSuspendStart = DateTimeFormatterUtil.convertStringToDate(bean.getTrainSuspendStart());
        this.trainSuspendEnd = DateTimeFormatterUtil.convertStringToDate(bean.getTrainSuspendEnd());
        this.stationInfoList = new ConcurrentHashMap<>();
        if (!ListUtil.isEmpty(infoList)) {
            for (BasicTrainStationInfoBean infoBean : infoList) {
                stationInfoList.put(infoBean.getStationName(), new BasicTrainStationInfo(infoBean, manager));
            }
        }
    }

    /**
     * 从ctc赋值到basicPlan
     */
    public BasicPlan(BasicTrainDetail basicTrainDetail, ConfigManager manager) {
        super();
        this.trainNum = basicTrainDetail.getTrainNum();
        this.uniqueTrainNum = this.trainNum;
        this.trainType = basicTrainDetail.getTrainType();
        this.startStation = manager.getStationInfoByStationName(basicTrainDetail.getStartStation());
        this.finalStation = manager.getStationInfoByStationName(basicTrainDetail.getFinalStation());
        this.stationInfoList = new ConcurrentHashMap<>();

    }

    /************* 车站信息内存对象的增删改查 **************/
    public boolean containSpecifiedTrainStation(String stationName) {
        return stationInfoList.containsKey(stationName);
    }

    public BasicTrainStationInfo getSpecifiedTrainStationInfo(String stationName) {
        if (null == stationName) {
            return null;
        }
        return stationInfoList.get(stationName);
    }

    public List<BasicTrainStationInfo> getStationList() {
        List<BasicTrainStationInfo> infoList = new ArrayList<>();
        for (Map.Entry<String, BasicTrainStationInfo> entry : stationInfoList.entrySet()
                ) {
            infoList.add(entry.getValue());
        }
        return infoList;
    }

    public void addStation(BasicTrainStationInfo info) {
        if (!stationInfoList.containsKey(info.getStationInfo().getStationName())) {
            stationInfoList.put(info.getStationInfo().getStationName(), info);
        }
    }

    public void removeStation(String stationName) {
        if (stationInfoList.containsKey(stationName)) {
            stationInfoList.remove(stationName);
        }
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getUniqueTrainNum() {
        return uniqueTrainNum;
    }

    public void setUniqueTrainNum(String uniqueTrainNum) {
        this.uniqueTrainNum = uniqueTrainNum;
    }

    public TrainTypeEnum getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainTypeEnum trainType) {
        this.trainType = trainType;
    }

    public TrainDirectionEnum getTrainDirection() {
        return PlanHelper.getTrainDirection(this.startStation.getStationName(), this.finalStation.getStationName());
    }

    public LocalDate getValidPeriodStart() {
        return validPeriodStart;
    }

    public void setValidPeriodStart(LocalDate validPeriodStart) {
        this.validPeriodStart = validPeriodStart;
    }

    public LocalDate getValidPeriodEnd() {
        return validPeriodEnd;
    }

    public void setValidPeriodEnd(LocalDate validPeriodEnd) {
        this.validPeriodEnd = validPeriodEnd;
    }

    public LocalDate getTrainSuspendStart() {
        return trainSuspendStart;
    }

    public void setTrainSuspendStart(LocalDate trainSuspendStart) {
        this.trainSuspendStart = trainSuspendStart;
    }

    public LocalDate getTrainSuspendEnd() {
        return trainSuspendEnd;
    }

    public void setTrainSuspendEnd(LocalDate trainSuspendEnd) {
        this.trainSuspendEnd = trainSuspendEnd;
    }

    public StationInfo getStartStation() {
        return startStation;
    }

    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }

    public StationInfo getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(StationInfo finalStation) {
        this.finalStation = finalStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicPlan plan = (BasicPlan) o;

        if (!getTrainNum().equals(plan.getTrainNum())) {
            return false;
        }
        if (getTrainType() != plan.getTrainType()) {
            return false;
        }
        if (!getStartStation().equals(plan.getStartStation())) {
            return false;
        }
        if (!getFinalStation().equals(plan.getFinalStation())) {
            return false;
        }
        return stationInfoList.equals(plan.stationInfoList);
    }

    @Override
    public int hashCode() {
        int result = getTrainNum().hashCode();
        result = 31 * result + getTrainType().hashCode();
        result = 31 * result + getStartStation().hashCode();
        result = 31 * result + getFinalStation().hashCode();
        result = 31 * result + stationInfoList.hashCode();
        return result;
    }

    //比较basicPlan和basicTrainDetail是否相同
    public boolean isSameWithCtcTrainInfo(BasicTrainDetail basicTrainDetail) {
        if (!basicTrainDetail.getTrainNum().equals(this.trainNum)) {
            return false;
        }
        if (!basicTrainDetail.getTrainType().equals(this.trainType)) {
            return false;
        }
        if (!basicTrainDetail.getStartStation().equals(this.startStation.getStationName())) {
            return false;
        }
        return basicTrainDetail.getFinalStation().equals(this.finalStation.getStationName());
    }
}
