package com.crscd.passengerservice.notice.enumtype;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/29
 * Time: 12:08
 */
public enum NoticeModifyEnum {
    ACTUAL_DEPARTURE_TIME("Adjust the departure time."),
    ACTUAL_ARRIVE_TIME("Adjust the arrive time."),
    ACTUAL_TRACK_NUM("Adjust the track number."),

    MANUAL_SUSPEND("Change the trainSuspend state."),

    START_CHECK_TIME("Adjust the start checking time."),
    STOP_CHECK_TIME("Adjust the stop checking time."),

    WAIT_ZONE("Adjust the wait zone."),
    ENTRANCE_PORT("Adjust the entrance port"),
    EXIT_PORT("Adjust the exit port"),
    ABOARD_CHECK_PORT("Adjust the check port."),
    EXIT_CHECK_PORT("Adjust the exit check port."),

    //ADD BY CSQ
    CHECK_STATUS("Adjust the check status.");

    // Implementing a fromString method on an enum type
    private static final Map<String, NoticeModifyEnum> STRING_TO_ENUM = new HashMap<>();

    static {
        // Initialize map from constant name to enum constant
        for (NoticeModifyEnum blah : values()) {
            STRING_TO_ENUM.put(blah.toString(), blah);
        }
    }

    private String modifyMessage;

    NoticeModifyEnum(String message) {
        this.modifyMessage = message;
    }

    // Returns Blah for string, or null if string is invalid
    public static NoticeModifyEnum fromString(String symbol) {
        return STRING_TO_ENUM.get(symbol);
    }

    public String getModifyMessage() {
        return this.modifyMessage;
    }
}
