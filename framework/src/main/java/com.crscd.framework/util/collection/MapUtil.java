package com.crscd.framework.util.collection;

import org.apache.commons.collections4.MapUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {

    /**
     * 判断 Map 是否非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return MapUtils.isNotEmpty(map);
    }

    /**
     * 判断 Map 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    /**
     * 转置 Map
     */
    public static <K, V> Map<V, K> invert(Map<K, V> source) {
        Map<V, K> target = null;
        if (isNotEmpty(source)) {
            target = new LinkedHashMap<V, K>(source.size());
            for (Map.Entry<K, V> entry : source.entrySet()) {
                target.put(entry.getValue(), entry.getKey());
            }
        }
        return target;
    }
}
