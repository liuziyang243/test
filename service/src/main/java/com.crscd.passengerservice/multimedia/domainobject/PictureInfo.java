package com.crscd.passengerservice.multimedia.domainobject;

/**
 * Created by cuishiqing on 2017/9/20.
 */
public class PictureInfo {
    /*文件名称*/
    private String fileName;
    /*图片宽度*/
    private int pictureWidth;
    /*图片高度*/
    private int pictureHight;
    /*图片类型*/
    private String pictureType;
    /*图片描述*/
    private String pictureDescription;
    /*审批状态 Unreviewed:未审核 Reviewed:已审核*/
    private String approvalStatus;
    /*审批结果 Not approved:审核未通过  Approved:审核通过*/
    private String approvalResult;
    /*下发状态*/
    private String sendingStatus;
    /*上传时间*/
    private String uploadTime;
    /*上传人*/
    private String uploader;
    /*审批时间*/
    private String approveTime;
    /*审批者*/
    private String approver;

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

    public int getPictureHight() {
        return pictureHight;
    }

    public void setPictureHight(int pictureHight) {
        this.pictureHight = pictureHight;
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

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getSendingStatus() {
        return sendingStatus;
    }

    public void setSendingStatus(String sendingStatus) {
        this.sendingStatus = sendingStatus;
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

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }
}
