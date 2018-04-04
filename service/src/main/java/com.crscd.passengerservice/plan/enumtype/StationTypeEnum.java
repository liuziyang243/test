package com.crscd.passengerservice.plan.enumtype;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 10:04
 */
public enum StationTypeEnum {
    START, PASS, FINAL;

    public static StationTypeEnum getStationType(String type) {
        switch (type.toLowerCase()) {
            case "start":
                return START;
            case "pass":
                return PASS;
            case "final":
                return FINAL;
            default:
                throw new IllegalArgumentException(type + " is not legal for StationTypeEnum!");
        }
    }

    public static String getStationTypeString(StationTypeEnum type) {
        switch (type) {
            case START:
                return "start";
            case PASS:
                return "pass";
            case FINAL:
                return "final";
            default:
                throw new IllegalArgumentException(type + " is not legal for StationTypeEnum!");
        }
    }
}
