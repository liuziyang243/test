package com.crscd.passengerservice.config.dto;

import com.crscd.passengerservice.config.enumtype.FirstRegionEnum;

/**
 * @author lzy
 * Date: 2017/9/5
 * Time: 9:42
 */
public class SecondaryRegionDTO {
    // 区域名称
    private String regionName;
    // 翻译后的区域名称
    private String translatedRegionName;
    // 隶属的一级区域
    private FirstRegionEnum firstRegion;

    public SecondaryRegionDTO() {
    }

    public FirstRegionEnum getFirstRegion() {
        return firstRegion;
    }

    public void setFirstRegion(FirstRegionEnum firstRegion) {
        this.firstRegion = firstRegion;
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
