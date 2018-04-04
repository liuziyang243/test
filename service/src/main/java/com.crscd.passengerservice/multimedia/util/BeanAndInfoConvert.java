package com.crscd.passengerservice.multimedia.util;

import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.multimedia.domainobject.*;
import com.crscd.passengerservice.multimedia.po.DocumentResourceInfoBean;
import com.crscd.passengerservice.multimedia.po.PictureResourceInfoBean;
import com.crscd.passengerservice.multimedia.po.VideoResourceInfoBean;

/**
 * Created by cuishiqing on 2017/9/19.
 */
public class BeanAndInfoConvert {

    /*Picture Bean Information Convert*/
    public PictureResourceInfoBean pictureInfoToBean(PictureUploadInfo picInfo) {
        PictureResourceInfoBean pri = new PictureResourceInfoBean();
        pri.setFileName(picInfo.getFileName());
        pri.setPictureWidth(picInfo.getPictureWidth());
        pri.setPictureHeight(picInfo.getPictureHeight());
        pri.setPictureType(picInfo.getPictureType());
        pri.setPictureDescription(picInfo.getPictureDescription());
        pri.setApprovalStatus(picInfo.getApprovalStatus());
        pri.setUploadTime(DateTimeUtil.getCurrentTimeString());
        pri.setUploader(picInfo.getUploader());
        pri.setApprovalResult("--");
        pri.setApprover("--");
        pri.setApproveTime("--");
        pri.setSendingStatus("--");
        return pri;
    }

    /*Video Bean Information Convert*/
    public VideoResourceInfoBean videoInfoToBean(VideoUploadInfo vidInfo) {
        VideoResourceInfoBean vri = new VideoResourceInfoBean();
        vri.setFileName(vidInfo.getFileName());
        vri.setVideoDuration(vidInfo.getVideoDuration());
        vri.setVideoType(vidInfo.getVideoType());
        vri.setVideoDescription(vidInfo.getVideoDescription());
        vri.setApprovalStatus(vidInfo.getApprovalStatus());
        vri.setUploadTime(DateTimeUtil.getCurrentTimeString());
        vri.setUploader(vidInfo.getUploader());
        vri.setApprovalResult("--");
        vri.setApprover("--");
        vri.setApproveTime("--");
        vri.setSendingStatus("--");
        return vri;
    }

    /*Document Bean information Convert*/
    public DocumentResourceInfoBean documentInfoToBean(DocumentUploadInfo docInfo) {
        DocumentResourceInfoBean dri = new DocumentResourceInfoBean();
        dri.setFileName(docInfo.getFileName());
        dri.setFileType(docInfo.getFileType());
        dri.setFileDescription(docInfo.getFileDescription());
        dri.setApprovalStatus(docInfo.getApprovalStatus());
        dri.setUploadTime(DateTimeUtil.getCurrentTimeString());
        dri.setUploader(docInfo.getUploader());
        dri.setApprovalResult("--");
        dri.setApprover("--");
        dri.setApproveTime("--");
        dri.setSendingStatus("--");

        return dri;
    }

    /*Picture Information Convert*/
    public PictureInfo pictureBeanToInfo(PictureResourceInfoBean picBean) {
        PictureInfo pmi = new PictureInfo();
        pmi.setFileName(picBean.getFileName());
        pmi.setPictureWidth(picBean.getPictureWidth());
        pmi.setPictureHight(picBean.getPictureHeight());
        pmi.setPictureType(picBean.getPictureType());
        pmi.setPictureDescription(picBean.getPictureDescription());
        pmi.setApprovalResult(picBean.getApprovalResult());
        pmi.setApprovalStatus(picBean.getApprovalStatus());
        pmi.setSendingStatus(picBean.getSendingStatus());
        pmi.setUploader(picBean.getUploader());
        pmi.setUploadTime(picBean.getUploadTime());
        pmi.setApproveTime(picBean.getApproveTime());
        pmi.setApprover(picBean.getApprover());
        return pmi;
    }

    /*Video Information Convert*/
    public VideoInfo videoBeanToInfo(VideoResourceInfoBean vidBean) {
        VideoInfo vmi = new VideoInfo();
        vmi.setFileName(vidBean.getFileName());
        vmi.setVideoDuration(vidBean.getVideoDuration());
        vmi.setVideoType(vidBean.getVideoType());
        vmi.setVideoDescription(vidBean.getVideoDescription());
        vmi.setApprovalStatus(vidBean.getApprovalStatus());
        vmi.setApprovalResult(vidBean.getApprovalResult());
        vmi.setSendingStatus(vidBean.getSendingStatus());
        vmi.setUploadTime(vidBean.getUploadTime());
        vmi.setUploader(vidBean.getUploader());
        vmi.setApproveTime(vidBean.getApproveTime());
        vmi.setApprover(vidBean.getApprover());
        return vmi;
    }

    /*Docuemnt Infomation Convert*/
    public DocumentInfo documentBeanToInfo(DocumentResourceInfoBean docBean) {
        DocumentInfo dmi = new DocumentInfo();
        dmi.setFileName(docBean.getFileName());
        dmi.setFileType(docBean.getFileType());
        dmi.setFileDescription(docBean.getFileDescription());
        dmi.setApprovalResult(docBean.getApprovalResult());
        dmi.setApprovalStatus(docBean.getApprovalStatus());
        dmi.setApproveTime(docBean.getApproveTime());
        dmi.setApprover(docBean.getApprover());
        dmi.setSendingStatus(docBean.getSendingStatus());
        dmi.setUploadTime(docBean.getUploadTime());
        dmi.setUploader(docBean.getUploader());
        return dmi;
    }
}
