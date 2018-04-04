package com.crscd.passengerservice.multimedia.serviceinterface;

import com.crscd.passengerservice.multimedia.domainobject.PlayListSendingStatusInfo;
import com.crscd.passengerservice.multimedia.dto.PlayListInfoDTO;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * Created by cuishiqing on 2017/11/23.
 */
public interface PlayListManagerInterface {

    //存储新播放列表
    @WebResult(name = "saveNewPlayList")
    boolean saveNewPlayList(@WebParam(name = "playListInfo") PlayListInfoDTO playListInfo);

    //更新播放列表
    @WebResult(name = "updatePlayList")
    boolean updatePlayList(@WebParam(name = "playListInfo") PlayListInfoDTO playListInfo);

    //删除播放列表
    @WebResult(name = "deletePlayList")
    boolean deletePlayList(@WebParam(name = "playListId") int playListId);

    //获取播放列表List -- 按播放列表类型
    @WebResult(name = "getPlayListsByListType")
    List<PlayListInfoDTO> getPlayListsByListType(@WebParam(name = "listType") String listType);

    //获取播放列表 -- 按播放列表ID
    @WebResult(name = "getPlayListById")
    PlayListInfoDTO getPlayListById(@WebParam(name = "id") int id);

    //存储播放列表下发状态信息
    boolean savePlayListSendingStatus(PlayListSendingStatusInfo playListSendingStatusInfo);

    //单站下发播放列表
    @WebResult(name = "playListSendSingleStation")
    boolean playListSendSingleStation(@WebParam(name = "stationName") String stationName, @WebParam(name = "playListIds") List<String> playListIds);

    //全线下发播放列表
    @WebResult(name = "playListSendLine")
    boolean playListSendLine(@WebParam(name = "playListIds") List<String> playListIds);
}
