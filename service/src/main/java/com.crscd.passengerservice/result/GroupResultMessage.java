package com.crscd.passengerservice.result;

import com.crscd.framework.util.collection.MapUtil;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.result.errorinfo.ErrorCodeReasonMap;

import java.util.HashMap;

/**
 * @author lzy
 * <p>
 * Create Time: 2017/11/3 9:00
 * @version v1.00
 */
public class GroupResultMessage extends ResultMessage {
    /**
     * 一组操作结果，存放在map中
     */
    private HashMap<String, Boolean> resultMap;

    public GroupResultMessage() {
        super();
        this.resultMap = new HashMap<>();
    }

    public GroupResultMessage(HashMap<String, Boolean> detail) {
        super();
        this.resultMap = detail;
        if (MapUtil.isNotEmpty(resultMap)) {
            for (Boolean result : resultMap.values()) {
                if (!result) {
                    this.setResult(false);
                    this.setStatusCode(30);
                    this.setMessage(ErrorCodeReasonMap.getReasonByCode(30));
                    break;
                }
            }
        }
    }

    public HashMap<String, Boolean> getResultMap() {
        return resultMap;
    }

    public void setResultMap(HashMap<String, Boolean> resultMap) {
        this.resultMap = resultMap;
    }
}
