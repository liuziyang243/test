package com.crscd.passengerservice.plan.business.innerinterface;

import com.crscd.passengerservice.plan.domainobject.BaseTrainPlan;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/31
 * Time: 16:42
 */
public interface PlanInterface<T extends BaseTrainPlan> {
    /**
     * 根据计划key获取指定的计划
     * 调度、客运、导向计划的key值获取方式如下：
     * String planKey = new KeyBase(trainNum, planDate, stationName).toString();
     * 广播计划根据key会搜出一组计划，后续如果有需要会追加方法
     *
     * @param key
     * @return
     */
    T getPlan(String key);

    /**
     * 根据站名、车次号、日期搜索计划
     * 最后一个参数指是否允许根据车次号进行模糊搜索，设置为true的时候表示允许
     * 站名和车次号可以为null，当为null的时候表示不用该参数过滤
     * 如果只搜索一天的计划，则将startDate和endDate设置为同一个值即可
     *
     * @param stationName
     * @param trainNum
     * @param startDate
     * @param endDate
     * @param fuzzyQueryFlag
     * @return
     */
    List<T> getPlanList(String stationName, String trainNum, String startDate, String endDate, boolean fuzzyQueryFlag);

    /**
     * 根据车站站名获取分页计划数据
     *
     * @param stationName
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<T> getPlanList(String stationName, int pageNumber, int pageSize);

    /**
     * 获取数据库总存储数目
     *
     * @return
     */
    long getPlanCount();
}
