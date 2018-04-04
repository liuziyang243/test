package com.crscd.passengerservice.result.base;

import com.crscd.framework.translation.annotation.TranslateAttribute;

/**
 * Created by Administrator on 2017/8/4.
 */
public abstract class BaseMessage {
    // 状态码
    private int statusCode;
    @TranslateAttribute
    // 携带信息
    private String message;

    public BaseMessage() {
        this.statusCode = 200;
        message = "OK";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
