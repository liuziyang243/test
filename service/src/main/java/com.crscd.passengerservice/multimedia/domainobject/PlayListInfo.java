package com.crscd.passengerservice.multimedia.domainobject;

import com.crscd.passengerservice.multimedia.dto.PlayListInfoDTO;
import com.crscd.passengerservice.multimedia.po.PlayListInfoBean;

import java.util.UUID;

/**
 * Created by cuishiqing on 2017/11/23.
 */
public class PlayListInfo {
    /*自增长ID*/
    private int id;
    /*播放列表名称*/
    private String listName;
    /*播放列表版本号*/
    private String version;
    /*播放列表类型*/
    private String listType;
    /*播放类型*/
    private String playType;
    /*列表内容*/
    private String content;

    public PlayListInfo() {
    }

    public PlayListInfo(PlayListInfoBean bean) {
        this.id = bean.getId();
        this.listName = bean.getListName();
        this.version = bean.getVersion();
        this.listType = bean.getListType();
        this.playType = bean.getPlayType();
        this.content = bean.getContent1() + bean.getContent2();
    }

    public PlayListInfo(PlayListInfoDTO dto) {
        this.id = dto.getId();
        this.listName = dto.getListName();
        this.version = UUID.randomUUID().toString();
        this.listType = dto.getListType();
        this.playType = dto.getPlayType();
        this.content = dto.getContent();
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
