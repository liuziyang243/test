package com.crscd.passengerservice.display.format.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by cuishiqing on 2017/7/17.
 */
public class DataTableRowVarBean {
    /*自增长ID*/
    @OrmIgnore
    private int id;
    /*帧名称*/
    private String frameName;
    /*元素ID*/
    private String elementID;
    /*数据源*/
    private String dataSource;
    /*列ID*/
    private String colID;
    /*变量名*/
    private String variateName;
    /*动态表格语言标记*/
    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public String getElementID() {
        return elementID;
    }

    public void setElementID(String elementID) {
        this.elementID = elementID;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getColID() {
        return colID;
    }

    public void setColID(String colID) {
        this.colID = colID;
    }

    public String getVariateName() {
        return variateName;
    }

    public void setVariateName(String variateName) {
        this.variateName = variateName;
    }
}
