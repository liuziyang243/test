package com.crscd.passengerservice.display;

import com.crscd.framework.util.io.FileUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.format.business.FrameManager;
import org.junit.Test;

import java.util.Map;

/**
 * Created by cuishiqing on 2017/9/26.
 */
public class FrameManagerTest {
    FrameManager frameManager = ContextHelper.getFrameManager();

    @Test
    public void SaveFrameDataTest() {
        frameManager.SaveFrameData("Athi River", FileUtil.readFile("W:\\客货服务系统\\软件开发相关\\测试\\frame.xml"));
    }

    @Test
    public void getFrameListTest() {
        String stationName = "Athi River";
        String screenType = "EntranceScreen";
        String screenWidth = "832";
        String screenHeight = "256";
        String screenColor = "DoubleColor";

        Map<String, String> frames = frameManager.getFrameList(stationName, screenType, screenWidth, screenHeight, screenColor);
    }

    @Test
    public void updateFrameDataTest() {
        String s = FileUtil.readFile("W:\\客货服务系统\\软件开发相关\\测试\\frame.xml");
        boolean result = frameManager.updateFrameData(s);
        System.out.print(result);
    }

    @Test
    public void getFrameIDTest() {
        String frameId = frameManager.getFrameID("Athi River");
        System.out.print(frameId + "\r" + "\n");
    }

    @Test
    public void DeleteFrameTest() {
        boolean result = frameManager.deleteFrame("33210");
        System.out.print(result + "\r" + "\n");
    }

    @Test
    public void getFrameAndDTVarTest() {
        String result = frameManager.getFrameAndDTVar("33210");
        FileUtil.writeFile("W:\\客货服务系统\\软件开发相关\\测试\\frame.xml", result);
        System.out.print(result + "\r" + "\n");
    }

}
