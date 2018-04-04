package com.crscd.framework.utiltest;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/7/19
 * Time: 15:21
 */
public class Userlist {
    private String date;
    private String ID;
    private List<User> users;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
