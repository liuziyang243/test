package com.crscd.passengerservice.plan.enumtype;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 16:08
 */
public enum TrainTimeStateEnum {
    EARLY("Early"),
    LATE("Late"),
    ON_TIME("On time"),
    INVALID("Invalid");

    private String state;

    TrainTimeStateEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }
}
