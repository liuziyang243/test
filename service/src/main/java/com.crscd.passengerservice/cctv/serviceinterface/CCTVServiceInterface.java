package com.crscd.passengerservice.cctv.serviceinterface;

import com.crscd.passengerservice.cctv.dto.CCTVSystemUserInfoDTO;
import com.crscd.passengerservice.cctv.dto.RoundRollingGroupDTO;
import com.crscd.passengerservice.result.base.ResultMessage;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 11:21
 */
public interface CCTVServiceInterface {

    // 获取视频监控系统的登陆用户信息
    @WebResult(name = "getCCTVSystemUserInfoResult")
    CCTVSystemUserInfoDTO getCCTVSystemUserInfo();

    // 获取广播组列表
    @WebResult(name = "getCCTVSystemUserInfoResult")
    List<RoundRollingGroupDTO> getRoundRollingGroupList(@WebParam(name = "stationName") String stationName);

    // 查询广播组名称是否存在
    @WebResult(name = "getCCTVSystemUserInfoResult")
    ResultMessage checkGroupNameExit(@WebParam(name = "stationName") String stationName, @WebParam(name = "groupName") String groupName);

    // 增加广播组
    @WebResult(name = "getCCTVSystemUserInfoResult")
    ResultMessage addRoundRollingGroup(@WebParam(name = "dto") RoundRollingGroupDTO dto);

    // 修改广播组
    @WebResult(name = "getCCTVSystemUserInfoResult")
    ResultMessage modifyRoundRollingGroup(@WebParam(name = "dto") RoundRollingGroupDTO dto);

    // 删除广播组
    @WebResult(name = "getCCTVSystemUserInfoResult")
    ResultMessage delRoundRollingGroup(@WebParam(name = "id") long id);
}
