package com.crscd.passengerservice.multimedia.domainobject;

/**
 * Created by cuishiqing on 2017/9/20.
 */
public class VideoUploadInfo {
    /*文件名称*/
    private String fileName;
    /*视频播放时长*/
    private int videoDuration;
    /*视频类型*/
    private String videoType;
    /*视频描述*/
    private String videoDescription;
    /*审批状态 Unreviewed:未审核 Reviewed:已审核*/
    private String approvalStatus;
    /*上传时间*/
    private String uploadTime;
    /*上传人*/
    private String uploader;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}
