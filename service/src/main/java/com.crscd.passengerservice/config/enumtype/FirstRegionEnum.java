package com.crscd.passengerservice.config.enumtype;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/5
 * Time: 9:37
 */
public enum FirstRegionEnum {
    WAIT_ZONE,
    STATION_ENTRANCE_PORT,
    STATION_EXIT_PORT,
    ABOARD_CHECK_GATE,
    EXIT_CHECK_GATE,
    OTHERS;

    /**
     * Implementing a fromString method on an enum type
     */
    private static final Map<String, FirstRegionEnum> STRING_TO_ENUM = new HashMap<>();

    static {
        /* Initialize map from constant name to enum constant */
        for (FirstRegionEnum blah : values()) {
            STRING_TO_ENUM.put(blah.toString(), blah);
        }
    }

    /**
     * Returns Blah for string, or null if string is invalid
     *
     * @param symbol
     * @return
     */
    public static FirstRegionEnum fromString(String symbol) {
        return STRING_TO_ENUM.get(symbol);
    }
}
