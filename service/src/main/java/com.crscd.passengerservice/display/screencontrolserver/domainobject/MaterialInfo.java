package com.crscd.passengerservice.display.screencontrolserver.domainobject;

/**
 * Created by cuishiqing on 2017/12/4.
 */
public class MaterialInfo {
    //素材名称
    private String name;
    //素材大小
    private String size;
    //素材最后编辑时间
    private String lastedittime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLastedittime() {
        return lastedittime;
    }

    public void setLastedittime(String lastedittime) {
        this.lastedittime = lastedittime;
    }
}
