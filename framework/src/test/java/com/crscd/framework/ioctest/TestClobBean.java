package com.crscd.framework.ioctest;

import com.crscd.framework.orm.annotation.Table;
import com.crscd.framework.util.base.ClobUtil;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs
 * on 2017/7/19.
 */
@Table("person")
public class TestClobBean {
    private long id;
    private String name;
    private String sex;
    private Clob description;
    private List<String> schoolList;

    public TestClobBean() {
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getDescription() {
        return ClobUtil.clob2Str(this.description);
    }

    public void setDescription(String description) {
        this.description = ClobUtil.str2Clob(description);
    }
}
