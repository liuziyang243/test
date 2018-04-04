package com.crscd.passengerservice.broadcast.content.serviceinterface;

import com.crscd.passengerservice.broadcast.content.dto.BroadcastContentSubstitutionDTO;
import com.crscd.passengerservice.broadcast.content.dto.NormalBroadcastContentDTO;
import com.crscd.passengerservice.broadcast.content.dto.SpecialBroadcastContentDTO;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/9
 *
 * @author Administrator
 */
public interface BroadcastContentInterface {
    /**************** 到发变更广播词操作函数 *******************/
    /**
     * 获取广播词列表，主要用于广播业务模版编辑
     *
     * @param stationName
     * @param kind
     * @return
     */
    @WebResult(name = "getNormalContentNameListResult")
    List<String> getNormalContentNameList(@WebParam(name = "stationName") String stationName, @WebParam(name = "kind") BroadcastKindEnum kind);

    /**
     * 提供到发广播词插入变量列表
     *
     * @return
     */
    @WebResult(name = "getNormalContentSubstitutionListResult")
    List<BroadcastContentSubstitutionDTO> getNormalContentSubstitutionList();

    /**
     * 获取到发/变更/其他广播词列表
     *
     * @param stationName
     * @param kind
     * @return
     */
    @WebResult(name = "getNormalContentResult")
    List<NormalBroadcastContentDTO> getNormalContent(@WebParam(name = "stationName") String stationName, @WebParam(name = "kind") BroadcastKindEnum kind);

    /**
     * 增加到发/变更/其他广播词
     *
     * @param dto
     * @return
     */
    @WebResult(name = "addNormalContentResult")
    ResultMessage addNormalContent(@WebParam(name = "dto") NormalBroadcastContentDTO dto);

    /**
     * 修改到发/变更/其他广播词
     *
     * @param dto
     * @return
     */
    @WebResult(name = "modifyNormalContentResult")
    ResultMessage modifyNormalContent(@WebParam(name = "dto") NormalBroadcastContentDTO dto);

    /**
     * 删除到发/变更/其他广播词
     *
     * @param id
     * @return
     */
    @WebResult(name = "delNormalContentResult")
    ResultMessage delNormalContent(@WebParam(name = "id") long id);

    /**************** 专题广播词操作函数 *******************/
    /**
     * 获取专题广播类型列表
     *
     * @param stationName
     * @return
     */
    @WebResult(name = "getSpecialContentKindListResult")
    List<String> getSpecialContentKindList(@WebParam(name = "stationName") String stationName);

    /**
     * 增加专题广播类型
     *
     * @param stationName
     * @param kind
     * @return
     */
    @WebResult(name = "addSpecialContentKindResult")
    ResultMessage addSpecialContentKind(@WebParam(name = "stationName") String stationName, @WebParam(name = "kind") String kind);

    /**
     * 删除专题广播类型
     *
     * @param stationName
     * @param kind
     * @return
     */
    @WebResult(name = "delSpecialContentKindResult")
    ResultMessage delSpecialContentKind(@WebParam(name = "stationName") String stationName, @WebParam(name = "kind") String kind);

    /**
     * 提供专题广播词插入变量列表
     *
     * @return
     */
    @WebResult(name = "getSpecialContentSubstitutionListResult")
    List<BroadcastContentSubstitutionDTO> getSpecialContentSubstitutionList();

    /**
     * 根据类别获取专题广播列表
     *
     * @param stationName
     * @param kind
     * @return
     */
    @WebResult(name = "getSpecialContentResult")
    List<SpecialBroadcastContentDTO> getSpecialContent(@WebParam(name = "stationName") String stationName, @WebParam(name = "kind") String kind);

    /**
     * 增加专题广播
     *
     * @param dto
     * @return
     */
    @WebResult(name = "addSpecialContentResult")
    ResultMessage addSpecialContent(@WebParam(name = "dto") SpecialBroadcastContentDTO dto);

    /**
     * 修改专题广播
     *
     * @param dto
     * @return
     */
    @WebResult(name = "modifySpecialContentResult")
    ResultMessage modifySpecialContent(@WebParam(name = "dto") SpecialBroadcastContentDTO dto);

    /**
     * 删除专题广播
     *
     * @param id
     * @return
     */
    @WebResult(name = "delSpecialContentResult")
    ResultMessage delSpecialContent(@WebParam(name = "id") long id);
}
