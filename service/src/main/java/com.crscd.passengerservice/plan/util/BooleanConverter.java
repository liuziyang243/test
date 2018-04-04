package com.crscd.passengerservice.plan.util;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 17:06
 */
public class BooleanConverter extends CustomConverter<Boolean, Integer> {
    @Override
    public Integer convert(Boolean source, Type<? extends Integer> destinationType, MappingContext mappingContext) {
        return convertFlagToInt(source);
    }

    private int convertFlagToInt(boolean flag) {
        if (flag) {
            return 1;
        } else {
            return 0;
        }
    }
}
