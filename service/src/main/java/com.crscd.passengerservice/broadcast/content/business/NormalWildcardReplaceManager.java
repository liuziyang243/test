package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.broadcast.content.domainobject.NormalBroadcastContentSubstitution;
import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;

import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/8
 * Time: 16:40
 */
public class NormalWildcardReplaceManager extends AbstractNormalWildcardReplaceManager {

    @Override
    public String makeReplaceForWildcard(String detailContent, Map<NormalSubstitutePropertyEnum, Object> fieldMap, String lan) {
        String finalContent = detailContent;
        if (null == detailContent) {
            return null;
        }
        for (NormalBroadcastContentSubstitution sub : BroadcastContentSubstitutionsList) {
            // 如果广播词中包含替换标识，则进行替换操作
            if (finalContent.contains(sub.getTag())) {
                if (fieldMap.containsKey(sub.getClassProperty())) {
                    finalContent = StringUtil.replaceAll(finalContent, sub.getTag(), getReplacedWord(fieldMap.get(sub.getClassProperty()), lan));
                }
            }
        }
        finalContent = finalContent.replaceAll("\\[", "").replaceAll("]", "");
        return finalContent;
    }

}
