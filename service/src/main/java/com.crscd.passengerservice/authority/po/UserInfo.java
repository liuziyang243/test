package com.crscd.passengerservice.authority.po;

import com.crscd.framework.orm.annotation.EqualBean;

import java.util.List;

/**
 * @author zs
 * @date 2017/8/3
 */
@EqualBean
public class UserInfo {
    //用户信息id，UUID
    private String userId;
    //用户名，唯一
    private String userName;
    //密码
    private String password;
    //盐
    private String salt;
    //用户属性(普通用户或者是管理员用户；普通用户只能查看用户；管理员用户可以新建用户)
    // todo: 将用户级别调整为enum类型
    private int userLevel;//1 superAdmin 2 administrator 3 operator
    //用户相关描述
    private String userDescription;
    //用户拥有权限的站名列表
    private List<String> stationNameList;
    //todo:加上创建时间，创建人信息

    public UserInfo() {
    }

    public UserInfo(String userName, String passWord, String userDescription, List<String> stationNameList) {
        this.userName = userName;
        this.password = passWord;
        this.userDescription = userDescription;
        this.stationNameList = stationNameList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        return userName + salt;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public List<String> getStationNameList() {
        return stationNameList;
    }

    public void setStationNameList(List<String> stationNameList) {
        this.stationNameList = stationNameList;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserInfo userInfo = (UserInfo) o;

        if (getUserLevel() != userInfo.getUserLevel()) {
            return false;
        }
        if (!getUserId().equals(userInfo.getUserId())) {
            return false;
        }
        if (!getUserName().equals(userInfo.getUserName())) {
            return false;
        }
        if (!getPassword().equals(userInfo.getPassword())) {
            return false;
        }
        if (!getSalt().equals(userInfo.getSalt())) {
            return false;
        }
        if (getUserDescription() != null ? !getUserDescription().equals(userInfo.getUserDescription()) : userInfo.getUserDescription() != null) {
            return false;
        }
        return getStationNameList() != null ? getStationNameList().equals(userInfo.getStationNameList()) : userInfo.getStationNameList() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId().hashCode();
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getSalt().hashCode();
        result = 31 * result + getUserLevel();
        result = 31 * result + (getUserDescription() != null ? getUserDescription().hashCode() : 0);
        result = 31 * result + (getStationNameList() != null ? getStationNameList().hashCode() : 0);
        return result;
    }
}
