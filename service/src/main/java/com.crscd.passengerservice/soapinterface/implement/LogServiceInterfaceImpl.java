package com.crscd.passengerservice.soapinterface.implement;

import com.crscd.passengerservice.log.business.LogManager;
import com.crscd.passengerservice.soapinterface.LogServiceInterface;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

/**
 * @author lzy
 * Date: 2016/10/21
 * Time: 9:29
 */
@WebService(serviceName = "LogService",
        targetNamespace = "http://passengerservice.crscd.com/service",
        endpointInterface = "com.crscd.passengerservice.soapinterface.LogServiceInterface")
@HandlerChain(file = "handlersdef.xml")
public class LogServiceInterfaceImpl implements LogServiceInterface {
    @Resource
    private WebServiceContext context;

    public WebServiceContext getContext() {
        return context;
    }

    public LogServiceInterfaceImpl() {
    }

    @Override
    public void makeClientLogRecord(String ip, String time, String csLoc, String loc, String error) {
        LogManager.logClientError(ip, time, csLoc, loc, error);
    }
}
