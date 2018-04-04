package com.crscd.passengerservice.soapinterface.implement;

import com.crscd.passengerservice.authority.dto.*;
import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.authority.po.Role;
import com.crscd.passengerservice.authority.serviceinterface.AuthorityBusiness;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.dto.SystemInfoDTO;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.config.serviceinterface.SystemConfigInterface;
import com.crscd.passengerservice.config.serviceinterface.SystemInfoInterface;
import com.crscd.passengerservice.log.dto.OperationLogDTO;
import com.crscd.passengerservice.log.serviceinterface.OperationLogInterface;
import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.soapinterface.SystemServiceInterface;
import com.crscd.passengerservice.warning.dto.DeviceWarningDTO;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;
import com.crscd.passengerservice.warning.serviceinterface.WarningServiceInterface;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
@WebService(serviceName = "SystemService",
        targetNamespace = "http://passengerservice.crscd.com/service",
        endpointInterface = "com.crscd.passengerservice.soapinterface.SystemServiceInterface")
@HandlerChain(file = "handlersdef.xml")
public class SystemServiceInterfaceImpl implements SystemServiceInterface {
    private AuthorityBusiness authorityBusinessInterface;
    private SystemConfigInterface systemConfigInterface;
    private SystemInfoInterface systemInfoInterface;
    private WarningServiceInterface warningServiceInterface;
    private OperationLogInterface operationLogInterface;

    @Resource
    private WebServiceContext context;

    public SystemServiceInterfaceImpl() {
    }

    public WebServiceContext getContext() {
        return context;
    }

    public void setWarningServiceInterface(WarningServiceInterface warningServiceInterface) {
        this.warningServiceInterface = warningServiceInterface;
    }

    public void setSystemInfoInterface(SystemInfoInterface systemInfoInterface) {
        this.systemInfoInterface = systemInfoInterface;
    }

    public void setAuthorityBusinessInterface(AuthorityBusiness authorityBusinessInterface) {
        this.authorityBusinessInterface = authorityBusinessInterface;
    }

    public void setSystemConfigInterface(SystemConfigInterface systemConfigInterface) {
        this.systemConfigInterface = systemConfigInterface;
    }

    public void setOperationLogInterface(OperationLogInterface operationLogInterface) {
        this.operationLogInterface = operationLogInterface;
    }

    //登录
    @Override
    public UserDtoMessage logIn(String userName, String password, String ipAddr, List<String> actionNameList, String clientVersion) {
        return authorityBusinessInterface.logIn(userName, password, ipAddr, actionNameList, clientVersion);
    }

    //注销
    @Override
    public ResultMessage logOut(String userName, String ipAddr) {
        return authorityBusinessInterface.logOut(userName, ipAddr);
    }

    //获取用户列表(当前用户创建的用户列表)
    @Override
    public List<UserInfoDto> getUserList(String logInName) {
        return authorityBusinessInterface.getUserList(logInName);
    }

    //获取单个用户的全部信息
    @Override
    public UserInfoDto getUserInfo(String userId, String logInName) {
        return authorityBusinessInterface.getUserInfo(userId, logInName);
    }

    //增加用户
    @Override
    public UserInfoMessage addUser(String userName, String passWord, String userDescription, List<String> stationNameList, String logInName) {
        return authorityBusinessInterface.addUser(userName, passWord, userDescription, stationNameList, logInName);
    }

    //修改用户基本信息
    @Override
    public UserInfoMessage editUser(String userId, String userName, String password, String description, List<String> stationNames) {
        return authorityBusinessInterface.editUser(userId, userName, password, description, stationNames);
    }

    //修改用户对应角色
    @Override
    public ResultMessage editUserRole(String userId, List<String> roleIdList) {
        return authorityBusinessInterface.editUserRole(userId, roleIdList);
    }

    //删除用户
    @Override
    public ResultMessage delUser(String userId) {
        return authorityBusinessInterface.delUser(userId);
    }

    //获取全部角色
    @Override
    public List<Role> getAllRole() {
        return authorityBusinessInterface.getAllRole();
    }

    //获取角色列表（当前用户拥有的角色列表）
    @Override
    public List<Role> getRoleList(String userName) {
        return authorityBusinessInterface.getRoleList(userName);
    }

    //增加角色
    @Override
    public RoleMessage addRole(Role role) {
        return authorityBusinessInterface.addRole(role);
    }

    //修改角色基本信息
    @Override
    public RoleMessage editRole(String roleId, String roleName, String roleDescription) {
        return authorityBusinessInterface.editRole(roleId, roleName, roleDescription);
    }

    //修改角色对应权限
    @Override
    public ResultMessage editRoleAction(String roleId, List<String> actionNames) {
        return authorityBusinessInterface.editRoleAction(roleId, actionNames);
    }

    //删除角色
    @Override
    public ResultMessage delRole(String roleId) {
        return authorityBusinessInterface.delRole(roleId);
    }

    //获取全部权限列表
    @Override
    public List<Action> getAllAction() {
        return authorityBusinessInterface.getAllAction();
    }

    //获取权限列表 （选中的角色拥有的权限列表）
    @Override
    public List<Action> getActionList(String roleId) {
        return authorityBusinessInterface.getActionList(roleId);
    }

    //修改权限描述
    @Override
    public ActionMessage editAction(String actionName, String actionDescription) {
        return authorityBusinessInterface.editAction(actionName, actionDescription);
    }

    //保持与前台联系，更新用户在线/离线状态
    @Override
    public ResultMessage ipStatus(String userName, String ipAddr) {
        return authorityBusinessInterface.ipStatus(userName, ipAddr);
    }

    //获取用户拥有的权限列表
    @Override
    public List<Action> getActionListByUserId(String userId) {
        return authorityBusinessInterface.getActionListByUserId(userId);
    }

    //获取用户不具备的权限列表
    @Override
    public List<Action> getOtherActionListByUserId(String userId) {
        return authorityBusinessInterface.getOtherActionListByUserId(userId);
    }

    @Override
    public SystemInfoDTO getSystemInfo(String lan) {
        return systemConfigInterface.getSystemInfo(lan);
    }

    @Override
    public ArrayList<ScreenConfig> getScreenConfigInfoByStationAndType(String station, ScreenTypeEnum type) {
        return systemConfigInterface.getScreenConfigInfoByStationAndType(station, type);
    }

    @Override
    public TrainDirectionEnum getTrainDirection(String startStation, String finalStation) {
        return systemInfoInterface.getTrainDirection(startStation, finalStation);
    }

    @Override
    public List<DeviceWarningDTO> getDeviceWarningListByStation(String station, SystemEnum system, String area, String startTime, String endTime) {
        return warningServiceInterface.getDeviceWarningListByStation(station, system, area, startTime, endTime);
    }

    @Override
    public ResultMessage confirmWarningMessage(long id, String user) {
        return warningServiceInterface.confirmWarningMessage(id, user);
    }


    @Override
    public List<String> getOperatorList(String currentUser) {
        return operationLogInterface.getOperatorList(currentUser);
    }

    /*
     * 日志查询
     */
    @Override
    public List<OperationLogDTO> logQueryByCondition(String operationTimeStart, String operationTimeEnd, String operator, String serviceType, String language, String stationName, String currentUser) {
        return operationLogInterface.logQueryByCondition(operationTimeStart, operationTimeEnd, operator, serviceType, language, stationName, currentUser);
    }


    //更新权限列表
    //@Override
    //public ResultMessage updateActionList(List<String> actionNameList, String clientVersion) {
    //    return authorityBusinessInterface.updateActionList(actionNameList, clientVersion);
    //}

    //版本握手
    //@Override
    //public ResultMessage versionConfirm(String clientVersion) {
    //    return authorityBusinessInterface.versionConfirm(clientVersion);
    //}
}
