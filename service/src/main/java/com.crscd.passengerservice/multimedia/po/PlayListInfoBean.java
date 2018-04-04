package com.crscd.passengerservice.multimedia.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/11/23.
 */
public class PlayListInfoBean {
    /*播放列表编号*/
    @OrmIgnore
    private int id;
    /*播放列表名称*/
    private String listName;
    /*播放列表版本号*/
    private String version;
    /*播放列表类型*/
    private String listType;
    /*播放列表播放类型*/
    private String playType;
    /*播放列表内容1*/
    private String content1;
    /*播放列表内容2*/
    private String content2;

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

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }
}
