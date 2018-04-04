package com.crscd.passengerservice.log.po;


/**
 * Created by zs
 * Date:2016/5/23
 * Time:14:59
 */
public class OperationLogBean {

    //操作日志的id，自增长id
    private String id;
    //操作时间
    private String operationTime;
    //操作人
    private String operator;
    //业务类型
    private String serviceType;
    //操作内容
    // TODO: 2017/10/18 Oracle数据库改为clob类型 private Clob operationContent;
    private String operationContent;

    //操作结果
    private String operationResult;
    //操作车站
    private String stationName;

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

    // TODO: 2017/10/18  public String getOperationContent () {return ClobUtil.clob2Str(operationContent);}

    // TODO: 2017/10/18  public void setOperationContent (String operationContent) {this.operationContent = ClobUtil.str2Clob(operationContent);}

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


}
