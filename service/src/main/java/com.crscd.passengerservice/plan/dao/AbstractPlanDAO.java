package com.crscd.passengerservice.plan.dao;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.CastUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.plan.dao.daointerface.DAOInterface;
import com.crscd.passengerservice.plan.dao.daointerface.PlanDAOInterface;
import com.crscd.passengerservice.plan.domainobject.BaseTrainPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/23
 * Time: 17:42
 * 对DO和PO类的增删改查合并
 */
public abstract class AbstractPlanDAO<T, M extends BaseTrainPlan> implements DAOInterface<T>, PlanDAOInterface<M> {

    DataSet dataSet;

    ConfigManager manager;

    AbstractPlanDAO(ConfigManager manager) {
        this.manager = manager;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    long selectPK(Class<T> clazz, String planKey) {
        String condition = "planKey=?";
        return CastUtil.castLong(dataSet.selectPKWithCondition(clazz, condition, planKey));
    }

    long getRowCount(Class<T> clazz) {
        return dataSet.selectCount(clazz, null);
    }

    List<T> selectPagedPlanBeanList(Class<T> clazz, String stationName, int pageNumber, int pageSize) {
        String sort = "plandate asc";
        return dataSet.selectListForPager(pageNumber, pageSize, clazz, null, sort);
    }

    @Override
    public boolean insert(T t) {
        return dataSet.insert(t);
    }

    @Override
    public boolean update(T t) {
        return dataSet.update(t);
    }


    boolean del(Class<T> clazz, long id) {
        return dataSet.delete(clazz, FrameworkConstant.DEL_BY_PK, id);
    }

    @Override
    public long getPlanCount() {
        return getRowCount();
    }

    public abstract T getDOFromPO(M plan);

    // 根据车次号、站名和日期筛选计划
    // 车次号、站名和日期可以为空，表示不对该属性进行过滤
    List<T> selectBeanList(Class<T> clazz, String trainNum, String stationName, String date) {
        List<T> beanList = new ArrayList<>();
        Map<String, String> conditionParamMap = new HashMap<>();
        if (null != trainNum) {
            conditionParamMap.put("trainNum=?", trainNum);
        }
        if (null != stationName) {
            conditionParamMap.put("presentStation=?", stationName);
        }
        if (null != date) {
            conditionParamMap.put("planDate=?", date);
        }
        if (conditionParamMap.isEmpty()) {
            beanList = dataSet.selectList(clazz);
        } else if (conditionParamMap.size() == 1) {
            for (Map.Entry<String, String> entry : conditionParamMap.entrySet()) {
                beanList = dataSet.selectListWithCondition(clazz, entry.getKey(), entry.getValue());
            }
        } else {
            String[] parms = new String[conditionParamMap.size()];
            int index = 0;
            StringBuilder condition = new StringBuilder();
            for (Map.Entry<String, String> entry : conditionParamMap.entrySet()) {
                if (index == 0) {
                    condition = new StringBuilder(entry.getKey());
                } else {
                    condition.append(" AND ").append(entry.getKey());
                }
                parms[index] = entry.getValue();
                index += 1;
            }
            beanList = dataSet.selectListWithCondition(clazz, condition.toString(), parms);
        }
        return beanList;
    }

    // 根据车次号、站名和日期筛选计划
    // 车次号、站名和日期可以为空，表示不对该属性进行过滤
    List<T> selectBeanList(Class<T> clazz, String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag) {
        List<T> beanList = new ArrayList<>();
        Map<String, String> conditionParamMap = new HashMap<>();
        if (fuzzyFlag) {
            if (null != trainNum) {
                conditionParamMap.put("trainNum like ?", "%" + trainNum + "%");
            }
        } else {
            if (null != trainNum) {
                conditionParamMap.put("trainNum=?", trainNum);
            }
        }
        if (null != stationName) {
            conditionParamMap.put("presentStation=?", stationName);
        }
        if (null != startDay) {
            conditionParamMap.put("to_date(planDate,'yyyy-mm-dd')>=to_date(?,'yyyy-mm-dd')", startDay);
        }
        if (null != endDay) {
            conditionParamMap.put("to_date(planDate,'yyyy-mm-dd')<=to_date(?,'yyyy-mm-dd')", endDay);
        }
        if (conditionParamMap.isEmpty()) {
            beanList = dataSet.selectList(clazz);
        } else if (conditionParamMap.size() == 1) {
            for (Map.Entry<String, String> entry : conditionParamMap.entrySet()) {
                beanList = dataSet.selectListWithCondition(clazz, entry.getKey(), entry.getValue());
            }
        } else {
            String[] parms = new String[conditionParamMap.size()];
            int index = 0;
            StringBuilder condition = new StringBuilder();
            for (Map.Entry<String, String> entry : conditionParamMap.entrySet()) {
                if (index == 0) {
                    condition = new StringBuilder(entry.getKey());
                } else {
                    condition.append(" AND ").append(entry.getKey());
                }
                parms[index] = entry.getValue();
                index += 1;
            }
            beanList = dataSet.selectListWithCondition(clazz, condition.toString(), parms);
        }
        return beanList;
    }

}
