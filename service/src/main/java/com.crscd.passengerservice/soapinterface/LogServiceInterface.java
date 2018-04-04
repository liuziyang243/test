package com.crscd.passengerservice.soapinterface;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Create by: lzy
 * Date: 2016/10/21
 * Time: 9:27
 */
@WebService
public interface LogServiceInterface {
    /*************************************************
     * 前台日志记录接口
     *************************************************/
    @WebResult(name = "makeClientLogRecordResult")
    void makeClientLogRecord(@WebParam(name = "ip") String ip, @WebParam(name = "time") String time, @WebParam(name = "csLoc") String csLoc, @WebParam(name = "loc") String loc, @WebParam(name = "error") String error);
}
