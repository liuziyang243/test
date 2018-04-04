package com.crscd.passengerservice.display;

import com.crscd.framework.util.text.DocumentUtil;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.COL;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DE;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DTVar;
import com.crscd.passengerservice.display.format.util.JaxbUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/9/1.
 */
public class FrameXMLGenerationTest {

    @Test
    public void newFrameStringGenerationTest() {
        COL col1 = new COL();
        col1.setID("1234");
        col1.setVarName("TestName");
        List<COL> colList = new ArrayList<>();
        colList.add(col1);

        DE de = new DE();
        de.setID("1234");
        de.setLang("English");
        de.setSource("Entrance");
        de.setCol(colList);
        List<DE> deList = new ArrayList<>();
        deList.add(de);

        DTVar dtVar = new DTVar();
        dtVar.setFrameName("20161423");
        dtVar.setDE(deList);

        String dtVarXml = "";
        try {
            dtVarXml = JaxbUtil.toXml(dtVar, DTVar.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        Document dtVarDoc = DocumentUtil.stringToDocument(dtVarXml);
        Element DTVar = dtVarDoc.getRootElement();

        Element Test = new Element("Test");
        Test.addContent("t1234");

        DTVar.addContent(Test);

        String dtVarString = DocumentUtil.documentToString(dtVarDoc);
        System.out.print(dtVarString);

        DTVar.removeChild("DEList");
        dtVarString = DocumentUtil.documentToString(dtVarDoc);
        System.out.print(dtVarString);

    }
}
