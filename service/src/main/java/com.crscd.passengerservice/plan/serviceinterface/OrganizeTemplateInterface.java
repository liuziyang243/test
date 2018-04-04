package com.crscd.passengerservice.plan.serviceinterface;

import com.crscd.passengerservice.plan.dto.OrganizeTemplateDTO;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/27
 */
public interface OrganizeTemplateInterface {
    /**
     * 获取全部列车时刻表（配置基本图）
     * 根据列车车次和列车类型过滤列表，车次和列车类型允许为空，表示无此过滤条件
     * 默认采用模糊查询车次的方式
     *
     * @param stationName
     * @param trainNum
     * @param trainType
     * @return
     */
    @WebResult(name = "getOrganizeTemplateInterfaceResult")
    List<OrganizeTemplateDTO> getOrganizeTemplate(@WebParam(name = "stationName") String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = "trainType") TrainTypeEnum trainType);

    /**
     * 修改基本图
     *
     * @param dto
     * @return
     */
    @WebResult(name = "modifyOrganizeTemplateResult")
    ResultMessage modifyOrganizeTemplate(@WebParam(name = "dto") OrganizeTemplateDTO dto);

    /**
     * 检查筛选未配置模版的时刻表列表
     *
     * @param stationName
     * @return
     */
    @WebResult(name = "getUnconfigedOrganizeTemplateResult")
    List<OrganizeTemplateDTO> getUnconfiguredOrganizeTemplate(@WebParam(name = "stationName") String stationName);

}
