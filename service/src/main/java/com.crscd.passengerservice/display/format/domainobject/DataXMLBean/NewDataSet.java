package com.crscd.passengerservice.display.format.domainobject.DataXMLBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cuishiqing on 2017/9/6.
 */
@XmlRootElement(name = "NEWDATASET")
public class NewDataSet {
    private LY ly;

    @XmlElement(name = "LY")
    public LY getLy() {
        return ly;
    }

    public void setLy(LY ly) {
        this.ly = ly;
    }
}
