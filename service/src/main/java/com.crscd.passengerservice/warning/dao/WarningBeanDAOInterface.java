package com.crscd.passengerservice.warning.dao;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 14:38
 */
public interface WarningBeanDAOInterface<T> {
    // 获取告警列表
    List<T> getBeanList();

    // 插入新的告警信息
    boolean insert(T t);

    // 确认告警信息
    boolean confirm(long id, String user);

}
