package com.crscd.passengerservice.display.ScreenControlServer;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.screencontrolserver.business.Heartbeat;
import org.junit.Test;

/**
 * Created by cuishiqing on 2017/12/19.
 */
public class HeartbeatTest {
    Heartbeat heartbeat = ContextHelper.getHeartbeat();
    DataSet dataSet = ContextHelper.getTestDataSet();

    @Test
    public void heartbeatTest() {
        boolean result = heartbeat.heatbeat("10.2.34.134");
        System.out.print(result);
    }


}
