package com.crscd.passengerservice.display.screencontrolserver.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.restful.client.ApacheBasedRestHttpClient;
import com.crscd.framework.restful.client.RestHttpClient;
import com.crscd.passengerservice.display.screencontrolserver.util.UrlUtil;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/11/30.
 */
public class Heartbeat {
    private static String heartbeatUrl = ConfigHelper.getString("screenCtrlHeartbeat");
    private static Logger logger = LoggerFactory.getLogger(Authentication.class);
    private Authentication authentication;
    private RestHttpClient restHttpClient = new ApacheBasedRestHttpClient();
    private UrlUtil urlUtil;

    public void setUrlUtil(UrlUtil urlUtil) {
        this.urlUtil = urlUtil;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    /**
     * 心跳
     */
    public boolean heatbeat(String ip) {
        String token = authentication.getTokenByIp(ip);
        try {
            if (null == token) {
                authentication.login(ip);
                token = authentication.getTokenByIp(ip);
                if (null == token) {
                    return false;
                } else {
                    return heartbeatAct(ip, token);
                }
            } else {
                return heartbeatAct(ip, token);
            }
        } catch (Exception e) {
            return false;
        }

    }

    private boolean heartbeatAct(String ip, String tocken) {
        int statusCode = 0;
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", tocken);
        String url = getHeartbeatUrl(ip);
        Response response = restHttpClient.getResponse(url, tokenMap);
        if (null != response) {
            try {
                statusCode = response.returnResponse().getStatusLine().getStatusCode();
            } catch (IOException e) {
                logger.error("Screen controller IP:" + ip + " heartbeat error", e);
                return false;
            }
            if (statusCode == 200) {
                return true;
            } else if (statusCode == 403) {
                authentication.login(ip);
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private String getHeartbeatUrl(String ip) {
        return urlUtil.getUrl(ip, heartbeatUrl);
    }
}
