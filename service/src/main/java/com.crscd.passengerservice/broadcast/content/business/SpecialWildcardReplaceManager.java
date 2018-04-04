package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.broadcast.content.domainobject.SpecialBroadcastContentSubstitution;
import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;
import com.crscd.passengerservice.broadcast.template.enumtype.SpecialSubstitutePropertyEnum;

import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/9/16
 */
public class SpecialWildcardReplaceManager extends AbstractSpecialWildcardReplaceManager {

    @Override
    public String makeReplaceForWildcard(String detailContent, Map<NormalSubstitutePropertyEnum, Object> fieldMap, String lan) {
        String finalContent = detailContent;
        if (null == detailContent) {
            return null;
        }
        for (SpecialBroadcastContentSubstitution sub : BroadcastContentSubstitutionsList) {
            // 如果广播词中包含替换标识，则进行替换操作
            if (finalContent.contains(sub.getTag())) {
                if (sub.getProperty().equals(SpecialSubstitutePropertyEnum.DATE)) {
                    finalContent = StringUtil.replaceAll(finalContent, sub.getTag(), getReplacedWord(DateTimeUtil.getCurrentDateString(), lan));
                }
                if (sub.getProperty().equals(SpecialSubstitutePropertyEnum.WEEKDAY)) {
                    finalContent = StringUtil.replaceAll(finalContent, sub.getTag(), getReplacedWord(DateTimeUtil.getCurrentWeekDayString(), lan));
                }
            }
        }
        finalContent = finalContent.replaceAll("\\[", "").replaceAll("]", "");
        return finalContent;
    }
}
