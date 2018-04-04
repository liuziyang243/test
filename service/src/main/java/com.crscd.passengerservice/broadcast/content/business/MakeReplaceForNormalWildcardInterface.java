package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.passengerservice.broadcast.content.domainobject.NormalBroadcastContentSubstitution;

import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/16
 */
public interface MakeReplaceForNormalWildcardInterface extends MakeReplaceForWildcardInterface {
    List<NormalBroadcastContentSubstitution> getContentSubstitutionList();
}
