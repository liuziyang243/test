package com.crscd.passengerservice.display.screencontrolserver.business;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.config.po.ScreenCtrlServerConfigBean;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.warning.business.MonitoringInterface;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/11/30.
 */
public class HeartbeatJob implements Job {
    private static Map<String, Integer> timeoutMap = new HashMap<>();
    //心跳最大连续失败5次，判定连接控制器失败
    private static int maxTimes = 5;
    //告警接口
    private MonitoringInterface monitoringInterface = ContextHelper.getScreenCtrlServerMonitor();

    @Override
    public void execute(JobExecutionContext context) {
        DataSet dataSet = ContextHelper.getDataSet();
        Heartbeat heartbeat = ContextHelper.getHeartbeat();
        List<String> screenControllerIps = dataSet.selectColumnList(ScreenCtrlServerConfigBean.class, "ip", "", "");
        for (String ip : screenControllerIps) {
            if (!heartbeat.heatbeat(ip)) {
                if (null == timeoutMap || !timeoutMap.containsKey(ip)) {
                    timeoutMap.put(ip, 1);
                } else {
                    timeoutMap.put(ip, timeoutMap.get(ip) + 1);
                }

                if (timeoutMap.get(ip) >= maxTimes) {
                    //上告警
                    monitoringInterface.notifyAbnormal(ip, "offLine");
                    //重置通信失败次数为0
                    timeoutMap.put(ip, 0);
                }
            } else {
                timeoutMap.put(ip, 0);
            }
        }
    }
}
