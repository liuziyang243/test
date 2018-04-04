package com.crscd.passengerservice.cctv.domainobject;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 11:11
 */
public class CCTVSystemUserInfo {
    // 视频服务器IP
    private String cmsIP;
    // 视频服务器端口号
    private int cmsPort;
    // 登陆用户名
    private String userName;
    // 登陆密码
    private String passWord;

    public CCTVSystemUserInfo(String cmsIP, int cmsPort, String userName, String passWord) {
        this.cmsIP = cmsIP;
        this.cmsPort = cmsPort;
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getCmsIP() {
        return cmsIP;
    }

    public int getCmsPort() {
        return cmsPort;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}
