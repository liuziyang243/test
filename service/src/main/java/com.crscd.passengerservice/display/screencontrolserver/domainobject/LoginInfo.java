package com.crscd.passengerservice.display.screencontrolserver.domainobject;

import com.crscd.framework.core.ConfigHelper;

/**
 * Created by cuishiqing on 2017/11/29.
 */
public class LoginInfo {
    private String identity;
    private String userName;
    private String password;

    public LoginInfo() {
        this.identity = ConfigHelper.getString("screenCtrlClientIdentity");
        this.userName = ConfigHelper.getString("screenCtrlUserName");
        this.password = ConfigHelper.getString("screenCtrlPassword");
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
