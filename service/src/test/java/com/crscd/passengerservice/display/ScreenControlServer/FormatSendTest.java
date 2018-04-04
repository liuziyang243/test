package com.crscd.passengerservice.display.ScreenControlServer;

import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.screencontrolserver.business.FormatSend;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.FormatSendInterfaceInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/12/22.
 */
public class FormatSendTest {
    FormatSend formatSend = ContextHelper.getFormatSend();

    @Test
    public void formatSendSingleTest() {
        boolean result = formatSend.formatSendSingle("1234", "XXXXX", 201, "XXXX");
    }

    @Test
    public void formatSendListTest() {
        FormatSendInterfaceInfo info1 = new FormatSendInterfaceInfo();
        info1.setFormatId("1234");
        info1.setFormat("XXXX");
        info1.setScreenId(201);
        info1.setData("XXXX");
        List<FormatSendInterfaceInfo> infos = new ArrayList<>();
        infos.add(info1);

        boolean result = formatSend.formatSendList("Achi River", infos);
    }

}
