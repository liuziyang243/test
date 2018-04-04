package com.crscd.passengerservice.display.format.serviceinterface;

import com.crscd.passengerservice.display.format.business.FormatManager;
import com.crscd.passengerservice.display.format.domainobject.FormatAndFrameListInfo;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by cuishiqing on 2018/1/9.
 */
public class FormatManagerInterfaceImpl implements FormatManagerInterface {
    private FormatManager formatManger;

    public void setFormatManger(FormatManager formatManger) {
        this.formatManger = formatManger;
    }

    @Override
    public HashMap<String, String> getFormatList(String stationName, String screenType, String screenWidth, String screenHeight, String screenColor) {
        return formatManger.getFormatList(stationName, screenType, screenWidth, screenHeight, screenColor);
    }

    @Override
    public String deleteFormat(String formatNo, int deleteFrame) {
        return formatManger.deleteFormat(formatNo, deleteFrame);
    }

    @Override
    public String saveFormatData(FormatInfo formatInfo, LinkedHashMap<String, String> frameList) {
        return formatManger.saveFormatData(formatInfo, frameList);
    }

    @Override
    public String updateFormatData(String formatId, LinkedHashMap<String, String> frameList) {
        return formatManger.updateFormatData(formatId, frameList);
    }

    @Override
    public boolean formatBinding(int screenId, int formatType, String formatNo) {
        return formatManger.formatBinding(screenId, formatType, formatNo);
    }

    @Override
    public FormatAndFrameListInfo getFormatAndFrameListInfo(String formatId) {
        return formatManger.getFormatAndFrameListInfo(formatId);
    }

    @Override
    public FormatAndFrameListInfo getBoundFormat(int screenId, int formatType) {
        return formatManger.getBoundFormat(screenId, formatType);
    }

    @Override
    public String importFormatData(String stationName, String formatData) {
        return formatManger.importFormatData(stationName, formatData);
    }

    @Override
    public String exportFormatData(String formatId) {
        return formatManger.exportFormatData(formatId);
    }

    @Override
    public String getFormatId(String stationName) {
        return formatManger.getFormatId(stationName);
    }

}
