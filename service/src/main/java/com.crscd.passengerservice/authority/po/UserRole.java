package com.crscd.passengerservice.authority.po;

import com.crscd.framework.orm.annotation.EqualBean;

/**
 * @author zs
 * @date 2017/8/3
 */
@EqualBean
public class UserRole {

    private String id;

    private String userId;

    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
