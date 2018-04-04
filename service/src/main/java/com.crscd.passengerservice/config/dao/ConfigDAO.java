package com.crscd.passengerservice.config.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.config.po.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/5
 * Time: 16:32
 */
public class ConfigDAO {
    private final String condition = "stationName=?";
    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    // 从数据库获取广播区到二级区的对应列表
    public List<BroadcastArea> getBroadcastZoneRegionMapByStation(String station) {
        return dataSet.selectListWithCondition(BroadcastArea.class, condition, station);
    }

    // 从数据库获取全部车站配置信息列表
    public List<StationConfigBean> getStationConfigList() {
        return dataSet.selectList(StationConfigBean.class);
    }

    // 根据站名从数据库获取二级区配置列表
    public List<StationRegionConfigBean> getStationRegionConfig(String station) {
        return dataSet.selectListWithCondition(StationRegionConfigBean.class, condition, station);
    }

    // 根据站名从数据库获取站台和股道对应关系
    public List<TrackPlatformMapBean> getTrackPlatformMapByStation(String station) {
        return dataSet.selectListWithCondition(TrackPlatformMapBean.class, condition, station);
    }

    // 读取systemconfig
    public List<SoftwareInfoBean> getSoftwareInfo() {
        return dataSet.selectList(SoftwareInfoBean.class);
    }

    // 更新systemConfig中的clientVersion
    public boolean updateSystemConfig(String clientVersion) {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("clientVersion", clientVersion);
        return dataSet.update(SoftwareInfoBean.class, fieldMap, "");
    }

    // 从数据库读取screen config
    public List<ScreenConfig> getScreenConfigByStation(String stationName) {
        String condition = "stationName=?";
        return dataSet.selectListWithCondition(ScreenConfig.class, condition, stationName);
    }

    // 从数据库读取screen config
    public List<ScreenConfig> getScreenConfigByStationAndType(String stationName, ScreenTypeEnum type) {
        String condition = "stationName=? AND ScreenType=?";
        return dataSet.selectListWithCondition(ScreenConfig.class, condition, stationName, type);
    }

    // 根据screenID查询screen config
    public ScreenConfig getScreenConfigByScreenID(int id) {
        String condition = "screenID=?";
        return dataSet.select(ScreenConfig.class, condition, id);
    }

    //根据站名查询综显服务器IP
    public String getScreenServerIpByStationName(String stationName) {
        ScreenCtrlServerConfigBean serverConfigBean = dataSet.select(ScreenCtrlServerConfigBean.class, "stationName = ?", stationName);
        return serverConfigBean.getIp();
    }


}
