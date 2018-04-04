package com.crscd.passengerservice.multimedia.business;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.multimedia.dao.MultimediaDAO;
import com.crscd.passengerservice.multimedia.domainobject.PlayListInfo;
import com.crscd.passengerservice.multimedia.domainobject.PlayListSendingStatusInfo;
import com.crscd.passengerservice.multimedia.dto.PlayListInfoDTO;
import com.crscd.passengerservice.multimedia.po.PlayListSendingStatusBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/11/23.
 */
public class PlayListManager {

    private MultimediaDAO multimediaDAO;

    private DataSet oracleDataSet;

    public PlayListManager() {
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setMultimediaDAO(MultimediaDAO multimediaDAO) {
        this.multimediaDAO = multimediaDAO;
    }

    /**
     * 新建播放列表
     *
     * @param playListInfo
     * @return
     */
    public boolean saveNewPlayList(PlayListInfo playListInfo) {
        return multimediaDAO.insertPlayListInfoBean(playListInfo);
    }

    /**
     * 更新播放列表
     *
     * @param playListInfo
     * @return
     */
    public boolean updatePlayList(PlayListInfo playListInfo) {
        return multimediaDAO.updatePlanListInfoBean(playListInfo);
    }

    /**
     * 删除播放列表
     *
     * @param playListId
     * @return
     */
    public boolean deletePlayList(int playListId) {
        return multimediaDAO.deletePlayListInfoBean(playListId);
    }

    /**
     * 按播放列表类型获取播放列表
     *
     * @param listType
     * @return
     */
    public List<PlayListInfoDTO> getPlayListInfoDTOByListType(String listType) {
        List<PlayListInfo> playListInfos = multimediaDAO.getPlayListInfosByListType(listType);
        List<PlayListInfoDTO> playListInfoDTOs = new ArrayList<>();
        for (PlayListInfo info : playListInfos) {
            PlayListInfoDTO playListInfoDTO = new PlayListInfoDTO(info);
            playListInfoDTOs.add(playListInfoDTO);
        }
        return playListInfoDTOs;
    }

    /**
     * 按播放列表ID获取播放列表
     *
     * @param id
     * @return
     */
    public PlayListInfoDTO getPlayListInfoDTOById(int id) {
        PlayListInfo playListInfo = multimediaDAO.getPlayListInfoById(id);
        PlayListInfoDTO playListInfoDTO = new PlayListInfoDTO(playListInfo);
        return playListInfoDTO;
    }

    /**
     * 保存播放列表下发状态
     *
     * @param playListSendingStatusInfo
     * @return
     */
    public boolean savePlayListSendingStatus(PlayListSendingStatusInfo playListSendingStatusInfo) {
        int playListId = playListSendingStatusInfo.getPlayListId();
        String serverIp = playListSendingStatusInfo.getServerIp();
        PlayListSendingStatusBean playListSendingStatusBean =
                oracleDataSet.select(PlayListSendingStatusBean.class, "playListId = ? AND serverIp = ?", playListId, serverIp);
        if (null != playListSendingStatusBean) {
            return multimediaDAO.updatePlayListSendingStatus(playListSendingStatusInfo);
        } else {
            return multimediaDAO.insertPlayListSendingStatus(playListSendingStatusInfo);
        }
    }

}
