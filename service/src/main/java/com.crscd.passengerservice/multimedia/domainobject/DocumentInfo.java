package com.crscd.passengerservice.multimedia.domainobject;

/**
 * Created by cuishiqing on 2017/9/20.
 */
public class DocumentInfo {
    /*文件名称*/
    private String fileName;
    /*文件类型*/
    private String fileType;
    /*文件描述*/
    private String fileDescription;
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
    /*审批人*/
    private String approver;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
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
