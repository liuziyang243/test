package com.crscd.passengerservice.warning.dao;

import com.crscd.passengerservice.warning.objectdomain.AbstractWarningInfo;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 13:49
 */
public interface WarningInfoDAOInterface<T extends AbstractWarningInfo> {
    // 获取告警列表
    List<T> getWarningList();

    // 插入新的告警信息
    boolean insertWarning(T t);

    // 确认告警信息
    boolean confirmWarning(long id, String user);

    // 判断是否存在相同告警信息
    boolean isExitSameWarning(T t, int interval);
}
