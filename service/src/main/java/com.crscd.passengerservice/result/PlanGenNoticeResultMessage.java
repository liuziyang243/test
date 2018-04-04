package com.crscd.passengerservice.result;

import com.crscd.framework.util.collection.MapUtil;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.result.errorinfo.ErrorCodeReasonMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/12 12:28
 * @version v1.00
 */
public class PlanGenNoticeResultMessage extends GroupResultMessage {
    private Map<ReceiverEnum, ArrayList<ResultMessage>> errorDetails;

    private String errorInfos;

    public PlanGenNoticeResultMessage(Map<ReceiverEnum, List<ResultMessage>> errorDetails) {
        super();
        this.errorDetails = new HashMap<>();
        for (Map.Entry<ReceiverEnum, List<ResultMessage>> entry : errorDetails.entrySet()) {
            boolean flag = true;
            // 只过滤出出现错误的结果并保存
            ArrayList<ResultMessage> tempList = new ArrayList<>();
            for (ResultMessage resultMessage : entry.getValue()) {
                if (!resultMessage.getResult()) {
                    tempList.add(resultMessage);
                    flag = false;
                }
            }
            if (!flag) {
                this.getErrorDetails().put(entry.getKey(), tempList);
            }
            this.getResultMap().put(entry.getKey().toString(), flag);
        }

        if (MapUtil.isNotEmpty(this.getResultMap())) {
            for (Boolean result : this.getResultMap().values()) {
                if (!result) {
                    this.setResult(false);
                    this.setStatusCode(30);
                    this.setMessage(ErrorCodeReasonMap.getReasonByCode(30));
                    break;
                }
            }
        }

        this.errorInfos = getErrorInfo();
    }

    public String getErrorInfos() {
        return errorInfos;
    }

    public void setErrorInfos(String errorInfos) {
        this.errorInfos = errorInfos;
    }

    private String getErrorInfo() {
        StringBuilder message = new StringBuilder("[");
        for (Map.Entry<ReceiverEnum, ArrayList<ResultMessage>> entry : errorDetails.entrySet()) {
            for (ResultMessage resultMessage : entry.getValue()) {
                if (!resultMessage.getResult()) {
                    message.append(entry.getKey().toString()).append(":").append(resultMessage.getMessage());
                }
            }
        }
        message.append("]");
        return message.toString();
    }

    public Map<ReceiverEnum, ArrayList<ResultMessage>> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(Map<ReceiverEnum, ArrayList<ResultMessage>> errorDetails) {
        this.errorDetails = errorDetails;
    }
}
