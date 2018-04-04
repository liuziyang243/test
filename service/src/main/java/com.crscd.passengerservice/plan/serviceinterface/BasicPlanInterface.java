package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.plan.dto.BasicTrainStationDTO;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 15:46
 */

/*******************************************************
 * 列车时刻表相关接口
 ******************************************************/
public interface BasicPlanInterface {
    /**
     * 获取车次信息列表
     * 列车车次号和列车类型允许为空，当为null的时候，表示无此过滤条件
     * 车站名称是为了显示当前车站到发时间，如
     * 列车车次统一采用模糊搜索的方式返回结果
     *
     * @param stationName
     * @param trainNum
     * @param trainType
     * @return
     */
    @WebResult(name = "getBasicPlanMainInfoListResult")
    List<BasicPlanDTO> getBasicPlanMainInfoList(@WebParam(name = "stationName") String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = "trainType") TrainTypeEnum trainType);

    /**
     * 选择车次信息后获取车站列表信息
     *
     * @param trainNum
     * @return
     */
    @WebResult(name = "getBasicStationInfoListResult")
    List<BasicTrainStationDTO> getBasicStationInfoList(@WebParam(name = "trainNum") String trainNum);

    /**
     * 添加车次信息
     * @deprecated 由于已经在updateBasicPlanMainInfo()接口中实现了对是否存在车次信息进行了判断
     * 当不存在的时候直接将dto插入数据库，从而完成添加车次信息的功能，本函数废弃
     *
     * @param dto
     * @return
     */
    @Deprecated
    @WebResult(name = "addBasicPlanMainInfoResult")
    ResultMessage addBasicPlanMainInfo(@WebParam(name = "dto") BasicPlanDTO dto);

    /**
     * 编辑车次信息
     *
     * @param dto
     * @return
     */
    @WebResult(name = "updateBasicPlanMainInfoResult")
    ResultMessage updateBasicPlanMainInfo(@WebParam(name = "dto") BasicPlanDTO dto);

    /**
     * 删除车次信息
     *
     * @param trainNum
     * @return
     */
    @WebResult(name = "updateBasicPlanMainInfoResult")
    ResultMessage removeBasicPlanMainInfo(@WebParam(name = "trainNum") String trainNum);

    /**
     * 添加车站信息
     * @deprecated 由于已经在updateBasicPlanStationInfo()接口中实现了对是否存在车站信息进行了判断
     * 当不存在的时候直接将dto插入数据库，从而完成添加车站信息的功能，本函数废弃
     *
     * @param trainNum
     * @param dto
     * @return
     */
    @Deprecated
    @WebResult(name = "addBasicStationUnitInfoResult")
    ResultMessage addBasicPlanStationInfo(@WebParam(name = "trainNum") String trainNum, @WebParam(name = "dto") BasicTrainStationDTO dto);

    /**
     * 编辑车站信息
     *
     * @param trainNum
     * @param dto
     * @return
     */
    @WebResult(name = "updateBasicStationUnitInfoResult")
    ResultMessage updateBasicPlanStationInfo(@WebParam(name = "trainNum") String trainNum, @WebParam(name = "dto") BasicTrainStationDTO dto);

    /**
     * 删除车站信息
     *
     * @param trainNum
     * @param stationName
     * @return
     */
    @WebResult(name = "removeBasicStationUnitInfoResult")
    ResultMessage removeBasicPlanStationInfo(@WebParam(name = "trainNum") String trainNum, @WebParam(name = "stationName") String stationName);

    /**
     * 获取车站里程计算
     *
     * @param startStation
     * @param presentStation
     * @return
     */
    @WebResult(name = "calculateMileageResult")
    float calculateMileage(@WebParam(name = "startStation") String startStation, @WebParam(name = "presentStation") String presentStation);

}
