package com.crscd.passengerservice.multimedia.domainobject;

import com.crscd.passengerservice.display.screencontrolserver.domainobject.MaterialInfo;

import java.util.List;

/**
 * Created by cuishiqing on 2017/9/21.
 */
public class ScreenCtrlServerMaterialInfo {
    private String ip;
    private List<MaterialInfo> fileList;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<MaterialInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<MaterialInfo> fileList) {
        this.fileList = fileList;
    }
}
