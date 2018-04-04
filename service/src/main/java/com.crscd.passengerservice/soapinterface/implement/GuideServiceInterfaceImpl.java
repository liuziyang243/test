package com.crscd.passengerservice.soapinterface.implement;

import com.crscd.passengerservice.config.enumtype.ScreenTypeEnum;
import com.crscd.passengerservice.display.device.serviceinterface.DeviceManagerInterface;
import com.crscd.passengerservice.display.format.domainobject.FormatAndFrameListInfo;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;
import com.crscd.passengerservice.display.format.domainobject.PlanDataElement;
import com.crscd.passengerservice.display.format.domainobject.TicketWinScreenContent;
import com.crscd.passengerservice.display.format.serviceinterface.FormatManagerInterface;
import com.crscd.passengerservice.display.format.serviceinterface.FormatSendInterface;
import com.crscd.passengerservice.display.format.serviceinterface.FrameManagerInterface;
import com.crscd.passengerservice.display.guiderule.dto.ScreenGuideRuleDTO;
import com.crscd.passengerservice.display.guiderule.serviceinterface.ScreenGuideRuleInterface;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusAskInfo;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.ScreenStatusInfo;
import com.crscd.passengerservice.multimedia.domainobject.*;
import com.crscd.passengerservice.multimedia.dto.PlayListInfoDTO;
import com.crscd.passengerservice.multimedia.serviceinterface.MaterialManagerInterface;
import com.crscd.passengerservice.multimedia.serviceinterface.PlayListManagerInterface;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.soapinterface.GuideServiceInterface;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/10
 */
@WebService(serviceName = "GuideService",
        targetNamespace = "http://passengerservice.crscd.com/service",
        endpointInterface = "com.crscd.passengerservice.soapinterface.GuideServiceInterface")
@HandlerChain(file = "handlersdef.xml")
public class GuideServiceInterfaceImpl implements GuideServiceInterface {
    @Resource
    private WebServiceContext context;
    private ScreenGuideRuleInterface screenGuideRuleInterface;
    private DeviceManagerInterface deviceManagerInterface;
    private FormatManagerInterface formatManagerInterface;
    private FrameManagerInterface frameManagerInterface;
    private MaterialManagerInterface materialManagerInterface;
    private PlayListManagerInterface playListManagerInterface;
    private FormatSendInterface formatSendInterface;

    public GuideServiceInterfaceImpl() {
    }

    public WebServiceContext getContext() {
        return context;
    }

    public void setFormatSendInterface(FormatSendInterface formatSendInterface) {
        this.formatSendInterface = formatSendInterface;
    }

    public void setDeviceManagerInterface(DeviceManagerInterface deviceManagerInterface) {
        this.deviceManagerInterface = deviceManagerInterface;
    }

    public void setFormatManagerInterface(FormatManagerInterface formatManagerInterface) {
        this.formatManagerInterface = formatManagerInterface;
    }

    public void setFrameManagerInterface(FrameManagerInterface frameManagerInterface) {
        this.frameManagerInterface = frameManagerInterface;
    }

    public void setMaterialManagerInterface(MaterialManagerInterface materialManagerInterface) {
        this.materialManagerInterface = materialManagerInterface;
    }

    public void setPlayListManagerInterface(PlayListManagerInterface playListManagerInterface) {
        this.playListManagerInterface = playListManagerInterface;
    }

    public void setGuideServiceInterface(ScreenGuideRuleInterface screenGuideRuleInterface) {
        this.screenGuideRuleInterface = screenGuideRuleInterface;
    }

    @Override
    public List<ScreenGuideRuleDTO> getScreenGuideRuleList(String stationName, ScreenTypeEnum type) {
        return screenGuideRuleInterface.getScreenGuideRuleList(stationName, type);
    }

    @Override
    public ResultMessage modifyScreenGuideRule(ScreenGuideRuleDTO dto) {
        return screenGuideRuleInterface.modifyScreenGuideRule(dto);
    }

    @Override
    public ResultMessage addScreenGuideRule(ScreenGuideRuleDTO dto) {
        return screenGuideRuleInterface.addScreenGuideRule(dto);
    }

    @Override
    public ResultMessage delScreenGuideRule(long id) {
        return screenGuideRuleInterface.delScreenGuideRule(id);
    }

    /*********************  Device Manager Interface  *********************/

    @Override
    public HashMap<String, Boolean> deviceControlLine(String action) {
        return deviceManagerInterface.deviceControlLine(action);
    }

    @Override
    public boolean deviceControlStation(String action, String stationName) {
        return deviceManagerInterface.deviceControlStation(action, stationName);
    }

    @Override
    public boolean deviceControlByIps(String action, String stationName, List<String> screenIps) {
        return deviceManagerInterface.deviceControlByIps(action, stationName, screenIps);
    }

    @Override
    public List<ScreenStatusInfo> getScreenStatusByIp(List<ScreenStatusAskInfo> statusAskInfos, String stationName) {
        return deviceManagerInterface.getScreenStatusByIp(statusAskInfos, stationName);
    }

    @Override
    public List<ScreenStatusInfo> getScreenStatusStation(String stationName) {
        return deviceManagerInterface.getScreenStatusStation(stationName);
    }

    /*********************  Format Manager Interface  *********************/

    @Override
    public HashMap<String, String> getFormatList(String stationName, String screenType, String screenWidth, String screenHeight, String screenColor) {
        return formatManagerInterface.getFormatList(stationName, screenType, screenWidth, screenHeight, screenColor);
    }

    @Override
    public String deleteFormat(String formatNo, int deleteFrame) {
        return formatManagerInterface.deleteFormat(formatNo, deleteFrame);
    }

    @Override
    public String saveFormatData(FormatInfo formatInfo, LinkedHashMap<String, String> frameList) {
        return formatManagerInterface.saveFormatData(formatInfo, frameList);
    }

    @Override
    public String updateFormatData(String formatId, LinkedHashMap<String, String> frameList) {
        return formatManagerInterface.updateFormatData(formatId, frameList);
    }

    @Override
    public boolean formatBinding(int screenId, int formatType, String formatNo) {
        return formatManagerInterface.formatBinding(screenId, formatType, formatNo);
    }

    @Override
    public FormatAndFrameListInfo getFormatAndFrameListInfo(String formatId) {
        return formatManagerInterface.getFormatAndFrameListInfo(formatId);
    }

    @Override
    public FormatAndFrameListInfo getBoundFormat(int screenId, int formatType) {
        return formatManagerInterface.getBoundFormat(screenId, formatType);
    }

    @Override
    public String importFormatData(String stationName, String formatData) {
        return formatManagerInterface.importFormatData(stationName, formatData);
    }

    @Override
    public String exportFormatData(String formatId) {
        return formatManagerInterface.exportFormatData(formatId);
    }

    @Override
    public String getFormatId(String stationName) {
        return formatManagerInterface.getFormatId(stationName);
    }

    /*********************  Format Send Interface  *********************/

    @Override
    public void ticketScreenFormatSend(int screenID, ArrayList<HashMap<String, String>> ticketData) {
        formatSendInterface.ticketScreenFormatSend(screenID, ticketData);
    }

    @Override
    public void formatSend(int screenID, List<PlanDataElement> planDataElements) {
        formatSendInterface.formatSend(screenID, planDataElements);
    }

    @Override
    public boolean realTimeFormatSend(int screenID, int currentOrStandby) {
        return formatSendInterface.realTimeFormatSend(screenID, currentOrStandby);
    }

    @Override
    public boolean screenMessageDisplay(int screenID, String formatID, ArrayList<HashMap<String, String>> tableData) {
        return formatSendInterface.screenMessageDisplay(screenID, formatID, tableData);
    }

    @Override
    public HashMap<String, String> ticketWinScreenFormatSend(String stationName, List<TicketWinScreenContent> tscList, String type) {
        return formatSendInterface.ticketWinScreenFormatSend(stationName, tscList, type);
    }

    /*********************  Frame Manager Interface  *********************/

    @Override
    public HashMap<String, String> getFrameList(String stationName, String screenType, String screenWidth, String screenHeight, String screenColor) {
        return frameManagerInterface.getFrameList(stationName, screenType, screenWidth, screenHeight, screenColor);
    }

    @Override
    public boolean SaveFrameData(String stationName, String frameVarData) {
        return frameManagerInterface.SaveFrameData(stationName, frameVarData);
    }

    @Override
    public List<String> getDataSourceType() {
        return frameManagerInterface.getDataSourceType();
    }

    @Override
    public List<String> getDataElement(String dataSourceType) {
        return frameManagerInterface.getDataElement(dataSourceType);
    }

    @Override
    public boolean updateFrameData(String frameVarData) {
        return frameManagerInterface.updateFrameData(frameVarData);
    }

    @Override
    public String getFrameID(String stationName) {
        return frameManagerInterface.getFrameID(stationName);
    }

    @Override
    public boolean deleteFrame(String frameName) {
        return frameManagerInterface.deleteFrame(frameName);
    }

    @Override
    public String getFrameAndDTVar(String frameName) {
        return frameManagerInterface.getFrameAndDTVar(frameName);
    }

    /*********************  Material Manager Interface  *********************/

    @Override
    public ClientFtpUserInfo getClientFtpUserInfo() {
        return materialManagerInterface.getClientFtpUserInfo();
    }

    @Override
    public boolean uploadPictureMaterialInfo(PictureUploadInfo picInfo) {
        return materialManagerInterface.uploadPictureMaterialInfo(picInfo);
    }

    @Override
    public boolean uploadVideoMaterialInfo(VideoUploadInfo vidInfo) {
        return materialManagerInterface.uploadVideoMaterialInfo(vidInfo);
    }

    @Override
    public boolean uploadDocumentMaterialInfo(DocumentUploadInfo docInfo) {
        return materialManagerInterface.uploadDocumentMaterialInfo(docInfo);
    }

    @Override
    public List<PictureInfo> getAllPictureInfoList() {
        return materialManagerInterface.getAllPictureInfoList();
    }

    @Override
    public List<VideoInfo> getAllVideoInfoList() {
        return materialManagerInterface.getAllVideoInfoList();
    }

    @Override
    public List<DocumentInfo> getAllDocumentInfoList() {
        return materialManagerInterface.getAllDocumentInfoList();
    }

    @Override
    public boolean deleteMaterial(String resourceType, String fileName) {
        return materialManagerInterface.deleteMaterial(resourceType, fileName);
    }

    @Override
    public boolean approveMaterial(MaterialApproveInfo mai) {
        return materialManagerInterface.approveMaterial(mai);
    }

    @Override
    public String sendMaterial(List<String> fileNameList) {
        return materialManagerInterface.sendMaterial(fileNameList);
    }

    @Override
    public void RecodeMaterialSendingStatus(ScreenCtrlServerMaterialInfo screenCtrlServerMaterialInfo) {
        materialManagerInterface.RecodeMaterialSendingStatus(screenCtrlServerMaterialInfo);
    }

    @Override
    public HashMap<String, String> getMaterialSentStatus(String materialName) {
        return materialManagerInterface.getMaterialSentStatus(materialName);
    }

    @Override
    public List<FrameAvailableResourceInfo> getPictureSentMaterialInfo() {
        return materialManagerInterface.getPictureSentMaterialInfo();
    }

    @Override
    public List<FrameAvailableResourceInfo> getVideoSentMaterialInfo() {
        return materialManagerInterface.getVideoSentMaterialInfo();
    }

    @Override
    public List<FrameAvailableResourceInfo> getDocumentSentMaterialInfo() {
        return materialManagerInterface.getDocumentSentMaterialInfo();
    }

    @Override
    public void getMaterialListOnController() {
        materialManagerInterface.getMaterialListOnController();
    }

    /*********************  PlayList Manager Interface  *********************/

    @Override
    public boolean saveNewPlayList(PlayListInfoDTO playListInfo) {
        return playListManagerInterface.saveNewPlayList(playListInfo);
    }

    @Override
    public boolean updatePlayList(PlayListInfoDTO playListInfo) {
        return playListManagerInterface.updatePlayList(playListInfo);
    }

    @Override
    public boolean deletePlayList(int playListId) {
        return playListManagerInterface.deletePlayList(playListId);
    }

    @Override
    public List<PlayListInfoDTO> getPlayListsByListType(String listType) {
        return playListManagerInterface.getPlayListsByListType(listType);
    }

    @Override
    public PlayListInfoDTO getPlayListById(int id) {
        return playListManagerInterface.getPlayListById(id);
    }

    @Override
    public boolean savePlayListSendingStatus(PlayListSendingStatusInfo playListSendingStatusInfo) {
        return playListManagerInterface.savePlayListSendingStatus(playListSendingStatusInfo);
    }

    @Override
    public boolean playListSendSingleStation(String stationName, List<String> playListIds) {
        return playListManagerInterface.playListSendSingleStation(stationName, playListIds);
    }

    @Override
    public boolean playListSendLine(List<String> playListIds) {
        return playListManagerInterface.playListSendLine(playListIds);
    }
}
