package com.crscd.framework.utiltest;

/**
 * @author lzy
 * Date: 2017/7/19
 * Time: 15:21
 */
public class User {
    private String name;
    private String age;
    private String sexy;
    private String group;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSexy() {
        return sexy;
    }

    public void setSexy(String sexy) {
        this.sexy = sexy;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return name + "/" + age + "/" + sexy + "/" + group + "/";
    }
}
