package com.crscd.passengerservice.plan.util;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 17:06
 */
public class DateConverter extends CustomConverter<LocalDate, String> {

    @Override
    public String convert(LocalDate source, Type<? extends String> destinationType, MappingContext mappingContext) {
        return DateTimeFormatterUtil.convertDateToString(source);
    }
}
