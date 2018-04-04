package com.crscd.passengerservice.device.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.passengerservice.device.objectdomain.BaseDeviceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 17:21
 */
public abstract class AbstractDeviceInfoDAO<T> implements DeviceInfoDAO<T> {
    private DataSet dataSet;

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    List<T> getDeviceInfoList(String stationName, Class<T> clazz) {
        String condition = "stationName=?";
        return dataSet.selectListWithCondition(clazz, condition, stationName);
    }

    Map<String, Boolean> modifyDeviceListInfo(List<Integer> idList, BaseDeviceInfo info, Class<T> clazz) {
        String condition = "id=?";
        Map<String, Boolean> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("manufacturer", info.getManufacturer());
        params.put("contactInfo", info.getContactInfo());
        params.put("deviceLocation", info.getDeviceLocation());
        List<String> customList = new ArrayList<>();
        for (Map.Entry<String, String> entry : info.getCustomizedConfig().entrySet()) {
            customList.add(entry.getKey() + "_" + entry.getValue());
        }
        params.put("customizedConfig", JsonUtil.toJSON(customList));
        for (Integer id : idList) {
            result.put(id.toString(), dataSet.update(clazz, params, condition, id));
        }
        return result;
    }

    Map<String, Boolean> modifyCustomDeviceInfo(List<Integer> idList, Map<String, String> customInfo, Class<T> clazz) {
        String condition = "id=?";
        Map<String, Boolean> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        List<String> customList = new ArrayList<>();
        for (Map.Entry<String, String> entry : customInfo.entrySet()) {
            customList.add(entry.getKey() + "_" + entry.getValue());
        }
        params.put("customizedConfig", JsonUtil.toJSON(customList));
        for (Integer id : idList) {
            result.put(id.toString(), dataSet.update(clazz, params, condition, id));
        }
        return result;
    }
}
