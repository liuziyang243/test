package com.crscd.passengerservice.broadcast.content.domainobject;

import com.crscd.framework.orm.annotation.EqualBean;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 17:21
 * 专题广播内容
 */
@EqualBean
public class SpecialBroadcastContent {
    private long id;
    // 专题广播名称
    private String contentName;
    // 专题广播内容
    private String broadcastContent;
    // 专题广播类型
    private String contentType;
    // 专题广播使用的车站
    private String stationName;

    public SpecialBroadcastContent() {
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

    public String getBroadcastContent() {
        return broadcastContent;
    }

    public void setBroadcastContent(String broadcastContent) {
        this.broadcastContent = broadcastContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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
                .append(broadcastContent)
                .append(contentType)
                .append(stationName).toHashCode();
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
            SpecialBroadcastContent bean = (SpecialBroadcastContent) obj;
            result = new EqualsBuilder()
                    .append(contentName, bean.contentName)
                    .append(broadcastContent, bean.broadcastContent)
                    .append(contentType, bean.contentType)
                    .append(stationName, bean.stationName)
                    .isEquals();
        }
        return result;
    }
}
