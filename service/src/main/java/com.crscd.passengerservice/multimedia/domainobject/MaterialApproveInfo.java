package com.crscd.passengerservice.multimedia.domainobject;

/**
 * Created by cuishiqing on 2017/9/20.
 */
public class MaterialApproveInfo {
    /*素材类型 Picture:图片 Video:视频 Document:文件*/
    private String materialType;
    /*素材名称*/
    private String fileName;
    /*审批结果 Not approved:审核未通过  Approved:审核通过*/
    private String approvalResult;
    /*审批人*/
    private String approver;
    /*审批时间*/
    private String approveTime;

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }
}
