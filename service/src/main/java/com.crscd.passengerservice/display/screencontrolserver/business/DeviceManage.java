package com.crscd.passengerservice.display.screencontrolserver.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.restful.client.ApacheBasedRestHttpClient;
import com.crscd.framework.restful.client.RestHttpClient;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.config.po.ScreenCtrlServerConfigBean;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenControlInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusAskInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusInfo;
import com.crscd.passengerservice.display.screencontrolserver.util.UrlUtil;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/12/1.
 */
public class DeviceManage {
    private static Logger logger = LoggerFactory.getLogger(Authentication.class);
    private DataSet oracleDataSet;
    private Authentication authentication;
    private RestHttpClient restHttpClient = new ApacheBasedRestHttpClient();
    private UrlUtil urlUtil;

    public void setUrlUtil(UrlUtil urlUtil) {
        this.urlUtil = urlUtil;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    /**
     * 单站多屏幕控制
     *
     * @param screenControlList
     * @param stationName
     * @return
     */
    public boolean screenControlSingleStation(List<ScreenControlInfo> screenControlList, String stationName) {
        String ip = getScreenServerIpByStationName(stationName);
        String token = authentication.getTokenByIp(ip);
        if (StringUtil.isEmpty(token)) {
            authentication.login(ip);
            token = authentication.getTokenByIp(ip);
            if (StringUtil.isNotEmpty(token)) {
                return screenControlSingleStationAct(screenControlList, ip, token);
            }
        } else {
            return screenControlSingleStationAct(screenControlList, ip, token);
        }

        return false;
    }

    /**
     * 单站所有屏幕状态
     *
     * @param stationName
     * @return
     */
    public List<ScreenStatusInfo> screenStatusStation(String stationName) {
        List<ScreenStatusInfo> screenControlList = new ArrayList<>();
        String ip = getScreenServerIpByStationName(stationName);
        String token = authentication.getTokenByIp(ip);
        if (StringUtil.isEmpty(token)) {
            authentication.login(ip);
            token = authentication.getTokenByIp(ip);
            if (StringUtil.isNotEmpty(token)) {
                screenControlList = screenStatusStationAct(ip, token);
            }
        } else {
            screenControlList = screenStatusStationAct(ip, token);
        }

        return screenControlList;
    }

    /**
     * 单站根据IP获取屏幕状态
     *
     * @param screenInfoList
     * @return
     */
    public List<ScreenStatusInfo> screenStatusIp(List<ScreenStatusAskInfo> screenInfoList, String stationName) {
        List<ScreenStatusInfo> screenStatusInfos = new ArrayList<>();
        String ip = getScreenServerIpByStationName(stationName);
        String token = authentication.getTokenByIp(ip);
        if (StringUtil.isEmpty(token)) {
            authentication.login(ip);
            token = authentication.getTokenByIp(ip);
            if (StringUtil.isNotEmpty(token)) {
                screenStatusInfos = screenStatusIpAct(ip, screenInfoList, token);
            }
        } else {
            screenStatusInfos = screenStatusIpAct(ip, screenInfoList, token);
        }
        return screenStatusInfos;
    }

    private List<ScreenStatusInfo> screenStatusIpAct(String ip, List<ScreenStatusAskInfo> screenInfoList, String token) {
        List<ScreenStatusInfo> screenStatusInfos = new ArrayList<>();
        //返回信息内容
        String returnContent;

        String interfaceStr = ConfigHelper.getString("screenCtrlScreenStateIp");
        String url = urlUtil.getUrl(ip, interfaceStr);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        String content = JsonUtil.toJSON(screenInfoList);
        Response response = restHttpClient.getResponse(url, content, tokenMap);
        if (null != response) {
            try {
                returnContent = response.returnContent().asString();
                screenStatusInfos = JsonUtil.jsonToList(returnContent, ScreenStatusInfo.class);
            } catch (IOException e) {
                logger.error("Screen controller IP:" + ip + " get screen status infos error", e);
                authentication.login(ip);
                return screenStatusInfos;
            }
        }

        return screenStatusInfos;
    }

    private List<ScreenStatusInfo> screenStatusStationAct(String ip, String token) {
        List<ScreenStatusInfo> screenStatusInfos = new ArrayList<>();
        //状态码
        int statusCode;
        //返回信息内容
        String returnContent;

        String interfaceStr = ConfigHelper.getString("screenCtrlScreenStateAll");
        String url = urlUtil.getUrl(ip, interfaceStr);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        Response response = restHttpClient.getResponse(url, tokenMap);
        if (null != response) {
            try {
                returnContent = response.returnContent().asString();
                screenStatusInfos = JsonUtil.jsonToList(returnContent, ScreenStatusInfo.class);
            } catch (IOException e) {
                logger.error("Screen controller IP:" + ip + " get screen status infos error", e);
                authentication.login(ip);
                return screenStatusInfos;
            }
        }

        return screenStatusInfos;
    }

    private boolean screenControlSingleStationAct(List<ScreenControlInfo> screenControlList, String ip, String token) {
        //状态码
        int statusCode = 0;

        String content = JsonUtil.toJSON(screenControlList);
        String interfaceStr = ConfigHelper.getString("screenCtrlScreenControl");
        String url = urlUtil.getUrl(ip, interfaceStr);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        Response response = restHttpClient.getResponse(url, content, tokenMap);
        if (null != response) {
            try {
                statusCode = response.returnResponse().getStatusLine().getStatusCode();
            } catch (IOException e) {
                logger.error("Screen controller IP:" + ip + " get material infos error", e);
                return false;
            }
            if (statusCode == 200) {
                return true;
            } else if (statusCode == 403) {
                authentication.login(ip);
            }
        }
        return false;
    }

    private String getScreenServerIpByStationName(String stationName) {
        ScreenCtrlServerConfigBean serverConfigBean = oracleDataSet.select(ScreenCtrlServerConfigBean.class, "stationName = ?", stationName);
        return serverConfigBean.getIp();
    }


}
