package com.crscd.passengerservice.broadcast.template.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.broadcast.template.dto.BroadcastTemplateDTO;
import com.crscd.passengerservice.broadcast.template.po.BroadcastTemplateBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/8/16
 */
public class BroadcastTemplateDao {

    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    // 根据站码和广播组名称获取广播模版列表
    public List<BroadcastTemplateBean> getBroadcastTemplateBean(String stationName, String groupName) {
        String condition = "stationName=? AND templateGroupName=?";
        List<BroadcastTemplateBean> beanList = dataSet.selectListWithCondition(BroadcastTemplateBean.class, condition, stationName, groupName);
        if (null == beanList) {
            return new ArrayList<>();
        }
        return beanList;
    }

    public boolean insertTemplate(BroadcastTemplateDTO dto) {
        if (null == dto.getFirstRegion()) {
            dto.setFirstRegion(new ArrayList<>());
        }
        if (null == dto.getBroadcastArea()) {
            dto.setBroadcastArea(new ArrayList<>());
        }
        BroadcastTemplateBean bean = MapperUtil.map(dto, BroadcastTemplateBean.class);
        return dataSet.insert(bean);
    }

    public boolean updateTemplate(BroadcastTemplateDTO dto) {
        BroadcastTemplateBean bean = MapperUtil.map(dto, BroadcastTemplateBean.class);
        return dataSet.update(bean);
    }

    public boolean updateTemplateGroupName(String station, String oldGroupName, String newGroupName) {
        String condition = "stationName=? AND templateGroupName=?";
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("templateGroupName", newGroupName);
        return dataSet.update(BroadcastTemplateBean.class, fieldMap, condition, station, oldGroupName);
    }

    public boolean deleteTemplate(long id) {
        String condition = "id=?";
        return dataSet.delete(BroadcastTemplateBean.class, condition, id);
    }
}
