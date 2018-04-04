package com.crscd.passengerservice.multimedia.serviceinterface;

import com.crscd.passengerservice.multimedia.domainobject.*;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cuishiqing on 2018/1/4.
 */
public interface MaterialManagerInterface {
    //获取客户端FTP用户登录信息
    @WebResult(name = "getClientFtpUserInfo")
    ClientFtpUserInfo getClientFtpUserInfo();

    //图片素材上传信息存储
    @WebResult(name = "uploadPictureMaterialInfo")
    boolean uploadPictureMaterialInfo(@WebParam(name = "picInfo") PictureUploadInfo picInfo);

    //视频上传信息存储
    @WebResult(name = "uploadVideoMaterialInfo")
    boolean uploadVideoMaterialInfo(@WebParam(name = "vidInfo") VideoUploadInfo vidInfo);

    //文档上传信息存储
    @WebResult(name = "uploadDocumentMaterialInfo")
    boolean uploadDocumentMaterialInfo(@WebParam(name = "docInfo") DocumentUploadInfo docInfo);

    //获取图片素材列表
    @WebResult(name = "getAllPictureInfoList")
    List<PictureInfo> getAllPictureInfoList();

    //获取视频素材列表
    @WebResult(name = "getAllVideoInfoList")
    List<VideoInfo> getAllVideoInfoList();

    //获取文件素材列表
    @WebResult(name = "getAllDocumentInfoList")
    List<DocumentInfo> getAllDocumentInfoList();

    //删除素材
    @WebResult(name = "deleteMaterial")
    boolean deleteMaterial(@WebParam(name = "resourceType") String resourceType, @WebParam(name = "fileName") String fileName);

    //素材审批
    @WebResult(name = "approveMaterial")
    boolean approveMaterial(@WebParam(name = "mai") MaterialApproveInfo mai);

    //下发素材
    @WebResult(name = "sendMaterial")
    String sendMaterial(@WebParam(name = "fileNameList") List<String> fileNameList);

    //记录素材在单个控制器上的下发状态
    void RecodeMaterialSendingStatus(ScreenCtrlServerMaterialInfo screenCtrlServerMaterialInfo);

    //获取素材在所有控制器上的下发状态
    @WebResult(name = "getMaterialSentStatus")
    HashMap<String, String> getMaterialSentStatus(@WebParam(name = "materialName") String materialName);

    //获取帧编辑可用图片列表
    @WebResult(name = "getPictureSentMaterialInfo")
    List<FrameAvailableResourceInfo> getPictureSentMaterialInfo();

    //获取帧编辑可用视频列表
    @WebResult(name = "getVideoSentMaterialInfo")
    List<FrameAvailableResourceInfo> getVideoSentMaterialInfo();

    //获取帧编辑可用文件列表
    @WebResult(name = "getDocumentSentMaterialInfo")
    List<FrameAvailableResourceInfo> getDocumentSentMaterialInfo();

    //获取各控制器上素材列表
    @WebResult(name = "getMaterialListOnController")
    void getMaterialListOnController();

}
