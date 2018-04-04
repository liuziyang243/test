package com.crscd.passengerservice.multimedia.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/12/7.
 */
public class PlayListSendingStatusBean {
    //自增长ID
    @OrmIgnore
    private int id;
    //播放列表ID
    private int playListId;
    //播放列表版本号
    private String version;
    //综显服务器IP
    private String serverIp;
    //下发状态
    private String sendingStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayListId() {
        return playListId;
    }

    public void setPlayListId(int playListId) {
        this.playListId = playListId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getSendingStatus() {
        return sendingStatus;
    }

    public void setSendingStatus(String sendingStatus) {
        this.sendingStatus = sendingStatus;
    }
}
