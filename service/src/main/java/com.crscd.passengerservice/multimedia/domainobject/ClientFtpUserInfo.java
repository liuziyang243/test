package com.crscd.passengerservice.multimedia.domainobject;

/**
 * Created by cuishiqing on 2017/9/19.
 */
public class ClientFtpUserInfo {
    private String ftpAddress;
    private String port;
    private String userName;
    private String passWord;

    public String getFtpAddress() {
        return ftpAddress;
    }

    public void setFtpAddress(String ftpAddress) {
        this.ftpAddress = ftpAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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
