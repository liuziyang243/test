package com.crscd.passengerservice.warning.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.warning.objectdomain.AbstractWarningInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 14:44
 */
public abstract class AbstractWarningDAO<B, T extends AbstractWarningInfo> implements WarningBeanDAOInterface<B>, WarningInfoDAOInterface<T> {
    DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public boolean insert(B b) {
        return dataSet.insert(b);
    }

    List<B> getBeanList(Class<B> clazz) {
        return dataSet.selectList(clazz);
    }

    boolean confirm(long id, String user, Class<B> clazz) {
        String condition = "id=?";
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("confirmUser", user);
        fieldMap.put("confirmTime", DateTimeUtil.getCurrentDatetimeString());
        return dataSet.update(clazz, fieldMap, condition, id);
    }

    @Override
    public boolean confirmWarning(long id, String user) {
        return confirm(id, user);
    }

    /**
     * 判断数据库对于同一个设备是否存在过去一段时间内是否存在相同的告警信息
     * 本方法适用于设备id在全系统是唯一的情况
     */
    public boolean isExitSameWarning(Class<T> clazz, T info, int interval) {
        String condition = "identification=? AND warningMessage=? AND to_date(generateTime,'yyyy-mm-dd hh24:mi:ss')>=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
        String present = DateTimeUtil.getCurrentDatetimeString();
        if (interval > 0) {
            interval = -interval;
        }
        String dtime = DateTimeUtil.calcTimeWithMinute(present, interval);
        return dataSet.selectCount(clazz, condition, info.getIdentification(), info.getWarningMessage(), dtime) > 0;
    }

}
