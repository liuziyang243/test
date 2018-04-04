package com.crscd.passengerservice.plan.business;

import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.notice.domainobject.NoticeMessage;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.plan.business.innerinterface.PlanInterface;
import com.crscd.passengerservice.plan.domainobject.BaseTrainPlan;
import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;
import com.crscd.passengerservice.plan.pool.AbstractPlanDataPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/25
 * Time: 16:02
 */
public abstract class AbstractPlanManager<T extends BaseTrainPlan, M extends AbstractPlanDataPool<T>> implements PlanInterface<T> {

    private M planDataPool;

    public M getPlanDataPool() {
        return planDataPool;
    }

    public void setPlanDataPool(M planDataPool) {
        this.planDataPool = planDataPool;
    }

    public boolean existPlan(String planKey) {
        return planDataPool.existPlan(planKey);
    }

    @Override
    public T getPlan(String key) {
        return planDataPool.getPlan(key);
    }

    /**
     * 对车次号支持模糊查询
     * 允许车站名称和车次号为空
     * TODO:查询有一定的优化空间
     *
     * @param stationName
     * @param trainNum
     * @param startDate
     * @param endDate
     * @param fuzzyQueryFlag
     * @return
     */
    @Override
    public List<T> getPlanList(String stationName, String trainNum, String startDate, String endDate, boolean fuzzyQueryFlag) {
        List<T> planList = new ArrayList<>();
        if (startDate == null || endDate == null) {
            return planDataPool.getPlanListByMultiCondition(trainNum, stationName, startDate, endDate, fuzzyQueryFlag);
        } else {
            List<String> dateList = DateTimeUtil.getDateList(startDate, endDate);
            for (String date : dateList) {
                List<T> candidatePlanList = planDataPool.getDataListByStationAndDate(stationName, date);
                if (null == trainNum) {
                    planList.addAll(candidatePlanList);
                } else {
                    for (T plan : candidatePlanList) {
                        if (fuzzyQueryFlag) {
                            if (plan.getTrainNum().contains(trainNum)) {
                                planList.add(plan);
                            }
                        } else {
                            if (plan.getTrainNum().equals(trainNum)) {
                                planList.add(plan);
                            }
                        }
                    }
                }
            }
        }
        return planList;
    }

    @Override
    public List<T> getPlanList(String stationName, int pageNumber, int pageSize) {
        return planDataPool.getDao().selectPlanList(stationName, pageNumber, pageSize);
    }

    @Override
    public long getPlanCount() {
        return planDataPool.getDao().getPlanCount();
    }

    /**
     * 将前台返回的区域列表json串还原为list
     *
     * @param json
     * @return
     */
    List<String> getZoneListFromJson(String json) {
        if (StringUtil.isEmpty(json)) {
            return new ArrayList<>();
        } else {
            return JsonUtil.jsonToList(json, String.class);
        }
    }

    public abstract boolean modifyPlan(NoticeMessage notice);

    public abstract boolean checkModifyValid(NoticeMessage notice);

    LateEarlyReasonEnum getReason(SenderEnum sender) {
        switch (sender) {
            case CTC:
                return LateEarlyReasonEnum.getReason("Modify form ctc system");
            case TICKET:
                return LateEarlyReasonEnum.getReason("Modify form ticket system");
            case DISPATCH_PLAN:
                return LateEarlyReasonEnum.getReason("Modify form dispatch plan");
            case PASSENGER_PLAN:
                return LateEarlyReasonEnum.getReason("Modify form passenger plan");
            default:
                throw new IllegalArgumentException("Wrong input:" + sender);
        }
    }
}
