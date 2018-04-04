package com.crscd.passengerservice.authority.serviceinterface;

import com.crscd.passengerservice.authority.dto.RoleMessage;
import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.authority.po.Role;

import java.util.List;


/**
 * Created by Administrator on 2017/8/7.
 */
public interface RoleService {
    //增加角色
    RoleMessage addRole(Role role);

    //修改角色
    RoleMessage editRole(String roleId, String roleName, String roleDescription);

    //删除角色
    boolean delRole(String roleId);

    //添加角色权限列表
    boolean addRoleAction(String roleId, List<String> actionNames);

    //删除角色权限列表
    boolean delRoleAction(String roleId, List<String> actionNames);

    //删除角色对应的所有权限列表
    boolean delRoleAllAction(String roleId);

    //获取角色对应权限名
    List<String> findActionNamesByRoleId(String roleId);

    //获取角色对应权限
    List<Action> findActionsByRoleId(String roleId);

    //删除用户角色表
    boolean delUserRoleByRoleId(String roleId);

    //获取全部角色表
    List<Role> getAllRole();
}
