package com.crscd.passengerservice.cctv.dto;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 11:20
 */
public class CCTVSystemUserInfoDTO {
    // 视频服务器IP
    private String cmsIP;
    // 视频服务器端口号
    private int cmsPort;
    // 登陆用户名
    private String userName;
    // 登陆密码
    private String passWord;

    public CCTVSystemUserInfoDTO() {
    }

    public String getCmsIP() {
        return cmsIP;
    }

    public void setCmsIP(String cmsIP) {
        this.cmsIP = cmsIP;
    }

    public int getCmsPort() {
        return cmsPort;
    }

    public void setCmsPort(int cmsPort) {
        this.cmsPort = cmsPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
