package com.crscd.framework.util.base;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;


public class DataUtil {

    /**
     * List to String
     *
     * @param stringList
     * @return
     */
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag && !"".equals(string)) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * String to List
     *
     * @param str
     * @return
     */
    public static List<String> stringToList(String str) {
        List<String> list = new ArrayList<>();
        if (str == null || "".equals(str)) {
            return list;
        }

        String[] array = str.split(",");
        Collections.addAll(list, array);
        return list;
    }

    /**
     * String to List 下划线分隔
     *
     * @param str
     * @return
     */
    public static List<String> stringToListByUnderline(String str) {
        List<String> list = new ArrayList<>();
        if (str == null || "".equals(str)) {
            list.add("");
            return list;
        }

        String[] array = str.split("_");
        Collections.addAll(list, array);
        return list;
    }

    /**
     * String to List 空格分隔
     *
     * @param str
     * @return
     */
    public static List<String> stringToListByBlank(String str) {
        List<String> list = new ArrayList<>();
        if (str == null || "".equals(str)) {
            list.add("");
            return list;
        }

        String[] array = str.split(" ");
        Collections.addAll(list, array);
        return list;
    }

    /**
     * Document转String
     *
     * @param doc
     * @return
     */
    public static String documentToString(Document doc) {
        XMLOutputter outputter = null;
        Format format = Format.getCompactFormat();
        format.setEncoding("gb2312");
        format.setIndent(null);
        outputter = new XMLOutputter(format);

        String xmlStr = outputter.outputString(doc);

        return xmlStr;
    }

    /**
     * 获取帧（FR）中的所有静态文字元素及其相应值
     *
     * @param frameXML
     * @return Map<元素名称       ，       元素值>
     */
    public static LinkedHashMap<String, String> getSWElementListInFrame(String frameXML) {
        LinkedHashMap<String, String> swMap = new LinkedHashMap<String, String>();
        Document doc = stringToDocument(frameXML);
        Element fr = doc.getRootElement();
        List swList = fr.getChildren("SW");
        for (int i = 0; i < swList.size(); i++) {
            Element sw = (Element) swList.get(i);
            String value = sw.getChildText("T");
            swMap.put("Static Words " + i, value);
        }

        return swMap;
    }

    /**
     * 修改帧中的静态文字内容
     *
     * @param frameXML
     * @param swMap
     * @return
     */
    public static String setSWElementListInFrame(String frameXML, LinkedHashMap<String, String> swMap) {
        Document doc = stringToDocument(frameXML);
        Element fr = doc.getRootElement();
        List swList = fr.getChildren("SW");
        for (int i = 0; i < swList.size(); i++) {
            Element sw = (Element) swList.get(i);
            String elementID = sw.getChildText("ID");
            if (swMap.containsKey(elementID)) {
                sw.getChild("T").setText(swMap.get(elementID));
            }
        }

        return documentToString(doc);
    }

    /**
     * String to Document
     *
     * @param xmlStr
     * @return
     */
    public static Document stringToDocument(String xmlStr) {
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        SAXBuilder sb = new SAXBuilder();
        Document doc = new Document();
        try {
            doc = sb.build(is);
        } catch (JDOMException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return doc;
        }

        return doc;

    }

}
