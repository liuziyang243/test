package com.crscd.passengerservice.plan.business.deletion;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.plan.business.innerinterface.DeletePeriodPlanInterface;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.plan.po.BroadcastStationPlanBean;
import com.crscd.passengerservice.plan.po.DispatchStationPlanBean;
import com.crscd.passengerservice.plan.po.GuideStationPlanBean;
import com.crscd.passengerservice.plan.po.PassengerStationPlanBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * <p>
 * Date: 2017/8/31
 * Time: 9:42
 * TODO：该类中过多的直接操作数据库的方法，应优化写到manager中
 */
public class PlanDeleter extends AbstractPlanDeleter implements DeletePeriodPlanInterface {
    public PlanDeleter(DataSet dataSet) {
        super(dataSet);
    }

    /**
     * TODO: 注意多用户并行操作问题，需要加锁，或者统一内部操作，外部只输入指令到队列中
     *
     * @param trainNumList
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public HashMap<String, String> delPlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {
        HashMap<String, String> result = new HashMap<>();
        Map<String, List<String>> broadcastPlanKeyListMap = new HashMap<>();
        // 首先构造需要操作的计划Key列表，该key是调度、客运、导向计划的唯一key
        // 但是对于广播计划，该key对应一组广播planKey
        List<String> keyList = new ArrayList<>();
        List<String> dateList = DateTimeUtil.getDateList(startDate, endDate);
        for (String trainNum : trainNumList) {
            for (String date : dateList) {
                String key = new KeyBase(trainNum, date, stationName).toString();
                keyList.add(key);
            }
        }

        // 首先从内存和调度器中删除广播计划，因为在该过程中还会用到数据库中的数据
        for (String key : keyList) {
            // 先模糊搜索出广播计划
            List<String> broadcastKeyList = getBroadcastKeyList(key);
            broadcastPlanKeyListMap.put(key, broadcastKeyList);
            for (String k : broadcastKeyList) {
                // 从调度器删除
                deleteBroadcastPlanFromScheduler(k);
                // 从内存中删除
                deletePlansInMem(k);
            }
        }

        // 从数据库删除计划
        boolean[] dispatchDelResult = deletePlans(DispatchStationPlanBean.class, keyList);
        boolean[] passengerDelResult = deletePlans(PassengerStationPlanBean.class, keyList);
        boolean[] guideDelResult = deletePlans(GuideStationPlanBean.class, keyList);
        boolean[] broadcastDelResult = deleteBroadcastPlans(broadcastPlanKeyListMap);

        // 处理数据库删除结果
        int index = 0;
        for (String key : keyList) {
            StringBuilder reason = new StringBuilder();
            if (!dispatchDelResult[index]) {
                reason.append("Dispatch plan missing in the data base. ");
            }
            if (!passengerDelResult[index]) {
                reason.append("Passenger plan missing in the data base. ");
            }
            if (!guideDelResult[index]) {
                reason.append("Guide plan missing in the data base. ");
            }
            if (!broadcastDelResult[index]) {
                reason.append("Broadcast plan missing in the data base or appear some mistakes during deleting. ");
            }
            if (reason.length() == 0) {
                reason.append("Delete successful.");
            }
            result.put(key, reason.toString());
            index += 1;
        }
        return result;
    }

    /**
     * 根据广播计划key列表删除数据库中的广播计划
     *
     * @param KeyListMap
     * @return
     */
    private boolean[] deleteBroadcastPlans(Map<String, List<String>> KeyListMap) {
        boolean[] result = new boolean[KeyListMap.size()];
        int index = 0;
        for (Map.Entry<String, List<String>> entry : KeyListMap.entrySet()) {
            boolean[] tempResult = deletePlans(BroadcastStationPlanBean.class, entry.getValue());
            boolean flag = false;
            if (null != tempResult && tempResult.length > 0) {
                flag = true;
                for (boolean t : tempResult) {
                    flag = flag && t;
                }
            }
            result[index] = flag;
            index += 1;
        }
        return result;
    }

}
