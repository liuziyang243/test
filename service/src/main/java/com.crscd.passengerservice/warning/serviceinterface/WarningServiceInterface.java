package com.crscd.passengerservice.warning.serviceinterface;

import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.warning.dto.DeviceWarningDTO;
import com.crscd.passengerservice.warning.enumtype.SystemEnum;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/21
 * Time: 10:41
 */
public interface WarningServiceInterface {
    // 根据车站获取设备告警信息列表
    // station, area, startTime, endTime可以为空
    // system.all表示全部系统
    // station, area, system都是combobox
    @WebResult(name = "getDeviceWarningListByStationResult")
    List<DeviceWarningDTO> getDeviceWarningListByStation(@WebParam(name = "stationName") String station, @WebParam(name = "system") SystemEnum system, @WebParam(name = "area") String area, @WebParam(name = "startTime") String startTime, @WebParam(name = "endTime") String endTime);

    // 确认告警信息
    // 对于已经确认过的告警信息，前台应提示用户无法再次确认并不再调用接口
    @WebResult(name = "confirmWarningMessageResult")
    ResultMessage confirmWarningMessage(@WebParam(name = "id") long id, @WebParam(name = "user") String user);
}
