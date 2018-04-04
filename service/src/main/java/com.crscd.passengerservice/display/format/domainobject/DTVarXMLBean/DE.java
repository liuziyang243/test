package com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class DE {
    private String ID;
    private String Lang;
    private String Source;
    private List<COL> col = new ArrayList<COL>();

    @XmlElement(name = "Lang")
    public String getLang() {
        return Lang;
    }

    public void setLang(String lang) {
        Lang = lang;
    }

    @XmlElement(name = "ID")
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @XmlElement(name = "Source")
    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    @XmlElement(name = "COL")
    public List<COL> getCol() {
        return col;
    }

    public void setCol(List<COL> col) {
        this.col = col;
    }
}
