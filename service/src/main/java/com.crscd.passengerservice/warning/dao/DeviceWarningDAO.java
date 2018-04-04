package com.crscd.passengerservice.warning.dao;

import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.objectdomain.DeviceWarningInfo;
import com.crscd.passengerservice.warning.po.DeviceWarningBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/20
 * Time: 14:42
 */
public class DeviceWarningDAO extends AbstractWarningDAO<DeviceWarningBean, DeviceWarningInfo> {
    @Override
    public List<DeviceWarningBean> getBeanList() {
        return getBeanList(DeviceWarningBean.class);
    }

    @Override
    public boolean confirm(long id, String user) {
        return confirm(id, user, DeviceWarningBean.class);
    }

    @Override
    public List<DeviceWarningInfo> getWarningList() {
        List<DeviceWarningBean> beans = getBeanList();
        List<DeviceWarningInfo> infoList = new ArrayList<>();
        for (DeviceWarningBean bean : beans) {
            infoList.add(new DeviceWarningInfo(bean));
        }
        return infoList;
    }

    @Override
    public boolean insertWarning(DeviceWarningInfo info) {
        return insert(new DeviceWarningBean(info));
    }

    @Override
    public boolean isExitSameWarning(DeviceWarningInfo info, int interval) {
        String condition = "identification=? AND station=? AND warningMessage=? AND to_date(generateTime,'yyyy-mm-dd hh24:mi:ss')>=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
        String present = DateTimeUtil.getCurrentDatetimeString();
        if (interval > 0) {
            interval = -interval;
        }
        String dtime = DateTimeUtil.calcTimeWithMinute(present, interval);
        return dataSet.selectCount(DeviceWarningBean.class, condition, info.getIdentification(), info.getStation(), info.getWarningMessage(), dtime) > 0;
    }

    // 根据车站获取设备告警信息列表
    // station, area, startTime, endTime可以为空
    // system.all表示全部系统
    public List<DeviceWarningInfo> getDeviceWarningListByStation(String station, SystemEnum system, String area, String startTime, String endTime) {
        List<DeviceWarningBean> beanList = new ArrayList<>();
        Map<String, String> conditionParamMap = new HashMap<>();
        if (null != station) {
            conditionParamMap.put("station=?", station);
        }
        if (!system.equals(SystemEnum.ALL)) {
            conditionParamMap.put("belongSystem=?", system.toString());
        }
        if (null != area) {
            conditionParamMap.put("deviceArea=?", area);
        }
        if (null != startTime) {
            conditionParamMap.put("to_date(generateTime,'yyyy-mm-dd hh24:mi:ss')>=to_date(?,'yyyy-mm-dd hh24:mi:ss')", endTime);
        }
        if (null != endTime) {
            conditionParamMap.put("to_date(generateTime,'yyyy-mm-dd hh24:mi:ss')<=to_date(?,'yyyy-mm-dd hh24:mi:ss')", endTime);
        }
        if (conditionParamMap.isEmpty()) {
            beanList = dataSet.selectList(DeviceWarningBean.class);
        } else if (conditionParamMap.size() == 1) {
            for (Map.Entry<String, String> entry : conditionParamMap.entrySet()) {
                beanList = dataSet.selectListWithCondition(DeviceWarningBean.class, entry.getKey(), entry.getValue());
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
            beanList = dataSet.selectListWithCondition(DeviceWarningBean.class, condition.toString(), parms);
        }
        List<DeviceWarningInfo> infoList = new ArrayList<>();
        for (DeviceWarningBean bean : beanList) {
            infoList.add(new DeviceWarningInfo(bean));
        }
        return infoList;
    }
}
