package com.crscd.passengerservice.multimedia;

import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.multimedia.business.MaterialManager;
import com.crscd.passengerservice.multimedia.domainobject.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/9/27.
 */
public class MaterialManagerTest {
    MaterialManager materialManager = ContextHelper.getMaterialManager();

    @Test
    public void getClientFtpUserInfoTest() {
        ClientFtpUserInfo result = materialManager.getClientFtpUserInfo();
    }

    @Test
    public void uploadPictureMaterialInfoTest() {
        PictureUploadInfo uploadInfo = new PictureUploadInfo();
        uploadInfo.setFileName("test.jpg");
        uploadInfo.setPictureWidth(320);
        uploadInfo.setPictureHeight(300);
        uploadInfo.setPictureType(".jpg");
        uploadInfo.setPictureDescription("test");
        uploadInfo.setApprovalStatus("Unreviewed");
        uploadInfo.setUploadTime("2017-09-28 12:00:00");
        uploadInfo.setUploader("user01");
        materialManager.uploadPictureMaterialInfo(uploadInfo);
    }

    @Test
    public void uploadVideoMaterialInfoTest() {
        VideoUploadInfo videoUploadInfo = new VideoUploadInfo();
        videoUploadInfo.setFileName("test.mp4");
        videoUploadInfo.setVideoDuration(3000);
        videoUploadInfo.setVideoType(".mp4");
        videoUploadInfo.setVideoDescription("test");
        videoUploadInfo.setApprovalStatus("Unreviewed");
        videoUploadInfo.setUploadTime("2017-09-28 12:00:00");
        videoUploadInfo.setUploader("user01");
        materialManager.uploadVideoMaterialInfo(videoUploadInfo);
    }

    @Test
    public void uploadDocumentMaterialInfoTest() {
        DocumentUploadInfo uploadInfo = new DocumentUploadInfo();
        uploadInfo.setFileName("test.txt");
        uploadInfo.setFileType(".txt");
        uploadInfo.setFileDescription("test");
        uploadInfo.setApprovalStatus("Unreviewed");
        uploadInfo.setUploadTime("2017-09-28 12:00:00");
        uploadInfo.setUploader("user01");
        materialManager.uploadDocumentMaterialInfo(uploadInfo);
    }

    @Test
    public void getAllPictureInfoListTest() {
        List<PictureInfo> pics = materialManager.getAllPictureInfoList();
    }

    @Test
    public void getAllVideoInfoListTest() {
        List<VideoInfo> videos = materialManager.getAllVideoInfoList();
    }

    @Test
    public void getAllDocumentInfoListTest() {
        List<DocumentInfo> docs = materialManager.getAllDocumentInfoList();
    }

    @Test
    public void deleteMaterialTest() {
        boolean result = materialManager.deleteMaterial("Picture", "test.jpg");
        System.out.print(result);
    }

    @Test
    public void approveMaterialTest() {
        MaterialApproveInfo mai = new MaterialApproveInfo();
        mai.setMaterialType("Picture");
        mai.setFileName("test.jpg");
        mai.setApprovalResult("Approved");
        mai.setApprover("user01");
        mai.setApproveTime("2017-02-01 12:00:00");
        materialManager.approveMaterial(mai);
    }

    @Test
    public void getPictureSentMaterialInfoTest() {
        List<FrameAvailableResourceInfo> results = materialManager.getPictureSentMaterialInfo();
    }

    @Test
    public void getVideoSentMaterialInfoTest() {
        List<FrameAvailableResourceInfo> results = materialManager.getVideoSentMaterialInfo();
    }

    @Test
    public void getDocumentSentMaterialInfoTest() {
        List<FrameAvailableResourceInfo> results = materialManager.getDocumentSentMaterialInfo();
    }

    @Test
    public void sendMaterialTest() {
        List<String> files = new ArrayList<>();
        files.add("3D小人 行李2.jpg");
        files.add("3D小人 旅行.jpg");
        String result = materialManager.sendMaterial(files);
        System.out.print(result);

    }

}
