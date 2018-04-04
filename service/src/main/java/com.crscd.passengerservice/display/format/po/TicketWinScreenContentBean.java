package com.crscd.passengerservice.display.format.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/9/7.
 */
public class TicketWinScreenContentBean {
    //自增长ID
    @OrmIgnore
    private int id;
    //屏幕ID
    private int screenID;
    //屏幕归属站名
    private String stationName;
    //售票窗口编号
    private String winNum;
    //窗口显示内容
    private String content;
    //屏幕归属分组
    private String groupName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getWinNum() {
        return winNum;
    }

    public void setWinNum(String winNum) {
        this.winNum = winNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
