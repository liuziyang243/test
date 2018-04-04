package com.crscd.passengerservice.display.format.domainobject.DataXMLBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/9/6.
 */
public class LY {
    private String LN;
    private String VN;
    private List<DC> DC = new ArrayList<DC>();

    @XmlElement(name = "LN")
    public String getLN() {
        return LN;
    }

    public void setLN(String LN) {
        this.LN = LN;
    }

    @XmlElement(name = "VN")
    public String getVN() {
        return VN;
    }

    public void setVN(String VN) {
        this.VN = VN;
    }

    @XmlElementWrapper(name = "EM")
    @XmlElement(name = "DC")
    public List<DC> getDC() {
        return DC;
    }

    public void setDC(List<DC> DC) {
        this.DC = DC;
    }
}
