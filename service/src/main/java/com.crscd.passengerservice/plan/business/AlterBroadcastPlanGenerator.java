package com.crscd.passengerservice.plan.business;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.broadcast.drivers.enumtype.BroadcastStateEnum;
import com.crscd.passengerservice.broadcast.template.business.BroadcastTemplateManager;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplate;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplateGroup;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.notice.enumtype.NoticeModifyEnum;
import com.crscd.passengerservice.plan.domainobject.BroadcastKey;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.pool.BroadcastPlanDataPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Create date: 2017/9/17
 */
public class AlterBroadcastPlanGenerator {
    private static final Logger logger = LoggerFactory.getLogger(AlterBroadcastPlanGenerator.class);
    private BroadcastTemplateManager templateManager;

    public void setTemplateManager(BroadcastTemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    public void genAlternationBroadcastPlan(BroadcastStationPlan plan, Map<NoticeModifyEnum, String> modifyParam, BroadcastPlanDataPool planDataPool) {
        for (Map.Entry<NoticeModifyEnum, String> entry : modifyParam.entrySet()) {
            List<BroadcastStationPlan> planList = genAlternationBroadcastPlan(plan, entry);
            for (BroadcastStationPlan alterPlan : planList) {
                planDataPool.addPlanToMemAndDB(alterPlan);
            }
        }
    }

    /**
     * 获取全部到发广播计划
     *
     * @param plan
     * @param entry
     * @return
     */
    private List<BroadcastStationPlan> genAlternationBroadcastPlan(BroadcastStationPlan plan, Map.Entry<NoticeModifyEnum, String> entry) {
        String stationName = plan.getPresentStation().getStationName();
        // 变更广播模版组的名称是固定的，名称规则是notice变更类型加_CHANGE
        // 例如发车时间变更模版组名称为 ACTUAL_DEPARTURE_TIME_CHANGE
        BroadcastTemplateGroup templateGroup = templateManager.getBroadcastTemplateGroupByName(stationName, entry.getKey().toString() + "_CHANGE");
        List<BroadcastStationPlan> planList = new ArrayList<>();
        for (BroadcastTemplate template : templateGroup.getBroadcastTemplateList()) {
            BroadcastStationPlan alterPlan = copyNewBroadcastPlan(plan);
            if (null != alterPlan) {
                // 重新生成计划key
                BroadcastKey key = new BroadcastKey(plan.getTrainNum(), DateTimeFormatterUtil.convertDateToString(plan.getPlanDate()), stationName, BroadcastKindEnum.ALTERATION);
                alterPlan.setPlanKey(key.toString());
                alterPlan.setBroadcastPriorityLevel(template.getPriorityLevel());
                alterPlan.setBroadcastContentName(template.getBroadcastContent().getContentName());
                alterPlan.setBroadcastBaseTime(template.getBaseTime());
                alterPlan.setBroadcastTimeOffset(template.getTimeOffset());
                alterPlan.setBroadcastKind(template.getBroadcastKind());
                alterPlan.setBroadcastOperationMode(template.getOperationMode());
                alterPlan.setBroadcastState(BroadcastStateEnum.WAIT_EXECUTE);
                alterPlan.setBroadcastArea(templateManager.getBroadcastAreaList(template, alterPlan));
                planList.add(plan);
            }
        }
        return planList;
    }

    /**
     * 深拷贝一个广播计划作为变更广播计划的基础
     *
     * @param plan
     * @return
     */
    private BroadcastStationPlan copyNewBroadcastPlan(BroadcastStationPlan plan) {
        try {
            return (BroadcastStationPlan) plan.deepClone();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Deep copy broadcast plan wrong!", e);
        }
        return null;
    }
}
