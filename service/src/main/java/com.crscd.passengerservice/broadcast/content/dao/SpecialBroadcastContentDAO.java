package com.crscd.passengerservice.broadcast.content.dao;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.broadcast.content.domainobject.SpecialBroadcastContent;
import com.crscd.passengerservice.broadcast.content.domainobject.SpecialBroadcastContentKind;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/8/16
 */
public class SpecialBroadcastContentDAO implements ContentDAOInterface<SpecialBroadcastContent> {

    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public List<SpecialBroadcastContent> getContentList(String stationName, Object kind) {
        String condition = "contentType=? AND stationName=?";
        return dataSet.selectListWithCondition(SpecialBroadcastContent.class, condition, kind, stationName);
    }

    @Override
    public boolean insertContent(SpecialBroadcastContent content) {
        return dataSet.insert(content);
    }

    @Override
    public boolean modifyContent(SpecialBroadcastContent content) {
        return dataSet.update(content);
    }

    @Override
    public boolean delContent(long id) {
        return dataSet.delete(SpecialBroadcastContent.class, FrameworkConstant.DEL_BY_PK, id);
    }

    public List<SpecialBroadcastContentKind> getSpecialContentKindList(String stationName) {
        String condition = "stationName=?";
        return dataSet.selectListWithCondition(SpecialBroadcastContentKind.class, condition, stationName);
    }

    public boolean insertSpecialContentKind(String stationName, String kind) {
        SpecialBroadcastContentKind contentKind = new SpecialBroadcastContentKind(kind, stationName);
        return dataSet.insert(contentKind);
    }

    public boolean delSpecialContentKind(String stationName, String kind) {
        String condition = "contentType=? AND stationName=?";
        dataSet.delete(SpecialBroadcastContent.class, condition, kind, stationName);
        return dataSet.delete(SpecialBroadcastContentKind.class, condition, kind, stationName);
    }

}
