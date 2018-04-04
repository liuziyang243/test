package com.crscd.passengerservice.broadcast.drivers.enumtype;

/**
 * @author lzy
 * Date: 2017/8/28
 * Time: 8:26
 */
public enum BroadcastStateEnum {
    // 后台执行幅值
    WAIT_EXECUTE,  // 待执行
    MANUAL_STOP, // 手动停止播放
    CONTENT_EMPTY_QUIT,  // 广播内容为空
    BROADCAST_AREA_EMPTY_QUIT, //广播区列表空

    // 驱动回写幅值
    IN_QUEUE,  // 队列中，调用驱动后接口机首先回写任务进入队列
    BROADCASTING,  // 播放中
    TIME_OUT_QUIT,  // 超时
    ERROR_QUIT,  // 错误退出
    COMPLETED  // 播放完毕
}
