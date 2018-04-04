package com.crscd.passengerservice.plan.business.generation;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import com.crscd.passengerservice.plan.domainobject.DispatchStationPlan;
import com.crscd.passengerservice.plan.domainobject.GuideStationPlan;
import com.crscd.passengerservice.plan.domainobject.PassengerStationPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * 为了封装生成计划的时候对应于一个key
 * 的一组调度计划、客运计划、导向计划和广播计划列表
 * 以便于进行生成操作
 * Create Time: 2018/1/30 16:50
 * @version v1.00
 */
public class PlanGenerateCell {

    private String planKey;

    private DispatchStationPlan dispatchStationPlan;

    private PassengerStationPlan passengerStationPlan;

    private GuideStationPlan guideStationPlan;

    private List<BroadcastStationPlan> broadcastStationPlanList;

    private String reason;

    private boolean genResult;

    public PlanGenerateCell(String planKey) {
        this.planKey = planKey;
        broadcastStationPlanList = new ArrayList<>();
    }

    public PlanGenerateCell() {
        broadcastStationPlanList = new ArrayList<>();
    }

    public boolean isCellcompleted() {
        return null != dispatchStationPlan &&
                null != passengerStationPlan &&
                null != guideStationPlan &&
                ListUtil.isNotEmpty(broadcastStationPlanList);
    }

    public String getPlanKey() {
        return planKey;
    }

    public void setPlanKey(String planKey) {
        this.planKey = planKey;
    }

    public DispatchStationPlan getDispatchStationPlan() {
        return dispatchStationPlan;
    }

    public void setDispatchStationPlan(DispatchStationPlan dispatchStationPlan) {
        this.dispatchStationPlan = dispatchStationPlan;
    }

    public PassengerStationPlan getPassengerStationPlan() {
        return passengerStationPlan;
    }

    public void setPassengerStationPlan(PassengerStationPlan passengerStationPlan) {
        this.passengerStationPlan = passengerStationPlan;
    }

    public GuideStationPlan getGuideStationPlan() {
        return guideStationPlan;
    }

    public void setGuideStationPlan(GuideStationPlan guideStationPlan) {
        this.guideStationPlan = guideStationPlan;
    }

    public List<BroadcastStationPlan> getBroadcastStationPlanList() {
        return broadcastStationPlanList;
    }

    public void setBroadcastStationPlanList(List<BroadcastStationPlan> broadcastStationPlanList) {
        this.broadcastStationPlanList = broadcastStationPlanList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isGenResult() {
        return genResult;
    }

    public void setGenResult(boolean genResult) {
        this.genResult = genResult;
    }
}
