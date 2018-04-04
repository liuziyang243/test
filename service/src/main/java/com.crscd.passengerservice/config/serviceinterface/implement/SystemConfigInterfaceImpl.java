package com.crscd.passengerservice.config.serviceinterface.implement;

import com.crscd.framework.translation.serviceinterface.TranslatorInterface;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.domainobject.StationInfo;
import com.crscd.passengerservice.config.dto.BroadcastAreaDTO;
import com.crscd.passengerservice.config.dto.SecondaryRegionDTO;
import com.crscd.passengerservice.config.dto.StationInfoDTO;
import com.crscd.passengerservice.config.dto.SystemInfoDTO;
import com.crscd.passengerservice.config.enumtype.FirstRegionEnum;
import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.config.po.BroadcastArea;
import com.crscd.passengerservice.config.po.StationRegionConfigBean;
import com.crscd.passengerservice.config.serviceinterface.SystemConfigInterface;
import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy
 * Date: 2017/9/6
 */
public class SystemConfigInterfaceImpl implements SystemConfigInterface {
    private ConfigManager manager;
    private TranslatorInterface translator;

    public void setManager(ConfigManager manager) {
        this.manager = manager;
    }

    public void setTranslator(TranslatorInterface translator) {
        this.translator = translator;
    }

    @Override
    public SystemInfoDTO getSystemInfo(String lan) {
        SystemInfoDTO systemInfoDTO = new SystemInfoDTO();
        systemInfoDTO.setSystemState("center");

        // 添加晚点原因列表
        HashMap<String, LateEarlyReasonEnum> reasonEnumHashMap = new HashMap<>();
        if (null == lan) {
            reasonEnumHashMap.put(LateEarlyReasonEnum.NONE.getReason(), LateEarlyReasonEnum.NONE);
            reasonEnumHashMap.put(LateEarlyReasonEnum.WEATHER.getReason(), LateEarlyReasonEnum.WEATHER);
            reasonEnumHashMap.put(LateEarlyReasonEnum.MAINTENANCE.getReason(), LateEarlyReasonEnum.MAINTENANCE);
            reasonEnumHashMap.put(LateEarlyReasonEnum.MANAGEMENT.getReason(), LateEarlyReasonEnum.MANAGEMENT);
        } else {
            reasonEnumHashMap.put(translator.makeTranslation(LateEarlyReasonEnum.NONE.getReason(), lan), LateEarlyReasonEnum.NONE);
            reasonEnumHashMap.put(translator.makeTranslation(LateEarlyReasonEnum.WEATHER.getReason(), lan), LateEarlyReasonEnum.WEATHER);
            reasonEnumHashMap.put(translator.makeTranslation(LateEarlyReasonEnum.MAINTENANCE.getReason(), lan), LateEarlyReasonEnum.MAINTENANCE);
            reasonEnumHashMap.put(translator.makeTranslation(LateEarlyReasonEnum.MANAGEMENT.getReason(), lan), LateEarlyReasonEnum.MANAGEMENT);
        }
        systemInfoDTO.setLateEarlyReason(reasonEnumHashMap);

        // todo:配置到发广播优先级
        ArrayList<Integer> levelList = new ArrayList<>();
        levelList.add(1);
        levelList.add(2);
        levelList.add(3);
        levelList.add(6);
        systemInfoDTO.setOnArriveBroadcastPriorityList(new ArrayList<>(levelList));
        // todo:配置变更广播优先级
        levelList = new ArrayList<>();
        levelList.add(7);
        levelList.add(8);
        levelList.add(9);
        systemInfoDTO.setAlterationBroadcastPriorityList(new ArrayList<>(levelList));
        // todo:配置车次广播优先级
        levelList = new ArrayList<>();
        levelList.add(10);
        levelList.add(11);
        levelList.add(12);
        systemInfoDTO.setManualBroadcastPriorityList(new ArrayList<>(levelList));

        // 添加车站信息
        List<StationInfoDTO> stationInfoDTOList = new ArrayList<>();
        List<String> stationNameList = manager.getAllStationName();
        for (String stationName : stationNameList) {
            StationInfo info = manager.getStationInfoByStationName(stationName);
            StationInfoDTO dto = new StationInfoDTO(info);

            ArrayList<BroadcastAreaDTO> broadcastAreaDTOList = new ArrayList<>();
            List<BroadcastArea> broadcastAreas = manager.getBroadcastAreasByStationName(stationName);
            Map<FirstRegionEnum, List<StationRegionConfigBean>> regionListMap = manager.getStationRegionListByStationName(stationName);
            for (BroadcastArea area : broadcastAreas) {
                BroadcastAreaDTO broadcastAreaDTO = new BroadcastAreaDTO();
                broadcastAreaDTO.setRegionName(area.getBroadcastZoneName());
                broadcastAreaDTO.setTranslatedRegionName(translator.makeTranslation(broadcastAreaDTO.getRegionName(), lan));
                broadcastAreaDTO.setGroupName(area.getGroupName());
                broadcastAreaDTO.setTranslatedGroupName(translator.makeTranslation(broadcastAreaDTO.getGroupName(), lan));
                broadcastAreaDTOList.add(broadcastAreaDTO);
            }
            dto.setBroadcastAreaList(broadcastAreaDTOList);

            ArrayList<SecondaryRegionDTO> regionlist = new ArrayList<>();
            for (FirstRegionEnum firstReg : regionListMap.keySet()) {
                List<StationRegionConfigBean> beanList = regionListMap.get(firstReg);
                for (StationRegionConfigBean bean : beanList) {
                    SecondaryRegionDTO regionDTO = new SecondaryRegionDTO();
                    regionDTO.setFirstRegion(firstReg);
                    regionDTO.setRegionName(bean.getSecondaryRegion());
                    regionDTO.setTranslatedRegionName(translator.makeTranslation(regionDTO.getRegionName(), lan));
                    regionlist.add(regionDTO);
                }
            }
            dto.setGeographicalRegionList(regionlist);

            dto.setTrackList(manager.getTrackNumByStationName(stationName));

            stationInfoDTOList.add(dto);
        }
        systemInfoDTO.setStationInfoList(stationInfoDTOList);
        return systemInfoDTO;
    }

    @Override
    public ArrayList<ScreenConfig> getScreenConfigInfoByStationAndType(String station, ScreenTypeEnum type) {
        return new ArrayList<>(manager.getScreenInfoListByStationAndType(station, type));
    }
}
