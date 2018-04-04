package com.crscd.passengerservice.broadcast.content.domainobject;

import com.crscd.framework.orm.annotation.EqualBean;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:52
 * 普通广播内容，可用于到发、变更及其他
 */
@EqualBean
public class NormalBroadcastContent {
    // id
    private long id;
    // 广播内容名称 -> 作业内容(广播业务模版)
    private String contentName;
    // 广播属性：到发、变更或者其他， toArrive, alteration, others
    private BroadcastKindEnum broadcastKind;
    // 站名
    private String stationName;
    // 本地语言广播详细内容
    private String contentInLocalLan;
    // 英文广播详细内容
    private String contentInEng;

    public NormalBroadcastContent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentInLocalLan() {
        return contentInLocalLan;
    }

    public void setContentInLocalLan(String contentInLocalLan) {
        this.contentInLocalLan = contentInLocalLan;
    }

    public String getContentInEng() {
        return contentInEng;
    }

    public void setContentInEng(String contentInEng) {
        this.contentInEng = contentInEng;
    }

    public BroadcastKindEnum getBroadcastKind() {
        return broadcastKind;
    }

    public void setBroadcastKind(BroadcastKindEnum broadcastKind) {
        this.broadcastKind = broadcastKind;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(id)
                .append(contentName)
                .append(broadcastKind)
                .append(stationName)
                .append(contentInLocalLan)
                .append(contentInEng)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        boolean result = false;
        if (this.getClass() == obj.getClass()) {
            NormalBroadcastContent bean = (NormalBroadcastContent) obj;
            result = new EqualsBuilder()
                    .append(contentName, bean.contentName)
                    .append(broadcastKind, bean.broadcastKind)
                    .append(contentInEng, bean.contentInEng)
                    .append(contentInLocalLan, bean.contentInLocalLan)
                    .append(stationName, bean.stationName)
                    .isEquals();
        }
        return result;
    }
}
