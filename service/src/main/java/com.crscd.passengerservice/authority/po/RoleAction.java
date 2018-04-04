package com.crscd.passengerservice.authority.po;

import com.crscd.framework.orm.annotation.EqualBean;

/**
 * @author zs
 * @date 2017/8/4
 */
@EqualBean
public class RoleAction {

    private String id;

    private String roleId;

    private String actionName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
