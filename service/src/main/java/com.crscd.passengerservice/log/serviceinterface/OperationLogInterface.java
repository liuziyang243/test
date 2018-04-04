package com.crscd.passengerservice.log.serviceinterface;

import com.crscd.passengerservice.log.dto.OperationLogDTO;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
public interface OperationLogInterface {
    /**
     * 获取可查询的用户列表
     *
     * @param currentUser
     * @return
     */
    @WebResult(name = "getOperatorListResult")
    List<String> getOperatorList(@WebParam(name = "currentUser") String currentUser);

    /**
     * 查询日志
     *
     * @param operationTimeStart
     * @param operationTimeEnd
     * @param operator
     * @param serviceType
     * @param language
     * @param stationName
     * @param currentUser
     * @return
     */
    @WebResult(name = "logQueryByConditionResult")
    List<OperationLogDTO> logQueryByCondition(@WebParam(name = "operationTimeStart") String operationTimeStart, @WebParam(name = "operationTimeEnd") String operationTimeEnd, @WebParam(name = "operator") String operator, @WebParam(name = "serviceType") String serviceType, @WebParam(name = "language") String language, @WebParam(name = "stationName") String stationName, @WebParam(name = "currentUser") String currentUser);

}
