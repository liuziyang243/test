package com.crscd.passengerservice.display.format.serviceinterface;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cuishiqing on 2018/1/9.
 */
public interface FrameManagerInterface {
    @WebResult(name = "getFrameList")
    HashMap<String, String> getFrameList(@WebParam(name = "stationName") String stationName, @WebParam(name = "screenType") String screenType, @WebParam(name = "screenWidth") String screenWidth, @WebParam(name = "screenHeight") String screenHeight, @WebParam(name = "screenColor") String screenColor);

    @WebResult(name = "SaveFrameData")
    boolean SaveFrameData(@WebParam(name = "stationName") String stationName, @WebParam(name = "frameVarData") String frameVarData);

    @WebResult(name = "getDataSourceType")
    List<String> getDataSourceType();

    @WebResult(name = "getDataElement")
    List<String> getDataElement(@WebParam(name = "dataSourceType") String dataSourceType);

    @WebResult(name = "updateFrameData")
    boolean updateFrameData(@WebParam(name = "frameVarData") String frameVarData);

    @WebResult(name = "getFrameID")
    String getFrameID(@WebParam(name = "stationName") String stationName);

    @WebResult(name = "deleteFrame")
    boolean deleteFrame(@WebParam(name = "frameName") String frameName);

    @WebResult(name = "getFrameAndDTVar")
    String getFrameAndDTVar(@WebParam(name = "frameName") String frameName);
}
