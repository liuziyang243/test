package com.crscd.passengerservice.plan.pool;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.dao.daointerface.PlanDAOInterface;
import com.crscd.passengerservice.plan.domainobject.BaseTrainPlan;
import com.crscd.passengerservice.plan.domainobject.KeyBase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 10:08
 * 包含了内存操作和数据库操作
 * TODO：需要考虑缓存穿透和缓存与数据库操作统一的问题
 */
public abstract class AbstractPlanDataPool<T extends BaseTrainPlan> extends AbstractDataPool<T> {

    private PlanDAOInterface<T> dao;

    public AbstractPlanDataPool(int durationInDays, PlanDAOInterface<T> dao) {
        super(durationInDays);
        this.dao = dao;
        refreshData();
    }

    public PlanDAOInterface<T> getDao() {
        return dao;
    }

    public void setDao(PlanDAOInterface<T> dao) {
        this.dao = dao;
    }

    public boolean existPlan(String planKey) {
        return dao.existPlan(planKey);
    }

    @Override
    Map<String, T> getOneDayPlan(LocalDate date) {
        List<T> planList = dao.selectPlanList(null, null, DateTimeFormatterUtil.convertDateToString(date));
        Map<String, T> planMap = new HashMap<>();
        for (T plan : planList) {
            planMap.put(plan.getPlanKey(), plan);
        }
        return planMap;
    }

    /**
     * 通过车次名称和列车计划日期过滤计划列表
     * 允许车次站名为空，这样就返回全部日期内的计划
     *
     * @param trainNum
     * @param date
     * @return
     */
    public List<T> getDataListByTrainNumAndDate(String trainNum, String date) {
        List<T> result = new ArrayList<>();
        if (containsInPool(date)) {
            List<T> planList = getDailyDataList(DateTimeFormatterUtil.convertStringToDate(date));
            if (null != trainNum) {
                for (T plan : planList) {
                    if (plan.getTrainNum().equals(trainNum)) {
                        result.add(plan);
                    }
                }
            } else {
                return planList;
            }
        } else {
            result = dao.selectPlanList(trainNum, null, date);
        }
        return result;
    }

    /**
     * 通过车站名称和列车计划日期过滤计划列表
     * 允许车站站名为空，这样就返回全部日期内的计划
     *
     * @param stationName
     * @param date
     * @return
     */
    public List<T> getDataListByStationAndDate(String stationName, String date) {
        List<T> result = new ArrayList<>();
        if (containsInPool(date)) {
            List<T> planList = getDailyDataList(DateTimeFormatterUtil.convertStringToDate(date));
            if (null != stationName) {
                for (T plan : planList) {
                    // 为了避免前台和后台接口传递站名时出现大小写问题，在比较的时候忽略字母大小写
                    if (plan.getPresentStation().getStationName().equalsIgnoreCase(stationName)) {
                        result.add(plan);
                    }
                }
            } else {
                return planList;
            }
        } else {
            result = dao.selectPlanList(null, stationName, date);
        }
        return result;
    }

    public List<T> getPlanListByMultiCondition(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        return dao.selectPlanList(trainNum, stationName, startDay, endDay, fuzzyFlag);
    }

    public T getPlan(String key) {
        KeyBase keyBase = new KeyBase(key);
        if (containsInPool(keyBase.getPlanDateString())) {
            return getData(key);
        } else {
            return dao.selectPlan(key);
        }
    }

    public boolean updatePlan(String key, T t) {
        KeyBase keyBase = new KeyBase(key);
        boolean flag;
        flag = dao.updatePlan(t);
        if (flag && containsInPool(keyBase.getPlanDateString())) {
            updateData(key, t);
        }
        return flag;
    }

    public List<T> getAllPlan() {
        return dao.getAllList();
    }

    /**
     * 仅向内存中加入计划即可
     *
     * @param key
     * @param t
     */
    public void addPlanToMem(String key, T t) {
        addData(key, t);
    }

    /**
     * 仅从内存中删掉计划即可
     *
     * @param key
     */
    public void delPlanFromMem(String key) {
        delData(key);
    }
}
