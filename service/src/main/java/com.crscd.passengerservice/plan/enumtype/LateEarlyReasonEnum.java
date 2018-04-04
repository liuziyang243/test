package com.crscd.passengerservice.plan.enumtype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * @author lzy
 * Date: 2017/8/15
 * Time: 16:17
 */
public enum LateEarlyReasonEnum {
    NONE("None."),
    WEATHER("Weather reason."),
    MAINTENANCE("Train maintenance reason."),
    MANAGEMENT("Management reason."),
    CTC_MODIFY("Changed from ctc system."),
    TICKET_MODIFY("Changed from ticket system."),
    DISPATCH_PLAN_MODIFY("Changed from dispatch plan."),
    PASSENGER_PLAN_MODIFY("Changed from passenger plan.");

    private static List<String> reasonList = new ArrayList<>();

    static {
        for (LateEarlyReasonEnum s : LateEarlyReasonEnum.values()) {
            reasonList.add(s.getReason());
        }
    }

    private final String reason;

    LateEarlyReasonEnum(String reason) {
        this.reason = reason;
    }

    public static List<String> getReasonList() {
        return reasonList;
    }

    public static LateEarlyReasonEnum getReason(String reason) {
        switch (reason.toLowerCase()) {
            case "none":
                return NONE;
            case "weather":
                return WEATHER;
            case "maintenance":
                return MAINTENANCE;
            case "management":
                return MAINTENANCE;
            case "ctc":
                return CTC_MODIFY;
            case "ticket":
                return TICKET_MODIFY;
            case "dispatch":
                return DISPATCH_PLAN_MODIFY;
            case "passenger":
                return PASSENGER_PLAN_MODIFY;
            default:
                throw new IllegalArgumentException(reason + " has not been support in LateEarlyReasonEnum!");
        }
    }

    public String getReason() {
        return this.reason;
    }

}
