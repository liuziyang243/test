package com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/8/30.
 */
@XmlRootElement(name = "DTVar")
public class DTVar {
    private String FrameName;
    private List<DE> DE = new ArrayList<DE>();

    @XmlElement(name = "FrameName")
    public String getFrameName() {
        return FrameName;
    }

    public void setFrameName(String frameName) {
        FrameName = frameName;
    }

    @XmlElementWrapper(name = "DEList")
    @XmlElement(name = "DE")
    public List<DE> getDE() {
        return DE;
    }

    public void setDE(List<DE> DE) {
        this.DE = DE;
    }
}
