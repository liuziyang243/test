package com.crscd.passengerservice.soapinterface.implement;

import com.crscd.passengerservice.broadcast.content.dto.BroadcastContentSubstitutionDTO;
import com.crscd.passengerservice.broadcast.content.dto.NormalBroadcastContentDTO;
import com.crscd.passengerservice.broadcast.content.dto.SpecialBroadcastContentDTO;
import com.crscd.passengerservice.broadcast.content.serviceinterface.BroadcastContentInterface;
import com.crscd.passengerservice.broadcast.record.dto.BroadcastRecordDTO;
import com.crscd.passengerservice.broadcast.record.serviceinterface.BroadcastRecordServiceInterface;
import com.crscd.passengerservice.broadcast.serviceinterface.BroadcastPlanExecuteInterface;
import com.crscd.passengerservice.broadcast.template.dto.BroadcastTemplateDTO;
import com.crscd.passengerservice.broadcast.template.dto.BroadcastTemplateGroupDTO;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.serviceinterface.BroadcastTemplateGroupInterface;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.soapinterface.BroadcastServiceInterface;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/9
 *
 * @author lzy
 */
@WebService(serviceName = "BroadcastService",
        endpointInterface = "com.crscd.passengerservice.soapinterface.BroadcastServiceInterface")
@HandlerChain(file = "handlersdef.xml")
public class BroadcastServiceInterfaceImpl implements BroadcastServiceInterface {
    @Resource
    private WebServiceContext context;
    private BroadcastTemplateGroupInterface templateGroupInterface;
    private BroadcastContentInterface contentInterface;
    private BroadcastRecordServiceInterface recordServiceInterface;
    private BroadcastPlanExecuteInterface executeInterface;

    public WebServiceContext getContext() {
        return context;
    }

    public BroadcastServiceInterfaceImpl() {
    }

    public void setExecuteInterface(BroadcastPlanExecuteInterface executeInterface) {
        this.executeInterface = executeInterface;
    }

    public void setTemplateGroupInterface(BroadcastTemplateGroupInterface templateGroupInterface) {
        this.templateGroupInterface = templateGroupInterface;
    }

    public void setContentInterface(BroadcastContentInterface contentInterface) {
        this.contentInterface = contentInterface;
    }

    public void setRecordServiceInterface(BroadcastRecordServiceInterface recordServiceInterface) {
        this.recordServiceInterface = recordServiceInterface;
    }

    @Override
    public ResultMessage makeManualBroadcast(String planKey) {
        return executeInterface.makeManualBroadcast(planKey);
    }

    @Override
    public HashMap<String, ResultMessage> stopBroadcastPlan(String planKey) {
        return executeInterface.stopBroadcastPlan(planKey);
    }

    @Override
    public ResultMessage stopSingleBroadcast(String recordKey) {
        return executeInterface.stopSingleBroadcast(recordKey);
    }

    @Override
    public ResultMessage makeTrainManualBroadcast(String stationName, String trainNum, String contentName, String localContent, String EngContent, List<String> broadcastArea, int priorityLevel) {
        return executeInterface.makeTrainManualBroadcast(stationName, trainNum, contentName, localContent, EngContent, broadcastArea, priorityLevel);
    }

    @Override
    public List<String> getTrainNumList(String stationName) {
        return executeInterface.getTrainNumList(stationName);
    }

    @Override
    public List<Integer> getPriorityList(String stationName) {
        return executeInterface.getPriorityList(stationName);
    }

    @Override
    public List<String> getNormalContentNameList(String stationName, BroadcastKindEnum kind) {
        return contentInterface.getNormalContentNameList(stationName, kind);
    }

    @Override
    public List<String> getBroadcastGroupNameList(String stationName) {
        return templateGroupInterface.getBroadcastGroupNameList(stationName);
    }

    @Override
    public List<BroadcastTemplateGroupDTO> getBroadcastGroupList(String stationName, BroadcastKindEnum kind) {
        return templateGroupInterface.getBroadcastGroupList(stationName, kind);
    }

    @Override
    public List<BroadcastTemplateDTO> getBroadcastTemplateList(String stationName, String groupName) {
        return templateGroupInterface.getBroadcastTemplateList(stationName, groupName);
    }

    @Override
    public ResultMessage addBroadcastGroup(BroadcastTemplateGroupDTO group) {
        return templateGroupInterface.addBroadcastGroup(group);
    }

    @Override
    public ResultMessage addBroadcastTemplate(BroadcastTemplateDTO template) {
        return templateGroupInterface.addBroadcastTemplate(template);
    }

    @Override
    public List<BroadcastContentSubstitutionDTO> getNormalContentSubstitutionList() {
        return contentInterface.getNormalContentSubstitutionList();
    }

    @Override
    public List<NormalBroadcastContentDTO> getNormalContent(String stationName, BroadcastKindEnum kind) {
        return contentInterface.getNormalContent(stationName, kind);
    }

    @Override
    public ResultMessage addNormalContent(NormalBroadcastContentDTO dto) {
        return contentInterface.addNormalContent(dto);
    }

    @Override
    public ResultMessage modifyNormalContent(NormalBroadcastContentDTO dto) {
        return contentInterface.modifyNormalContent(dto);
    }

    @Override
    public ResultMessage delNormalContent(long id) {
        return contentInterface.delNormalContent(id);
    }

    @Override
    public List<String> getSpecialContentKindList(String stationName) {
        return contentInterface.getSpecialContentKindList(stationName);
    }

    @Override
    public ResultMessage addSpecialContentKind(String stationName, String kind) {
        return contentInterface.addSpecialContentKind(stationName, kind);
    }

    @Override
    public ResultMessage delSpecialContentKind(String stationName, String kind) {
        return contentInterface.delSpecialContentKind(stationName, kind);
    }

    @Override
    public List<BroadcastContentSubstitutionDTO> getSpecialContentSubstitutionList() {
        return contentInterface.getSpecialContentSubstitutionList();
    }

    @Override
    public List<SpecialBroadcastContentDTO> getSpecialContent(String stationName, String kind) {
        return contentInterface.getSpecialContent(stationName, kind);
    }

    @Override
    public ResultMessage addSpecialContent(SpecialBroadcastContentDTO dto) {
        return contentInterface.addSpecialContent(dto);
    }

    @Override
    public ResultMessage modifySpecialContent(SpecialBroadcastContentDTO dto) {
        return contentInterface.modifySpecialContent(dto);
    }

    @Override
    public ResultMessage delSpecialContent(long id) {
        return contentInterface.delSpecialContent(id);
    }

    @Override
    public ResultMessage modifyGroupName(String stationName, String oldGroupName, String newGroupName) {
        return templateGroupInterface.modifyGroupName(stationName, oldGroupName, newGroupName);
    }

    @Override
    public ResultMessage modifyBroadcastTemplate(BroadcastTemplateDTO template) {
        return templateGroupInterface.modifyBroadcastTemplate(template);
    }

    @Override
    public ResultMessage delBroadcastGroup(long groupID) {
        return templateGroupInterface.delBroadcastGroup(groupID);
    }

    @Override
    public ResultMessage delBroadcastTemplate(long templateID) {
        return templateGroupInterface.delBroadcastTemplate(templateID);
    }

    @Override
    public List<BroadcastRecordDTO> getBroadcastRecord(String stationName, String trainNum, String startDate, String endDate) {
        return recordServiceInterface.getBroadcastRecord(stationName, trainNum, startDate, endDate);
    }
}
