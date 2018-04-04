package com.crscd.passengerservice.authority.serviceinterface.implement;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.authority.dto.ActionMessage;
import com.crscd.passengerservice.authority.po.Action;
import com.crscd.passengerservice.authority.po.RoleAction;
import com.crscd.passengerservice.authority.serviceinterface.ActionService;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs
 * on 2017/8/7.
 */
public class ActionServiceImpl implements ActionService {

    private DataSet oracleDataSet;

    public ActionServiceImpl(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    //增加权限
    @Override
    public boolean addAction(List<String> actionNames) {
        if (null == actionNames || actionNames.size() == 0) {
            return false;
        } else {
            List<Action> actions = new ArrayList<>();
            for (String actionName : actionNames) {
                Action action = new Action();
                action.setActionName(actionName);
                actions.add(action);
            }
            return oracleDataSet.insertList(actions);
        }
    }

    //删除权限
    @Override
    public boolean delAction(List<String> actionNames) {
        if (null == actionNames || actionNames.size() == 0) {
            return false;
        } else {
            boolean result = true;
            for (String actionName : actionNames) {
                result = oracleDataSet.delete(Action.class, "actionName = ?", actionName) && result;
            }
            return result;
        }
    }

    //编辑权限说明
    @Override
    public ActionMessage editAction(String actionName, String actionDescription) {
        if (null == actionName) {
            return new ActionMessage(new ResultMessage(64));
        }
        Action check = oracleDataSet.select(Action.class, "actionName = ?", actionName);
        if (null == check) {
            return new ActionMessage(new ResultMessage(64));
        }
        if (null != actionDescription) {
            check.setActionDescription(actionDescription);
            if (oracleDataSet.update(check, "actionName")) {
                return new ActionMessage(check);
            } else {
                return new ActionMessage(new ResultMessage(100));
            }
        } else {
            return new ActionMessage(check);
        }

    }

    //获取全部权限
    @Override
    public List<Action> getAllAction() {
        return oracleDataSet.selectList(Action.class);
    }

    //获取全部权限名
    @Override
    public List<String> getAllActionName() {
        return oracleDataSet.selectColumnList(Action.class, "actionName", "", "");
    }

    //删除权限角色对应列表
    @Override
    public boolean delRoleActionByAction(String actionName) {
        if (oracleDataSet.selectCount(RoleAction.class, "actionName = ?", actionName) > 0) {
            return oracleDataSet.delete(RoleAction.class, "actionName = ?", actionName);
        } else {
            return true;
        }
    }
}
