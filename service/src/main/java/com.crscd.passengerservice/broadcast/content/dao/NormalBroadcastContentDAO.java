package com.crscd.passengerservice.broadcast.content.dao;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.broadcast.content.domainobject.NormalBroadcastContent;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/16
 */
public class NormalBroadcastContentDAO implements ContentDAOInterface<NormalBroadcastContent> {
    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    // 根据广播名称、广播业务类型和站名获取普通广播词,用于生成广播计划
    public NormalBroadcastContent getContentByNameForTemplate(String contentName, BroadcastKindEnum kind, String station) {
        String condition = "contentName=? AND broadcastKind=? AND stationName=?";
        return dataSet.select(NormalBroadcastContent.class, condition, contentName, kind.toString(), station);
    }

    @Override
    public List<NormalBroadcastContent> getContentList(String stationName, Object kind) {
        String condition = "broadcastKind=? AND stationName=?";
        return dataSet.selectListWithCondition(NormalBroadcastContent.class, condition, kind, stationName);
    }

    @Override
    public boolean insertContent(NormalBroadcastContent content) {
        return dataSet.insert(content);
    }

    @Override
    public boolean modifyContent(NormalBroadcastContent content) {
        return dataSet.update(content);
    }

    @Override
    public boolean delContent(long id) {
        return dataSet.delete(NormalBroadcastContent.class, FrameworkConstant.DEL_BY_PK, id);
    }
}
