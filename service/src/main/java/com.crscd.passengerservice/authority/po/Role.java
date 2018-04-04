package com.crscd.passengerservice.authority.po;

import com.crscd.framework.orm.annotation.EqualBean;

/**
 * @author zs
 * @date 2017/8/3
 */
@EqualBean
public class Role {
    /**
     * 角色id，UUID
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDescription;

    public Role() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
