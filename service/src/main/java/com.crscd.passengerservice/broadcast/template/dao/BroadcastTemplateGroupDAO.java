package com.crscd.passengerservice.broadcast.template.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.broadcast.template.dto.BroadcastTemplateGroupDTO;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.po.BroadcastTemplateBean;
import com.crscd.passengerservice.broadcast.template.po.BroadcastTemplateGroupBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/8/16
 */
public class BroadcastTemplateGroupDAO {

    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    // 通过站名和广播类型获取车站的广播模版列表
    // 当类型为空的时候表示获取全部车站的广播模版
    public List<BroadcastTemplateGroupBean> getBroadcastTemplateGroupBeanList(String stationName, BroadcastKindEnum kind) {
        List<BroadcastTemplateGroupBean> beanList;
        if (BroadcastKindEnum.ALL == kind) {
            String condition = "stationName=?";
            beanList = dataSet.selectListWithCondition(BroadcastTemplateGroupBean.class, condition, stationName);
        } else {
            String condition = "stationName=? AND broadcastKind=?";
            beanList = dataSet.selectListWithCondition(BroadcastTemplateGroupBean.class, condition, stationName, kind);
        }

        if (null == beanList) {
            return new ArrayList<>();
        }
        return beanList;
    }

    // 通过站名和模版类型获取车站的广播模版
    public BroadcastTemplateGroupBean getBroadcastTemplateGroupBean(String stationName, String groupName) {
        String condition = "stationName=? AND templateGroupName=?";
        return dataSet.select(BroadcastTemplateGroupBean.class, condition, stationName, groupName);
    }

    public boolean insertGroup(BroadcastTemplateGroupDTO dto) {
        BroadcastTemplateGroupBean bean = MapperUtil.map(dto, BroadcastTemplateGroupBean.class);
        return dataSet.insert(bean);
    }

    public boolean updateGroup(String station, String oldGroupName, String newGroupName) {
        String condition = "stationName=? AND templateGroupName=?";
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("templateGroupName", newGroupName);
        return dataSet.update(BroadcastTemplateGroupBean.class, fieldMap, condition, station, oldGroupName);
    }

    public boolean deleteGroup(long id) {
        String condition = "id=?";
        BroadcastTemplateGroupBean groupBean = dataSet.select(BroadcastTemplateGroupBean.class, condition, id);
        if (null != groupBean) {
            dataSet.delete(BroadcastTemplateGroupBean.class, condition, id);
            // 连带删除广播组中的模版
            String delTemplateCondition = "templateGroupName=? AND stationName=? AND broadcastKind=?";
            dataSet.delete(BroadcastTemplateBean.class, delTemplateCondition, groupBean.getTemplateGroupName(), groupBean.getStationName(), groupBean.getBroadcastKind().toString());
        }
        return true;
    }
}
