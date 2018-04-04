package com.crscd.passengerservice.broadcast.content.domainobject;

import com.crscd.passengerservice.broadcast.template.enumtype.SpecialSubstitutePropertyEnum;

/**
 * Created by liuziyang
 * Create date: 2017/9/16
 */
public class SpecialBroadcastContentSubstitution {
    // 替换名称 @xxx的形式
    private String tag;
    // 注释
    private String explain;
    // 属性
    private SpecialSubstitutePropertyEnum property;

    public SpecialBroadcastContentSubstitution() {
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

    public SpecialSubstitutePropertyEnum getProperty() {
        return property;
    }

    public void setProperty(SpecialSubstitutePropertyEnum property) {
        this.property = property;
    }
}
