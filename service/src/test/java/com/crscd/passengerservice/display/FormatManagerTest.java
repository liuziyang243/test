package com.crscd.passengerservice.display;

import com.crscd.framework.util.io.FileUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.format.business.FormatManager;
import com.crscd.passengerservice.display.format.domainobject.FormatAndFrameListInfo;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/9/27.
 */
public class FormatManagerTest {
    FormatManager formatManager = ContextHelper.getFormatManager();

    @Test
    public void importFormatDataTest() {
        String result = formatManager.importFormatData("Athi River", FileUtil.readFile("W:\\客货服务系统\\软件开发相关\\测试\\Test.xml"));
        System.out.print(result);
    }

    @Test
    public void exportFormatData() {
        String result = formatManager.exportFormatData("2_13");
        FileUtil.writeFile("W:\\客货服务系统\\软件开发相关\\测试\\Test.xml", result);
    }

    @Test
    public void getFormatListTest() {
        String stationName = "Athi River";
        String screenType = "EntranceScreen";
        String screenWidth = "1152";
        String screenHeight = "352";
        String screenColor = "FullColor";
        Map<String, String> result = formatManager.getFormatList(stationName, screenType, screenWidth, screenHeight, screenColor);
        System.out.print(result);
    }

    @Test
    public void deleteFormatTest() {
        String result = formatManager.deleteFormat("2_13", 1);
        System.out.print(result);
    }

    @Test
    public void saveFormatDataTest() {
        FormatInfo formatInfo = new FormatInfo();
        formatInfo.setScreenType("PlatformScreen");
        formatInfo.setStationName("Athi River");
        formatInfo.setScreenWidth("1000");
        formatInfo.setScreenHeight("500");
        formatInfo.setScreenColor("DoubleColor");
        formatInfo.setFormatName("PlatformTest");
        formatInfo.setFormatID("12_12");
        LinkedHashMap<String, String> frameMap = new LinkedHashMap<>();
        frameMap.put("33210", "10");
        frameMap.put("21310", "30");
        String result = formatManager.saveFormatData(formatInfo, frameMap);
        System.out.print(result);
    }

    @Test
    public void updateFormatDataTest() {
        LinkedHashMap<String, String> frameMap = new LinkedHashMap<>();
        frameMap.put("33210", "10");
        frameMap.put("21310", "30");
        String result = formatManager.updateFormatData("12_12", frameMap);
        System.out.print(result);
    }

    @Test
    public void formatBindingTest() {
        boolean result = formatManager.formatBinding(201, 1, "2_13");
        System.out.print(result);
    }

    @Test
    public void getFormatAndFrameListInfoTest() {
        FormatAndFrameListInfo result = formatManager.getFormatAndFrameListInfo("12_12");
    }

    @Test
    public void getBoundFormatTest() {
        FormatAndFrameListInfo result = formatManager.getBoundFormat(201, 1);
    }

    @Test
    public void getFormatIdTest() {
        String result = formatManager.getFormatId("Athi River");
        System.out.print(result);
    }

}
