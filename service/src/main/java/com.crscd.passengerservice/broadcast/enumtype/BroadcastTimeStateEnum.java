package com.crscd.passengerservice.broadcast.enumtype;

/**
 * Created by liuziyang
 * Create date: 2017/9/14
 *
 * @author lzy
 */
public enum BroadcastTimeStateEnum {
    TIME_NULL("Broadcast time is null."),
    TIME_OVERDUE("Broadcast time is overdue."),
    TIME_OK("Broadcast time is ok.");

    private String message;

    BroadcastTimeStateEnum(String state) {
        this.message = state;
    }

    public String getState() {
        return this.message;
    }
}
