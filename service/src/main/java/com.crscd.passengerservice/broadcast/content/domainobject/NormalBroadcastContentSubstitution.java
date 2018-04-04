package com.crscd.passengerservice.broadcast.content.domainobject;

import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;

/**
 * @author lzy
 * Date: 2017/9/8
 * Time: 16:45
 */
public class NormalBroadcastContentSubstitution {
    // 替换名称 @xxx的形式
    private String tag;
    // 注释
    private String explain;
    // 对象属性: 用于存储从广播计划中获得的可以替换的属性名称
    private NormalSubstitutePropertyEnum classProperty;

    public NormalBroadcastContentSubstitution() {
    }

    public NormalSubstitutePropertyEnum getClassProperty() {
        return classProperty;
    }

    public void setClassProperty(NormalSubstitutePropertyEnum classProperty) {
        this.classProperty = classProperty;
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
