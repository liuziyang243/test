package com.crscd.passengerservice.plan.business.generation;

import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.broadcast.template.business.BroadcastTemplateManager;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplate;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplateGroup;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.dao.*;
import com.crscd.passengerservice.plan.domainobject.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/15 14:54
 * @version v1.00
 */
public class PlanGenerateInMem {
    private static final Logger logger = LoggerFactory.getLogger(PlanGenerator.class);

    private OrganizeTemplateDAO organizeTemplateDAO;
    private BroadcastTemplateManager broadcastTemplateManager;
    private ConfigManager manager;
    private BasicPlanDAO basicPlanDAO;
    private DispatchPlanDAO dispatchPlanDAO;
    private PassengerPlanDAO passengerPlanDAO;
    private GuidePlanDAO guidePlanDAO;
    private BroadcastPlanDAO broadcastPlanDAO;
    private List<PlanGenerateCell> cellList;

    public PlanGenerateInMem() {
        this.cellList = new CopyOnWriteArrayList<>();
    }

    public void setManager(ConfigManager manager) {
        this.manager = manager;
    }

    public void setBasicPlanDAO(BasicPlanDAO basicPlanDAO) {
        this.basicPlanDAO = basicPlanDAO;
    }

    public void setOrganizeTemplateDAO(OrganizeTemplateDAO organizeTemplateDAO) {
        this.organizeTemplateDAO = organizeTemplateDAO;
    }

    public void setBroadcastTemplateManager(BroadcastTemplateManager broadcastTemplateManager) {
        this.broadcastTemplateManager = broadcastTemplateManager;
    }

    public void setDispatchPlanDAO(DispatchPlanDAO dispatchPlanDAO) {
        this.dispatchPlanDAO = dispatchPlanDAO;
    }

    public void setPassengerPlanDAO(PassengerPlanDAO passengerPlanDAO) {
        this.passengerPlanDAO = passengerPlanDAO;
    }

    public void setGuidePlanDAO(GuidePlanDAO guidePlanDAO) {
        this.guidePlanDAO = guidePlanDAO;
    }

    public void setBroadcastPlanDAO(BroadcastPlanDAO broadcastPlanDAO) {
        this.broadcastPlanDAO = broadcastPlanDAO;
    }

    public List<PlanGenerateCell> getCellList() {
        return cellList;
    }

    /**
     * 清理缓存
     */
    void clearCachedPlan() {
        cellList.clear();
    }

    /**
     * 生成多趟列车多日的计划
     *
     * @param trainNumList
     * @param stationName
     * @param startDate
     * @param endDate
     */
    void generateMultiPeriodPlan(List<String> trainNumList, String stationName, String startDate, String endDate) {
        for (String trainNum : trainNumList) {
            generatePeriodPlan(trainNum, stationName, startDate, endDate);
        }
    }

    /**
     * 生成一趟列车的多日的计划
     *
     * @param trainNum
     * @param stationName
     * @param startDate
     * @param endDate
     */
    private void generatePeriodPlan(String trainNum, String stationName, String startDate, String endDate) {
        List<String> dateList = DateTimeUtil.getDateList(startDate, endDate);
        for (String date : dateList) {
            generateDailyPlan(trainNum, stationName, date);
        }
    }

    /**
     * 生成一趟列车单日的计划
     *
     * @param trainNum
     * @param stationName
     * @param date
     */
    private void generateDailyPlan(String trainNum, String stationName, String date) {
        String failReason = null;
        BasicPlan basicPlan = basicPlanDAO.getBasicPlanByTrainNum(trainNum);
        String key = new KeyBase(trainNum, date, stationName).toString();
        PlanGenerateCell cell = new PlanGenerateCell(key);
        // 先判断数据库是否已经有存在计划，存在任意一条计划都无法继续生成计划
        if (dispatchPlanDAO.existPlan(key)
                || passengerPlanDAO.existPlan(key)
                || guidePlanDAO.existPlan(key)
                || broadcastPlanDAO.existPlan(key)) {
            failReason = "There has plans for" + trainNum + " at " + date + " in the data base already when generating plans!";
            cell.setGenResult(false);
            cell.setReason(failReason);
            logger.warn(failReason);
        } else {
            if (null != basicPlan) {
                OrganizeTemplate template = organizeTemplateDAO.getOrganizeTemplate(trainNum, stationName);
                if (null != template) {
                    cell.setDispatchStationPlan(generateDailyDispatchPlan(basicPlan, date, stationName));
                    cell.setPassengerStationPlan(generateDailyPassengerPlan(basicPlan, date, stationName, template));
                    cell.setGuideStationPlan(generateDailyGuidePlan(basicPlan, date, stationName, template));

                    BroadcastTemplateGroup templateGroup = broadcastTemplateManager.getBroadcastTemplateGroupByName(stationName, template.getBroadcastTemplateGroupName());
                    // 生成广播计划首先获取广播组,如果广播组不为空，则认为生成计划成功
                    if (null != templateGroup) {
                        List<BroadcastStationPlan> planList = new ArrayList<>();
                        for (BroadcastTemplate broadcastTemplate : templateGroup.getBroadcastTemplateList()) {
                            BroadcastStationPlan broadcastStationPlan = generateDailyBroadcastPlan(basicPlan, date, stationName, template, broadcastTemplate);
                            planList.add(broadcastStationPlan);
                        }
                        cell.setBroadcastStationPlanList(planList);
                        cell.setReason("Generate success");
                        cell.setGenResult(true);
                    }
                    // 如果广播组为空则无法整成相应的广播计划
                    else {
                        failReason = "Can't find broadcast template group " + template.getBroadcastTemplateGroupName() + " when generating plans for train-" + trainNum + " at station " + stationName;
                        cell.setReason("Generate dispatchPlan, passengerPlan and guidePlan success, and don't generate broadcastPlan. " + failReason);
                        cell.setGenResult(false);
                        logger.warn(failReason);
                    }
                } else {
                    failReason = "Can't find organize template when generating plans for train-" + trainNum + " at station " + stationName;
                    cell.setReason(failReason);
                    cell.setGenResult(false);
                    logger.warn(failReason);
                }
            } else {
                failReason = "Can't find train-" + trainNum + " in memory when generating plans!";
                cell.setReason(failReason);
                cell.setGenResult(false);
                logger.warn(failReason);
            }
        }
        // 将生成结果放入结果列表中
        cellList.add(cell);
    }

    /**
     * 生成一趟列车单日的调度计划
     *
     * @param basicPlan
     * @param date
     * @param stationName
     * @return
     */
    private DispatchStationPlan generateDailyDispatchPlan(BasicPlan basicPlan, String date, String stationName) {
        return new DispatchStationPlan(basicPlan, date, stationName, manager);
    }

    /**
     * 生成一趟列车单日的客运计划
     *
     * @param basicPlan
     * @param date
     * @param stationName
     * @param template
     * @return
     */
    private PassengerStationPlan generateDailyPassengerPlan(BasicPlan basicPlan, String date, String stationName, OrganizeTemplate template) {
        return new PassengerStationPlan(basicPlan, date, stationName, template, manager);
    }

    /**
     * 生成一趟列车单日的导向计划
     *
     * @param basicPlan
     * @param date
     * @param stationName
     * @param template
     * @return
     */
    private GuideStationPlan generateDailyGuidePlan(BasicPlan basicPlan, String date, String stationName, OrganizeTemplate template) {
        return new GuideStationPlan(basicPlan, date, stationName, template, manager);
    }

    /**
     * 生成一趟列车单日的广播计划
     *
     * @param basicPlan
     * @param date
     * @param stationName
     * @param template
     * @param broadcastTemplate
     * @return
     */
    private BroadcastStationPlan generateDailyBroadcastPlan(BasicPlan basicPlan, String date, String stationName, OrganizeTemplate template, BroadcastTemplate broadcastTemplate) {
        return new BroadcastStationPlan(basicPlan, date, stationName, template, broadcastTemplate, manager, broadcastTemplateManager);
    }
}

