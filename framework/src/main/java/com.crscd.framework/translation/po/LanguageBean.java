package com.crscd.framework.translation.po;

/**
 * Create by: lzy
 * Date: 2016/7/5
 * Time: 9:32
 */
public class LanguageBean {
    private String languageName;

    public LanguageBean() {
    }

    public LanguageBean(String name) {
        this.languageName = name;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
