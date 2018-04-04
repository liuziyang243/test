package com.crscd.passengerservice.plan.dao.daointerface;

import com.crscd.passengerservice.plan.domainobject.BaseTrainPlan;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/22
 * 对于计划类PO的增删改查
 */
public interface PlanDAOInterface<T extends BaseTrainPlan> {

    boolean existPlan(String planKey);

    T selectPlan(String planKey);

    long getPlanCount();

    List<T> selectPlanList(String trainNum, String stationName, String date);

    List<T> selectPlanList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag);

    List<T> selectPlanList(String stationName, int pageNumber, int pageSize);

    List<T> getAllList();

    boolean insertPlan(T t);

    boolean updatePlan(T t);

    boolean delPlan(String planKey);
}
