package com.crscd.passengerservice.broadcast.record.serviceinterface;

import com.crscd.passengerservice.broadcast.record.dto.BroadcastRecordDTO;

import javax.jws.WebParam;
import javax.jws.WebResult;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 14:39
 */
public interface BroadcastRecordServiceInterface {

    /**
     * 根据站名获取广播记录列表
     * 需要指定起始日期和结束日期
     *
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    @WebResult(name = "getBroadcastRecordResult")
    List<BroadcastRecordDTO> getBroadcastRecord(@WebParam(name = "stationName") String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);
}
