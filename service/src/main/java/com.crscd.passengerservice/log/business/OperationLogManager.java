package com.crscd.passengerservice.log.business;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.authority.po.UserInfo;
import com.crscd.passengerservice.authority.serviceinterface.UserService;
import com.crscd.passengerservice.log.domainobject.OperationLog;
import com.crscd.passengerservice.log.po.OperationLogBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zs
 * Created by zs
 * on 2017/10/16.
 */

public class OperationLogManager {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogManager.class);
    private MethodRelationManager methodRelationManager;
    private DataSet dataSet;
    private UserService userService;

    public OperationLogManager() {
    }

    public void setMethodRelationManager(MethodRelationManager methodRelationManager) {
        this.methodRelationManager = methodRelationManager;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 操作记录
     */
    public void logRecord(String stationName, String operator, String methodName, Map<String, Object> paramMap, String operationResult) {
        OperationLogBean bean = new OperationLogBean();
        //OperationTime
        bean.setOperationTime(DateTimeUtil.getCurrentDatetimeString());

        //operator
        bean.setOperator(operator);

        //stationName
        bean.setStationName(stationName);

        //serviceType
        String serviceType = methodRelationManager.findServiceType(methodName);
        bean.setServiceType(serviceType);

        //operationContent
        String operationContent = "";
        if (null != paramMap && 0 != paramMap.size()) {
            operationContent = JsonUtil.toJSON(paramMap);
        }
        bean.setOperationContent(operationContent);

        //OperationResult
        bean.setOperationResult(operationResult);

        dataSet.insert(bean);

    }


    //当前用户可查看的全部操作人
    public List<String> getOperatorList(String currentUser) {
        List<UserInfo> userInfos = userService.getUserList(currentUser);
        List<String> operators = new ArrayList<>();
        if (null != userInfos && 0 != userInfos.size()) {
            for (UserInfo userInfo : userInfos) {
                operators.add(userInfo.getUserName());
            }
        }
        if (currentUser.equals(ServiceConstant.SUPER_ADMIN)) {
            operators.add(ServiceConstant.SYSTEM);
        }
        return operators;
    }


    /**
     * 操作记录查询
     */
    public List<OperationLog> logQueryByCondition(String operationTimeStart, String operationTimeEnd, String operator, String serviceType, String stationName, String currentUser) {
        StringBuilder condition = new StringBuilder("1=1");
        //组合查询条件
        if (operationTimeStart.length() != 0) {
            condition.append(" and OPERATIONTIME >= '");
            condition.append(operationTimeStart);
            condition.append("'");
        }
        if (operationTimeEnd.length() != 0) {
            condition.append(" and OPERATIONTIME <= '");
            condition.append(operationTimeEnd);
            condition.append("'");
        }

        //操作人选项输入不允许为null
        if (null == operator) {
            return null;
        } else {
            if (operator.equals(FrameworkConstant.QUERY_ALL)) {
                List<String> operators = getOperatorList(currentUser);
                condition.append(" and OPERATOR in ('");
                for (String operatorInner : operators) {
                    condition.append(operatorInner);
                    condition.append("',");
                }
                condition.deleteCharAt(condition.length() - 1);
                condition.append(")");


            } else {
                condition.append(" and OPERATOR = '");
                condition.append(operator);
                condition.append("'");
            }
        }


        if (serviceType.length() != 0) {
            if (!serviceType.equals(FrameworkConstant.QUERY_ALL)) {
                condition.append(" and SERVICETYPE = '");
                condition.append(serviceType);
                condition.append("'");
            }
        }
        if (stationName.length() != 0) {
            condition.append(" and STAIONNAME = '");
            condition.append(stationName);
            condition.append("'");
        }


        //排序条件
        String sort = "OPERATIONTIME";

        List<OperationLogBean> operationLogBeanList = dataSet.selectListWithConditionAndSort(OperationLogBean.class, condition.toString(), sort);

        List<OperationLog> operationLogList = new ArrayList<>();
        if (operationLogBeanList.size() != 0) {
            for (OperationLogBean operationLogBean : operationLogBeanList) {
                OperationLog operationLog = new OperationLog(operationLogBean);
                operationLogList.add(operationLog);
            }
        }

        return operationLogList;
    }

}
