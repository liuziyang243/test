package com.crscd.passengerservice.ctc.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.ctc.po.BasicMapBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/11.
 */
public class CtcMessageDAO {

    private DataSet oracleDataSet;

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    //保存基本图
    public boolean saveBasicMap(UUID uuid, String basicMap, String receiveTime) {
        BasicMapBean oldBasicMapBean = oracleDataSet.select(BasicMapBean.class, "uuid= ?", uuid.toString());
        BasicMapBean basicMapBean = new BasicMapBean(uuid, basicMap, receiveTime);
        if (null == oldBasicMapBean) {
            return oracleDataSet.insert(basicMapBean);
        } else {
            return oracleDataSet.update(basicMapBean, "uuid");
        }
    }

    //根据起始和结束时间获取BasicMapBean
    public List<BasicMapBean> getBasicMapList(String startTime, String endTime) {
        StringBuilder condition = new StringBuilder();
        condition.append("1=1");
        if (null != startTime) {
            condition.append(" and receiveTime>= '");
            condition.append(startTime);
            condition.append("'");
        }
        if (null != endTime) {
            condition.append(" and receiveTime<= '");
            condition.append(endTime);
            condition.append("'");
        }
        return oracleDataSet.selectListWithCondition(BasicMapBean.class, condition.toString());
    }

    //更新审核状态
    public boolean updateConfirmState(String uuid) {
        String condition = "uuid = ?";
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("confirmState", 1);
        return oracleDataSet.update(BasicMapBean.class, fieldMap, condition, uuid);
    }

    //根据uuid获取BasicMapBean
    public BasicMapBean getSingleBasicMap(String uuid) {
        return oracleDataSet.select(BasicMapBean.class, "uuid = ?", uuid);
    }


    //获取接收时间
    public List<String> getBasicMapRecTime() {
        String sql = "select receiveTime from basicmap order by receiveTime desc";
        return oracleDataSet.select(sql);
    }

    //获取最新的uuid
    public String getLatestBasicMapUuid() {
        String sql = "select uuid from basicmap order by receiveTime desc";
        List<String> uuids = oracleDataSet.select(sql);
        if (uuids.size() != 0) {
            return uuids.get(0);
        } else {
            return null;
        }
    }


}
