package com.crscd.passengerservice.display.screencontrolserver.domainobject;

/**
 * Created by cuishiqing on 2017/12/15.
 */
public class FormatSendInterfaceInfo {
    private String formatId;
    private String format;
    private int screenId;
    private String data;

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
