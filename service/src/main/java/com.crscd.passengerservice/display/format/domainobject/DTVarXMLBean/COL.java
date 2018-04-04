package com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class COL {
    private String ID;
    private String VarName;

    @XmlElement(name = "ID")
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @XmlElement(name = "VarName")
    public String getVarName() {
        return VarName;
    }

    public void setVarName(String varName) {
        VarName = varName;
    }
}
