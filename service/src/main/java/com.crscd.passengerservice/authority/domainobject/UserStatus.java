package com.crscd.passengerservice.authority.domainobject;

import java.time.LocalDateTime;

/**
 * @author zs
 * @date 2017/8/29
 */
public class UserStatus {
    private String userName;
    private String ipAddr;
    private LocalDateTime dateTime;

    public UserStatus(String userName, String ipAddr) {
        this.userName = userName;
        this.ipAddr = ipAddr;
        this.dateTime = LocalDateTime.now();
    }

    public void updateTime() {
        this.dateTime = LocalDateTime.now();
    }

    public String getUserName() {
        return userName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
