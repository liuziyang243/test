package com.crscd.passengerservice.plan.enumtype;

/**
 * Created by Administrator on 2017/9/14.
 */
public enum AnalyseEnum {
    SAME,
    DELETE,
    ADD,
    REPLACE;

    public static String getTypeString(AnalyseEnum type) {
        switch (type) {
            case SAME:
                return "same";
            case DELETE:
                return "delete";
            case ADD:
                return "add";
            case REPLACE:
                return "replace";
            default:
                throw new IllegalArgumentException(type + " is not legal for AnalyseEnum!");
        }
    }
}
