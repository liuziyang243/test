package com.crscd.passengerservice.plan.dao.daointerface;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/17
 * Time: 16:38
 * 对于DO的增删改查
 */
public interface DAOInterface<T> {

    long selectPK(String planKey);

    T select(String planKey);

    List<T> selectList(String trainNum, String stationName, String date);

    long getRowCount();

    List<T> selectList(String trainNum, String stationName, String startDay, String endDay, boolean fuzzyFlag);

    List<T> selectList(String stationName, int pageNumber, int pageSize);

    List<T> getAll();

    boolean insert(T t);

    boolean update(T t);

    boolean del(long id);
}
