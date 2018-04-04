package com.crscd.passengerservice.display.format.domainobject;

import java.util.List;

/**
 * Created by cuishiqing on 2017/9/15.
 */
public class FormatAndFrameListInfo {
    private FormatInfo formatInfo;
    private List<FrameListInfo> frameList;

    public FormatInfo getFormatInfo() {
        return formatInfo;
    }

    public void setFormatInfo(FormatInfo formatInfo) {
        this.formatInfo = formatInfo;
    }

    public List<FrameListInfo> getFrameList() {
        return frameList;
    }

    public void setFrameList(List<FrameListInfo> frameList) {
        this.frameList = frameList;
    }
}
