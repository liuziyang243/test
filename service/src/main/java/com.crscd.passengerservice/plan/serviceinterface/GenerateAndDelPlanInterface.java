package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.result.GenAndDelPlanResultMessage;
import com.crscd.passengerservice.result.PagedGenAndDelPlanResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/28
 * Time: 11:13
 */
public interface GenerateAndDelPlanInterface {
    /**
     * 查询可以生成计划的时刻表
     * 即已经配置了客运组织业务模版
     * 允许车次号模糊查询
     *
     * @param trainNum
     * @param stationName
     * @return
     */
    @WebResult(name = "getValidBasicPlanResult")
    List<BasicPlanDTO> getValidBasicPlan(@WebParam(name = "trainNum") String trainNum, @WebParam(name = "stationName") String stationName);

    /**
     * 批量生成计划
     * @deprecated 当返回的对象很大时，前台会报错，因此改为分页的形式，该接口会在后续的改进中删除
     * 新的接口为：generatePlanListPaged
     *
     * @param trainNumList
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    @Deprecated
    @WebResult(name = "generatePlanListResult")
    GenAndDelPlanResultMessage generatePlanList(@WebParam(name = "trainNumList") List<String> trainNumList, @WebParam(name = "stationName") String stationName, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 批量删除计划
     * @deprecated 当返回的对象很大时，前台会报错，因此改为分页的形式，该接口会在后续的改进中删除
     * 新的接口为：delPlanListPaged
     *
     * @param trainNumList
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    @Deprecated
    @WebResult(name = "delPlanListResult")
    GenAndDelPlanResultMessage delPlanList(@WebParam(name = "trainNumList") List<String> trainNumList, @WebParam(name = "stationName") String stationName, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);

    /**
     * 以分页方式返回生成计划的结果
     *
     * @param trainNumList
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    PagedGenAndDelPlanResultMessage generatePlanListPaged(List<String> trainNumList, String stationName, String startDate, String endDate);


    /**
     * 以分页的方式返回删除计划的结果
     *
     * @param trainNumList
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    PagedGenAndDelPlanResultMessage delPlanListPaged(List<String> trainNumList, String stationName, String startDate, String endDate);


    /**
     * 查询分页结果
     *
     * @param uid
     * @param pageNum
     * @return
     */
    PagedGenAndDelPlanResultMessage getErrorDetailsByPageNum(Long uid, int pageNum);

    /**
     * 前台退出页面时调用，后台清除分页缓存
     *
     * @param uid
     * @return
     */
    ResultMessage dropErrorDetailCache(Long uid);
}
