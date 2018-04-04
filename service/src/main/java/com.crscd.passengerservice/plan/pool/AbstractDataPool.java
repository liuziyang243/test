package com.crscd.passengerservice.plan.pool;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.plan.domainobject.KeyBase;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 9:17
 */
public abstract class AbstractDataPool<T> {
    /**
     * 存储数据的周期
     */
    private int durationInDays;
    /**
     * 存储在池中数据的起始时间
     */
    private LocalDate poolDataStartDate;
    /**
     * 存储在池中数据的截止时间
     */
    private LocalDate poolDataEndDate;
    /**
     * Key:date, value: daily data list
     * 采用按天分页存储计划的方式组织数据
     */
    private Map<LocalDate, DailyData<T>> dataMap;

    public AbstractDataPool(int durationInDays) {
        this.durationInDays = durationInDays;
        dataMap = new ConcurrentHashMap<>();
    }

    /**
     * 刷新数据
     */
    void refreshData() {
        loadDailyPlan();
        clearDailyPlan();
    }

    /**
     * 从数据库获取某天的计划数据
     *
     * @param date
     * @return
     */
    abstract Map<String, T> getOneDayPlan(LocalDate date);

    /**
     * 增加一天计划到内存池
     *
     * @param date
     */
    private void addOneDayPlan(LocalDate date) {
        this.dataMap.put(date, new DailyData<>(getOneDayPlan(date)));
    }

    /**
     * 删除内存池一天计划
     *
     * @param date
     */
    private void delOneDayPlan(LocalDate date) {
        this.dataMap.remove(date);
    }

    /**
     * 加载周期内计划
     */
    private void loadDailyPlan() {
        List<String> dateList = DateTimeUtil.getDateList(this.getDataInPoolStartDate(), this.getDataInPoolEndDate());
        for (String date : dateList) {
            LocalDate localDate = DateTimeFormatterUtil.convertStringToDate(date);
            if (!this.dataMap.keySet().contains(localDate)) {
                addOneDayPlan(localDate);
            }
        }
    }

    /**
     * 清理不在周期内的计划
     */
    private void clearDailyPlan() {
        for (Map.Entry<LocalDate, DailyData<T>> entry : this.dataMap.entrySet()) {
            if (!DateTimeUtil.isInInterval(entry.getKey(), this.getDataInPoolStartDate(), this.getDataInPoolEndDate())) {
                delOneDayPlan(entry.getKey());
            }
        }
    }

    /**
     * 根据planKey判断池中是否包含某个车次的计划
     *
     * @param key
     * @return
     */
    private boolean isContains(String key) {
        KeyBase base = new KeyBase(key);
        if (!DateTimeUtil.isInInterval(base.getPlanDate(), getDataInPoolStartDate(), getDataInPoolEndDate())) {
            return false;
        } else {
            return dataMap.containsKey(base.getPlanDate()) && dataMap.get(base.getPlanDate()).isContains(key);
        }
    }

    /**
     * 根据planKey获取池中的数据
     *
     * @param key
     * @return
     */
    T getData(String key) {
        KeyBase base = new KeyBase(key);
        if (isContains(key)) {
            return dataMap.get(base.getPlanDate()).getData(key);
        }
        return null;
    }

    /**
     * 获取一天的全部计划列表
     *
     * @param date
     * @return
     */
    List<T> getDailyDataList(LocalDate date) {
        return getDailyData(date).getAllData();
    }

    /**
     * 获取一天的全部计划
     *
     * @param date
     * @return
     */
    private DailyData<T> getDailyData(LocalDate date) {
        return dataMap.get(date);
    }

    /**
     * 增加一条计划到内存池中
     *
     * @param key
     * @param t
     */
    void addData(String key, T t) {
        KeyBase base = new KeyBase(key);
        if (containsInPool(DateTimeFormatterUtil.convertDateToString(base.getPlanDate()))) {
            if (isContains(key)) {
                getDailyData(base.getPlanDate()).delData(key);
                getDailyData(base.getPlanDate()).addData(key, t);
            } else {
                getDailyData(base.getPlanDate()).addData(key, t);
            }
        }
    }

    /**
     * 更新内存池中的一条计划信息
     *
     * @param key
     * @param t
     */
    void updateData(String key, T t) {
        KeyBase base = new KeyBase(key);
        if (containsInPool(DateTimeFormatterUtil.convertDateToString(base.getPlanDate()))) {
            if (isContains(key)) {
                delData(key);
                addData(key, t);
            } else {
                addData(key, t);
            }
        }
    }

    /**
     * 删除内存池中的一条计划
     *
     * @param key
     */
    void delData(String key) {
        KeyBase base = new KeyBase(key);
        if (containsInPool(DateTimeFormatterUtil.convertDateToString(base.getPlanDate()))) {
            if (isContains(key)) {
                getDailyData(base.getPlanDate()).delData(key);
            }
        }
    }

    /**
     * 根据计划日期判断是否存在于内存池中
     *
     * @param date
     * @return 是否存在于内存池中
     */
    boolean containsInPool(String date) {
        return DateTimeUtil.isInInterval(DateTimeFormatterUtil.convertStringToDate(date), getDataInPoolStartDate(), getDataInPoolEndDate());
    }

    private LocalDate getDataInPoolStartDate() {
        return DateTimeUtil.getCurrentDate().minusDays(this.durationInDays);
    }

    private LocalDate getDataInPoolEndDate() {
        return DateTimeUtil.getCurrentDate().plusDays(this.durationInDays);
    }

}
