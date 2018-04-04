package com.crscd.passengerservice.display.screencontrolserver.domainobject;

/**
 * Created by cuishiqing on 2017/12/7.
 */
public class PlayListResponseInfo {
    private String id;
    private String version;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
