package com.crscd.passengerservice.authority.serviceinterface;


import com.crscd.passengerservice.authority.dto.ActionMessage;
import com.crscd.passengerservice.authority.po.Action;

import java.util.List;

/**
 * Created by zs
 * on 2017/8/7.
 */
public interface ActionService {
    //增加权限
    boolean addAction(List<String> actionNames);

    //删除权限
    boolean delAction(List<String> actionNames);

    //编辑权限说明
    ActionMessage editAction(String actionName, String actionDescription);

    //获取全部权限
    List<Action> getAllAction();

    //获取全部权限名
    List<String> getAllActionName();

    //删除权限角色对应列表
    boolean delRoleActionByAction(String actionName);
}
