package com.crscd.passengerservice.plan.business.deletion;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.plan.business.innerinterface.DeletePeriodPlanInterface;
import com.crscd.passengerservice.plan.po.BroadcastStationPlanBean;
import com.crscd.passengerservice.plan.po.DispatchStationPlanBean;
import com.crscd.passengerservice.plan.po.GuideStationPlanBean;
import com.crscd.passengerservice.plan.po.PassengerStationPlanBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/2/1 8:52
 * @version v1.00
 */
public class PlanDeleterStream extends AbstractPlanDeleter implements DeletePeriodPlanInterface {
    public PlanDeleterStream(DataSet dataSet) {
        super(dataSet);
    }

    @Override
    public HashMap<String, String> delPlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {
        List<PlanDeleterCell> cellList = new ArrayList<>();

        List<String> dateList = DateTimeUtil.getDateList(startDate, endDate);
        for (String trainNum : trainNumList) {
            for (String date : dateList) {
                PlanDeleterCell cell = new PlanDeleterCell(trainNum, stationName, date);
                cellList.add(cell);
            }
        }

        Map<String, String> result = new ConcurrentHashMap<>();
        cellList.parallelStream().forEach(c -> delPlan(c, result));

        return new HashMap<>(result);
    }

    private void delPlan(PlanDeleterCell cell, Map<String, String> resultMap) {
        StringBuilder reason = new StringBuilder();
        String key = cell.getPlanKey();

        // 首先从内存和调度器中删除广播计划，因为在该过程中还会用到数据库中的数据
        // 先模糊搜索出广播计划
        List<String> broadcastPlanKeyList = getBroadcastKeyList(key);

        // 从调度器删除
        deleteBroadcastPlanFromScheduler(key);
        // 从内存中删除
        deletePlansInMem(key);

        // 从数据库删除
        boolean dispatchDelResult = deletePlan(DispatchStationPlanBean.class, key);
        boolean passengerDelResult = deletePlan(PassengerStationPlanBean.class, key);
        boolean guideDelResult = deletePlan(GuideStationPlanBean.class, key);
        boolean broadcastDelResult = deleteBroadcastPlan(broadcastPlanKeyList);

        if (!dispatchDelResult) {
            reason.append("Dispatch plan missing in the data base. ");
        }
        if (!passengerDelResult) {
            reason.append("Passenger plan missing in the data base. ");
        }
        if (!guideDelResult) {
            reason.append("Guide plan missing in the data base. ");
        }
        if (!broadcastDelResult) {
            reason.append("Broadcast plan missing in the data base or appear some mistakes during deleting. ");
        }
        if (reason.length() == 0) {
            reason.append("Delete successful.");
        }

        resultMap.put(key, reason.toString());
    }

    private boolean deletePlan(Class<?> clazz, String param) {
        String condition = "planKey=?";
        return dataSet.delete(clazz, condition, param);
    }

    private boolean deleteBroadcastPlan(List<String> keyList) {
        boolean[] tempResult = deletePlans(BroadcastStationPlanBean.class, keyList);
        boolean flag = false;
        if (null != tempResult && tempResult.length > 0) {
            flag = true;
            for (boolean t : tempResult) {
                flag = flag && t;
            }
        }
        return flag;
    }

}
