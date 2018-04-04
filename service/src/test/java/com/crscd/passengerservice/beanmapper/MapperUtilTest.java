package com.crscd.passengerservice.beanmapper;

import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.config.domainobject.StationInfo;
import com.crscd.passengerservice.config.po.StationConfigBean;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author lzy
 * Date: 2017/7/19
 * Time: 8:49
 */
public class MapperUtilTest {

    @Test
    public void MapConfingBeanToDO() {
        StationConfigBean bean = new StationConfigBean();
        bean.setBureauCode("0");
        bean.setMileage(120.45f);
        bean.setStationCode("32");
        bean.setStationName("AthiRiver");

        StationInfo info = MapperUtil.map(bean, StationInfo.class);
        System.out.println(info.getBureauCode());
        System.out.println(info.getMileage());
        System.out.println(info.getStationCode());
        System.out.println(info.getStationName());

        assertNotNull(info);
    }
}
