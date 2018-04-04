package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.passengerservice.broadcast.content.domainobject.SpecialBroadcastContentSubstitution;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/16
 */
public interface MakeReplaceForSpecialWildcardInterface extends MakeReplaceForWildcardInterface {
    List<SpecialBroadcastContentSubstitution> getContentSubstitutionList();
}
