package com.crscd.framework.restful.client;

import org.apache.http.client.fluent.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/7/6
 */
public interface RestHttpClient {

    /* get string content of http response */
    String getContent(String url);

    /* get response with url*/
    Response getResponce(String url);

    /*get string content of http response */
    String getContent(String url, Map<String, String> header);

    /* get response with url and header*/
    Response getResponse(String url, Map<String, String> header);

    /* convert string content of http response to a instance of T*/
    <T> T getObject(String url, Class<T> clazz);

    /* convert string content of http response to a list of T*/
    <T> List<T> getObjectList(String url, Class<T> clazz);

    /*POST get string content of http response */
    String getContent(String url, String jsonEntity);

    /* POST get response with url and jsonEntity*/
    Response getResponse(String url, String jsonEntity);

    /*POST get string content of http response */
    String getContent(String url, String jsonEntity, Map<String, String> header);

    /* POST get response with url and jsonEntity and header*/
    Response getResponse(String url, String jsonEntity, Map<String, String> header);

    /*POST convert string content of http response to a instance of T*/
    <T> T getObject(String url, String jsonEntity, Class<T> clazz);

    /*POST convert string content of http response to a list of T*/
    <T> List<T> getObjectList(String url, String jsonEntity, Class<T> clazz);
}
