package com.crscd.passengerservice.aop;

import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-03-06 上午11:28
 */
public class SoapHeaderHandler implements SOAPHandler<SOAPMessageContext> {
    private static Logger logger = LoggerFactory.getLogger(SoapHeaderHandler.class);

    private String userName;

    private String ipAddress;

    private String language;

    private String station;

    public String getUserName() {
        return userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getLanguage() {
        return language;
    }

    public String getStation() {
        return station;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        logger.info("Server.handleMessage() is invoked......");

        //从服务端角度看:inbound表示接收客户端消息,outbound表示响应消息给客户端..从客户端角度看时正好与之相反
        Boolean isOutBound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (isOutBound) {
            return true;
        }

        SOAPMessage message = context.getMessage();
        SOAPHeader header = null;
        try {
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            header = envelope.getHeader();
        } catch (SOAPException e) {
            logger.error("[SoapHeader Error]", e);
        }

        logger.info("[SOAP_Header] ---- " + DateTimeUtil.getCurrentDatetimeString() + " ----");
        this.userName = getHeaderValue(header, ServiceConstant.USER_NAME);
        this.language = getHeaderValue(header, ServiceConstant.USER_LAN);
        this.station = getHeaderValue(header, ServiceConstant.USER_STATION);
        this.ipAddress = getHeaderValue(header, ServiceConstant.USER_IP_ADDRESS);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logger.info("Server.handleFault() is invoked......");
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }

    /**
     * 根据约定从header中取出数据
     * @param header
     * @param value
     * @return
     */
    private String getHeaderValue(SOAPHeader header, String value) {
        if (null == header) {
            return null;
        }
        Iterator iterator = header.getChildElements(new QName(ServiceConstant.QNAME_SPACE, "auth"));
        String result = null;
        if (null != iterator) {
            if (iterator.hasNext()) {
                SOAPElement element = (SOAPElement) iterator.next();
                Iterator it = element.getChildElements(new QName(ServiceConstant.QNAME_SPACE, value));
                if (null != it) {
                    if (it.hasNext()) {
                        SOAPElement element1 = (SOAPElement) it.next();
                        result = element1.getValue();
                    }
                }
            }
        }
        logger.info("[SOAP_Header-{}]:{}", value, result);
        return result;
    }
}
