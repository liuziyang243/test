package com.crscd.passengerservice.broadcast.template.serviceinterface;

import com.crscd.passengerservice.broadcast.template.dto.BroadcastTemplateDTO;
import com.crscd.passengerservice.broadcast.template.dto.BroadcastTemplateGroupDTO;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/2
 *
 * @author Administrator
 */
public interface BroadcastTemplateGroupInterface {
    /**
     * 获取广播组列表
     *
     * @param stationName
     * @param kind
     * @return
     */
    @WebResult(name = "getBroadcastGroupListResult")
    List<BroadcastTemplateGroupDTO> getBroadcastGroupList(@WebParam(name = "stationName") String stationName, @WebParam(name = "kind") BroadcastKindEnum kind);

    /**
     * 获取广播组列表名称
     *
     * @param stationName
     * @return
     */
    @WebResult(name = "getBroadcastGroupNameListResult")
    List<String> getBroadcastGroupNameList(@WebParam(name = "stationName") String stationName);

    /**
     * 获取广播组下的模版列表
     *
     * @param stationName
     * @param groupName
     * @return
     */
    @WebResult(name = "getBroadcastTemplateListResult")
    List<BroadcastTemplateDTO> getBroadcastTemplateList(@WebParam(name = "stationName") String stationName, @WebParam(name = "groupName") String groupName);

    /**
     * 增加广播组
     *
     * @param group
     * @return
     */
    @WebResult(name = "addBroadcastGroupResult")
    ResultMessage addBroadcastGroup(@WebParam(name = "group") BroadcastTemplateGroupDTO group);

    /**
     * 增加广播业务模版
     *
     * @param template
     * @return
     */
    @WebResult(name = "addBroadcastTemplateResult")
    ResultMessage addBroadcastTemplate(@WebParam(name = "template") BroadcastTemplateDTO template);

    /**
     * 修改广播组名称
     *
     * @param stationName
     * @param oldGroupName
     * @param newGroupName
     * @return
     */
    @WebResult(name = "modifyGroupNameResult")
    ResultMessage modifyGroupName(@WebParam(name = "stationName") String stationName, @WebParam(name = "oldGroupName") String oldGroupName, @WebParam(name = "newGroupName") String newGroupName);

    /**
     * 修改广播业务模版
     *
     * @param template
     * @return
     */
    @WebResult(name = "modifyBroadcastTemplateResult")
    ResultMessage modifyBroadcastTemplate(@WebParam(name = "template") BroadcastTemplateDTO template);

    /**
     * 删除广播组
     *
     * @param groupID
     * @return
     */
    @WebResult(name = "delBroadcastGroupResult")
    ResultMessage delBroadcastGroup(@WebParam(name = "groupID") long groupID);

    /**
     * 删除广播业务模版
     *
     * @param templateID
     * @return
     */
    @WebResult(name = "delBroadcastTemplateResult")
    ResultMessage delBroadcastTemplate(@WebParam(name = "templateID") long templateID);
}
