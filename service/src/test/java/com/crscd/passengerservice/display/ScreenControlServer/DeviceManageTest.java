package com.crscd.passengerservice.display.ScreenControlServer;

import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.screencontrolserver.business.DeviceManage;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenControlInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusAskInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/12/20.
 */
public class DeviceManageTest {
    DeviceManage deviceManage = ContextHelper.getDeviceManage();

    @Test
    public void screenControlSingleStationTest() {
        ScreenControlInfo screenControlInfo = new ScreenControlInfo();
        screenControlInfo.setIp("10.2.34.135");
        screenControlInfo.setType("SYNC_CONTROLLER");
        screenControlInfo.setAction(ServiceConstant.SCREENCONTROL_POWERON);
        List<ScreenControlInfo> controlInfoList = new ArrayList<>();
        controlInfoList.add(screenControlInfo);
        boolean result = deviceManage.screenControlSingleStation(controlInfoList, "Achi River");
        System.out.print(result);
    }

    @Test
    public void screenStatusStationTest() {
        List<ScreenStatusInfo> screenStatusInfos = deviceManage.screenStatusStation("Achi River");
        for (ScreenStatusInfo s : screenStatusInfos) {
            System.out.print(s.getIp());
            System.out.print(s.getState());
        }
    }

    @Test
    public void screenStatusIpTest() {
        ScreenStatusAskInfo screenStatusAskInfo = new ScreenStatusAskInfo();
        screenStatusAskInfo.setIp("10.2.34.135");
        screenStatusAskInfo.setType("SYNC_CONTROLLER");
        ScreenStatusAskInfo screenStatusAskInfo1 = new ScreenStatusAskInfo();
        screenStatusAskInfo1.setIp("10.2.34.136");
        screenStatusAskInfo1.setType("SYNC_CONTROLLER");
        List<ScreenStatusAskInfo> screenStatusAskInfos = new ArrayList<>();
        screenStatusAskInfos.add(screenStatusAskInfo);
        screenStatusAskInfos.add(screenStatusAskInfo1);
        List<ScreenStatusInfo> statusInfos = deviceManage.screenStatusIp(screenStatusAskInfos, "Achi River");
        for (ScreenStatusInfo s : statusInfos) {
            System.out.print(s.getIp());
            System.out.print(s.getState());
        }
    }


}
