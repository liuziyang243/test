package com.crscd.passengerservice.broadcast.content.dao;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/9
 */
public interface ContentDAOInterface<T> {
    // 根据站名获取广播词列表
    List<T> getContentList(String stationName, Object kind);

    // 增加广播词
    boolean insertContent(T content);

    // 修改广播词
    boolean modifyContent(T content);

    // 删除广播词
    boolean delContent(long id);
}
