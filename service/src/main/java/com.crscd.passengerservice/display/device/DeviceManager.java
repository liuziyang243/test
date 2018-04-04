package com.crscd.passengerservice.display.device;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.po.ScreenCtrlServerConfigBean;
import com.crscd.passengerservice.display.format.po.ScreenManageBean;
import com.crscd.passengerservice.display.screencontrolserver.business.DeviceManage;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenControlInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusAskInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusInfo;
import com.crscd.passengerservice.display.util.ScreenInfoUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/12/7.
 */
public class DeviceManager {
    private DataSet oracleDataSet;
    private DeviceManage deviceManage;

    public void setDeviceManage(DeviceManage deviceManage) {
        this.deviceManage = deviceManage;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    /**
     * 全线设备控制
     *
     * @param action "控制屏幕动作"
     * @return "各站设备控制结果 key: 站名 value: 下发结果"
     */
    public HashMap<String, Boolean> deviceControlLine(String action) {
        //各站屏幕控制结果 key: 站名 value：控制结果
        Map<String, Boolean> controlResult = new HashMap<>();
        List<ScreenCtrlServerConfigBean> serverConfigBeanList = oracleDataSet.selectList(ScreenCtrlServerConfigBean.class);
        for (ScreenCtrlServerConfigBean bean : serverConfigBeanList) {
            //站名
            String stationName = bean.getStationName();
            //单站所有屏幕控制信息
            List<ScreenControlInfo> screenControlInfos = new ArrayList<>();
            //单站所有屏幕信息
            List<ScreenConfig> screenConfigs = oracleDataSet.selectListWithCondition(ScreenConfig.class, "stationName = ?", stationName);
            for (ScreenConfig info : screenConfigs) {
                ScreenControlInfo screenControlInfo = new ScreenControlInfo();
                screenControlInfo.setIp(info.getScreenIp());
                screenControlInfo.setType(info.getControllerType().toString());
                screenControlInfo.setAction(action);
                screenControlInfos.add(screenControlInfo);
            }
            boolean deviceControlResult = deviceManage.screenControlSingleStation(screenControlInfos, stationName);
            controlResult.put(stationName, deviceControlResult);
        }
        return (HashMap<String, Boolean>) controlResult;
    }

    /**
     * 单站设备控制
     *
     * @param action      "控制屏幕动作"
     * @param stationName "站名"
     * @return
     */
    public boolean deviceControlStation(String action, String stationName) {
        List<ScreenControlInfo> screenControlInfos = new ArrayList<>();
        List<ScreenConfig> screenConfigs = oracleDataSet.selectListWithCondition(ScreenConfig.class, "stationName = ?", stationName);
        for (ScreenConfig info : screenConfigs) {
            ScreenControlInfo screenControlInfo = new ScreenControlInfo();
            screenControlInfo.setIp(info.getScreenIp());
            screenControlInfo.setType(info.getControllerType().toString());
            screenControlInfo.setAction(action);
            screenControlInfos.add(screenControlInfo);
        }
        return deviceManage.screenControlSingleStation(screenControlInfos, stationName);
    }

    /**
     * 单站指定IP屏幕控制
     *
     * @param action
     * @param stationName
     * @param screenIps
     * @return
     */
    public boolean deviceControlByIps(String action, String stationName, List<String> screenIps) {
        List<ScreenControlInfo> screenControlInfos = new ArrayList<>();
        for (String ip : screenIps) {
            ScreenConfig screenConfig = oracleDataSet.select(ScreenConfig.class, "screenIp = ?", ip);
            ScreenControlInfo screenControlInfo = new ScreenControlInfo();
            screenControlInfo.setIp(ip);
            screenControlInfo.setType(screenConfig.getControllerType().toString());
            screenControlInfo.setAction(action);
            screenControlInfos.add(screenControlInfo);
        }
        return deviceManage.screenControlSingleStation(screenControlInfos, stationName);
    }

    /**
     * 单站根据IP获取屏幕状态
     *
     * @param statusAskInfos
     * @param stationName
     * @return
     */
    public List<ScreenStatusInfo> getScreenStatusByIp(List<ScreenStatusAskInfo> statusAskInfos, String stationName) {
        List<ScreenStatusInfo> screenStatusInfos = deviceManage.screenStatusIp(statusAskInfos, stationName);
        saveScreenStatus(screenStatusInfos);
        return screenStatusInfos;
    }

    /**
     * 获取单站所有屏幕状态
     *
     * @param stationName
     * @return
     */
    public List<ScreenStatusInfo> getScreenStatusStation(String stationName) {
        List<ScreenStatusInfo> screenStatusInfos = deviceManage.screenStatusStation(stationName);
        saveScreenStatus(screenStatusInfos);
        return screenStatusInfos;
    }

    private void saveScreenStatus(List<ScreenStatusInfo> screenStatusInfos) {
        for (ScreenStatusInfo info : screenStatusInfos) {
            int screenId = ScreenInfoUtil.getScreenIdByIp(info.getIp());
            ScreenManageBean screenManageBean = oracleDataSet.select(ScreenManageBean.class, "screenID = ?", screenId);
            if (null != screenManageBean) {
                screenManageBean.setOnOff(info.getState());
                oracleDataSet.update(screenManageBean);
            } else {
                screenManageBean = new ScreenManageBean();
                screenManageBean.setScreenID(screenId);
                screenManageBean.setOnOff(info.getState());
                oracleDataSet.insert(screenManageBean);
            }
        }
    }

}
