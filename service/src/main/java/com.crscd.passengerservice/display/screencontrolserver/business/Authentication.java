package com.crscd.passengerservice.display.screencontrolserver.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.restful.client.ApacheBasedRestHttpClient;
import com.crscd.framework.restful.client.RestHttpClient;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.LoginInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.Token;
import com.crscd.passengerservice.display.screencontrolserver.util.UrlUtil;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cuishiqing on 2017/11/29.
 */
public class Authentication {
    private static final String LOGIN_URL = ConfigHelper.getString("screenCtrlLogin");
    private static Logger logger = LoggerFactory.getLogger(Authentication.class);
    private static Map<String, String> tokenMap = new ConcurrentHashMap<>();
    private RestHttpClient restHttpClient = new ApacheBasedRestHttpClient();
    private UrlUtil urlUtil;

    public void setUrlUtil(UrlUtil urlUtil) {
        this.urlUtil = urlUtil;
    }

    public String getTokenByIp(String ip) {
        if (null == tokenMap) {
            return null;
        }
        String token = tokenMap.get(ip);
        return token;
    }

    /**
     * 登录
     *
     * @param ip
     */
    public void login(String ip) {
        String content;
        String tokenId;

        LoginInfo loginInfo = new LoginInfo();
        String url = getLoginUrl(ip);
        String jsonEntity = JsonUtil.toJSON(loginInfo);
        Response response = restHttpClient.getResponse(url, jsonEntity);
        if (null == response) {
            logger.error("Authentication login get content wrong,");
            tokenMap.put(ip, "");
        } else {
            try {
                content = response.returnContent().asString();
                Token token = JsonUtil.fromJSON(content, Token.class);
                tokenId = token.getTokenId();
                tokenMap.put(ip, tokenId);
            } catch (IOException e) {
                logger.error("Authentication login get content wrong,", e);
                tokenMap.put(ip, "");
            }
        }
    }

    private String getLoginUrl(String ip) {
        return urlUtil.getUrl(ip, LOGIN_URL);
    }
}
