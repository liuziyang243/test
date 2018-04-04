package com.crscd.passengerservice.display.screencontrolserver.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.restful.client.ApacheBasedRestHttpClient;
import com.crscd.framework.restful.client.RestHttpClient;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.MaterialInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.MaterialSendReturnInfo;
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
public class Material {
    private static String materialSendUrl = ConfigHelper.getString("screenCtrlMaterialSend");
    private static String materialAllInfoGetUrl = ConfigHelper.getString("screenCtrlMaterialCtrlerAllInfo");
    private static Logger logger = LoggerFactory.getLogger(Authentication.class);
    private RestHttpClient restHttpClient = new ApacheBasedRestHttpClient();
    private DataSet oracleDataSet;
    private Authentication authentication;
    private UrlUtil urlUtil;

    public void setUrlUtil(UrlUtil urlUtil) {
        this.urlUtil = urlUtil;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    /**
     * 素材下发
     *
     * @param materialInfos
     * @param ip
     * @return
     */
    public boolean materialSend(List<MaterialInfo> materialInfos, String ip) {
        String token = authentication.getTokenByIp(ip);
        if (StringUtil.isEmpty(token)) {
            authentication.login(ip);
            token = authentication.getTokenByIp(ip);
            if (StringUtil.isNotEmpty(token)) {
                return materialSendAct(materialInfos, ip, token);
            }
        } else {
            return materialSendAct(materialInfos, ip, token);
        }

        return false;
    }

    /**
     * 素材列表信息获取
     *
     * @param ip
     * @return
     */
    public List<MaterialInfo> materialAllInfoGet(String ip) {
        List<MaterialInfo> materialInfos = new ArrayList<>();
        String token = authentication.getTokenByIp(ip);
        if (StringUtil.isEmpty(token)) {
            authentication.login(ip);
            token = authentication.getTokenByIp(ip);
            if (StringUtil.isNotEmpty(token)) {
                materialInfos = materialAllInfoGetAct(ip, token);
            }
        } else {
            materialInfos = materialAllInfoGetAct(ip, token);
        }
        return materialInfos;
    }

    private boolean materialSendAct(List<MaterialInfo> materialInfos, String ip, String token) {
        //返回信息内容
        String returnContent;
        //发送信息内容
        String content = JsonUtil.toJSON(materialInfos);
        //token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        String url = urlUtil.getUrl(ip, materialSendUrl);
        Response response = restHttpClient.getResponse(url, content, tokenMap);
        if (null != response) {
            try {
                returnContent = response.returnContent().asString();
                MaterialSendReturnInfo materialSendReturnInfo = JsonUtil.fromJSON(returnContent, MaterialSendReturnInfo.class);
                if ("ok".equals(materialSendReturnInfo.getState())) {
                    return true;
                }
            } catch (IOException e) {
                logger.error("Screen controller IP:" + ip + " material send error", e);
                authentication.login(ip);
                return false;
            }
        }

        return false;
    }

    private List<MaterialInfo> materialAllInfoGetAct(String ip, String token) {
        //返回信息内容
        String returnContent;
        //返回素材信息列表
        List<MaterialInfo> materialInfos = new ArrayList<>();
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        String url = urlUtil.getUrl(ip, materialAllInfoGetUrl);
        Response response = restHttpClient.getResponse(url, tokenMap);
        if (null != response) {
            try {
                returnContent = response.returnContent().asString();
                materialInfos = JsonUtil.jsonToList(returnContent, MaterialInfo.class);
            } catch (IOException e) {
                logger.error("Screen controller IP:" + ip + " get material infos error", e);
                authentication.login(ip);
                return materialInfos;
            }
        }
        return materialInfos;
    }
}
