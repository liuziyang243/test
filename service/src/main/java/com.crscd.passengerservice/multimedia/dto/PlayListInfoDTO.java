package com.crscd.passengerservice.multimedia.dto;

import com.crscd.passengerservice.multimedia.domainobject.PlayListInfo;

/**
 * Created by cuishiqing on 2017/11/23.
 */
public class PlayListInfoDTO {
    /*播放列表ID*/
    private int id;
    /*播放列表名称*/
    private String listName;
    /*播放列表类型*/
    private String listType;
    /*播放列表播放类型*/
    private String playType;
    /*播放列表内容1*/
    private String content;

    public PlayListInfoDTO() {
    }

    public PlayListInfoDTO(PlayListInfo playListInfo) {
        this.id = playListInfo.getId();
        this.listName = playListInfo.getListName();
        this.listType = playListInfo.getListType();
        this.playType = playListInfo.getPlayType();
        this.content = playListInfo.getContent();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
