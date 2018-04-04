package com.crscd.passengerservice.plan.enumtype;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 8:54
 * "highspeed" 高速铁路
 * "intercity" 城际铁路
 * "passengerspecial" 客运专线
 * "normal" 普速铁路
 */
public enum TrainTypeEnum {
    HIGH_SPEED,
    INTERCITY,
    PASSENGER_SPECIAL,
    NORMAL,
    ALL;

    public static TrainTypeEnum getTrainTypeEnum(String type) {
        switch (type.toLowerCase()) {
            case "highspeed":
                return HIGH_SPEED;
            case "intercity":
                return INTERCITY;
            case "passengerspecial":
                return PASSENGER_SPECIAL;
            case "normal":
                return NORMAL;
            case "all":
                return ALL;
            default:
                throw new IllegalArgumentException(type + " is not legal for TrainTypeEnum!");
        }
    }


    public static String getTrainTypeString(TrainTypeEnum type) {
        switch (type) {
            case HIGH_SPEED:
                return "highspeed";
            case INTERCITY:
                return "intercity";
            case PASSENGER_SPECIAL:
                return "passengerspecial";
            case NORMAL:
                return "normal";
            case ALL:
                return "all";
            default:
                throw new IllegalArgumentException(type + " is not legal for TrainTypeEnum!");
        }
    }
}
