package com.crscd.passengerservice.plan.util;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDateTime;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 17:05
 */
public class DateTimeConverter extends CustomConverter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime source, Type<? extends String> destinationType, MappingContext mappingContext) {
        return DateTimeFormatterUtil.convertDatetimeToString(source);
    }
}
