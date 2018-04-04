package com.crscd.passengerservice.result;

import com.crscd.framework.util.collection.MapUtil;
import com.crscd.passengerservice.result.errorinfo.ErrorCodeReasonMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/8 15:34
 * @version v1.00
 */
public class GenAndDelPlanResultMessage extends GroupResultMessage {
    private Map<String, String> errorDetails;

    public GenAndDelPlanResultMessage(Map<String, String> errorDetails) {
        super();
        this.errorDetails = new HashMap<>();
        for (Map.Entry<String, String> entry :
                errorDetails.entrySet()) {
            if ("Generate success".equalsIgnoreCase(entry.getValue())
                    || "Delete successful.".equalsIgnoreCase(entry.getValue())) {
                this.getResultMap().put(entry.getKey(), true);
            } else {
                this.getResultMap().put(entry.getKey(), false);
                this.getErrorDetails().put(entry.getKey(), entry.getValue());
            }
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
    }

    public Map<String, String> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(Map<String, String> errorDetails) {
        this.errorDetails = errorDetails;
    }

}
