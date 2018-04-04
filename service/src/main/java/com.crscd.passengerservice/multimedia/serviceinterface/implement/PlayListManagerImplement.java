package com.crscd.passengerservice.multimedia.serviceinterface.implement;

import com.crscd.framework.util.number.NumberUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.display.screencontrolserver.business.PlayList;
import com.crscd.passengerservice.multimedia.business.PlayListManager;
import com.crscd.passengerservice.multimedia.dao.MultimediaDAO;
import com.crscd.passengerservice.multimedia.domainobject.PlayListInfo;
import com.crscd.passengerservice.multimedia.domainobject.PlayListSendingStatusInfo;
import com.crscd.passengerservice.multimedia.dto.PlayListInfoDTO;
import com.crscd.passengerservice.multimedia.serviceinterface.PlayListManagerInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/12/26.
 */
public class PlayListManagerImplement implements PlayListManagerInterface {
    private ConfigManager configManager;
    private MultimediaDAO multimediaDAO;
    private PlayListManager playListManager;
    private PlayList playList;

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setMultimediaDAO(MultimediaDAO multimediaDAO) {
        this.multimediaDAO = multimediaDAO;
    }

    public void setPlayListManager(PlayListManager playListManager) {
        this.playListManager = playListManager;
    }

    public void setPlayList(PlayList playList) {
        this.playList = playList;
    }

    @Override
    public boolean saveNewPlayList(PlayListInfoDTO playListInfoDTO) {
        PlayListInfo playListInfo = new PlayListInfo(playListInfoDTO);
        return playListManager.saveNewPlayList(playListInfo);
    }

    @Override
    public boolean updatePlayList(PlayListInfoDTO playListInfoDTO) {
        PlayListInfo playListInfo = new PlayListInfo(playListInfoDTO);
        return playListManager.updatePlayList(playListInfo);
    }

    @Override
    public boolean deletePlayList(int playListId) {
        return playListManager.deletePlayList(playListId);
    }

    @Override
    public List<PlayListInfoDTO> getPlayListsByListType(String listType) {
        return playListManager.getPlayListInfoDTOByListType(listType);
    }

    @Override
    public PlayListInfoDTO getPlayListById(int id) {
        return playListManager.getPlayListInfoDTOById(id);
    }

    @Override
    public boolean savePlayListSendingStatus(PlayListSendingStatusInfo playListSendingStatusInfo) {
        return playListManager.savePlayListSendingStatus(playListSendingStatusInfo);
    }

    @Override
    public boolean playListSendSingleStation(String stationName, List<String> playListIds) {
        List<PlayListInfo> playListInfos = new ArrayList<>();
        for (String id : playListIds) {
            playListInfos.add(multimediaDAO.getPlayListInfoById(NumberUtil.toInt(id)));
        }
        return playList.playListSend(stationName, playListInfos);
    }

    @Override
    public boolean playListSendLine(List<String> playListIds) {
        List<String> stationNames = configManager.getAllStationName();
        List<PlayListInfo> playListInfos = new ArrayList<>();
        for (String id : playListIds) {
            playListInfos.add(multimediaDAO.getPlayListInfoById(NumberUtil.toInt(id)));
        }
        List<Boolean> resultList = new ArrayList<>();
        for (String stationName : stationNames) {
            resultList.add(playList.playListSend(stationName, playListInfos));
        }
        return resultList.contains(true);
    }
}
