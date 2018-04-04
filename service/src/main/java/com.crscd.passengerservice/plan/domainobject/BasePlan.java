package com.crscd.passengerservice.plan.domainobject;

import com.crscd.framework.util.time.DateTimeUtil;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 8:18
 * This class contains basic property for all plans, and
 * it will be inherited by other plans.
 */
public abstract class BasePlan {
    /* plan id */
    protected long id;

    /* Generated time record */
    private String generateTimestamp;

    public BasePlan() {
        this.generateTimestamp = DateTimeUtil.getCurrentDatetimeString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenerateTimestamp() {
        return generateTimestamp;
    }

    public void setGenerateTimestamp(String generateTimestamp) {
        this.generateTimestamp = generateTimestamp;
    }
}
