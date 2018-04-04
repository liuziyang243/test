package com.crscd.passengerservice.display.screencontrolserver.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.restful.client.ApacheBasedRestHttpClient;
import com.crscd.framework.restful.client.RestHttpClient;
import com.crscd.framework.util.number.NumberUtil;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.config.po.ScreenCtrlServerConfigBean;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.PlayListResponseInfo;
import com.crscd.passengerservice.display.screencontrolserver.util.UrlUtil;
import com.crscd.passengerservice.multimedia.domainobject.PlayListInfo;
import com.crscd.passengerservice.multimedia.domainobject.PlayListSendingStatusInfo;
import com.crscd.passengerservice.multimedia.serviceinterface.PlayListManagerInterface;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/12/1.
 */
public class PlayList {
    private static Logger logger = LoggerFactory.getLogger(Authentication.class);
    private DataSet oracleDataSet;
    private Authentication authentication;
    private UrlUtil urlUtil;
    private RestHttpClient restHttpClient = new ApacheBasedRestHttpClient();

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
     * 播放列表下发
     *
     * @param stationName
     * @param playListInfos
     * @return
     */
    public boolean playListSend(String stationName, List<PlayListInfo> playListInfos) {
        String serverIp = getScreenServerIpByStationName(stationName);
        String token = authentication.getTokenByIp(serverIp);
        if (StringUtil.isEmpty(token)) {
            authentication.login(serverIp);
            token = authentication.getTokenByIp(serverIp);
            if (StringUtil.isNotEmpty(token)) {
                return playListSendAct(playListInfos, serverIp, token);
            }
        } else {
            return playListSendAct(playListInfos, serverIp, token);
        }

        return false;
    }

    private boolean playListSendAct(List<PlayListInfo> playListInfos, String ip, String token) {
        //发送内容
        String content = JsonUtil.toJSON(playListInfos);
        //返回信息内容
        String returnContent;
        String interfaceStr = ConfigHelper.getString("screenCtrlPlayListSend");
        String url = urlUtil.getUrl(ip, interfaceStr);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        Response response = restHttpClient.getResponse(url, content, tokenMap);
        if (null != response) {
            try {
                returnContent = response.returnContent().asString();
                List<PlayListResponseInfo> responseInfos = JsonUtil.jsonToList(returnContent, PlayListResponseInfo.class);
                PlayListManagerInterface playListManagerInterface = ContextHelper.getPlaylistManager();
                for (PlayListResponseInfo info : responseInfos) {
                    PlayListSendingStatusInfo playListSendingStatusInfo = playListResponseInfoToStatusInfo(info, ip);
                    playListManagerInterface.savePlayListSendingStatus(playListSendingStatusInfo);
                }
                return true;
            } catch (IOException e) {
                logger.error("Screen controller IP:" + ip + " send playlists error", e);
                authentication.login(ip);
                return false;
            }
        }

        return false;
    }

    private PlayListSendingStatusInfo playListResponseInfoToStatusInfo(PlayListResponseInfo playListResponseInfo, String ip) {
        PlayListSendingStatusInfo playListSendingStatusInfo = new PlayListSendingStatusInfo();
        int playListId = NumberUtil.toInt(playListResponseInfo.getId());
        playListSendingStatusInfo.setPlayListId(playListId);
        playListSendingStatusInfo.setVersion(playListResponseInfo.getVersion());
        playListSendingStatusInfo.setServerIp(ip);
        playListSendingStatusInfo.setSendingStatus(playListResponseInfo.getState());
        return playListSendingStatusInfo;
    }

    private String getScreenServerIpByStationName(String stationName) {
        ScreenCtrlServerConfigBean serverConfigBean = oracleDataSet.select(ScreenCtrlServerConfigBean.class, "stationName = ?", stationName);
        return serverConfigBean.getIp();
    }
}
