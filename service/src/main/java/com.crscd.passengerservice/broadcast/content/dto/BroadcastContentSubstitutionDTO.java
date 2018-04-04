package com.crscd.passengerservice.broadcast.content.dto;

/**
 * @author lzy
 * Date: 2017/9/8
 * Time: 16:48
 */
public class BroadcastContentSubstitutionDTO {
    // 替换名称 @xxx的形式
    private String tag;
    // 注释
    private String explain;

    public BroadcastContentSubstitutionDTO() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
