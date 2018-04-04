package com.crscd.passengerservice.authority.dto;

import com.crscd.passengerservice.authority.po.Role;
import com.crscd.passengerservice.authority.po.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zs
 * @date 2017/9/8
 */
public class UserInfoDto {
    //用户信息id，UUID
    private String userId;
    //用户名，唯一
    private String userName;
    //密码
    private String password;
    //用户属性(普通用户或者是管理员用户；普通用户只能查看用户；管理员用户可以新建用户)
    private int userLevel;//1 superAdmin 2 administrator 3 operator
    //用户相关描述
    private String userDescription;
    //用户拥有权限的站名列表
    private List<String> stationNameList;
    //用户可用的站名列表
    private List<String> maxStationNameList;
    //用户具备的角色列表
    private List<Role> roleList;
    //用户可用的角色列表
    private List<Role> maxRoleList;

    public UserInfoDto(UserInfo userInfo) {
        this.userId = userInfo.getUserId();
        this.userName = userInfo.getUserName();
        this.password = userInfo.getPassword();
        this.userLevel = userInfo.getUserLevel();
        this.userDescription = userInfo.getUserDescription();
        this.stationNameList = userInfo.getStationNameList();
        this.maxStationNameList = new ArrayList<>();
        this.roleList = new ArrayList<>();
        this.maxRoleList = new ArrayList<>();
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

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
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

    public List<String> getMaxStationNameList() {
        return maxStationNameList;
    }

    public void setMaxStationNameList(List<String> maxStationNameList) {
        this.maxStationNameList = maxStationNameList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Role> getMaxRoleList() {
        return maxRoleList;
    }

    public void setMaxRoleList(List<Role> maxRoleList) {
        this.maxRoleList = maxRoleList;
    }
}
