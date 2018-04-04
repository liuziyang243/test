package com.crscd.passengerservice.app;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.soapinterface.BroadcastServiceInterface;
import com.crscd.passengerservice.soapinterface.implement.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Endpoint;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-04-03 下午5:20
 */
public class PubSoapService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PubSoapService.class);

    public static void pub() {
        BroadcastServiceInterface broadcastService = ContextHelper.getBroadcastSerivce();
        ExosystemServiceInterfaceImpl exoSystemService = ContextHelper.getExoSystemSerivce();
        GuideServiceInterfaceImpl guideService = ContextHelper.getGuideSerivce();
        LogServiceInterfaceImpl logService = ContextHelper.getLogSerivce();
        PlanServiceInterfaceImpl planService = ContextHelper.getPlanSerivce();
        SystemServiceInterfaceImpl systemService = ContextHelper.getSystemSerivce();

        String baseUrl = ConfigHelper.getString("travelService.baseServiceURL");
        Endpoint.publish(baseUrl + "BroadcastService", broadcastService);
        LOGGER.error("publish BroadcastService...");
        Endpoint.publish(baseUrl + "ExoSystemService", exoSystemService);
        LOGGER.error("publish exoSystemService...");
        Endpoint.publish(baseUrl + "GuideService", guideService);
        LOGGER.error("publish guideService...");
        Endpoint.publish(baseUrl + "LogService", logService);
        LOGGER.error("publish logService...");
        Endpoint.publish(baseUrl + "PlanService", planService);
        LOGGER.error("publish planService...");
        Endpoint.publish(baseUrl + "SystemService", systemService);
        LOGGER.error("publish systemService...");
    }
}
