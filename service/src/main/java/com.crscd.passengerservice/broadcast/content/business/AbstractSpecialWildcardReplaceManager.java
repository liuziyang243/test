package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.passengerservice.broadcast.content.domainobject.SpecialBroadcastContentSubstitution;
import com.crscd.passengerservice.broadcast.template.enumtype.SpecialSubstitutePropertyEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/16
 */
public abstract class AbstractSpecialWildcardReplaceManager extends AbstractWildcardReplaceManager implements MakeReplaceForSpecialWildcardInterface {
    static List<SpecialBroadcastContentSubstitution> BroadcastContentSubstitutionsList = new ArrayList<>();

    /*
     * 定义一组替换广播词模版
     */
    static {
        // 日期
        SpecialBroadcastContentSubstitution template = new SpecialBroadcastContentSubstitution();
        template.setTag("<@Date>");
        template.setExplain("Date");
        template.setProperty(SpecialSubstitutePropertyEnum.DATE);
        BroadcastContentSubstitutionsList.add(template);

        // 星期几
        template = new SpecialBroadcastContentSubstitution();
        template.setTag("<@Weekday>");
        template.setExplain("Weekday");
        template.setProperty(SpecialSubstitutePropertyEnum.WEEKDAY);
        BroadcastContentSubstitutionsList.add(template);
    }

    @Override
    public List<SpecialBroadcastContentSubstitution> getContentSubstitutionList() {
        return BroadcastContentSubstitutionsList;
    }
}
