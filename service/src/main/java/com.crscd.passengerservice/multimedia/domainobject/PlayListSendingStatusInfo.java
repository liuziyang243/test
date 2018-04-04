package com.crscd.passengerservice.multimedia.domainobject;

/**
 * Created by cuishiqing on 2017/12/7.
 */
public class PlayListSendingStatusInfo {
    //播放列表ID
    private int playListId;
    //播放列表版本号
    private String version;
    //综显控制器IP
    private String serverIp;
    //播放列表下发状态
    private String sendingStatus;

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
