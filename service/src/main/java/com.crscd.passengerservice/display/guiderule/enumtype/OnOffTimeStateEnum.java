package com.crscd.passengerservice.display.guiderule.enumtype;

/**
 * @author lzy
 * Date: 2017/9/13
 * Time: 16:16
 */
public enum OnOffTimeStateEnum {
    ON_SCREEN_TIME_EMPTY("On screen time is empty."),
    OFF_SCREEN_TIME_EMPTY("Off screen time is empty"),
    OFF_SCREEN_TIME_BEFORE_PRESENT("Off screen time is before present."),
    UP_TIME_AFTER_DOWN_TIME("Up screen time is after down time."),
    UP_TIME_BEFORE_PRESENT("Up screen time is before present."),
    NEED_ON_SCREEN("Can put on screen");

    private String note;

    OnOffTimeStateEnum(String state) {
        this.note = state;
    }

    public String getState() {
        return this.note;
    }
}
