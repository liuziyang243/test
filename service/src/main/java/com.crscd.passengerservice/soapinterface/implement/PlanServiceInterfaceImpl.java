package com.crscd.passengerservice.soapinterface.implement;

import com.crscd.framework.annotation.MakeRecord;
import com.crscd.framework.translation.annotation.MakeTranslation;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.notice.dto.NoticeMessageDTO;
import com.crscd.passengerservice.notice.dto.OperationLogDTO;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.notice.serviceinterface.NoticeServiceInterface;
import com.crscd.passengerservice.notice.serviceinterface.PlanGenNoticeInterface;
import com.crscd.passengerservice.plan.dto.*;
import com.crscd.passengerservice.plan.enumtype.*;
import com.crscd.passengerservice.plan.serviceinterface.*;
import com.crscd.passengerservice.result.GenAndDelPlanResultMessage;
import com.crscd.passengerservice.result.GroupResultMessage;
import com.crscd.passengerservice.result.PagedGenAndDelPlanResultMessage;
import com.crscd.passengerservice.result.PlanGenNoticeResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.result.page.PagedDispatchPlans;
import com.crscd.passengerservice.soapinterface.PlanServiceInterface;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 15:40
 */
@WebService(serviceName = "PlanService",
        targetNamespace = "http://passengerservice.crscd.com/service",
        endpointInterface = "com.crscd.passengerservice.soapinterface.PlanServiceInterface")
@HandlerChain(file = "handlersdef.xml")
public class PlanServiceInterfaceImpl implements PlanServiceInterface {

    @Resource
    private WebServiceContext context;

    private BasicPlanInterface basicPlanInterface;
    private DispatchPlanInterface dispatchPlanInterface;
    private PassengerPlanInterface passengerPlanInterface;
    private GuidePlanInterface guidePlanInterface;
    private BroadcastPlanInterface broadcastPlanInterface;
    private OrganizeTemplateInterface organizeTemplateInterface;
    private NoticeServiceInterface noticeServiceInterface;
    private GenerateAndDelPlanInterface generateAndDelPlanInterface;
    private PlanGenNoticeInterface planGenNoticeInterface;
    private BasicMapInterface basicMapInterface;

    public PlanServiceInterfaceImpl() {
    }

    public WebServiceContext getContext() {
        return context;
    }

    public void setDispatchPlanInterface(DispatchPlanInterface dispatchPlanInterface) {
        this.dispatchPlanInterface = dispatchPlanInterface;
    }

    public void setBasicPlanInterface(BasicPlanInterface basicPlanInterface) {
        this.basicPlanInterface = basicPlanInterface;
    }

    public void setPassengerPlanInterface(PassengerPlanInterface passengerPlanInterface) {
        this.passengerPlanInterface = passengerPlanInterface;
    }

    public void setGuidePlanInterface(GuidePlanInterface guidePlanInterface) {
        this.guidePlanInterface = guidePlanInterface;
    }

    public void setBroadcastPlanInterface(BroadcastPlanInterface broadcastPlanInterface) {
        this.broadcastPlanInterface = broadcastPlanInterface;
    }

    public void setOrganizeTemplateInterface(OrganizeTemplateInterface organizeTemplateInterface) {
        this.organizeTemplateInterface = organizeTemplateInterface;
    }

    public void setNoticeServiceInterface(NoticeServiceInterface noticeServiceInterface) {
        this.noticeServiceInterface = noticeServiceInterface;
    }

    public void setGenerateAndDelPlanInterface(GenerateAndDelPlanInterface generateAndDelPlanInterface) {
        this.generateAndDelPlanInterface = generateAndDelPlanInterface;
    }

    public void setPlanGenNoticeInterface(PlanGenNoticeInterface planGenNoticeInterface) {
        this.planGenNoticeInterface = planGenNoticeInterface;
    }

    public void setBasicMapInterface(BasicMapInterface basicMapInterface) {
        this.basicMapInterface = basicMapInterface;
    }

    @MakeTranslation
    @Override
    public List<BasicPlanDTO> getBasicPlanMainInfoList(String stationName, String trainNum, TrainTypeEnum trainType) {
        return basicPlanInterface.getBasicPlanMainInfoList(stationName, trainNum, trainType);
    }

    @MakeTranslation
    @Override
    public List<BasicTrainStationDTO> getBasicStationInfoList(String trainNum) {
        return basicPlanInterface.getBasicStationInfoList(trainNum);
    }

    @MakeRecord
    @Override
    public ResultMessage addBasicPlanMainInfo(BasicPlanDTO dto) {
        return basicPlanInterface.addBasicPlanMainInfo(dto);
    }

    @MakeRecord
    @Override
    public ResultMessage updateBasicPlanMainInfo(BasicPlanDTO dto) {
        return basicPlanInterface.updateBasicPlanMainInfo(dto);
    }

    @MakeRecord
    @Override
    public ResultMessage removeBasicPlanMainInfo(String trainNum) {
        return basicPlanInterface.removeBasicPlanMainInfo(trainNum);
    }

    @MakeRecord
    @Override
    public ResultMessage addBasicPlanStationInfo(String trainNum, BasicTrainStationDTO dto) {
        return basicPlanInterface.addBasicPlanStationInfo(trainNum, dto);
    }

    @MakeRecord
    @Override
    public ResultMessage updateBasicPlanStationInfo(String trainNum, BasicTrainStationDTO dto) {
        return basicPlanInterface.updateBasicPlanStationInfo(trainNum, dto);
    }

    @MakeRecord
    @Override
    public ResultMessage removeBasicPlanStationInfo(String trainNum, String stationName) {
        return basicPlanInterface.removeBasicPlanStationInfo(trainNum, stationName);
    }

    @Override
    public float calculateMileage(String startStation, String presentStation) {
        return basicPlanInterface.calculateMileage(startStation, presentStation);
    }

    @MakeTranslation
    @Override
    public List<DispatchPlanDTO> getPeriodDispatchPlan(String stationName, String trainNum, String startDate, String endDate) {
        return dispatchPlanInterface.getPeriodDispatchPlan(stationName, trainNum, startDate, endDate);
    }

    @Override
    public PagedDispatchPlans getPagedDispatchPlan(String stationName, int pageNum, int pageSize) {
        return dispatchPlanInterface.getPagedDispatchPlan(stationName, pageNum, pageSize);
    }

    @MakeRecord
    @Override
    public ResultMessage updateDispatchPlan(String planKey, HashMap<DispatchPlanModifyEnum, String> modifyList, LateEarlyReasonEnum arriveTimeModifyReason, LateEarlyReasonEnum departureTimeModifyReason) {
        return dispatchPlanInterface.updateDispatchPlan(planKey, modifyList, arriveTimeModifyReason, departureTimeModifyReason);
    }

    @MakeTranslation
    @Override
    public List<PassengerStationPlanDTO> getPeriodPassengerPlan(String stationName, String trainNum, String startDate, String endDate) {
        return passengerPlanInterface.getPeriodPassengerPlan(stationName, trainNum, startDate, endDate);
    }

    @MakeRecord
    @Override
    public ResultMessage updatePassengerPlan(String planKey, HashMap<PassengerPlanModifyEnum, String> modifyList) {
        return passengerPlanInterface.updatePassengerPlan(planKey, modifyList);
    }

    @MakeTranslation
    @Override
    public List<GuideStationPlanDTO> getPeriodGuidePlan(String stationName, String trainNum, String startDate, String endDate) {
        return guidePlanInterface.getPeriodGuidePlan(stationName, trainNum, startDate, endDate);
    }

    @MakeRecord
    @Override
    public ResultMessage updateGuidePlan(String planKey, HashMap<GuidePlanModifyEnum, String> modifyList, LateEarlyReasonEnum arriveTimeModifyReason, LateEarlyReasonEnum departureTimeModifyReason) {
        return guidePlanInterface.updateGuidePlan(planKey, modifyList, arriveTimeModifyReason, departureTimeModifyReason);
    }

    @MakeTranslation
    @Override
    public List<BroadcastStationPlanDTO> getPeriodBroadcastPlan(String stationName, String trainNum, BroadcastKindEnum broadcastKind, String startDate, String endDate) {
        return broadcastPlanInterface.getPeriodBroadcastPlan(stationName, trainNum, broadcastKind, startDate, endDate);
    }

    @MakeRecord
    @Override
    public ResultMessage modifyBroadcastPlanExecuteTime(String planKey, String executeTime) {
        return broadcastPlanInterface.modifyBroadcastPlanExecuteTime(planKey, executeTime);
    }

    @MakeRecord
    @Override
    public ResultMessage modifyBroadcastPlanExecuteArea(String planKey, List<String> executeArea) {
        return broadcastPlanInterface.modifyBroadcastPlanExecuteArea(planKey, executeArea);
    }

    @MakeRecord
    @Override
    public GroupResultMessage changePeriodBroadcastPlanModeToManualMode(String stationName, String startDate, String endDate) {
        return broadcastPlanInterface.changePeriodBroadcastPlanModeToManualMode(stationName, startDate, endDate);
    }

    @MakeRecord
    @Override
    public GroupResultMessage changePeriodBroadcastPlanModeToAutoMode(String stationName, String startDate, String endDate) {
        return broadcastPlanInterface.changePeriodBroadcastPlanModeToAutoMode(stationName, startDate, endDate);
    }

    @MakeRecord
    @Override
    public ResultMessage changeSingleBroadcastPlanModeToManualMode(String planKey) {
        return broadcastPlanInterface.changeSingleBroadcastPlanModeToManualMode(planKey);
    }

    @MakeRecord
    @Override
    public ResultMessage changeSingleBroadcastPlanModeToAutoMode(String planKey) {
        return broadcastPlanInterface.changeSingleBroadcastPlanModeToAutoMode(planKey);
    }

    @MakeTranslation
    @Override
    public List<BasicPlanDTO> getValidBasicPlan(String trainNum, String stationName) {
        return generateAndDelPlanInterface.getValidBasicPlan(trainNum, stationName);
    }

    @MakeRecord
    @Override
    public GenAndDelPlanResultMessage generatePlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {
        return generateAndDelPlanInterface.generatePlanList(trainNumList, stationName, startDate, endDate);
    }

    @MakeRecord
    @Override
    public GenAndDelPlanResultMessage delPlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {
        return generateAndDelPlanInterface.delPlanList(trainNumList, stationName, startDate, endDate);
    }

    @MakeRecord
    @Override
    public PagedGenAndDelPlanResultMessage generatePlanListPaged(List<String> trainNumList, String stationName, String startDate, String endDate) {
        return generateAndDelPlanInterface.generatePlanListPaged(trainNumList, stationName, startDate, endDate);
    }

    @MakeRecord
    @Override
    public PagedGenAndDelPlanResultMessage delPlanListPaged(List<String> trainNumList, String stationName, String startDate, String endDate) {
        return generateAndDelPlanInterface.delPlanListPaged(trainNumList, stationName, startDate, endDate);
    }

    @Override
    public ResultMessage dropErrorDetailCache(Long uid) {
        return generateAndDelPlanInterface.dropErrorDetailCache(uid);
    }

    @MakeTranslation
    @Override
    public PagedGenAndDelPlanResultMessage getErrorDetailsByPageNum(Long uid, int pageNum) {
        return generateAndDelPlanInterface.getErrorDetailsByPageNum(uid, pageNum);
    }

    @MakeTranslation
    @Override
    public List<OrganizeTemplateDTO> getOrganizeTemplate(String stationName, String trainNum, TrainTypeEnum trainType) {
        return organizeTemplateInterface.getOrganizeTemplate(stationName, trainNum, trainType);
    }

    @MakeRecord
    @Override
    public ResultMessage modifyOrganizeTemplate(OrganizeTemplateDTO dto) {
        return organizeTemplateInterface.modifyOrganizeTemplate(dto);
    }

    @MakeTranslation
    @Override
    public List<OrganizeTemplateDTO> getUnconfiguredOrganizeTemplate(String stationName) {
        return organizeTemplateInterface.getUnconfiguredOrganizeTemplate(stationName);
    }

    @MakeTranslation
    @Override
    public List<NoticeMessageDTO> getUnhandledNoticeList() {
        return noticeServiceInterface.getUnhandledNoticeList();
    }

    @MakeTranslation
    @Override
    public List<NoticeMessageDTO> getNoticeMessageByStation(String stationName, ReceiverEnum pageName, String startDate, String endDate) {
        return noticeServiceInterface.getNoticeMessageByStation(stationName, pageName, startDate, endDate);
    }

    @MakeTranslation
    @Override
    public List<OperationLogDTO> getOperationLogByStation(String stationName, SenderEnum senderPage, ReceiverEnum pageName, String startDate, String endDate) {
        return noticeServiceInterface.getOperationLogByStation(stationName, senderPage, pageName, startDate, endDate);
    }

    @MakeRecord
    @Override
    public PlanGenNoticeResultMessage generateDispatchPlanModifyNotice(String user, String planKey, HashMap<DispatchPlanModifyEnum, String> modifyList, List<ReceiverEnum> receiverList) {
        return planGenNoticeInterface.generateDispatchPlanModifyNotice(user, planKey, modifyList, receiverList);
    }

    @MakeRecord
    @Override
    public PlanGenNoticeResultMessage generatePassengerPlanModifyNotice(String user, String planKey, HashMap<PassengerPlanModifyEnum, String> modifyList, List<ReceiverEnum> receiverList) {
        return planGenNoticeInterface.generatePassengerPlanModifyNotice(user, planKey, modifyList, receiverList);
    }

    @MakeRecord
    @Override
    public PlanGenNoticeResultMessage forwardPassengerPlanNotice(String user, NoticeMessageDTO dto, List<ReceiverEnum> receiverList) {
        return planGenNoticeInterface.forwardPassengerPlanNotice(user, dto, receiverList);
    }

    @MakeRecord
    @Override
    public GroupResultMessage acceptNotice(String processUser, ArrayList<Long> idList, ReceiverEnum pageName) {
        return noticeServiceInterface.acceptNotice(processUser, idList, pageName);
    }

    @MakeRecord
    @Override
    public GroupResultMessage rejectNotice(String processUser, ArrayList<Long> idList) {
        return noticeServiceInterface.rejectNotice(processUser, idList);
    }

    @MakeRecord
    @Override
    public ResultMessage startAutoProcessTicketNotice() {
        return noticeServiceInterface.startAutoProcessTicketNotice();
    }

    @MakeRecord
    @Override
    public ResultMessage stopAutoProcessTicketNotice() {
        return noticeServiceInterface.stopAutoProcessTicketNotice();
    }

    @MakeRecord
    @Override
    public ResultMessage startAutoProcessCTCNotice() {
        return noticeServiceInterface.startAutoProcessCTCNotice();
    }

    @MakeRecord
    @Override
    public ResultMessage stopAutoProcessCTCNotice() {
        return noticeServiceInterface.stopAutoProcessCTCNotice();
    }

    //列车时刻表比较
    /*
     *获取历史基本图列表基本信息
     */
    @Override
    public List<BasicMapDTO> getBasicMapList(String startDate, String endDate) {
        return basicMapInterface.getBasicMapList(startDate, endDate);
    }

    /*
     *根据UUID获得某基本图的基本信息
     */
    @Override
    public BasicMapDTO getBasicMapByUuid(String uuid) {
        return basicMapInterface.getBasicMapByUuid(uuid);
    }


    /*
     *根据UUID分析某基本图的基本信息
     */
    @Override
    public BasicMapDTO analyseBasicMapByUuid(String uuid) {
        return basicMapInterface.analyseBasicMapByUuid(uuid);
    }

    /*
     *查看当前基本图的详细比较信息
     */
    @Override
    public BasicMapDetailDTO getCompareDetail(String uuid, String stationName) {
        return basicMapInterface.getCompareDetail(uuid, stationName);
    }

    /*
     * 合并基本图
     * uuid为合并的基本图对象索引号，trainNum为合并的车次号，result为合并的方式
     */
    @Override
    public BasicMapMergeDTO mergeBasicMap(List<BasicTrainDetailDTO> mergeTrainList, String uuid, String stationName) {
        return basicMapInterface.mergeBasicMap(mergeTrainList, uuid, stationName);
    }

    /*
     * 获取基本图更新时间
     */
    @Override
    public String getLastBasicMapRecTime() {
        return basicMapInterface.getLastBasicMapRecTime();
    }

}
