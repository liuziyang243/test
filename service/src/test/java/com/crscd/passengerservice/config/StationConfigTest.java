package com.crscd.passengerservice.config;

import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.enumtype.FirstRegionEnum;
import com.crscd.passengerservice.config.po.StationRegionConfigBean;
import com.crscd.passengerservice.context.ContextHelper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/8
 * Time: 11:28
 */
public class StationConfigTest {
    ApplicationContext ctx = ContextUtil.getInstance();

    @Test
    public void testStationCfg() {
        ConfigManager manager = ContextHelper.getConfigManager();
        String station = "Mariakani";
        System.out.println("----------------------");
        System.out.println("station config:" + station);
        System.out.println("----------------------");
        Map<FirstRegionEnum, List<StationRegionConfigBean>> regionEnumListMap = manager.getStationRegionListByStationName(station);
        for (Map.Entry<FirstRegionEnum, List<StationRegionConfigBean>> entry : regionEnumListMap.entrySet()) {
            System.out.println(entry.getKey());
            for (StationRegionConfigBean bean : entry.getValue()) {
                System.out.println("Secondary region:" + bean.getSecondaryRegion() + " covered by broadcast areas " + bean.getBroadcastAreaList());
            }
            System.out.println("----------------------");
        }
        System.out.println("Track num list:");
        List<Integer> trackNumList = manager.getTrackNumByStationName(station);
        for (Integer trackNum : trackNumList) {
            System.out.println(trackNum + ":" + manager.getPlatformByTrackNum(station, trackNum));
        }
    }
}
