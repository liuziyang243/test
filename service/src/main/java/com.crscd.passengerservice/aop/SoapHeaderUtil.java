package com.crscd.passengerservice.aop;

import com.crscd.passengerservice.app.ServiceConstant;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import com.sun.xml.internal.ws.server.WSEndpointImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.Handler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 *
 * Date:2016/6/12
 * Time:8:09
 */
public class SoapHeaderUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapHeaderUtil.class);

    /**
     * 获取soap header部分约定的值
     * 利用WebServiceContext获取绑定的终结点，然后从终结点处获取处理链
     * 在处理链中找到自定义的handler并取回自定义的soapheader值
     */
    public static Map<String, String> getSoapHeaderList(WebServiceContext ctx) {
        Map<String, String> soapHeaderList = new HashMap<>();
        if (ctx == null) {
            return soapHeaderList;
        }

        WSEndpointImpl endpoint = (WSEndpointImpl) ctx.getMessageContext().get(JAXWSProperties.WSENDPOINT);
        List<Handler> handlers = endpoint.getBinding().getHandlerChain();

        for (Handler handler : handlers) {
            if (handler instanceof SoapHeaderHandler) {
                SoapHeaderHandler sHandler = (SoapHeaderHandler) handler;
                soapHeaderList.put(ServiceConstant.USER_NAME, sHandler.getUserName());
                soapHeaderList.put(ServiceConstant.USER_IP_ADDRESS, sHandler.getIpAddress());
                soapHeaderList.put(ServiceConstant.USER_LAN, sHandler.getLanguage());
                soapHeaderList.put(ServiceConstant.USER_STATION, sHandler.getStation());
            }
        }

        return soapHeaderList;
    }
}
