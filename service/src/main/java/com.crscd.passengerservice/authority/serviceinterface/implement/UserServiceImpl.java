package com.crscd.passengerservice.authority.serviceinterface.implement;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.authority.dto.UserInfoDto;
import com.crscd.passengerservice.authority.dto.UserInfoMessage;
import com.crscd.passengerservice.authority.po.*;
import com.crscd.passengerservice.authority.serviceinterface.UserService;
import com.crscd.passengerservice.authority.util.PasswordHelper;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.*;

/**
 * Created by zs
 * on 2017/8/4.
 */
public class UserServiceImpl implements UserService {

    private DataSet oracleDataSet;

    public UserServiceImpl(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    //增加用户
    @Override
    public UserInfoMessage addUser(UserInfo user, UserInfo loginUser) {
        UserInfoMessage result;
        //查询用户名是否已存在
        UserInfo check = oracleDataSet.select(UserInfo.class, "userName=?", user.getUserName());
        if (null == check) {
            //Id
            String uuid = UUID.randomUUID().toString();
            user.setUserId(uuid);
            //加密密码
            PasswordHelper.encryptPassword(user);
            //增加到数据库中
            if (oracleDataSet.insert(user)) {
                UserInfoDto userInfoDto = new UserInfoDto(user);
                userInfoDto.setMaxStationNameList(loginUser.getStationNameList());
                //查询创建当前用户的用户角色，即为当前用户的角色最大范围
                userInfoDto.setMaxRoleList(findRolesByUserId(loginUser.getUserId()));
                result = new UserInfoMessage(userInfoDto);
            } else {
                result = new UserInfoMessage(new ResultMessage(100));
            }
        } else {
            result = new UserInfoMessage(new ResultMessage(101));
        }
        return result;

    }

    //修改用户基本信息,在已有用户基础上更改信息，不包括对未创建用户进行新建的情况，因为用户可能更改用户名
    //前台使用，密码是加密密码
    //入参如果为null，则不更改
    @Override
    public UserInfoMessage editUser(String userId, String userName, String password, String description, List<String> stationNames) {
        //检测用户id
        if (null == userId) {
            return new UserInfoMessage(new ResultMessage(64));
        }
        UserInfo originUserInfo = oracleDataSet.select(UserInfo.class, "userId = ?", userId);
        if (null == originUserInfo) {
            return new UserInfoMessage(new ResultMessage(64));
        }


        //修改了车站列表
        if (null != stationNames) {
            //如果被修改用户为二级用户，且用户的可用车站变少，则需要级联删除三级用户的可用车站
            if (originUserInfo.getUserLevel() == 2) {
                List<String> originStationNames = originUserInfo.getStationNameList();
                if (originStationNames.size() != 0) {
                    List<String> delStationNames = new ArrayList<>();
                    delStationNames.addAll(originStationNames);
                    delStationNames.removeAll(stationNames);
                    if (delStationNames.size() != 0) {
                        //找到减少的车站名，然后查找拥有此车站权限的三级用户，删除其对此车站的控制权限
                        List<UserInfo> users = getUserList(originUserInfo.getUserName());
                        for (UserInfo userInfo : users) {
                            List<String> leftStationNames = userInfo.getStationNameList();
                            int originStationSize = leftStationNames.size();
                            leftStationNames.removeAll(delStationNames);
                            if (leftStationNames.size() != originStationSize) {
                                oracleDataSet.update(userInfo, "userId");
                            }
                        }
                    }
                }
            }
            originUserInfo.setStationNameList(stationNames);
        }


        //修改用户名
        if (null != userName) {
            //检查修改后的用户名是否存在数据库中
            String condition = "userName = ? and userId != ? ";
            List<UserInfo> checkNames = oracleDataSet.selectListWithCondition(UserInfo.class, condition, userName, userId);
            if (checkNames.size() != 0) {
                return new UserInfoMessage(new ResultMessage(101));
            } else {
                originUserInfo.setUserName(userName);
            }
        }
        //修改了用户密码
        if (null != password) {
            if (!originUserInfo.getPassword().equals(password)) {
                originUserInfo.setPassword(password);
                PasswordHelper.encryptPassword(originUserInfo);//如果前台传递的密码变更，则传递过来的是新的原始密码，需要加密存储
            }

        }
        //修改了用户描述
        if (null != description) {
            originUserInfo.setUserDescription(description);
        }


        //更新到数据库
        if (oracleDataSet.update(originUserInfo, "userId")) {
            UserInfoDto userInfoDto = new UserInfoDto(originUserInfo);
            //填充最大可用车站(查找父节点的可用车站列表)
            String parentId = findParentUserId(userId);
            UserInfo parentUserInfo = getUserById(parentId);
            userInfoDto.setMaxStationNameList(parentUserInfo.getStationNameList());
            return new UserInfoMessage(userInfoDto);
        } else {
            return new UserInfoMessage(new ResultMessage(100));
        }

    }

    //已知更新的用户信息和其父节点用户

    //检查用户是否存在，存在即更新，不存在即创建 后台使用，密码是原始密码
    @Override
    public Map<UserInfo, ResultMessage> updateUser(UserInfo user) {
        Map<UserInfo, ResultMessage> result = new HashMap<>();
        UserInfo checkUser = oracleDataSet.select(UserInfo.class, "userName = ?", user.getUserName());
        if (null == checkUser) {
            //Id
            String uuid = UUID.randomUUID().toString();
            user.setUserId(uuid);
            //加密密码
            PasswordHelper.encryptPassword(user);
            //增加到数据库中
            if (oracleDataSet.insert(user)) {
                result.put(user, new ResultMessage());
            } else {
                result.put(null, new ResultMessage(100));
            }
        } else {
            //更新用户id
            user.setUserId(checkUser.getUserId());
            PasswordHelper.encryptPassword(user);//后台使用，密码是原始密码，需要加密存到数据库
            if (oracleDataSet.update(user, "userId")) {
                result.put(user, new ResultMessage());
            } else {
                result.put(null, new ResultMessage(100));
            }
        }
        return result;
    }

    //删除用户
    @Override
    public boolean delUser(String userId) {
        String condition = "userId = ?";
        return oracleDataSet.delete(UserInfo.class, condition, userId);
    }

    //添加用户-角色关系
    @Override
    public boolean addUserRole(String userId, List<String> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return false;
        }
        List<UserRole> userRoles = new ArrayList<>();
        for (String roleId : roleIds) {
            UserRole userRole = new UserRole();
            String id = userId + roleId;
            userRole.setId(id);
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        return oracleDataSet.insertList(userRoles);
    }

    //删除用户-角色关系
    @Override
    public boolean delUserRole(String userId, List<String> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return false;
        }
        boolean result = true;
        for (String roleId : roleIds) {
            String id = userId + roleId;
            result = oracleDataSet.delete(UserRole.class, "id=?", id) && result;
        }
        return result;

    }

    //删除用户对应的所有角色关系
    @Override
    public Boolean delUserAllRole(String userId) {

        if (oracleDataSet.selectCount(UserRole.class, "userId = ?", userId) > 0) {
            return oracleDataSet.delete(UserRole.class, "userId = ?", userId);
        } else {
            return true;
        }
    }

    //获取用户对应角色id  不涉及到超级用户
    @Override
    public List<String> findRoleIdsByUserId(String userId) {
        return oracleDataSet.selectColumnList(UserRole.class, "RoleId", "userId = ?", "", userId);
    }

    //获取用户对应角色  涉及到超级用户
    @Override
    public List<Role> findRolesByUserId(String userId) {
        UserInfo userInfo = getUserById(userId);
        if (userInfo.getUserName().equals(ServiceConstant.SUPER_ADMIN)) {
            return oracleDataSet.selectList(Role.class);
        } else {
            List<String> roleIds = findRoleIdsByUserId(userId);
            List<Role> roles = new ArrayList<>();
            if (null != roleIds && roleIds.size() != 0) {
                for (String roleId : roleIds) {
                    Role role = oracleDataSet.select(Role.class, "roleId=?", roleId);
                    roles.add(role);
                }
            }
            return roles;
        }
    }

    //获取用户对应权限
    @Override
    public List<Action> findActionsByUserId(String userId, boolean type) {
        String sql = "select actionName from RoleAction ra, UserRole ur where ur.userId = ? and ur.roleId = ra.roleId";
        List<String> actionNames = oracleDataSet.select(sql, userId);
        if (null == actionNames || actionNames.size() == 0) {
            return null;
        }
        StringBuilder condition = new StringBuilder();
        condition.append("(");
        for (String actionName : actionNames) {
            condition.append("'");
            condition.append(actionName);
            condition.append("',");
        }
        condition.setLength(condition.length() - 1);
        condition.append(")");
        String sqlActionCondition;
        if (type) {
            sqlActionCondition = "actionName in " + condition.toString();
        } else {
            sqlActionCondition = "actionName not in " + condition.toString();
        }
        return oracleDataSet.selectListWithCondition(Action.class, sqlActionCondition);
    }

    //获取用户列表(当前用户创建的用户列表)
    @Override
    public List<UserInfo> getUserList(String userName) {
        List<UserInfo> users = new ArrayList<>();
        //超级管理员
        if (ServiceConstant.SUPER_ADMIN.equals(userName)) {
            users = oracleDataSet.selectList(UserInfo.class);
        } else {
            UserInfo user = getUserByName(userName);
            users.add(user);
            //管理员
            if (user.getUserLevel() == 2) {
                List<String> userIds = oracleDataSet.selectColumnList(UserTree.class, "childUserId", "parentUserId = ?", "", user.getUserId());
                for (String userId : userIds) {
                    UserInfo childUser = oracleDataSet.select(UserInfo.class, "userId = ?", userId);
                    users.add(childUser);
                }
            }
        }
        return users;
    }

    //根据用户名获取用户
    @Override
    public UserInfo getUserByName(String userName) {
        return oracleDataSet.select(UserInfo.class, "userName = ?", userName);
    }

    //根据用户id获取用户
    @Override
    public UserInfo getUserById(String userId) {
        return oracleDataSet.select(UserInfo.class, "userId = ?", userId);
    }

    //增加userTree
    @Override
    public boolean addUserTree(String childUserId, String parentUserId) {
        UserTree userTree = new UserTree(childUserId, parentUserId);
        return oracleDataSet.insert(userTree);
    }


    //更新userTree 被删除的二级用户创建的三级用户的父节点更新为超级用户
    @Override
    public boolean delUserTreeByParent(String parentId) {
        int count = (int) oracleDataSet.selectCount(UserTree.class, "parentUserId=?", parentId);
        if (0 != count) {
            return oracleDataSet.delete(UserTree.class, "parentUserId=?", parentId);
        } else {
            return true;
        }

    }


    //更新userTree 删除子节点对应的用户关系树
    @Override
    public boolean delUserTreeByChild(String childId) {
        int count = (int) oracleDataSet.selectCount(UserTree.class, "childUserId=?", childId);
        if (0 != count) {
            return oracleDataSet.delete(UserTree.class, "childUserId=?", childId);
        } else {
            return true;
        }
    }


    //根据childUserId查找parentUserId
    @Override
    public String findParentUserId(String childUserId) {
        return oracleDataSet.selectColumn(UserTree.class, "parentUserId", "childUserId=?", childUserId);
    }


}
