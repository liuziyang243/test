package com.crscd.passengerservice.log.serviceinterface.implement;

import com.crscd.framework.FrameworkConstant;
import com.crscd.passengerservice.log.business.OperationLogManager;
import com.crscd.passengerservice.log.domainobject.OperationLog;
import com.crscd.passengerservice.log.dto.OperationLogDTO;
import com.crscd.passengerservice.log.serviceinterface.OperationLogInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
public class OperationLogInterfaceImpl implements OperationLogInterface {

    private OperationLogManager operationLogManager;

    public void setOperationLogManager(OperationLogManager operationLogManager) {
        this.operationLogManager = operationLogManager;
    }

    /**
     * 操作人查询
     */
    @Override
    public List<String> getOperatorList(String currentUser) {
        List<String> operators = operationLogManager.getOperatorList(currentUser);
        if (null != operators && 0 != operators.size()) {
            operators.add(FrameworkConstant.QUERY_ALL);
        }
        return operators;
    }

    /**
     * 日志查询
     */
    @Override
    public List<OperationLogDTO> logQueryByCondition(String operationTimeStart, String operationTimeEnd, String operator, String serviceType, String language, String stationName, String currentUser) {

        List<OperationLog> operationLogList = operationLogManager.logQueryByCondition(operationTimeStart, operationTimeEnd, operator, serviceType, stationName, currentUser);
        List<OperationLogDTO> operationLogDTOList = new ArrayList<>();
        if (null != operationLogList && 0 != operationLogList.size()) {
            for (OperationLog operationLog : operationLogList) {
                OperationLogDTO operationLogDTO = new OperationLogDTO(operationLog, language);
                operationLogDTOList.add(operationLogDTO);
            }
        }
        return operationLogDTOList;
    }


}
