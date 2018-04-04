package com.crscd.passengerservice.multimedia.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.text.FtpUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.config.po.ScreenCtrlServerConfigBean;
import com.crscd.passengerservice.display.screencontrolserver.business.Material;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.MaterialInfo;
import com.crscd.passengerservice.multimedia.domainobject.*;
import com.crscd.passengerservice.multimedia.po.DocumentResourceInfoBean;
import com.crscd.passengerservice.multimedia.po.PictureResourceInfoBean;
import com.crscd.passengerservice.multimedia.po.ResourceSendingStatusBean;
import com.crscd.passengerservice.multimedia.po.VideoResourceInfoBean;
import com.crscd.passengerservice.multimedia.util.BeanAndInfoConvert;
import com.crscd.passengerservice.multimedia.util.FtpHelper;
import com.crscd.passengerservice.multimedia.util.SizeConverterUtil;
import org.apache.commons.net.ftp.FTPFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/9/19.
 */
public class MaterialManager {
    //末次下发文件列表
    private static List<MaterialInfo> lastSentFiles = new ArrayList<>();
    //数据库
    private DataSet oracleDataSet;
    //Info与Bean格式转换工具
    private BeanAndInfoConvert convert;
    //素材管理接口
    private Material material;

    public MaterialManager() {
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setConvert(BeanAndInfoConvert convert) {
        this.convert = convert;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    /**
     * @return
     * @see "获取客户端FTP用户登录信息"
     */
    public ClientFtpUserInfo getClientFtpUserInfo() {
        ClientFtpUserInfo cfu = new ClientFtpUserInfo();
        cfu.setFtpAddress(ConfigHelper.getString("travelService.ftpAddress"));
        cfu.setPort(ConfigHelper.getString("travelService.ftpServerPort"));
        cfu.setUserName(ConfigHelper.getString("travelService.clientFtpUserName"));
        cfu.setPassWord(ConfigHelper.getString("travelService.clientFtpPassword"));
        return cfu;
    }

    /**
     * @param picInfo
     * @return
     * @see "图片素材上传信息存储"
     */
    public boolean uploadPictureMaterialInfo(PictureUploadInfo picInfo) {
        String newPictureName = picInfo.getFileName();
        List<String> pictureNameList = oracleDataSet.selectColumnList(PictureResourceInfoBean.class, "FileName", "", "");
        if (pictureNameList.contains(newPictureName)) {
            return false;
        }
        PictureResourceInfoBean pri = convert.pictureInfoToBean(picInfo);
        return oracleDataSet.insert(pri);
    }

    /**
     * @param vidInfo
     * @return
     * @see "视频上传信息存储"
     */
    public boolean uploadVideoMaterialInfo(VideoUploadInfo vidInfo) {
        String newVideoName = vidInfo.getFileName();
        List<String> videoNameList = oracleDataSet.selectColumnList(VideoResourceInfoBean.class, "FileName", "", "");
        if (videoNameList.contains(newVideoName)) {
            return false;
        }
        VideoResourceInfoBean vri = convert.videoInfoToBean(vidInfo);
        return oracleDataSet.insert(vri);
    }

    /**
     * @param docInfo
     * @return
     * @see "文档上传信息存储"
     */
    public boolean uploadDocumentMaterialInfo(DocumentUploadInfo docInfo) {
        String newDocName = docInfo.getFileName();
        List<String> docNameList = oracleDataSet.selectColumnList(DocumentResourceInfoBean.class, "FileName", "", "");
        if (docNameList.contains(newDocName)) {
            return false;
        }
        DocumentResourceInfoBean dri = convert.documentInfoToBean(docInfo);
        return oracleDataSet.insert(dri);
    }

    /**
     * @return
     * @see "获取图片素材列表"
     */
    public List<PictureInfo> getAllPictureInfoList() {
        List<PictureResourceInfoBean> picBeanList = oracleDataSet.selectList(PictureResourceInfoBean.class);
        List<PictureInfo> pictureInfos = new ArrayList<>();
        for (int i = 0; i < picBeanList.size(); i++) {
            pictureInfos.add(convert.pictureBeanToInfo(picBeanList.get(i)));
        }
        return pictureInfos;
    }

    /**
     * @return
     * @see "获取视频素材列表"
     */
    public List<VideoInfo> getAllVideoInfoList() {
        List<VideoResourceInfoBean> vidBeanList = oracleDataSet.selectList(VideoResourceInfoBean.class);
        List<VideoInfo> videoInfos = new ArrayList<>();
        for (int i = 0; i < vidBeanList.size(); i++) {
            videoInfos.add(convert.videoBeanToInfo(vidBeanList.get(i)));
        }
        return videoInfos;
    }

    /**
     * @return
     * @see "获取文件素材列表"
     */
    public List<DocumentInfo> getAllDocumentInfoList() {
        List<DocumentResourceInfoBean> docBeanList = oracleDataSet.selectList(DocumentResourceInfoBean.class);
        List<DocumentInfo> docInfos = new ArrayList<>();
        for (int i = 0; i < docBeanList.size(); i++) {
            docInfos.add(convert.documentBeanToInfo(docBeanList.get(i)));
        }
        return docInfos;
    }

    /**
     * @param resourceType 素材类型 Picture:图片 Video:视频 Document:文件
     * @param fileName     文件名
     * @return
     * @see "删除素材"
     */
    public boolean deleteMaterial(String resourceType, String fileName) {
        //连接FTP并删除文件
        FtpUtil fcm = FtpHelper.getFtpUtil();
        boolean deleteResult = fcm.ftpDelete(fileName);
        //删除数据库中该文件的记录
        if (deleteResult) {
            oracleDataSet.delete(ResourceSendingStatusBean.class, "FileName=?", fileName);
            switch (resourceType) {
                case "Picture":
                    return oracleDataSet.delete(PictureResourceInfoBean.class, "FileName=?", fileName);
                case "Video":
                    return oracleDataSet.delete(VideoResourceInfoBean.class, "FileName=?", fileName);
                case "Document":
                    return oracleDataSet.delete(DocumentResourceInfoBean.class, "FileName=?", fileName);
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * @param mai
     * @return
     * @see "素材审批"
     */
    public boolean approveMaterial(MaterialApproveInfo mai) {
        switch (mai.getMaterialType()) {
            case "Picture":
                PictureResourceInfoBean priBean = oracleDataSet.select(PictureResourceInfoBean.class, "FileName=?", mai.getFileName());
                if (priBean.getSendingStatus().equals(ServiceConstant.MATERIAL_SENT)) {
                    return false;
                } else {
                    priBean.setApprovalStatus(ServiceConstant.MATERIAL_REVIEWED);
                    priBean.setApprovalResult(mai.getApprovalResult());
                    priBean.setApproveTime(DateTimeUtil.getCurrentTimeString());
                    priBean.setApprover(mai.getApprover());
                    priBean.setSendingStatus(ServiceConstant.MATERIAL_UNSENT);
                    return oracleDataSet.update(priBean, "FileName=?", priBean.getFileName());
                }
            case "Video":
                VideoResourceInfoBean vriBean = oracleDataSet.select(VideoResourceInfoBean.class, "FileName=?", mai.getFileName());
                if (vriBean.getSendingStatus().equals(ServiceConstant.MATERIAL_SENT)) {
                    return false;
                } else {
                    vriBean.setApprovalStatus(ServiceConstant.MATERIAL_REVIEWED);
                    vriBean.setApprovalResult(mai.getApprovalResult());
                    vriBean.setApproveTime(DateTimeUtil.getCurrentTimeString());
                    vriBean.setApprover(mai.getApprover());
                    vriBean.setSendingStatus(ServiceConstant.MATERIAL_UNSENT);
                    return oracleDataSet.update(vriBean, "FileName=?", vriBean.getFileName());
                }
            case "Document":
                DocumentResourceInfoBean briBean = oracleDataSet.select(DocumentResourceInfoBean.class, "FileName=?", mai.getFileName());
                if (briBean.getSendingStatus().equals(ServiceConstant.MATERIAL_SENT)) {
                    return false;
                } else {
                    briBean.setApprovalStatus(ServiceConstant.MATERIAL_REVIEWED);
                    briBean.setApprovalResult(mai.getApprovalResult());
                    briBean.setApproveTime(DateTimeUtil.getCurrentTimeString());
                    briBean.setApprover(mai.getApprover());
                    briBean.setSendingStatus(ServiceConstant.MATERIAL_UNSENT);
                    return oracleDataSet.update(briBean, "FileName=?", briBean.getFileName());
                }
            default:
                break;
        }
        return false;
    }

    /**
     * @param fileNameList 文件名称列表
     * @return SendFailed : 下发失败
     * OutOfSpace : 空间不足
     * Success : 下发成功
     * @see "下发素材"
     */
    public String sendMaterial(List<String> fileNameList) {
        //下发结果
        Map<String, Boolean> sendResultMap = new HashMap<>();
        //下发列表为空
        if (ListUtil.isEmpty(fileNameList)) {
            return "Unapproval material cannot be sent";
        }
        //判断控制器上是否有足够存储空间
        List<String> sentFileList = getSentMaterialNameList("All");
        sentFileList = ListUtil.copyList(sentFileList, fileNameList);
        if (!controllerSpaceJudgment(sentFileList)) {
            return "OutOfSpace";
        }
        lastSentFiles = getFileInfoListOnFtpServer(sentFileList);
        List<String> ipList = oracleDataSet.selectColumnList(ScreenCtrlServerConfigBean.class, "ip", "", "");
        for (String ip : ipList) {
            boolean result = material.materialSend(lastSentFiles, ip);
            sendResultMap.put(ip, result);
        }
        recordMaterialSendStatus(fileNameList);
        if (!sendResultMap.containsValue(false)) {
            return "SendSuccess.";
        } else {
            return "Some screen servers recieve new material failed.";
        }
    }

    /**
     * @param screenCtrlServerMaterialInfo
     * @see "记录素材在单个控制器上的下发状态"
     */
    public void RecodeMaterialSendingStatus(ScreenCtrlServerMaterialInfo screenCtrlServerMaterialInfo) {
        String serverIp = screenCtrlServerMaterialInfo.getIp();
        List<MaterialInfo> ctrlerMaterialList = screenCtrlServerMaterialInfo.getFileList();
        if (ListUtil.isEmpty(lastSentFiles)) {
            lastSentFiles = getSentFilesInfo();
        }
        //存储素材下发状态
        for (MaterialInfo file : lastSentFiles) {
            if (ctrlerMaterialList.contains(file)) {
                ResourceSendingStatusBean rss = oracleDataSet.select(ResourceSendingStatusBean.class, "FileName=? AND ServerIp=?", file.getName(), serverIp);
                if (null != rss) {
                    rss.setSendingStatus("1");
                    oracleDataSet.update(rss, "FileName=? AND ServerIp=?", file.getName(), serverIp);
                } else {
                    rss = new ResourceSendingStatusBean();
                    rss.setFileName(file.getName());
                    rss.setServerIp(serverIp);
                    rss.setSendingStatus("1");
                    oracleDataSet.insert(rss);
                }
            } else {
                ResourceSendingStatusBean rss = oracleDataSet.select(ResourceSendingStatusBean.class, "FileName=? AND ServerIp=?", file.getName(), serverIp);
                if (null != rss) {
                    rss.setSendingStatus("0");
                    oracleDataSet.update(rss, "FileName=? AND ServerIp=?", file.getName(), serverIp);
                } else {
                    rss = new ResourceSendingStatusBean();
                    rss.setFileName(file.getName());
                    rss.setServerIp(serverIp);
                    rss.setSendingStatus("0");
                    oracleDataSet.insert(rss);
                }
            }
        }
    }

    /**
     * @param materialName 素材名称
     * @return
     * @see "获取素材在所有控制器上的下发状态"
     */
    public HashMap<String, String> getMaterialSentStatus(String materialName) {
        List<String> screenServerIps = oracleDataSet.selectColumnList(ScreenCtrlServerConfigBean.class, "ip", "", "");
        //初始化所有控制器上该素材下发状态
        Map<String, String> sentStatus = new HashMap<>();
        for (String ip : screenServerIps) {
            sentStatus.put(ip, "0");
        }
        //修改控制器相应下发状态
        List<ResourceSendingStatusBean> rssList = oracleDataSet.selectListWithCondition(ResourceSendingStatusBean.class, "FileName=?", materialName);
        for (ResourceSendingStatusBean rss : rssList) {
            sentStatus.put(rss.getServerIp(), rss.getSendingStatus());
        }
        return (HashMap<String, String>) sentStatus;
    }

    /**
     * @return
     * @see "获取帧编辑可用图片列表"
     */
    public List<FrameAvailableResourceInfo> getPictureSentMaterialInfo() {
        List<PictureResourceInfoBean> picList = oracleDataSet.selectListWithCondition(PictureResourceInfoBean.class, "SendingStatus=?", ServiceConstant.MATERIAL_SENT);
        List<FrameAvailableResourceInfo> pics = new ArrayList<>();
        for (PictureResourceInfoBean p : picList) {
            FrameAvailableResourceInfo info = new FrameAvailableResourceInfo();
            info.setName(p.getFileName());
            info.setType(p.getPictureType());
            pics.add(info);
        }
        return pics;
    }

    /**
     * @return
     * @see "获取帧编辑可用视频列表"
     */
    public List<FrameAvailableResourceInfo> getVideoSentMaterialInfo() {
        List<VideoResourceInfoBean> vidList = oracleDataSet.selectListWithCondition(VideoResourceInfoBean.class, "SendingStatus=?", ServiceConstant.MATERIAL_SENT);
        List<FrameAvailableResourceInfo> videos = new ArrayList<>();
        for (VideoResourceInfoBean v : vidList) {
            FrameAvailableResourceInfo info = new FrameAvailableResourceInfo();
            info.setName(v.getFileName());
            info.setType(v.getVideoType());
            info.setDuration(v.getVideoDuration());
            videos.add(info);
        }
        return videos;
    }

    /**
     * @return
     * @see "获取帧编辑可用文件列表"
     */
    public List<FrameAvailableResourceInfo> getDocumentSentMaterialInfo() {
        List<DocumentResourceInfoBean> docList = oracleDataSet.selectListWithCondition(DocumentResourceInfoBean.class, "SendingStatus=?", ServiceConstant.MATERIAL_SENT);
        List<FrameAvailableResourceInfo> docs = new ArrayList<>();
        for (DocumentResourceInfoBean d : docList) {
            FrameAvailableResourceInfo info = new FrameAvailableResourceInfo();
            if (!".rtf".equals(d.getFileType())) {
                info.setName(d.getFileName());
                info.setType(d.getFileType());
                docs.add(info);
            }
        }
        return docs;
    }

    /**
     * @return
     * @see "获取帧编辑可用富文本文件列表（专题元素用）"
     */
    public List<FrameAvailableResourceInfo> getRichTextFileInfo() {
        List<DocumentResourceInfoBean> docList = oracleDataSet.selectListWithCondition(DocumentResourceInfoBean.class, "SendingStatus=?", ServiceConstant.MATERIAL_SENT);
        List<FrameAvailableResourceInfo> docs = new ArrayList<>();
        for (DocumentResourceInfoBean d : docList) {
            FrameAvailableResourceInfo info = new FrameAvailableResourceInfo();
            if (".rtf".equals(d.getFileType())) {
                info.setName(d.getFileName());
                info.setType(d.getFileType());
                docs.add(info);
            }
        }
        return docs;
    }

    /**
     * @return
     * @see "获取各控制器上素材列表"
     */
    public void getMaterialListOnController() {
        List<String> serverIps = oracleDataSet.selectColumnList(ScreenCtrlServerConfigBean.class, "ip", "", "");
        for (String ip : serverIps) {
            List<MaterialInfo> materialInfos = material.materialAllInfoGet(ip);
            if (null == materialInfos) {
                continue;
            }
            ScreenCtrlServerMaterialInfo serverMaterialInfo = new ScreenCtrlServerMaterialInfo();
            serverMaterialInfo.setIp(ip);
            serverMaterialInfo.setFileList(materialInfos);
            RecodeMaterialSendingStatus(serverMaterialInfo);
        }
    }

    //更新素材下发状态
    private void recordMaterialSendStatus(List<String> fileNameList) {
        for (String fileName : fileNameList) {
            PictureResourceInfoBean picBean = oracleDataSet.select(PictureResourceInfoBean.class, "FileName=?", fileName);
            if (picBean != null) {
                picBean.setSendingStatus("sent");
                oracleDataSet.update(picBean, "FileName=?", fileName);
                continue;
            }
            VideoResourceInfoBean vidBean = oracleDataSet.select(VideoResourceInfoBean.class, "FileName=?", fileName);
            if (vidBean != null) {
                vidBean.setSendingStatus("sent");
                oracleDataSet.update(vidBean, "FileName=?", fileName);
                continue;
            }
            DocumentResourceInfoBean docBean = oracleDataSet.select(DocumentResourceInfoBean.class, "FileName=?", fileName);
            if (docBean != null) {
                docBean.setSendingStatus("sent");
                oracleDataSet.update(docBean, "FileName=?", fileName);
                continue;
            }
        }
    }

    //判断综显服务器存储素材空间是否足够
    private boolean controllerSpaceJudgment(List<String> fileNameList) {
        long fileListSize = 0;
        List<MaterialInfo> fileList = getFileInfoListOnFtpServer(fileNameList);
        for (MaterialInfo file : fileList) {
            fileListSize += Long.parseLong(file.getSize());
        }
        String ctrlSpaceSize = StringUtil.deleteAlphabet(ConfigHelper.getString("travelService.controllerSpaceSize"));
        String ctrlSpaceunit = StringUtil.deleteNumber(ConfigHelper.getString("travelService.controllerSpaceSize"));
        long ctrlSpaceSizeBit = SizeConverterUtil.ConvertToBit(ctrlSpaceunit, ctrlSpaceSize);
        return fileListSize < ctrlSpaceSizeBit;
    }

    //获取FTP Server上指定文件的大小及最后修改时间
    private List<MaterialInfo> getFileInfoListOnFtpServer(List<String> nameList) {
        List<MaterialInfo> fileInfoList = new ArrayList<>();
        //获取FTP服务器上的文件列表
        FtpUtil fcm = FtpHelper.getFtpUtil();
        FTPFile[] ftpFiles = fcm.getFileInfoList();
        //获取指定文件的大小及最后修改时间
        Map<String, FTPFile> ftpFileMap = new HashMap<>();
        for (FTPFile file : ftpFiles) {
            String name = file.getName();
            ftpFileMap.put(name, file);
        }

        for (String fileName : nameList) {
            MaterialInfo materialInfo = new MaterialInfo();
            FTPFile ftpFile = ftpFileMap.get(fileName);
            if (ftpFile == null) {
                continue;
            }
            materialInfo.setName(fileName);
            materialInfo.setSize(Long.toString(ftpFile.getSize()));
            String lastedittime = DateTimeFormatterUtil.convertCalenderToString(ftpFile.getTimestamp(), "yyyy-MM-dd HH:mm:ss");
            materialInfo.setLastedittime(lastedittime);
            fileInfoList.add(materialInfo);
        }
        return fileInfoList;
    }

    //获取已下发素材列表
    private List<MaterialInfo> getSentFilesInfo() {
        List<String> picMaterialNames = oracleDataSet.selectColumnList(PictureResourceInfoBean.class, "FileName", "SendingStatus=?", "FileName", ServiceConstant.MATERIAL_SENT);
        List<String> vidMaterialNames = oracleDataSet.selectColumnList(VideoResourceInfoBean.class, "FileName", "SendingStatus=?", "FileName", ServiceConstant.MATERIAL_SENT);
        List<String> docMaterialNames = oracleDataSet.selectColumnList(DocumentResourceInfoBean.class, "FileName", "SendingStatus=?", "FileName", ServiceConstant.MATERIAL_SENT);

        List<String> allSentFileNames = new ArrayList<>();
        allSentFileNames = ListUtil.copyList(allSentFileNames, picMaterialNames);
        allSentFileNames = ListUtil.copyList(allSentFileNames, vidMaterialNames);
        allSentFileNames = ListUtil.copyList(allSentFileNames, docMaterialNames);

        return getFileInfoListOnFtpServer(allSentFileNames);
    }

    /**
     * @return
     * @author cuishiqing
     * @see "获取已下发素材名称列表"
     * @since 2016-06-15
     */
    private List<String> getSentMaterialNameList(String materialType) {
        List<String> picSentMaterialNameList = oracleDataSet.selectColumnList(PictureResourceInfoBean.class, "FileName", "SendingStatus=?", "FileName", "sent");
        List<String> vidSentMaterialNameList = oracleDataSet.selectColumnList(VideoResourceInfoBean.class, "FileName", "SendingStatus=?", "FileName", "sent");
        List<String> docSentMaterialNameList = oracleDataSet.selectColumnList(DocumentResourceInfoBean.class, "FileName", "SendingStatus=?", "FileName", "sent");

        List<String> sentMaterialNameList = new ArrayList<String>();
        sentMaterialNameList = ListUtil.copyList(sentMaterialNameList, picSentMaterialNameList);
        sentMaterialNameList = ListUtil.copyList(sentMaterialNameList, vidSentMaterialNameList);
        sentMaterialNameList = ListUtil.copyList(sentMaterialNameList, docSentMaterialNameList);

        switch (materialType) {
            case "Picture":
                return picSentMaterialNameList;
            case "Video":
                return vidSentMaterialNameList;
            case "Document":
                return docSentMaterialNameList;
            case "All":
                return sentMaterialNameList;
            default:
                return null;
        }
    }


}
