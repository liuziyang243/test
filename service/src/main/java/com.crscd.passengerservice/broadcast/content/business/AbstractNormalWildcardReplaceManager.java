package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.passengerservice.broadcast.content.domainobject.NormalBroadcastContentSubstitution;
import com.crscd.passengerservice.broadcast.template.enumtype.NormalSubstitutePropertyEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/9
 */
public abstract class AbstractNormalWildcardReplaceManager extends AbstractWildcardReplaceManager implements MakeReplaceForNormalWildcardInterface {
    static List<NormalBroadcastContentSubstitution> BroadcastContentSubstitutionsList = new ArrayList<>();

    /*
     * 定义一组替换广播词模版
     */
    static {
        //始发站
        NormalBroadcastContentSubstitution template = new NormalBroadcastContentSubstitution();
        template.setTag("<@startStation>");
        template.setExplain("Start station");
        template.setClassProperty(NormalSubstitutePropertyEnum.START_STATION);
        BroadcastContentSubstitutionsList.add(template);
        //终到站
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@finalStation>");
        template.setExplain("Final station");
        template.setClassProperty(NormalSubstitutePropertyEnum.FINAL_STATION);
        BroadcastContentSubstitutionsList.add(template);

        //实际到达时间
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@actualArriveTime>");
        template.setExplain("Actual arrive time");
        template.setClassProperty(NormalSubstitutePropertyEnum.ACTUAL_ARRIVE_TIME);
        BroadcastContentSubstitutionsList.add(template);
        //实际发车时间
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@actualDepartureTime>");
        template.setExplain("Actual departure time");
        template.setClassProperty(NormalSubstitutePropertyEnum.ACTUAL_DEPARTURE_TIME);
        BroadcastContentSubstitutionsList.add(template);

        //开始检票时间
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@startCheckTime>");
        template.setExplain("Start check time");
        template.setClassProperty(NormalSubstitutePropertyEnum.START_CHECK_TIME);
        BroadcastContentSubstitutionsList.add(template);
        //停止检票时间
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@stopCheckTime>");
        template.setExplain("Stop check time");
        template.setClassProperty(NormalSubstitutePropertyEnum.STOP_CHECK_TIME);
        BroadcastContentSubstitutionsList.add(template);

        //站台编号
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@Platform>");
        template.setExplain("Platform");
        template.setClassProperty(NormalSubstitutePropertyEnum.PLATFORM);
        BroadcastContentSubstitutionsList.add(template);
        //股道
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@planTrackNum>");
        template.setExplain("Track number");
        template.setClassProperty(NormalSubstitutePropertyEnum.ACTUAL_TRACK_NUM);
        BroadcastContentSubstitutionsList.add(template);

        //候车室
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@waitZone>");
        template.setExplain("waitZone");
        template.setClassProperty(NormalSubstitutePropertyEnum.WAIT_ZONE);
        BroadcastContentSubstitutionsList.add(template);
        //进站检票口
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@AboardCheckPort>");
        template.setExplain("Aboard check port");
        template.setClassProperty(NormalSubstitutePropertyEnum.ABOARD_CHECK_GATE);
        BroadcastContentSubstitutionsList.add(template);
        //出站口
        template = new NormalBroadcastContentSubstitution();
        template.setTag("<@ExitPort>");
        template.setExplain("entrancePort");
        template.setClassProperty(NormalSubstitutePropertyEnum.EXIT_PORT);
        BroadcastContentSubstitutionsList.add(template);
    }

    @Override
    public List<NormalBroadcastContentSubstitution> getContentSubstitutionList() {
        return BroadcastContentSubstitutionsList;
    }
}
