package com.crscd.passengerservice.config.dto;

/**
 * @author lzy
 * Date: 2017/9/5
 * Time: 9:48
 */
public class BroadcastAreaDTO {
    // 区域名称
    private String regionName;
    // 翻译后的区域名称
    private String translatedRegionName;
    // 广播区分组名称
    private String groupName;
    // 翻译后的分组名称
    private String translatedGroupName;

    public BroadcastAreaDTO() {
    }

    public String getTranslatedGroupName() {
        return translatedGroupName;
    }

    public void setTranslatedGroupName(String translatedGroupName) {
        this.translatedGroupName = translatedGroupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getTranslatedRegionName() {
        return translatedRegionName;
    }

    public void setTranslatedRegionName(String translatedRegionName) {
        this.translatedRegionName = translatedRegionName;
    }
}
