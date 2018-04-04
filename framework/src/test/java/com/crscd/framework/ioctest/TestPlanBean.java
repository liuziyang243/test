package com.crscd.framework.ioctest;

import com.crscd.framework.orm.annotation.OrmIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author lzy
 * Date: 2017/8/17
 * Time: 11:19
 */
public class TestPlanBean {
    @OrmIgnore
    private long id;
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime localDateTime;

    public TestPlanBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
