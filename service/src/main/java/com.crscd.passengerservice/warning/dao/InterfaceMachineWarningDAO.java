package com.crscd.passengerservice.warning.dao;

import com.crscd.passengerservice.warning.objectdomain.InterfaceMachineWarningInfo;
import com.crscd.passengerservice.warning.po.InterfaceMachineWarningBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 15:10
 */
public class InterfaceMachineWarningDAO extends AbstractWarningDAO<InterfaceMachineWarningBean, InterfaceMachineWarningInfo> {
    @Override
    public List<InterfaceMachineWarningBean> getBeanList() {
        return getBeanList(InterfaceMachineWarningBean.class);
    }

    @Override
    public boolean confirm(long id, String user) {
        return confirm(id, user, InterfaceMachineWarningBean.class);
    }

    @Override
    public List<InterfaceMachineWarningInfo> getWarningList() {
        List<InterfaceMachineWarningBean> beans = getBeanList();
        List<InterfaceMachineWarningInfo> infoList = new ArrayList<>();
        for (InterfaceMachineWarningBean bean : beans) {
            infoList.add(new InterfaceMachineWarningInfo(bean));
        }
        return infoList;
    }

    @Override
    public boolean insertWarning(InterfaceMachineWarningInfo info) {
        return insert(new InterfaceMachineWarningBean(info));
    }

    @Override
    public boolean isExitSameWarning(InterfaceMachineWarningInfo info, int interval) {
        return isExitSameWarning(InterfaceMachineWarningInfo.class, info, interval);
    }


}
