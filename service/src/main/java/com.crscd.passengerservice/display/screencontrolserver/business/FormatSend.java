package com.crscd.passengerservice.display.screencontrolserver.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.restful.client.ApacheBasedRestHttpClient;
import com.crscd.framework.restful.client.RestHttpClient;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.config.dao.ConfigDAO;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.config.po.ScreenCtrlServerConfigBean;
import com.crscd.passengerservice.display.format.business.FormatFileManager;
import com.crscd.passengerservice.display.format.business.FormatSendReturnManage;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.FormatSendInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.FormatSendInterfaceInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.FormatSendReturnInfo;
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
public class FormatSend {
    private static Logger logger = LoggerFactory.getLogger(Authentication.class);
    private DataSet oracleDataSet;
    private Authentication authentication;
    private UrlUtil urlUtil;
    private RestHttpClient restHttpClient = new ApacheBasedRestHttpClient();
    private FormatSendReturnManage formatSendReturnManage;
    private ConfigDAO configDAO;
    private FormatFileManager formatFileManager;

    public void setFormatFileManager(FormatFileManager formatFileManager) {
        this.formatFileManager = formatFileManager;
    }

    public void setConfigDAO(ConfigDAO configDAO) {
        this.configDAO = configDAO;
    }

    public void setFormatSendReturnManage(FormatSendReturnManage formatSendReturnManage) {
        this.formatSendReturnManage = formatSendReturnManage;
    }

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
     * 单屏版式下发
     *
     * @param formatId
     * @param format
     * @param screenId
     * @param data
     * @return
     */
    public boolean formatSendSingle(String formatId, String format, int screenId, String data) {
        ScreenConfig screenConfig = oracleDataSet.select(ScreenConfig.class, "screenId = ?", screenId);
        String serverIp = screenConfig.getServerIp();
        String token = authentication.getTokenByIp(serverIp);
        if (StringUtil.isEmpty(token)) {
            authentication.login(serverIp);
            token = authentication.getTokenByIp(serverIp);
            if (StringUtil.isNotEmpty(token)) {
                return formatSendSingleAct(formatId, format, data, screenConfig, token);
            }
        } else {
            return formatSendSingleAct(formatId, format, data, screenConfig, token);
        }

        return false;
    }

    /**
     * 单站多屏版式下发
     *
     * @param formatSendInterfaceInfo
     * @return
     */
    public boolean formatSendList(String stationName, List<FormatSendInterfaceInfo> formatSendInterfaceInfo) {
        ScreenCtrlServerConfigBean serverConfigBean = oracleDataSet.select(ScreenCtrlServerConfigBean.class, "stationName = ?", stationName);
        String serverIp = serverConfigBean.getIp();
        String token = authentication.getTokenByIp(serverIp);
        if (StringUtil.isEmpty(token)) {
            authentication.login(serverIp);
            token = authentication.getTokenByIp(serverIp);
            if (StringUtil.isNotEmpty(token)) {
                return formatSendListAct(formatSendInterfaceInfo, stationName, token);
            }
        } else {
            return formatSendListAct(formatSendInterfaceInfo, stationName, token);
        }

        return false;
    }

    private boolean formatSendListAct(List<FormatSendInterfaceInfo> formatSendInterfaceInfos, String stationName, String token) {
        //返回信息内容
        String returnContent;
        //综显服务器IP
        String serverIp = configDAO.getScreenServerIpByStationName(stationName);
        //下发版式信息列表
        List<FormatSendInfo> formatSendInfos = new ArrayList<>();
        for (FormatSendInterfaceInfo info : formatSendInterfaceInfos) {
            FormatSendInfo formatSendInfo = new FormatSendInfo();
            int screenId = info.getScreenId();
            ScreenConfig screenConfig = oracleDataSet.select(ScreenConfig.class, "screenID = ?", screenId);
            String versionId = formatFileManager.select(info.getFormatId()).getVersion();
            formatSendInfo.setIp(screenConfig.getScreenIp());
            formatSendInfo.setType(screenConfig.getControllerType().toString());
            formatSendInfo.setFormatId(info.getFormatId());
            formatSendInfo.setVersionId(versionId);
            formatSendInfo.setFormat(info.getFormat());
            formatSendInfo.setDate(info.getData());
            formatSendInfos.add(formatSendInfo);
        }

        //发送信息内容
        String content = JsonUtil.toJSON(formatSendInfos);
        //接口地址
        String interfaceStr = ConfigHelper.getString("screenCtrlFormatSendList");
        String url = urlUtil.getUrl(serverIp, interfaceStr);
        //token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        //版式下发
        Response response = restHttpClient.getResponse(url, content, tokenMap);
        if (null != response) {
            try {
                returnContent = response.returnContent().asString();
                List<FormatSendReturnInfo> returnInfos = JsonUtil.jsonToList(returnContent, FormatSendReturnInfo.class);
                List<Boolean> resultList = new ArrayList<>();
                for (FormatSendReturnInfo info : returnInfos) {
                    //版式下发失败
                    if (info.getState().equals(ServiceConstant.FORMATSEND_FAIL)) {
                        resultList.add(false);
                    } else {
                        formatSendReturnManager(info);
                        resultList.add(true);
                    }
                }
                if (resultList.contains(true)) {
                    return true;
                }
            } catch (IOException e) {
                logger.error("Screen controller IP:" + serverIp + " send format error", e);
                authentication.login(serverIp);
                return false;
            }
        }

        return false;
    }

    private boolean formatSendSingleAct(String formatId, String format, String data, ScreenConfig screenConfig, String token) {
        //返回信息内容
        String returnContent;
        //综显服务器IP
        String serverIp = screenConfig.getServerIp();
        //屏幕IP
        String screenIp = screenConfig.getScreenIp();
        //屏幕控制类型
        String type = screenConfig.getControllerType().toString();
        //版式版本号
        String versionId = formatFileManager.select(formatId).getVersion();

        FormatSendInfo formatSendInfo = new FormatSendInfo();
        formatSendInfo.setIp(screenIp);
        formatSendInfo.setType(type);
        formatSendInfo.setFormatId(formatId);
        formatSendInfo.setVersionId(versionId);
        formatSendInfo.setFormat(format);
        formatSendInfo.setDate(data);

        //发送信息内容
        String content = JsonUtil.toJSON(formatSendInfo);
        //接口地址
        String interfaceStr = ConfigHelper.getString("screenCtrlFormatSendSingle");
        String url = urlUtil.getUrl(serverIp, interfaceStr);
        //token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        //版式下发
        Response response = restHttpClient.getResponse(url, content, tokenMap);
        if (null != response) {
            try {
                returnContent = response.returnContent().asString();
                FormatSendReturnInfo formatSendReturnInfo = JsonUtil.fromJSON(returnContent, FormatSendReturnInfo.class);
                //版式下发失败
                if (formatSendReturnInfo.getState().equals(ServiceConstant.FORMATSEND_FAIL)) {
                    return false;
                } else {
                    formatSendReturnManager(formatSendReturnInfo);
                    return true;
                }
            } catch (IOException e) {
                logger.error("Screen controller IP:" + serverIp + " send format error", e);
                authentication.login(serverIp);
                return false;
            }
        }

        return false;
    }

    //版式下发返回信息处理
    private void formatSendReturnManager(FormatSendReturnInfo formatSendReturnInfo) {
        String state = formatSendReturnInfo.getState();
        switch (state) {
            case ServiceConstant.FORMATSEND_OK:
                break;
            case ServiceConstant.FORMATSEND_FORMATMISSING:
                formatSendReturnManage.FormatMissing(formatSendReturnInfo);
                break;
            case ServiceConstant.FORMATSEND_PLAYLISTMISSING:
                formatSendReturnManage.PlayListMissing(formatSendReturnInfo);
                break;
            default:
                break;
        }
    }


}
