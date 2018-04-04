package com.crscd.passengerservice.display.format.domainobject.FormatXMLBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cuishiqing on 2017/9/14.
 */
@XmlRootElement(name = "LY")
public class LYBean {
    //站码
    private String LN;
    //版式编号
    private String VN;
    //版式中包含的帧数量
    private String FN;
    //每帧持续显示时间
    private String TM;

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

    @XmlElement(name = "FN")
    public String getFN() {
        return FN;
    }

    public void setFN(String FN) {
        this.FN = FN;
    }

    @XmlElement(name = "TM")
    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }
}
