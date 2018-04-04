package com.crscd.passengerservice.authority.serviceinterface.implement;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.job.IndependentJobHelper;
import com.crscd.framework.job.IndependentJobManager;
import com.crscd.framework.util.net.IPUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.authority.business.UserStatusDetectJob;
import com.crscd.passengerservice.authority.business.UserStatusManager;
import com.crscd.passengerservice.authority.domainobject.UserStatus;
import com.crscd.passengerservice.authority.dto.*;
import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.authority.po.Role;
import com.crscd.passengerservice.authority.po.UserInfo;
import com.crscd.passengerservice.authority.serviceinterface.ActionService;
import com.crscd.passengerservice.authority.serviceinterface.AuthorityBusiness;
import com.crscd.passengerservice.authority.serviceinterface.RoleService;
import com.crscd.passengerservice.authority.serviceinterface.UserService;
import com.crscd.passengerservice.authority.util.PasswordHelper;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.dto.StationInfoDTO;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zs
 * on 2017/8/4.
 */
public class AuthorityBusinessImpl implements AuthorityBusiness {

    private UserService userService;
    private RoleService roleService;
    private ActionService actionService;
    private ConfigManager config;
    private UserStatusManager userStatusManager;
    private Map<String, String> stationIpRange = new HashMap<>();
    private List<String> stationCodes = new ArrayList<>();

    public AuthorityBusinessImpl(UserService userService, ConfigManager config) {
        this.config = config;
        this.userService = userService;
        stationCodes = config.getAllStationCode();
        //如果stationConfig中没有标记车站0，则需要增加0代表中心的客户端
        if (!stationCodes.contains("0")) {
            stationCodes.add("0");
        }
        //将超级用户写入或更新到数据库
        UserInfo superUser = new UserInfo();
        superUser.setUserName(ServiceConstant.SUPER_ADMIN);
        superUser.setPassword(ConfigHelper.getString("AdminPassword"));
        superUser.setUserLevel(1);
        String currentStationCode = ConfigHelper.getString("stationCode");//获取当前后台的定位，车站/中心
        if ("0".equals(currentStationCode)) {
            superUser.setStationNameList(config.getAllStationName());
        } else {
            String currentStationName = config.getStationNameByCode(currentStationCode);
            List<String> stationNames = new ArrayList<>();
            stationNames.add(currentStationName);
            superUser.setStationNameList(stationNames);
        }
        userService.updateUser(superUser);
        //从配置文件中获取各个车站的ip范围
        for (String stationCode : stationCodes) {
            String key = "station_" + stationCode;
            String ipRange = ConfigHelper.getString(key);
            stationIpRange.put(stationCode, ipRange);
        }
        IndependentJobHelper clientConnectJobHelper = IndependentJobManager.getClientConnectJobHelper();
        clientConnectJobHelper.startRepeatedJobNow(UserStatusDetectJob.class, ConfigHelper.getInt("travelService.CheckUserStatusTime", 30));
    }

    /**
     * 先比较大版本号，然后比较小版本号(version1=version2，返回0；version1>version2,返回1；version1<version2 返回-1)
     *
     * @param version1
     * @param version2
     * @return
     */
    private static int versionCompare(String[] version1, String[] version2) {
        int Length1 = version1.length;
        int Length2 = version2.length;

        int[] versions1 = new int[Length1];
        int[] versions2 = new int[Length2];
        for (int i = 0; i < 2; i++) {
            versions1[i] = Integer.parseInt(version1[i]);
            versions2[i] = Integer.parseInt(version2[i]);
        }
        if (versions1[0] > versions2[0]) {
            return 1;
        } else if (versions1[0] < versions2[0]) {
            return -1;
        } else {
            //比较小版本号
            if (versions1[1] > versions2[1]) {
                return 1;
            } else if (versions1[1] < versions2[1]) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void setActionService(ActionService actionService) {
        this.actionService = actionService;
    }

    public void setUserStatusManager(UserStatusManager userStatusManager) {
        this.userStatusManager = userStatusManager;
    }

    //登录
    @Override
    public UserDtoMessage logIn(String userName, String password, String ipAddr, List<String> actionNameList, String clientVersion) {
        //流程为版本握手--更新权限--用户鉴权
        //版本握手
        LocalDateTime startTime = LocalDateTime.now();
        ResultMessage resultMessage = versionConfirm(clientVersion);
        int statusCode = resultMessage.getStatusCode();
        if (50 == statusCode) {
            return new UserDtoMessage(resultMessage);
        } else if (51 == statusCode) {//更新权限
            updateActionList(actionNameList, clientVersion);
        }
        //用户鉴权
        UserDtoMessage result;
        //验证ip地址
        String stationCode = ConfigHelper.getString("stationCode", "0");//默认为中心后台
        String currentRange;
        if ("0".equals(stationCode)) {
            boolean ipReady = false;
            for (String code : stationCodes) {
                currentRange = stationIpRange.get(code);
                if (null == currentRange || "".equals(currentRange)) {
                    continue;
                }
                if (IPUtil.verifyIpAddr(ipAddr, currentRange)) {
                    ipReady = true;
                    break;
                }
            }
            if (!ipReady) {
                result = new UserDtoMessage(new ResultMessage(63));
                return result;
            }
        } else {
            currentRange = stationIpRange.get(stationCode);
            if (null == currentRange) {
                result = new UserDtoMessage(new ResultMessage(62));
                return result;
            } else {
                if (!IPUtil.verifyIpAddr(ipAddr, currentRange)) {
                    result = new UserDtoMessage(new ResultMessage(63));
                    return result;
                }
            }
        }

        UserInfo user = userService.getUserByName(userName);
        //未找到账号
        if (null == user) {
            result = new UserDtoMessage(new ResultMessage(60));
            return result;
        }
        //账号用户名密码不匹配
        if (!PasswordHelper.verifyPassword(user, password)) {
            result = new UserDtoMessage(new ResultMessage(60));
            return result;
        }
        //账号已经在其他地点登录
        if (null != userStatusManager.getStatus(userName)) {
            result = new UserDtoMessage(new ResultMessage(61));
            return result;
        }
        //用户名密码验证成功
        UserDto userDto = new UserDto();
        List<Action> actions;
        if (userName.equals(ServiceConstant.SUPER_ADMIN)) {
            actions = actionService.getAllAction();
        } else {
            actions = userService.findActionsByUserId(user.getUserId(), true);
        }
        List<String> stationNames = user.getStationNameList();
        List<StationInfoDTO> stationInfoDTOS = config.getStationInfosByNames(stationNames);
        userDto.setStationInfoDTOS(stationInfoDTOS);
        userDto.setActions(actions);
        userDto.setUserLevel(user.getUserLevel());
        UserStatus userStatus = new UserStatus(userName, ipAddr);
        userStatusManager.putStatus(userName, userStatus);
        result = new UserDtoMessage(userDto);
        LocalDateTime finalTime = LocalDateTime.now();
        System.out.println("startTime:" + startTime.toLocalTime());
        System.out.println("finalTime:" + finalTime.toLocalTime());
        return result;

    }

    //注销
    @Override
    public ResultMessage logOut(String userName, String ipAddr) {
        UserStatus userStatus = userStatusManager.getStatus(userName);
        if (null != userStatus) {
            String logInAddr = userStatus.getIpAddr();
            if (logInAddr.equals(ipAddr)) {
                userStatusManager.removeStatus(userName);
                return new ResultMessage();
            } else {
                return new ResultMessage(63);
            }
        } else {
            return new ResultMessage(64);
        }
    }

    //获取用户列表(当前用户创建的用户列表)
    @Override
    public List<UserInfoDto> getUserList(String logInName) {
        List<UserInfo> userInfos = userService.getUserList(logInName);
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        for (UserInfo user : userInfos) {
            UserInfoDto userInfoDto = packageUserDto(user, logInName);
            userInfoDtoList.add(userInfoDto);
        }
        return userInfoDtoList;
    }

    //获取单个用户的全部信息
    @Override
    public UserInfoDto getUserInfo(String userId, String logInName) {
        UserInfo user = userService.getUserById(userId);
        return packageUserDto(user, logInName);

    }

    //组装userDTO
    private UserInfoDto packageUserDto(UserInfo user, String logInName) {
        UserInfoDto userInfoDto = new UserInfoDto(user);
        String userName = user.getUserName();
        String userId = user.getUserId();
        //根据userId找到其创建者的userid
        if (userName.equals(ServiceConstant.SUPER_ADMIN)) {//登录用户为超级用户
            userInfoDto.setMaxStationNameList(userInfoDto.getStationNameList());
            userInfoDto.setRoleList(roleService.getAllRole());
            userInfoDto.setMaxRoleList(userInfoDto.getRoleList());
        } else if (userName.equals(logInName)) {//登录用户查看自己的信息
            userInfoDto.setMaxStationNameList(userInfoDto.getStationNameList());
            userInfoDto.setRoleList(userService.findRolesByUserId(userId));
            userInfoDto.setMaxRoleList(userInfoDto.getRoleList());
        } else {
            userInfoDto.setRoleList(userService.findRolesByUserId(userId));
            String parentUserId = userService.findParentUserId(userId);
            UserInfo parentUserInfo = userService.getUserById(parentUserId);
            userInfoDto.setMaxStationNameList(parentUserInfo.getStationNameList());
            userInfoDto.setMaxRoleList(userService.findRolesByUserId(parentUserId));
        }
        return userInfoDto;
    }

    //增加用户
    @Override
    public UserInfoMessage addUser(String userName, String passWord, String userDescription, List<String> stationNameList, String logInName) {
        UserInfo user = new UserInfo(userName, passWord, userDescription, stationNameList);
        UserInfo logInUser = userService.getUserByName(logInName);
        int logInUserLevel = logInUser.getUserLevel();
        switch (logInUserLevel) {
            case 1:
                user.setUserLevel(2);
                break;
            case 2:
                user.setUserLevel(3);
                break;
            default:
                return new UserInfoMessage(new ResultMessage(103));
        }
        UserInfoMessage addResult = userService.addUser(user, logInUser);
        //增加登录用户与被创建用户的创建关系
        if (addResult.isResult()) {
            String logInUserId = logInUser.getUserId();
            userService.addUserTree(user.getUserId(), logInUserId);
        }
        return addResult;
    }

    //修改用户
    @Override
    public UserInfoMessage editUser(String userId, String userName, String password, String description, List<String> stationNames) {
        return userService.editUser(userId, userName, password, description, stationNames);
    }

    //修改用户对应角色
    @Override
    public ResultMessage editUserRole(String userId, List<String> roleIds) {
        List<String> userRoleIds = userService.findRoleIdsByUserId(userId);
        UserInfo userInfo = userService.getUserById(userId);
        int userLevel = userInfo.getUserLevel();
        String userName = userInfo.getUserName();
        boolean result = true;
        List<String> addRoleIds = new ArrayList<>();//需要增加的角色
        List<String> delRoleIds = new ArrayList<>();//需要删除的角色
        if (roleIds.size() != 0) {
            addRoleIds.addAll(roleIds);
            addRoleIds.removeAll(userRoleIds);
        }
        if (userRoleIds.size() != 0) {
            delRoleIds.addAll(userRoleIds);
            delRoleIds.removeAll(roleIds);
        }
        if (addRoleIds.size() != 0) {
            result = userService.addUserRole(userId, addRoleIds);
        }
        if (delRoleIds.size() != 0) {
            //如果被修改的用户为二级用户，并且删除了部分原始角色，则其建立的三级用户也会删除此角色
            if (userLevel == 2) {
                List<UserInfo> users = userService.getUserList(userName);
                for (UserInfo user : users) {
                    String thirdUserId = user.getUserId();
                    if (!thirdUserId.equals(userId)) {
                        List<String> thirdUserRoleIds = userService.findRoleIdsByUserId(thirdUserId);
                        List<String> thirdDelRoleIds = new ArrayList<>();
                        thirdDelRoleIds.addAll(thirdUserRoleIds);
                        thirdDelRoleIds.retainAll(delRoleIds);
                        if (thirdDelRoleIds.size() != 0) {
                            result = userService.delUserRole(thirdUserId, thirdDelRoleIds) && result;
                        }
                    }
                }
            }
            result = userService.delUserRole(userId, delRoleIds) && result;
        }
        if (result) {
            return new ResultMessage();
        } else {
            return new ResultMessage(100);
        }

        /*
        List<String> userRoleIds = userService.findRoleIdsByUserId(userId);
        boolean result = true;
        if (userRoleIds.size() != 0) {
            result = userService.delUserAllRole(userId);
        }
        result = userService.addUserRole(userId, roleIds) && result;
        if(result)
            return new ResultMessage();
        else
            return new ResultMessage(100);
            */
    }

    //删除用户,登录用户不能删除自己；一级用户可以删除二级用户（级联删除三级用户）；一级和二级用户可以删除三级用户
    @Override
    public ResultMessage delUser(String userId) {
        boolean result = true;
        //如果被删除用户为二级用户，则其创建的三级用户也将被删除
        UserInfo userInfo = userService.getUserById(userId);
        if (userInfo.getUserLevel() == 2) {
            List<UserInfo> users = userService.getUserList(userInfo.getUserName());
            if (users.size() != 1) {
                for (UserInfo user : users) {
                    if (!user.getUserId().equals(userId)) {
                        ResultMessage delResult = delUser(user.getUserId());
                        if (!delResult.getResult()) {
                            result = false;
                        }
                    }
                }
            }
        }
        //删除用户角色表
        if (result) {
            result = userService.delUserAllRole(userId);
        }
        //删除用户表
        if (result) {
            result = userService.delUser(userId);
        }
        //删除用户关系表
        if (result) {
            result = userService.delUserTreeByChild(userId);
        }
        if (result) {
            return new ResultMessage();
        } else {
            return new ResultMessage(100);
        }
    }

    //获取全部角色
    @Override
    public List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    //获取角色列表（当前用户拥有的角色列表）
    /*
    @Override
    public List<Role> getRoleList(String logInName) {
        if (logInName.equals(ServiceConstant.SUPER_ADMIN))
            return roleService.getAllRole();
        else {
            UserInfo user = userService.getUserByName(logInName);
            String userId = user.getUserId();
            return userService.findRolesByUserId(userId);
        }
    }
    */
    @Override
    public List<Role> getRoleList(String userName) {
        if (userName.equals(ServiceConstant.SUPER_ADMIN)) {
            return roleService.getAllRole();
        } else {
            UserInfo userInfo = userService.getUserByName(userName);
            return userService.findRolesByUserId(userInfo.getUserId());
        }
    }

    //增加角色
    @Override
    public RoleMessage addRole(Role role) {
        return roleService.addRole(role);
    }

    //修改角色基本信息
    @Override
    public RoleMessage editRole(String roleId, String roleName, String roleDescription) {
        return roleService.editRole(roleId, roleName, roleDescription);
    }

    //修改角色对应权限
    @Override
    public ResultMessage editRoleAction(String roleId, List<String> actionNames) {
        List<String> roleActionNames = roleService.findActionNamesByRoleId(roleId);
        boolean result = true;
        if (null != roleActionNames) {
            result = roleService.delRoleAllAction(roleId);
        }
        result = roleService.addRoleAction(roleId, actionNames) && result;
        if (result) {
            return new ResultMessage();
        } else {
            return new ResultMessage(100);
        }
    }

    //删除角色
    @Override
    public ResultMessage delRole(String roleId) {
        //删除角色权限表
        boolean result = roleService.delRoleAllAction(roleId);
        //删除用户角色表
        result = roleService.delUserRoleByRoleId(roleId) && result;
        //删除角色表
        result = roleService.delRole(roleId) && result;
        if (result) {
            return new ResultMessage();
        } else {
            return new ResultMessage(100);
        }
    }

    //获取全部权限列表
    @Override
    public List<Action> getAllAction() {
        return actionService.getAllAction();
    }

    //获取权限列表 （选中的角色拥有的权限列表）
    @Override
    public List<Action> getActionList(String roleId) {
        return roleService.findActionsByRoleId(roleId);
    }

    //修改权限描述
    @Override
    public ActionMessage editAction(String actionName, String actionDescription) {
        return actionService.editAction(actionName, actionDescription);
    }

    @Override
    public ResultMessage ipStatus(String userName, String ipAddr) {
        UserStatus userStatus = userStatusManager.getStatus(userName);
        if (null == userStatus) {
            UserStatus userStatusNew = new UserStatus(userName, ipAddr);
            userStatusManager.putStatus(userName, userStatusNew);
        } else {
            userStatus.updateTime();
        }
        return new ResultMessage();
    }

    //获取用户拥有的权限列表
    @Override
    public List<Action> getActionListByUserId(String userId) {
        UserInfo userInfo = userService.getUserById(userId);
        if (userInfo.getUserName().equals(ServiceConstant.SUPER_ADMIN)) {
            return actionService.getAllAction();
        } else {
            return userService.findActionsByUserId(userId, true);
        }
    }

    //获取用户不具备的权限列表
    @Override
    public List<Action> getOtherActionListByUserId(String userId) {
        return userService.findActionsByUserId(userId, false);
    }

    //更新权限列表
    private ResultMessage updateActionList(List<String> actionNameList, String clientVersion) {
        List<String> actionNameListHistory = actionService.getAllActionName();
        List<String> actionNamesAdd = new ArrayList<>();
        List<String> actionNamesDel = new ArrayList<>();
        actionNamesAdd.addAll(actionNameList);
        actionNamesAdd.removeAll(actionNameListHistory);
        actionNamesDel.addAll(actionNameListHistory);
        actionNamesDel.removeAll(actionNameList);
        boolean result = true;
        //删除的权限
        if (actionNamesDel.size() != 0) {
            result = actionService.delAction(actionNamesDel);
            for (String actionName : actionNamesDel) {
                result = actionService.delRoleActionByAction(actionName);
            }
        }
        //新增的权限
        if (actionNamesAdd.size() != 0) {
            result = actionService.addAction(actionNamesAdd) && result;
        }
        //更新前台版本号到db
        config.updateSystemVersion(clientVersion);
        if (result) {
            return new ResultMessage();
        } else {
            return new ResultMessage(100);
        }
    }

    //版本握手
    private ResultMessage versionConfirm(String clientVersion) {
        //判断后台是否支持此前台版本号
        String acceptVersion = ConfigHelper.getString("clientVersion");
        String[] acceptVersions = acceptVersion.split("\\.");
        String[] versions = clientVersion.split("\\.");
        if (versions.length != 2 || versions[1].length() < 2 || (versionCompare(versions, acceptVersions) < 0)) {
            return new ResultMessage(50);
        } else {
            //判断是否需要进行数据库权限的更新
            String dbVersion = config.getSystemVersion();//从数据库读取前台版本号
            if (null == dbVersion) {
                return new ResultMessage(51);
            } else {
                String[] dbVersions = dbVersion.split("\\.");
                int versionCompare = versionCompare(versions, dbVersions);
                if (versionCompare == 0) {
                    return new ResultMessage();
                } else if (versionCompare < 0) {
                    return new ResultMessage(50);
                } else {
                    return new ResultMessage(51);
                }
            }

        }
    }

}
