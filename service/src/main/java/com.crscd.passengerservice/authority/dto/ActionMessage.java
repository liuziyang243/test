package com.crscd.passengerservice.authority.dto;

import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.result.base.BaseMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

/**
 * @author zs
 * @date 2017/8/31
 */
public class ActionMessage extends BaseMessage {
    private boolean result;
    private Action action;

    private ActionMessage() {
    }

    public ActionMessage(ResultMessage resultMessage) {
        this.action = null;
        this.result = resultMessage.getResult();
        this.setMessage(resultMessage.getMessage());
        this.setStatusCode(resultMessage.getStatusCode());
    }

    public ActionMessage(Action action) {
        super();
        this.action = action;
        this.result = true;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
