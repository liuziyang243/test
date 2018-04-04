package com.crscd.passengerservice.plan.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 10:46
 * 用来存储同一天的信息
 */
public class DailyData<T> {
    /**
     * Key:planKey, value: T data
     */
    private Map<String, T> dataMap;

    DailyData(Map<String, T> planList) {
        dataMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, T> entry : planList.entrySet()) {
            dataMap.put(entry.getKey(), entry.getValue());
        }
    }

    List<T> getAllData() {
        List<T> result = new ArrayList<>();
        for (Map.Entry<String, T> entry : dataMap.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    boolean isContains(String key) {
        return dataMap.containsKey(key);
    }

    T getData(String key) {
        return dataMap.get(key);
    }

    void addData(String key, T t) {
        if (dataMap.containsKey(key)) {
            dataMap.remove(key);
            dataMap.put(key, t);
        } else {
            dataMap.put(key, t);
        }
    }

    void delData(String key) {
        if (dataMap.containsKey(key)) {
            dataMap.remove(key);
        }
    }

}
