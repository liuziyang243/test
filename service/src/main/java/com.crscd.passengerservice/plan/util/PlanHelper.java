package com.crscd.passengerservice.plan.util;

import com.crscd.framework.util.number.RandomUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.plan.enumtype.TrainDirectionEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTimeStateEnum;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.time.LocalDateTime;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 11:28
 */
public class PlanHelper {

    /*
     * 判断计划时间和实际之间的关系，计划是否晚点或早点
     */
    public static TrainTimeStateEnum isActualPlanTimeEarlyOrLate(LocalDateTime planedTime, LocalDateTime actualTime) {

        if (planedTime == null || actualTime == null) {
            return TrainTimeStateEnum.INVALID;
        } else {
            if (planedTime.equals(actualTime)) {
                return TrainTimeStateEnum.ON_TIME;
            } else if (actualTime.isAfter(planedTime)) {
                return TrainTimeStateEnum.LATE;
            } else {
                return TrainTimeStateEnum.EARLY;
            }
        }
    }

    /*
     * todo:根据列车起始站和终到站获取列车的开行方向
     */
    public static TrainDirectionEnum getTrainDirection(String startStation, String finalStation) {
        return TrainDirectionEnum.DOWN;
    }

    /*
     * todo:根据列车起始站和当前站计算列车里程
     */
    public static float getMileageFromStartStation(String startStation, String presentStation) {
        return RandomUtil.nextLong(10000);
    }


    // 获取自定义mapperFactory
    public static MapperFactory getMapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();

        converterFactory.registerConverter("dateTimeConverter", new DateTimeConverter());
        converterFactory.registerConverter("dateConverter", new DateConverter());
        converterFactory.registerConverter("booleanConverter", new BooleanConverter());
        converterFactory.registerConverter("stationConverter", new StationConverter());
        return mapperFactory;
    }

    public static void setBasicPlanDTOTime(BasicTrainStationInfo info, BasicPlanDTO dto) {
        if (null == info) {
            dto.setDepartureTime("--");
            dto.setArriveTime("--");
        } else {
            dto.setDepartureTime(DateTimeFormatterUtil.convertTimeToString(info.getPlanedDepartureTime()));
            dto.setArriveTime(DateTimeFormatterUtil.convertTimeToString(info.getPlanedArriveTime()));
        }
    }
}
