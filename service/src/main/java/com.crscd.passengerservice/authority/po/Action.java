package com.crscd.passengerservice.authority.po;

import com.crscd.framework.orm.annotation.EqualBean;

/**
 * @author zs
 * @date 2017/8/4
 */
@EqualBean
public class Action {
    /**
     * 权限名称, 权限划分的粒度是页面，
     * 即一种权限代表用户可以看到一个页面并进行操作
     */
    private String actionName;
    /**
     * 权限说明
     */
    private String actionDescription;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
