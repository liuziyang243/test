package com.crscd.passengerservice.display.format.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/7/18.
 */
public class StreamMediaInfoBean {
    /*自增长ID*/
    @OrmIgnore
    private int id;
    /*流媒体名称*/
    private String sm_name;
    /*流媒体地址*/
    private String url;
    /*流媒体资源描述*/
    private String description;
    /*流媒体地址运行状态*/
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSm_name() {
        return sm_name;
    }

    public void setSm_name(String sm_name) {
        this.sm_name = sm_name;
    }
}
