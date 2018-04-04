package com.crscd.passengerservice.display.format.serviceinterface;

import com.crscd.passengerservice.display.format.domainobject.FormatAndFrameListInfo;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by cuishiqing on 2018/1/9.
 */
public interface FormatManagerInterface {
    @WebResult(name = "getFormatList")
    HashMap<String, String> getFormatList(@WebParam(name = "stationName") String stationName, @WebParam(name = "screenType") String screenType, @WebParam(name = "screenWidth") String screenWidth, @WebParam(name = "screenHeight") String screenHeight, @WebParam(name = "screenColor") String screenColor);

    @WebResult(name = "deleteFormat")
    String deleteFormat(@WebParam(name = "formatNo") String formatNo, @WebParam(name = "deleteFrame") int deleteFrame);

    @WebResult(name = "saveFormatData")
    String saveFormatData(@WebParam(name = "formatInfo") FormatInfo formatInfo, @WebParam(name = "frameList") LinkedHashMap<String, String> frameList);

    @WebResult(name = "updateFormatData")
    String updateFormatData(@WebParam(name = "formatId") String formatId, @WebParam(name = "frameList") LinkedHashMap<String, String> frameList);

    @WebResult(name = "formatBinding")
    boolean formatBinding(@WebParam(name = "screenId") int screenId, @WebParam(name = "formatType") int formatType, @WebParam(name = "formatNo") String formatNo);

    @WebResult(name = "getFormatAndFrameListInfo")
    FormatAndFrameListInfo getFormatAndFrameListInfo(@WebParam(name = "formatId") String formatId);

    @WebResult(name = "getBoundFormat")
    FormatAndFrameListInfo getBoundFormat(@WebParam(name = "screenId") int screenId, @WebParam(name = "formatType") int formatType);

    @WebResult(name = "importFormatData")
    String importFormatData(@WebParam(name = "stationName") String stationName, @WebParam(name = "formatData") String formatData);

    @WebResult(name = "exportFormatData")
    String exportFormatData(@WebParam(name = "formatId") String formatId);

    @WebResult(name = "getFormatId")
    String getFormatId(@WebParam(name = "stationName") String stationName);
}
