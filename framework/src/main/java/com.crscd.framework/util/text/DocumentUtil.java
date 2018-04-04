package com.crscd.framework.util.text;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by cuishiqing on 2017/8/31.
 */
public class DocumentUtil {

    public static Document stringToDocument(String xmlStr) {
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        Document doc = new Document();
        try {
            doc = sb.build(is);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            return doc;
        }

        return doc;
    }

    public static String documentToString(Document doc) {
        XMLOutputter outputter = null;
        Format format = Format.getCompactFormat();
        format.setEncoding("gb2312");
        format.setIndent(null);
        outputter = new XMLOutputter(format);

        String xmlStr = outputter.outputString(doc);

        return xmlStr;
    }

}
