package com.crscd.passengerservice.multimedia.domainobject;

/**
 * Created by cuishiqing on 2017/9/19.
 */
public class PictureUploadInfo {
    /*文件名称*/
    private String fileName;
    /*图片宽度*/
    private int pictureWidth;
    /*图片高度*/
    private int pictureHeight;
    /*图片类型*/
    private String pictureType;
    /*图片描述*/
    private String pictureDescription;
    /*审批状态 Unreviewed:未审核 Reviewed:已审核**/
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

    public int getPictureWidth() {
        return pictureWidth;
    }

    public void setPictureWidth(int pictureWidth) {
        this.pictureWidth = pictureWidth;
    }

    public int getPictureHeight() {
        return pictureHeight;
    }

    public void setPictureHeight(int pictureHeight) {
        this.pictureHeight = pictureHeight;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public String getPictureDescription() {
        return pictureDescription;
    }

    public void setPictureDescription(String pictureDescription) {
        this.pictureDescription = pictureDescription;
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
