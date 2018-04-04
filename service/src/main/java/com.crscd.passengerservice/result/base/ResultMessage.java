package com.crscd.passengerservice.result.base;

import com.crscd.passengerservice.result.errorinfo.ErrorCodeReasonMap;

/**
 * @author zs
 * Created by zs
 * on 2017/8/4.
 */
public class ResultMessage extends BaseMessage {
    // 操作结果
    private boolean result;

    public ResultMessage() {
        super();
        this.setResult(true);
        this.setStatusCode(200);
        this.setMessage(ErrorCodeReasonMap.getReasonByCode(200));
    }

    public ResultMessage(int errorCode) {
        this.setResult(false);
        this.setStatusCode(errorCode);
        switch (errorCode) {
            case 200:
                this.setResult(true);
                break;
            case 1202:
                this.setResult(true);
                break;
            default:
                this.setResult(false);
                this.setMessage(ErrorCodeReasonMap.getReasonByCode(errorCode));
        }
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
