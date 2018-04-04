package com.crscd.passengerservice.broadcast.template.enumtype;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 15:41
 */
public enum BroadcastKindEnum {
    ARRIVE_DEPARTURE,   // 到发
    ALTERATION,  // 变更
    TRAIN_MANUAL,  // 车次
    OTHERS,  // 其他
    ALL;  //全部

    // Implementing a fromString method on an enum type
    private static final Map<String, BroadcastKindEnum> STRING_TO_ENUM = new HashMap<>();

    static {
        // Initialize map from constant name to enum constant
        for (BroadcastKindEnum blah : values()) {
            STRING_TO_ENUM.put(blah.toString(), blah);
        }
    }

    // Returns Blah for string, or null if string is invalid
    public static BroadcastKindEnum fromString(String symbol) {
        return STRING_TO_ENUM.get(symbol);
    }
}
