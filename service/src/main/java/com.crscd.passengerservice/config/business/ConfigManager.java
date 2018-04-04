package com.crscd.passengerservice.config.business;

import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.config.dao.ConfigDAO;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.domainobject.StationInfo;
import com.crscd.passengerservice.config.dto.StationInfoDTO;
import com.crscd.passengerservice.config.enumtype.FirstRegionEnum;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.config.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 9:01
 */
public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    //站码--车站信息
    private Map<String, StationInfo> stationInfosByCode = new HashMap<>();
    //站名--车站信息
    private Map<String, StationInfo> stationInfosByName = new HashMap<>();
    // key:站名， map-key:股道号， value-站台名称
    private Map<String, Map<Integer, String>> trackNumPlatformMapList = new HashMap<>();
    // 区域配置信息
    private Map<String, Map<FirstRegionEnum, List<StationRegionConfigBean>>> stationRegionList = new HashMap<>();
    // 广播区列表
    private Map<String, List<BroadcastArea>> stationBroadcastAreaMap = new HashMap<>();
    // 屏幕配置列表
    private Map<String, List<ScreenConfig>> screenConfigMap = new HashMap<>();
    //softwareInfo
    private SoftwareInfoBean softwareInfo;

    private ConfigDAO configDAO;

    private static boolean initFlag = false;

    public ConfigManager() {
    }

    public void setConfigDAO(ConfigDAO configDAO) {
        this.configDAO = configDAO;
    }

    private synchronized void init() {
        //从数据库读取systemConfig
        List<SoftwareInfoBean> softwareInfoBeanList = configDAO.getSoftwareInfo();
        if (softwareInfoBeanList.size() == 0) {
            this.softwareInfo = new SoftwareInfoBean();
        } else if (softwareInfoBeanList.size() != 1) {
            logger.error("softwareInfo in dataBase is wrong, please Check and restart this server");
            this.softwareInfo = softwareInfoBeanList.get(0);
        }
        //从数据库读取，初始化stationInfo
        List<StationConfigBean> stationConfigBeanList = configDAO.getStationConfigList();
        for (StationConfigBean stationConfigBean : stationConfigBeanList) {
            // 初始化车站信息
            String station = stationConfigBean.getStationName();
            StationInfo stationInfo = new StationInfo(stationConfigBean);
            stationInfosByCode.put(stationInfo.getStationCode(), stationInfo);
            stationInfosByName.put(stationInfo.getStationName(), stationInfo);
            // 初始化广播区
            List<BroadcastArea> broadcastAreas = configDAO.getBroadcastZoneRegionMapByStation(station);
            stationBroadcastAreaMap.put(station, broadcastAreas);

            // 初始化二级区
            Map<FirstRegionEnum, List<StationRegionConfigBean>> regionEnumListMap = new HashMap<>();
            regionEnumListMap.put(FirstRegionEnum.WAIT_ZONE, new ArrayList<>());
            regionEnumListMap.put(FirstRegionEnum.ABOARD_CHECK_GATE, new ArrayList<>());
            regionEnumListMap.put(FirstRegionEnum.EXIT_CHECK_GATE, new ArrayList<>());
            regionEnumListMap.put(FirstRegionEnum.STATION_ENTRANCE_PORT, new ArrayList<>());
            regionEnumListMap.put(FirstRegionEnum.STATION_EXIT_PORT, new ArrayList<>());
            List<StationRegionConfigBean> regionConfigBeanList = configDAO.getStationRegionConfig(station);
            for (StationRegionConfigBean bean : regionConfigBeanList) {
                switch (bean.getFirstRegion()) {
                    case WAIT_ZONE:
                        regionEnumListMap.get(FirstRegionEnum.WAIT_ZONE).add(bean);
                        break;
                    case EXIT_CHECK_GATE:
                        regionEnumListMap.get(FirstRegionEnum.EXIT_CHECK_GATE).add(bean);
                        break;
                    case ABOARD_CHECK_GATE:
                        regionEnumListMap.get(FirstRegionEnum.ABOARD_CHECK_GATE).add(bean);
                        break;
                    case STATION_EXIT_PORT:
                        regionEnumListMap.get(FirstRegionEnum.STATION_EXIT_PORT).add(bean);
                        break;
                    case STATION_ENTRANCE_PORT:
                        regionEnumListMap.get(FirstRegionEnum.STATION_ENTRANCE_PORT).add(bean);
                        break;
                    default:
                        throw new IllegalArgumentException("wrong input param for FirstRegionEnum" + bean.getFirstRegion());
                }
            }
            stationRegionList.put(station, regionEnumListMap);

            // 初始化股道信息
            List<TrackPlatformMapBean> trackPlatformMapList = configDAO.getTrackPlatformMapByStation(station);
            Map<Integer, String> trackPlatformMap = new HashMap<>();
            for (TrackPlatformMapBean bean : trackPlatformMapList) {
                trackPlatformMap.put(bean.getTrackNum(), bean.getPlatform());
            }
            trackNumPlatformMapList.put(station, trackPlatformMap);

            // 初始化屏幕信息
            List<ScreenConfig> screenConfigList = configDAO.getScreenConfigByStation(station);
            if (null == screenConfigList) {
                screenConfigList = new ArrayList<>();
            }
            screenConfigMap.put(station, screenConfigList);
        }
    }

    // 获取站名列表
    public List<String> getAllStationName() {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        List<String> stationNames = new ArrayList<>();
        stationNames.addAll(stationInfosByName.keySet());
        return stationNames;
    }

    // 获取站码列表
    public List<String> getAllStationCode() {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        List<String> stationCodes = new ArrayList<>();
        stationCodes.addAll(stationInfosByCode.keySet());
        return stationCodes;
    }

    // 根据站码获取站名
    public String getStationCodeByName(String stationName) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        StationInfo stationInfo = stationInfosByName.get(stationName);
        if (null == stationInfo) {
            return null;
        } else {
            return stationInfo.getStationCode();
        }
    }

    // 根据站名获取站码
    public String getStationNameByCode(String stationCode) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        StationInfo stationInfo = stationInfosByCode.get(stationCode);
        if (null == stationInfo) {
            return null;
        } else {
            return stationInfo.getStationName();
        }
    }

    // 根据站名列表获取车站信息列表
    public List<StationInfoDTO> getStationInfosByNames(List<String> stationNames) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        List<StationInfoDTO> stationInfoDTOS = new ArrayList<>();
        if (stationNames.size() != 0) {
            for (String stationName : stationNames) {
                StationInfo stationInfo = stationInfosByName.get(stationName);
                if (null != stationInfo) {
                    StationInfoDTO stationInfoDTO = new StationInfoDTO(stationInfo);
                    stationInfoDTOS.add(stationInfoDTO);
                }
            }
        }
        return stationInfoDTOS;
    }

    // 通过站名获取车站信息
    public StationInfo getStationInfoByStationName(String stationName) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        if (!stationInfosByName.containsKey(stationName)) {
            throw new IllegalArgumentException("Wrong station name for " + stationName);
        }
        return stationInfosByName.get(stationName);
    }

    // 通过站码获取车站信息
    public StationInfo getStationInfoByStationCode(String stationCode) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        if (!stationInfosByCode.containsKey(stationCode)) {
            throw new IllegalArgumentException("Wrong station code for " + stationCode);
        }
        return stationInfosByCode.get(stationCode);
    }

    // 通过站名获取股道号列表
    public List<Integer> getTrackNumByStationName(String stationName) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        if (trackNumPlatformMapList.containsKey(stationName)) {
            return new ArrayList<>(trackNumPlatformMapList.get(stationName).keySet());
        }
        return new ArrayList<>();
    }

    // 通过股道号获取站台号
    public String getPlatformByTrackNum(String stationName, int trackNum) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        if (trackNumPlatformMapList.containsKey(stationName)) {
            String platform = trackNumPlatformMapList.get(stationName).get(trackNum);
            if (null != platform) {
                return platform;
            }
        }
        logger.warn("Can't get platform name at station " + stationName + " for track-" + trackNum + "\n" + "Please check the table TrackPlatformMap in the database");
        return ServiceConstant.UNKNOWN_PLATFORM;
    }

    // 通过站名获取局码
    public String getBureauCodeByStationName(String stationName) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        StationInfo info = stationInfosByName.get(stationName);
        if (null == info) {
            logger.warn("Can't get station " + stationName + "\n" + "Please check the table stationConfig in the database");
            return ServiceConstant.UNKNOWN_BUREAU_CODE;
        }
        return info.getBureauCode();
    }

    // TODO:通过检票口编号获取检票口名称
    public String getTicketBarrierNameByBarrierNum(int barrierNum) {
        return "TicketBarrierName";
    }

    // 通过站名获取广播区列表
    public List<BroadcastArea> getBroadcastAreasByStationName(String stationName) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        if (stationBroadcastAreaMap.containsKey(stationName)) {
            return stationBroadcastAreaMap.get(stationName);
        }
        return new ArrayList<>();
    }

    // 通过站名获取车站区域配置信息
    public Map<FirstRegionEnum, List<StationRegionConfigBean>> getStationRegionListByStationName(String stationName) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        if (stationRegionList.containsKey(stationName)) {
            return stationRegionList.get(stationName);
        }
        return new HashMap<>();
    }

    // 从systemconfig表中取出前台版本号
    public String getSystemVersion() {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        return softwareInfo.getClientVersion();
    }

    // 更新systemconfig表中的前台版本号
    public boolean updateSystemVersion(String clientVersion) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        softwareInfo.setClientVersion(clientVersion);
        return configDAO.updateSystemConfig(clientVersion);
    }


    // 通过车站获取指定类型的屏幕列表
    public List<ScreenConfig> getScreenListByStation(String stationName, ScreenTypeEnum screenType) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        List<ScreenConfig> result = new ArrayList<>();
        List<ScreenConfig> screenConfigList = screenConfigMap.get(stationName);
        if (ListUtil.isNotEmpty(screenConfigList)) {
            for (ScreenConfig config : screenConfigList) {
                if (config.getScreenType().equals(screenType)) {
                    result.add(config);
                }
            }
        }
        return result;
    }

    // 通过股道号获取站台屏列表
    public List<ScreenConfig> getPlatformScreenListByStation(String stationName, int trackNum) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        String platform = getPlatformByTrackNum(stationName, trackNum);
        List<ScreenConfig> configList = new ArrayList<>();
        List<ScreenConfig> screenConfigList = getScreenListByStation(stationName, ScreenTypeEnum.PLATFORM_SCREEN);
        if (platform != null && ListUtil.isNotEmpty(screenConfigList)) {
            for (ScreenConfig config : screenConfigList) {
                if (config.getSecondaryRegion().equals(platform)) {
                    configList.add(config);
                }
            }
        }
        return configList;
    }

    // 根据屏幕ID获取归属站名
    public String getStationNameByScreenID(int screenID) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        ScreenConfig config = configDAO.getScreenConfigByScreenID(screenID);
        if (null != config) {
            return config.getStationName();
        }
        return null;
    }

    // 根据屏幕ID获取归属二级区域
    public String getScreenAreaByScreenID(int screenID) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        ScreenConfig config = configDAO.getScreenConfigByScreenID(screenID);
        if (null != config) {
            return config.getStationName();
        }
        return null;
    }

    // 根据广播区获取广播区id列表
    // 带有去重功能
    public List<Integer> getBroadcastAreaIDList(List<String> broadcastArea, String station) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        // 首先去掉重复的广播区
        List<String> areaWithoutDup = new ArrayList<>(new HashSet<>(broadcastArea));
        List<BroadcastArea> broadcastAreas = stationBroadcastAreaMap.get(station);
        List<Integer> result = new ArrayList<>();
        if (ListUtil.isNotEmpty(broadcastAreas)) {
            for (String areaString : areaWithoutDup) {
                for (BroadcastArea area : broadcastAreas) {
                    if (area.getBroadcastZoneName().equals(areaString)) {
                        result.add(area.getBroadcastZoneID());
                    }
                }
            }
        }
        return result;
    }

    // 根据二级区返回广播区列表,包括站台
    public List<String> getBroadcastAreaListBySecondaryRegion(List<String> regionList, String station) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        List<BroadcastArea> broadcastAreas = stationBroadcastAreaMap.get(station);
        List<String> result = new ArrayList<>();
        if (ListUtil.isNotEmpty(broadcastAreas)) {
            for (String region : regionList) {
                for (BroadcastArea area : broadcastAreas) {
                    if (area.getSecondaryRegionList().contains(region)) {
                        result.add(area.getBroadcastZoneName());
                    }
                }
            }
        }
        return result;
    }

    // 返回全部的screenID
    public List<String> getAllScreenID() {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        List<String> screenIDList = new ArrayList<>();
        for (Map.Entry<String, List<ScreenConfig>> entry : screenConfigMap.entrySet()) {
            for (ScreenConfig screenConfig : entry.getValue()) {
                screenIDList.add(CastUtil.castString(screenConfig.getScreenID()));
            }
        }
        return screenIDList;
    }

    // 根据车站名称和屏幕类型返回屏幕列表
    public List<ScreenConfig> getScreenInfoListByStationAndType(String station, ScreenTypeEnum type) {
        if (!initFlag) {
            init();
            initFlag = true;
        }
        return configDAO.getScreenConfigByStationAndType(station, type);
    }
}
