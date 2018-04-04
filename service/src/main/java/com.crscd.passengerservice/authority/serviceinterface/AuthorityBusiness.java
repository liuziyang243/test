package com.crscd.passengerservice.authority.serviceinterface;

import com.crscd.passengerservice.authority.dto.*;
import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.authority.po.Role;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;


/**
 * Created by zs
 * on 2017/8/3.
 */
@WebService
public interface AuthorityBusiness {
    //登录 （版本握手，权限更新，用户验证，ip地址验证）
    @WebResult(name = "logInResult")
    UserDtoMessage logIn(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password, @WebParam(name = "ipAddr") String ipAddr, @WebParam(name = "actionNameList") List<String> actionNameList, @WebParam(name = "clientVersion") String clientVersion);

    //注销
    @WebResult(name = "logOutResult")
    ResultMessage logOut(@WebParam(name = "userName") String userName, @WebParam(name = "ipAddr") String ipAddr);

    //获取用户列表(当前用户创建的用户列表)
    @WebResult(name = "getUserListResult")
    List<UserInfoDto> getUserList(@WebParam(name = "logInName") String logInName);


    //获取单个用户的全部信息
    @WebResult(name = "getUserInfoResult")
    UserInfoDto getUserInfo(@WebParam(name = "userId") String userId, @WebParam(name = "logInName") String logInName);

    //增加用户
    @WebResult(name = "addUserResult")
    UserInfoMessage addUser(@WebParam(name = "userName") String userName, @WebParam(name = "passWord") String passWord, @WebParam(name = "userDescription") String userDescription, @WebParam(name = "stationNameList") List<String> stationNameList, @WebParam(name = "logInName") String logInName);

    //修改用户基本信息
    @WebResult(name = "editUserResult")
    UserInfoMessage editUser(@WebParam(name = "userId") String userId, @WebParam(name = "userName") String userName, @WebParam(name = "password") String password, @WebParam(name = "description") String description, @WebParam(name = "stationNames") List<String> stationNames);

    //修改用户对应角色
    @WebResult(name = "editUserRoleResult")
    ResultMessage editUserRole(@WebParam(name = "userId") String userId, @WebParam(name = "roleIdList") List<String> roleIdList);

    //删除用户
    @WebResult(name = "delUserResult")
    ResultMessage delUser(@WebParam(name = "userId") String userId);

    //获取全部角色
    @WebResult(name = "getAllRoleResult")
    List<Role> getAllRole();

    //获取角色列表（当前用户拥有的角色列表）
    @WebResult(name = "getRoleListResult")
    List<Role> getRoleList(@WebParam(name = "userName") String userName);

    //增加角色
    @WebResult(name = "addRoleResult")
    RoleMessage addRole(@WebParam(name = "role") Role role);

    //修改角色基本信息
    @WebResult(name = "editRoleResult")
    RoleMessage editRole(@WebParam(name = "roleId") String roleId, @WebParam(name = "roleName") String roleName, @WebParam(name = "roleDescription") String roleDescription);

    //修改角色对应权限
    @WebResult(name = "editRoleActionResult")
    ResultMessage editRoleAction(@WebParam(name = "roleId") String roleId, @WebParam(name = "actionNames") List<String> actionNames);

    //删除角色
    @WebResult(name = "delRoleResult")
    ResultMessage delRole(@WebParam(name = "roleId") String roleId);

    //获取全部权限列表
    @WebResult(name = "getAllActionResult")
    List<Action> getAllAction();

    //获取权限列表 （选中的角色拥有的权限列表）
    @WebResult(name = "getActionListResult")
    List<Action> getActionList(@WebParam(name = "roleId") String roleId);

    //修改权限描述
    @WebResult(name = "editActionResult")
    ActionMessage editAction(@WebParam(name = "actionName") String actionName, @WebParam(name = "actionDescription") String actionDescription);

    //保持与前台联系，更新用户在线/离线状态
    @WebResult(name = "statusResult")
    ResultMessage ipStatus(@WebParam(name = "userName") String userName, @WebParam(name = "ipAddr") String ipAddr);

    //获取用户拥有的权限列表
    @WebResult(name = "getActionListByUserIdResult")
    List<Action> getActionListByUserId(@WebParam(name = "userId") String userId);

    //获取用户不具备的权限列表
    @WebResult(name = "getActionListByUserIdResult")
    List<Action> getOtherActionListByUserId(@WebParam(name = "userId") String userId);

    //更新权限列表
    //@WebResult(name = "updateActionListResult")
    //ResultMessage updateActionList(@WebParam(name = "actionNameList") List<String> actionNameList, @WebParam(name = "clientVersion") String clientVersion);

    //版本握手
    //@WebResult(name = "versionConfirmResult")
    //ResultMessage versionConfirm(@WebParam(name = "clientVersion") String clientVersion);
}
