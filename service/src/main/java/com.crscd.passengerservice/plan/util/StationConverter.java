package com.crscd.passengerservice.plan.util;

import com.crscd.passengerservice.config.domainobject.StationInfo;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 17:07
 */
public class StationConverter extends CustomConverter<StationInfo, String> {
    @Override
    public String convert(StationInfo source, Type<? extends String> destinationType, MappingContext mappingContext) {
        return source.getStationName();
    }
}
