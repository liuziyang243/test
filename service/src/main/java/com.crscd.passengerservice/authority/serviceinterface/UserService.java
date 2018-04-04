package com.crscd.passengerservice.authority.serviceinterface;

import com.crscd.passengerservice.authority.dto.UserInfoMessage;
import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.authority.po.Role;
import com.crscd.passengerservice.authority.po.UserInfo;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.List;
import java.util.Map;


/**
 * Created by zs
 * on 2017/8/4.
 *
 * @author zs
 */
public interface UserService {
    //增加用户
    UserInfoMessage addUser(UserInfo user, UserInfo loginUser);

    //修改用户基本信息
    UserInfoMessage editUser(String userId, String userName, String password, String description, List<String> stationNames);

    //检查用户是否存在，存在即更新，不存在即创建
    Map<UserInfo, ResultMessage> updateUser(UserInfo user);

    //删除用户
    boolean delUser(String userId);

    //添加用户-角色关系
    boolean addUserRole(String userId, List<String> roleIds);

    //删除用户-角色关系
    boolean delUserRole(String userId, List<String> roleIds);

    //删除用户对应的所有角色关系
    Boolean delUserAllRole(String userId);

    //获取用户对应角色id
    List<String> findRoleIdsByUserId(String userId);

    //获取用户对应角色
    List<Role> findRolesByUserId(String userId);

    //获取用户对应权限 true:用户具备的权限  false:用户不具备的权限
    List<Action> findActionsByUserId(String userId, boolean type);

    //获取用户列表
    List<UserInfo> getUserList(String userName);

    //根据用户名获取用户
    UserInfo getUserByName(String userName);

    //根据用户id获取用户
    UserInfo getUserById(String userId);

    //增加userTree
    boolean addUserTree(String childUserId, String parentUserId);

    //更新userTree 删除父节点对应的用户关系树
    boolean delUserTreeByParent(String parentId);

    //更新userTree 删除子节点对应的用户关系树
    boolean delUserTreeByChild(String childId);

    //根据childUserId查找parentUserId
    String findParentUserId(String childUserId);
}
