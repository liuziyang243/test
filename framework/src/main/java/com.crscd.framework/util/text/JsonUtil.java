package com.crscd.framework.util.text;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * JSON 操作工具类
 *
 * @author lzy
 * @since 1.0
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 将 Java 对象转为 JSON 字符串
     */
    public static <T> String toJSON(T obj) {
        String jsonStr;
        try {
            jsonStr = JSON.toJSONString(obj);
        } catch (Exception e) {
            logger.error("Java 转 JSON 出错！", e);
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /**
     * 将 JSON 字符串转为 Java 对象
     */
    public static <T> T fromJSON(String json, Class<T> type) {
        T obj;
        try {
            obj = JSON.parseObject(json, type, Feature.OrderedField);
        } catch (Exception e) {
            logger.error("JSON 转 Java 出错！", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * 将JSON字符串转换为List<>
     */
    public static <T> List<T> jsonToList(String json, Class<T> type) {
        try {
            return JSON.parseArray(json, type);
        } catch (Exception e) {
            logger.error("JSON 转 Java List<> 出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转化为Map
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> keyType, Class<V> valueType) {
        try {
            return JSON.parseObject(json, new TypeReference<Map<K, V>>() {
            });
        } catch (Exception e) {
            logger.error("JSON 转 Java Map<> 出错！", e);
            throw new RuntimeException(e);
        }
    }

}
