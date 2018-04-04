package com.crscd.passengerservice.plan.dto;

import com.crscd.framework.translation.annotation.TranslateAttribute;
import com.crscd.passengerservice.plan.domainobject.BasicMap;
import com.crscd.passengerservice.plan.enumtype.AnalyseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public class BasicMapDTO {
    private String uuid;
    private String receiveTime;
    private boolean confirmState;//true 代表已分析；false代表未分析
    private int basicMapType;
    private Map<String, AnalyseEnum> analyseResult;//<车次号，分析结果>

    @TranslateAttribute
    private Map<String, String> analyseResultTrans;//<车次号，分析结果> 翻译项

    public BasicMapDTO(BasicMap basicMap, Map<String, AnalyseEnum> analyseEnumMap) {
        this.uuid = basicMap.getUuid();
        this.receiveTime = basicMap.getReceiveTime();
        this.confirmState = basicMap.isConfirmState();
        this.basicMapType = 0x05;//完整计划(基本图)
        this.analyseResult = analyseEnumMap;
        //翻译项
        this.analyseResultTrans = new HashMap<>();
        if (analyseEnumMap.size() != 0) {
            for (String trainNum : analyseEnumMap.keySet()) {
                analyseResultTrans.put(trainNum, AnalyseEnum.getTypeString(analyseEnumMap.get(trainNum)));
            }
        }

    }

    public BasicMapDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public boolean isConfirmState() {
        return confirmState;
    }

    public void setConfirmState(boolean confirmState) {
        this.confirmState = confirmState;
    }

    public int getBasicMapType() {
        return basicMapType;
    }

    public void setBasicMapType(int basicMapType) {
        this.basicMapType = basicMapType;
    }

    public Map<String, AnalyseEnum> getAnalyseResult() {
        return analyseResult;
    }

    public void setAnalyseResult(Map<String, AnalyseEnum> analyseResult) {
        this.analyseResult = analyseResult;
    }

    public Map<String, String> getAnalyseResultTrans() {
        return analyseResultTrans;
    }

    public void setAnalyseResultTrans(Map<String, String> analyseResult) {
        this.analyseResultTrans = analyseResultTrans;
    }
}
