package com.crscd.passengerservice.plan.enumtype;

/**
 * @author lzy
 * Date: 2017/8/16
 * Time: 10:18
 */
public enum CheckStateEnum {
    /**
     * 检票状态
     * 等待检票，开检，停检，未知
     */
    WAIT_CHECKING("wait checking"),
    START_CHECKING("checking"),
    STOP_CHECKING("stop checking"),
    NOT_VALID("plan date is invalid");

    private String state;

    CheckStateEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

}
