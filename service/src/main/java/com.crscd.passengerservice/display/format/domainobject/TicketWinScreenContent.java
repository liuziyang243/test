package com.crscd.passengerservice.display.format.domainobject;

/**
 * Created by cuishiqing on 2017/9/7.
 */
public class TicketWinScreenContent {
    private int screenID;
    private String winNum;
    private String content;

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
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
}
