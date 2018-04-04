package com.crscd.framework.restful.client;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/7/5
 * Time: 9:01
 */
public class ApacheBasedRestHttpClient extends AbstractRestHttpClient {
    private static Logger logger = LoggerFactory.getLogger(ApacheBasedRestHttpClient.class);

    private static CloseableHttpClient httpClient;

    static {
        initApacheHttpClient();
    }

    // 创建包含connection pool与超时设置的client
    private static void initApacheHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000)
                .setConnectTimeout(TIMEOUT_SECONDS * 1000).build();

        httpClient = HttpClientBuilder.create().setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
                .setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * return json string
     *
     * @param url
     * @return
     */
    @Override
    public String getContent(String url) {
        Response response = getResponse(url);
        return getContent(response);
    }

    @Override
    public Response getResponce(String url) {
        Response response = null;
        try {
            Executor executor = Executor.newInstance(httpClient);
            response = executor.execute(Request.Get(url));
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] Get http response wrong.", e);
        }
        return response;
    }

    @Override
    public String getContent(String url, Map<String, String> headers) {
        String resultString = null;
        try {
            Executor executor = Executor.newInstance(httpClient);
            Request getRequest = Request.Get(url);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                getRequest.addHeader(entry.getKey(), entry.getValue());
            }
            Response response = executor.execute(getRequest);
            resultString = response.returnContent().asString();
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] GET http response wrong.", e);
        }
        return resultString;
    }

    @Override
    public Response getResponse(String url, Map<String, String> headers) {
        Response response = null;
        try {
            Executor executor = Executor.newInstance(httpClient);
            Request getRequest = Request.Get(url);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                getRequest.addHeader(entry.getKey(), entry.getValue());
            }
            response = executor.execute(getRequest);
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] GET http response wrong.", e);
        }
        return response;
    }

    /**
     * return json string
     *
     * @param response
     * @return
     */
    public String getContent(Response response) {
        String resultString = null;
        try {
            resultString = response == null ? null : response.returnContent().asString();
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] Get http response wrong.", e);
        }
        return resultString;
    }

    /**
     * return all http headers
     *
     * @param response
     * @return
     */
    public Header[] getHeader(Response response) {
        Header[] headerElements = null;
        try {
            headerElements = response == null ? new Header[0] : response.returnResponse().getAllHeaders();
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] Get http response wrong.", e);
        }
        return headerElements;
    }

    /**
     * return http response
     *
     * @param url
     * @return
     */
    public Response getResponse(String url) {
        Response response = null;
        try {
            Executor executor = Executor.newInstance(httpClient);
            response = executor.execute(Request.Get(url));
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] Get http response wrong.", e);
        }
        return response;
    }

    /**
     * return json string
     *
     * @param url
     * @param jsonEntity
     * @return
     */
    @Override
    public String getContent(String url, String jsonEntity) {
        return getContent(url, jsonEntity, null);
    }

    @Override
    public Response getResponse(String url, String jsonEntity) {
        return getResponse(url, jsonEntity, null);
    }

    @Override
    public String getContent(String url, String jsonEntity, Map<String, String> headers) {
        String resultString = null;
        try {

            Executor executor = Executor.newInstance(httpClient);
            Request postRequest = Request.Post(url);
            StringEntity input = new StringEntity(jsonEntity);
            input.setContentType("application/json");
            postRequest.body(input);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                postRequest.addHeader(entry.getKey(), entry.getValue());
            }
            Response response = executor.execute(postRequest);
            resultString = response.returnContent().asString();
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] POST http response wrong.", e);
        }
        return resultString;
    }

    @Override
    public Response getResponse(String url, String jsonEntity, Map<String, String> headers) {
        Response response = null;
        try {
            Executor executor = Executor.newInstance(httpClient);
            Request postRequest = Request.Post(url);
            StringEntity input = new StringEntity(jsonEntity);
            input.setContentType("application/json");
            postRequest.body(input);
            if (null != headers) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    postRequest.addHeader(entry.getKey(), entry.getValue());
                }
            }
            response = executor.execute(postRequest);
        } catch (IOException e) {
//            logger.error("[ApacheBasedRestHttpClient] POST http response wrong.", e);
        }
        return response;
    }

}
