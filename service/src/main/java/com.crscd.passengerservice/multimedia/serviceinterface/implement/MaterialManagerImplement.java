package com.crscd.passengerservice.multimedia.serviceinterface.implement;

import com.crscd.passengerservice.multimedia.business.MaterialManager;
import com.crscd.passengerservice.multimedia.domainobject.*;
import com.crscd.passengerservice.multimedia.serviceinterface.MaterialManagerInterface;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cuishiqing on 2018/1/4.
 */
public class MaterialManagerImplement implements MaterialManagerInterface {
    private MaterialManager materialManager;

    public void setMaterialManager(MaterialManager materialManager) {
        this.materialManager = materialManager;
    }

    @Override
    public ClientFtpUserInfo getClientFtpUserInfo() {
        return materialManager.getClientFtpUserInfo();
    }

    @Override
    public boolean uploadPictureMaterialInfo(PictureUploadInfo picInfo) {
        return materialManager.uploadPictureMaterialInfo(picInfo);
    }

    @Override
    public boolean uploadVideoMaterialInfo(VideoUploadInfo vidInfo) {
        return materialManager.uploadVideoMaterialInfo(vidInfo);
    }

    @Override
    public boolean uploadDocumentMaterialInfo(DocumentUploadInfo docInfo) {
        return materialManager.uploadDocumentMaterialInfo(docInfo);
    }

    @Override
    public List<PictureInfo> getAllPictureInfoList() {
        return materialManager.getAllPictureInfoList();
    }

    @Override
    public List<VideoInfo> getAllVideoInfoList() {
        return materialManager.getAllVideoInfoList();
    }

    @Override
    public List<DocumentInfo> getAllDocumentInfoList() {
        return materialManager.getAllDocumentInfoList();
    }

    @Override
    public boolean deleteMaterial(String resourceType, String fileName) {
        return materialManager.deleteMaterial(resourceType, fileName);
    }

    @Override
    public boolean approveMaterial(MaterialApproveInfo mai) {
        return materialManager.approveMaterial(mai);
    }

    @Override
    public String sendMaterial(List<String> fileNameList) {
        return materialManager.sendMaterial(fileNameList);
    }

    @Override
    public void RecodeMaterialSendingStatus(ScreenCtrlServerMaterialInfo screenCtrlServerMaterialInfo) {
        materialManager.RecodeMaterialSendingStatus(screenCtrlServerMaterialInfo);
    }

    @Override
    public HashMap<String, String> getMaterialSentStatus(String materialName) {
        return materialManager.getMaterialSentStatus(materialName);
    }

    @Override
    public List<FrameAvailableResourceInfo> getPictureSentMaterialInfo() {
        return materialManager.getPictureSentMaterialInfo();
    }

    @Override
    public List<FrameAvailableResourceInfo> getVideoSentMaterialInfo() {
        return materialManager.getVideoSentMaterialInfo();
    }

    @Override
    public List<FrameAvailableResourceInfo> getDocumentSentMaterialInfo() {
        return materialManager.getDocumentSentMaterialInfo();
    }

    @Override
    public void getMaterialListOnController() {
        materialManager.getMaterialListOnController();
    }
}
