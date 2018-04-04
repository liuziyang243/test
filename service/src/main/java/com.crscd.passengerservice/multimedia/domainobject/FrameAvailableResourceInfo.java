package com.crscd.passengerservice.multimedia.domainobject;

/**
 * Created by cuishiqing on 2017/9/21.
 */
public class FrameAvailableResourceInfo {
    //素材名称
    private String name;
    //素材文件格式
    private String type;
    //播放时长（只用于视频）
    private int duration;

    public FrameAvailableResourceInfo() {
        duration = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
