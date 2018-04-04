package com.crscd.passengerservice.plan.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.plan.domainobject.OrganizeTemplate;

/**
 * @author lzy
 * Date: 2017/8/28
 * Time: 9:02
 */
public class OrganizeTemplateDAO {

    private final String condition = "trainNum=? AND stationName=?";
    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * 判断数据库里面是否有列车时刻表在车站对应的客运组织业务模版
     *
     * @param trainNum
     * @param station
     * @return
     */
    public boolean existOrganizeTemplateInDB(String trainNum, String station) {
        return dataSet.selectCount(OrganizeTemplate.class, condition, trainNum, station) > 0;
    }

    /**
     * 根据站名和车次号获取客运组织业务模版
     *
     * @param trainNum
     * @param station
     * @return
     */
    public OrganizeTemplate getOrganizeTemplate(String trainNum, String station) {
        return dataSet.select(OrganizeTemplate.class, condition, trainNum, station);
    }

    /**
     * 插入客运组织业务模版信息到数据库
     *
     * @param template
     * @return
     */
    public boolean insertTemplate(OrganizeTemplate template) {
        return dataSet.insert(template);
    }

    /**
     * 更新客运组织业务模版信息
     *
     * @param template
     * @return
     */
    public boolean updateTemplate(OrganizeTemplate template) {
        return dataSet.update(template, condition, template.getTrainNum(), template.getStationName());
    }

    /**
     * 删除客运组织业务模版信息，在删除列车时刻表车站的时候也要删除该信息
     *
     * @param trainNum
     * @param station
     * @return
     */
    public boolean deleteTemplate(String trainNum, String station) {
        return dataSet.delete(OrganizeTemplate.class, condition, trainNum, station);
    }
}
