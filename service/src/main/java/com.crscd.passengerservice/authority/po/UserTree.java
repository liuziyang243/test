package com.crscd.passengerservice.authority.po;

import com.crscd.framework.orm.annotation.EqualBean;

/**
 * Created by zs
 * on 2017/8/7.
 *
 * @author zs
 */
@EqualBean
public class UserTree {
    private String childUserId;
    private String parentUserId;

    public UserTree(String childUserId, String parentUserId) {
        this.childUserId = childUserId;
        this.parentUserId = parentUserId;
    }

    public String getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(String childUserId) {
        this.childUserId = childUserId;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }
}
