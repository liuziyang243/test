package com.crscd.framework.restful.client;

import okhttp3.*;
import okio.BufferedSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lzy
 * Create date: 2017/7/6
 */
public class OkhttpBasedRestHttpClient extends AbstractRestHttpClient {

    private static Logger logger = LoggerFactory.getLogger(OkhttpBasedRestHttpClient.class);
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT_SECONDS * 1000, TimeUnit.MILLISECONDS)
            .connectionPool(new ConnectionPool(POOL_SIZE, 60L, TimeUnit.SECONDS))
            .build();
    private MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/json");

    /*
     * asynchronous network call
     */
    public void getContentAndProcess(String url, HttpResponseProcessCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.error("[ApacheBasedRestHttpClient] Get http response wrong.", e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("[ApacheBasedRestHttpClient] Get http response and received unexpected code." + response);
                } else {
                    // do something wih the result
                    callback.processResponse(response.body().string());
                }
            }
        });
    }

    /*
     * synchronous network call
     */
    @Override
    public String getContent(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (null != response) {
                return response.body().string();
            }
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] Get http response wrong.", e);
        }

        return null;
    }

    @Override
    public org.apache.http.client.fluent.Response getResponce(String url) {
        return null;
    }

    @Override
    public String getContent(String url, Map<String, String> header) {
        return null;
    }

    @Override
    public org.apache.http.client.fluent.Response getResponse(String url, Map<String, String> header) {
        return null;
    }

    /*
     * synchronous network call
     */
    @Override
    public String getContent(String url, String jsonEntity) {
        return getContent(url, jsonEntity, null);
    }

    @Override
    public org.apache.http.client.fluent.Response getResponse(String url, String jsonEntity) {
        return null;
    }

    @Override
    public String getContent(String url, String jsonEntity, Map<String, String> headers) {
        /*
        基于字符串提交post请求，适用于请求内容较小的情况
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_TEXT, requestBody))
                .build();
                */
        //使用流方法提交post请求，适用于请求内容较大的情况
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_TEXT;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(jsonEntity);
            }

            @Override
            public long contentLength() {
                return jsonEntity.length();
            }
        };

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            logger.error("[ApacheBasedRestHttpClient] Get http response wrong.", e);
        }

        return null;
    }

    @Override
    public org.apache.http.client.fluent.Response getResponse(String url, String jsonEntity, Map<String, String> header) {
        return null;
    }
}
