package com.crscd.passengerservice.multimedia.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/9/20.
 */
public class ResourceSendingStatusBean {
    @OrmIgnore
    /*自增长ID*/
    private int id;
    /*文件名*/
    private String fileName;
    /*综显服务器IP*/
    private String serverIp;
    /*下发状态*/
    private String sendingStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSendingStatus() {
        return sendingStatus;
    }

    public void setSendingStatus(String sendingStatus) {
        this.sendingStatus = sendingStatus;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}
