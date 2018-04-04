package com.crscd.framework.ioctest;

import com.crscd.framework.orm.annotation.OrmIgnore;
import com.crscd.framework.orm.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/7/17
 * Time: 9:25
 */
@Table("person")
public class TestPersonBean {
    @OrmIgnore
    private long id;
    private String name;
    private Sex sex;
    private String birthday;
    private List<String> schoolList;

    public TestPersonBean() {
        schoolList = new ArrayList<>();
    }

    public List<String> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<String> schoolList) {
        this.schoolList = schoolList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
