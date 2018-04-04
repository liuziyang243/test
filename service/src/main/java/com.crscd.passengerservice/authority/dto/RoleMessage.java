package com.crscd.passengerservice.authority.dto;

import com.crscd.passengerservice.authority.po.Role;
import com.crscd.passengerservice.result.base.BaseMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

/**
 * @author zs
 * @date 2017/8/29
 */
public class RoleMessage extends BaseMessage {

    private boolean result;
    private Role role;

    private RoleMessage() {
    }

    public RoleMessage(Role role) {
        super();
        this.role = role;
        this.result = true;
    }

    public RoleMessage(ResultMessage resultMessage) {
        this.role = null;
        this.result = resultMessage.getResult();
        this.setMessage(resultMessage.getMessage());
        this.setStatusCode(resultMessage.getStatusCode());
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
