package com.crscd.passengerservice.plan.dto;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/20.
 */
public class BasicMapMergeDTO {
    private String uuid;
    private Map<String, Boolean> mergeResult;//<trainNum,mergeResult>
    private BasicMapDetailDTO basicMapDetailDTO;

    public BasicMapMergeDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<String, Boolean> getMergeResult() {
        return mergeResult;
    }

    public void setMergeResult(Map<String, Boolean> mergeResult) {
        this.mergeResult = mergeResult;
    }

    public BasicMapDetailDTO getBasicMapDetailDTO() {
        return basicMapDetailDTO;
    }

    public void setBasicMapDetailDTO(BasicMapDetailDTO basicMapDetailDTO) {
        this.basicMapDetailDTO = basicMapDetailDTO;
    }
}
