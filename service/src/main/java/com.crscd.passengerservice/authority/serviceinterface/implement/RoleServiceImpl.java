package com.crscd.passengerservice.authority.serviceinterface.implement;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.authority.dto.RoleMessage;
import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.authority.po.Role;
import com.crscd.passengerservice.authority.po.RoleAction;
import com.crscd.passengerservice.authority.po.UserRole;
import com.crscd.passengerservice.authority.serviceinterface.RoleService;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/7.
 */
public class RoleServiceImpl implements RoleService {

    private DataSet oracleDataSet;

    public RoleServiceImpl(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    //增加角色
    @Override
    public RoleMessage addRole(Role role) {
        RoleMessage result;
        //查询角色名是否已存在
        Role check = oracleDataSet.select(Role.class, "roleName=?", role.getRoleName());
        if (null == check) {
            //Id
            String uuid = UUID.randomUUID().toString();
            role.setRoleId(uuid);
            //增加到数据库中
            if (oracleDataSet.insert(role)) {
                result = new RoleMessage(role);
            } else {
                result = new RoleMessage(new ResultMessage(100));
            }

        } else {
            result = new RoleMessage(new ResultMessage(102));
        }
        return result;

    }

    //修改角色
    @Override
    public RoleMessage editRole(String roleId, String roleName, String roleDescription) {
        if (null == roleId) {
            return new RoleMessage(new ResultMessage(64));
        }
        Role originalRole = oracleDataSet.select(Role.class, "RoleId = ?", roleId);
        if (null == originalRole) {
            return new RoleMessage(new ResultMessage(64));
        }
        if (null != roleName) {
            //检查修改后的用户名是否存在数据库中
            String condition = "roleName = ? and roleId != ? ";
            List<Role> checkNames = oracleDataSet.selectListWithCondition(Role.class, condition, roleName, roleId);
            if (checkNames.size() != 0) {
                return new RoleMessage(new ResultMessage(102));
            } else {
                originalRole.setRoleName(roleName);
            }
        }
        if (null != roleDescription) {
            originalRole.setRoleDescription(roleDescription);
        }
        //更新到数据库
        if (oracleDataSet.update(originalRole, "roleId")) {
            return new RoleMessage(originalRole);
        } else {
            return new RoleMessage(new ResultMessage(100));
        }

    }

    //删除角色
    @Override
    public boolean delRole(String roleId) {
        String condition = "roleId = ?";
        return oracleDataSet.delete(Role.class, condition, roleId);
    }

    //添加角色权限列表
    @Override
    public boolean addRoleAction(String roleId, List<String> actionNames) {
        if (actionNames == null || actionNames.size() == 0) {
            return false;
        }
        List<RoleAction> roleActions = new ArrayList<>();
        for (String action : actionNames) {
            RoleAction roleAction = new RoleAction();
            String id = roleId + action;
            roleAction.setId(id);
            roleAction.setRoleId(roleId);
            roleAction.setActionName(action);
            roleActions.add(roleAction);
        }
        return oracleDataSet.insertList(roleActions);
    }

    //删除角色权限列表
    @Override
    public boolean delRoleAction(String roleId, List<String> actionNames) {
        if (actionNames == null || actionNames.size() == 0) {
            return false;
        }
        boolean result = true;
        for (String action : actionNames) {
            String id = roleId + action;
            result = oracleDataSet.delete(RoleAction.class, id) && result;
        }
        return result;
    }

    //删除角色对应的所有权限列表
    @Override
    public boolean delRoleAllAction(String roleId) {
        if (oracleDataSet.selectCount(RoleAction.class, "roleId = ?", roleId) > 0) {
            return oracleDataSet.delete(RoleAction.class, "roleId = ?", roleId);
        } else {
            return true;
        }
    }

    //获取角色对应权限
    @Override
    public List<String> findActionNamesByRoleId(String roleId) {
        return oracleDataSet.selectColumnList(RoleAction.class, "actionName", "roleId = ?", "", roleId);
    }

    //获取角色对应权限
    @Override
    public List<Action> findActionsByRoleId(String roleId) {
        List<Action> actions = new ArrayList<>();
        List<String> actionNames = oracleDataSet.selectColumnList(RoleAction.class, "actionName", "roleId = ?", "", roleId);
        if (null != actionNames && actionNames.size() != 0) {
            for (String actionName : actionNames) {
                Action action = oracleDataSet.select(Action.class, "actionName = ?", actionName);
                actions.add(action);
            }
        }
        return actions;
    }

    //删除用户角色表
    @Override
    public boolean delUserRoleByRoleId(String roleId) {
        if (oracleDataSet.selectCount(UserRole.class, "roleId = ?", roleId) > 0) {
            return oracleDataSet.delete(UserRole.class, "roleId = ?", roleId);
        } else {
            return true;
        }
    }

    //获取全部角色表
    @Override
    public List<Role> getAllRole() {
        return oracleDataSet.selectList(Role.class);
    }
}
