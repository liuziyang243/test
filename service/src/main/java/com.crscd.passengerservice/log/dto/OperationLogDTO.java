package com.crscd.passengerservice.log.dto;

import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.log.domainobject.OperationLog;

/**
 * @author Administrator
 * @date 2017/10/17
 */
public class OperationLogDTO {
    //操作日志的id，UUID
    private String id;
    //操作时间
    private String operationTime;
    //操作人
    private String operator;
    //业务类型
    private String serviceType;
    //操作内容
    private String operationContent;
    //操作结果
    private String operationResult;
    //详细操作信息
    private String operationDetail;
    //操作车站
    private String stationName;

    public OperationLogDTO() {
    }

    public OperationLogDTO(OperationLog operationLog, String language) {
        this.id = operationLog.getId();
        this.operationTime = operationLog.getOperationTime();
        this.operator = operationLog.getOperator();
        this.serviceType = operationLog.getServiceType();
        this.operationContent = operationLog.getOperationContent();
        this.operationResult = operationLog.getOperationResult();
        this.stationName = operationLog.getStationName();
        if (language.equals(ServiceConstant.CN)) {
            operationDetail = "操作人" + operator + "在" + operationTime + "进行了" + serviceType + "操作，操作结果为" + operationResult + "，详细操作参数信息记录在操作参数中。";
        } else {
            operationDetail = "User " + operator + " make " + serviceType + " operation at " + operationTime + ", the relsult of operation is " + operationResult + ", the details of operation parameters has been recorded.";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationDetail() {
        return operationDetail;
    }

    public void setOperationDetail(String operationDetail) {
        this.operationDetail = operationDetail;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "OperationLogDTO{" +
                "id='" + id + '\'' +
                ", operationTime='" + operationTime + '\'' +
                ", operator='" + operator + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", operationContent='" + operationContent + '\'' +
                ", operationResult='" + operationResult + '\'' +
                ", operationDetail='" + operationDetail + '\'' +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}
