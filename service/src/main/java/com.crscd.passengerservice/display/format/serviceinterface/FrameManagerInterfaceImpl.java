package com.crscd.passengerservice.display.format.serviceinterface;

import com.crscd.passengerservice.display.format.business.FrameManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cuishiqing on 2018/1/10.
 */
public class FrameManagerInterfaceImpl implements FrameManagerInterface {
    private FrameManager frameManager;

    public void setFrameManager(FrameManager frameManager) {
        this.frameManager = frameManager;
    }

    @Override
    public HashMap<String, String> getFrameList(String stationName, String screenType, String screenWidth, String screenHeight, String screenColor) {
        return frameManager.getFrameList(stationName, screenType, screenWidth, screenHeight, screenColor);
    }

    @Override
    public boolean SaveFrameData(String stationName, String frameVarData) {
        return frameManager.SaveFrameData(stationName, frameVarData);
    }

    @Override
    public List<String> getDataSourceType() {
        return frameManager.getDataSourceType();
    }

    @Override
    public List<String> getDataElement(String dataSourceType) {
        return frameManager.getDataElement(dataSourceType);
    }

    @Override
    public boolean updateFrameData(String frameVarData) {
        return frameManager.updateFrameData(frameVarData);
    }

    @Override
    public String getFrameID(String stationName) {
        return frameManager.getFrameID(stationName);
    }

    @Override
    public boolean deleteFrame(String frameName) {
        return frameManager.deleteFrame(frameName);
    }

    @Override
    public String getFrameAndDTVar(String frameName) {
        return frameManager.getFrameAndDTVar(frameName);
    }
}
