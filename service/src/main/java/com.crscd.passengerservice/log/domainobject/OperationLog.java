package com.crscd.passengerservice.log.domainobject;

import com.crscd.framework.translation.annotation.TranslateAttribute;
import com.crscd.passengerservice.log.po.OperationLogBean;

/**
 * Created by Administrator on 2017/10/17.
 */
public class OperationLog {
    //操作日志的id，自增长id
    private String id;
    //操作时间
    private String operationTime;
    //操作人
    private String operator;
    //业务类型
    @TranslateAttribute
    private String serviceType;
    //操作内容
    private String operationContent;
    //操作结果
    @TranslateAttribute
    private String operationResult;
    //操作车站
    private String stationName;

    public OperationLog(OperationLogBean operationLogBean) {
        this.id = operationLogBean.getId();
        this.operationTime = operationLogBean.getOperationTime();
        this.operator = operationLogBean.getOperator();
        this.serviceType = operationLogBean.getServiceType();
        this.operationContent = operationLogBean.getOperationContent();
        this.operationResult = operationLogBean.getOperationResult();
        this.stationName = operationLogBean.getStationName();
    }

    public String getId() {
        return id;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public String getOperator() {
        return operator;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public String getStationName() {
        return stationName;
    }
}
