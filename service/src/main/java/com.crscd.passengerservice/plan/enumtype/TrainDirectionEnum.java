package com.crscd.passengerservice.plan.enumtype;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 10:23
 */
public enum TrainDirectionEnum {
    UP, DOWN;

    public static TrainDirectionEnum getTrainDirectionEnum(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
                return UP;
            case "down":
                return DOWN;
            default:
                throw new IllegalArgumentException(direction + " is not legal for TrainDirectionEnum!");
        }
    }
}
