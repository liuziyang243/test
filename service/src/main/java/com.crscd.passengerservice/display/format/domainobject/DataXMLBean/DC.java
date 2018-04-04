package com.crscd.passengerservice.display.format.domainobject.DataXMLBean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/9/6.
 */
public class DC {
    private String ID;
    private List<String> row = new ArrayList<>();

    @XmlAttribute(name = "ID")
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @XmlElement(name = "row")
    public List<String> getRow() {
        return row;
    }

    public void setRow(List<String> row) {
        this.row = row;
    }
}
