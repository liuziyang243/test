package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;

import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/8
 * Time: 16:42
 */
public interface MakeReplaceForWildcardInterface {

    String makeReplaceForWildcard(String detailContent, Map<NormalSubstitutePropertyEnum, Object> fieldMap, String lan);

}
