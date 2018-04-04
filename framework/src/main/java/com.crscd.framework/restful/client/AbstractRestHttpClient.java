package com.crscd.framework.restful.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/7/6
 */
public abstract class AbstractRestHttpClient implements RestHttpClient {
    // time-out interval, in seconds
    static final int TIMEOUT_SECONDS = 10;
    // thread-pool capacity, how many requests can be handled concurrently.
    static final int POOL_SIZE = 50;

    @Override
    abstract public String getContent(String url);

    @Override
    abstract public String getContent(String url, String jsonEntity);

    @Override
    abstract public String getContent(String url, String jsonEntity, Map<String, String> headers);

    /**
     * return T object
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T getObject(String url, Class<T> clazz) {
        String jsonString = getContent(url);
        if (null != jsonString) {
            return JSON.parseObject(jsonString, clazz, Feature.OrderedField);
        }
        return null;
    }

    /**
     * return list<T>
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> getObjectList(String url, Class<T> clazz) {
        String jsonString = getContent(url);
        List<T> result = new ArrayList<>();
        if (null != jsonString) {
            result = JSON.parseArray(jsonString, clazz);
        }
        return result;
    }


    /**
     * return T object
     *
     * @param url
     * @param jsonEntity
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T getObject(String url, String jsonEntity, Class<T> clazz) {
        String jsonString = getContent(url, jsonEntity);
        if (null != jsonString) {
            return JSON.parseObject(jsonString, clazz);
        }
        return null;
    }

    /**
     * return list<T>
     *
     * @param url
     * @param jsonEntity
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> getObjectList(String url, String jsonEntity, Class<T> clazz) {
        String jsonString = getContent(url, jsonEntity);
        List<T> result = new ArrayList<>();
        if (null != jsonString) {
            result = JSON.parseArray(jsonString, clazz);
        }
        return result;
    }
}
