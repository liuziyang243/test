package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.plan.dto.BasicMapDTO;
import com.crscd.passengerservice.plan.dto.BasicMapDetailDTO;
import com.crscd.passengerservice.plan.dto.BasicMapMergeDTO;
import com.crscd.passengerservice.plan.dto.BasicTrainDetailDTO;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface BasicMapInterface {
    /**
     * 获取历史基本图列表基本信息
     */
    @WebResult(name = "getBasicMapListResult")
    List<BasicMapDTO> getBasicMapList(@WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 根据UUID获得某基本图的基本信息
     */
    @WebResult(name = "getBasicMapByUuidResult")
    BasicMapDTO getBasicMapByUuid(@WebParam(name = "uuid") String uuid);


    /**
     * 根据UUID分析某基本图的基本信息
     */
    @WebResult(name = "getBasicMapByUuidResult")
    BasicMapDTO analyseBasicMapByUuid(@WebParam(name = "uuid") String uuid);

    /**
     * 查看当前基本图的详细比较信息
     */
    @WebResult(name = "getCompareDetailResult")
    BasicMapDetailDTO getCompareDetail(@WebParam(name = "uuid") String uuid, @WebParam(name = "stationName") String stationName);


    /**
     * 合并基本图
     * uuid为合并的基本图对象索引号，trainNum为合并的车次号，result为合并的方式
     */
    @WebResult(name = "mergeBasicMapResult")
    BasicMapMergeDTO mergeBasicMap(@WebParam(name = "mergeTrainList") List<BasicTrainDetailDTO> mergeTrainList, @WebParam(name = "uuid") String uuid, @WebParam(name = "stationName") String stationName);

    /**
     * 获取基本图更新时间
     */
    @WebResult(name = "getLastBasicMapRecTimeResult")
    String getLastBasicMapRecTime();

}
