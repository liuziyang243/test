package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.text.DocumentUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DTVar;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.Format;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;
import com.crscd.passengerservice.display.format.domainobject.FormatXMLBean.LYBean;
import com.crscd.passengerservice.display.format.domainobject.FrameListInfo;
import com.crscd.passengerservice.display.format.util.JaxbUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class FormatXMLGeneration {
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(FrameXMLGeneration.class);

    private FormatManager formatManager;

    private DTVarXMLGeneration dtVarXMLGeneration;

    private FormatFileManager formatFileManager;

    private FrameFileManager frameFileManager;

    public void setFrameFileManager(FrameFileManager frameFileManager) {
        this.frameFileManager = frameFileManager;
    }

    public void setFormatManager(FormatManager formatManager) {
        this.formatManager = formatManager;
    }

    public void setDtVarXMLGeneration(DTVarXMLGeneration dtVarXMLGeneration) {
        this.dtVarXMLGeneration = dtVarXMLGeneration;
    }

    public void setFormatFileManager(FormatFileManager formatFileManager) {
        this.formatFileManager = formatFileManager;
    }

    /**
     * @param formatID
     * @return
     * @see "版式+数据表格格式XML生成"
     */
    public String formatVarXMLGeneration(String formatID) {
        FormatInfo formatInfo = formatFileManager.select(formatID);
        if (formatInfo == null) {
            return null;
        }
        String formatXml = formatStringGeneration(formatInfo);
        String formatData = formatInfo.getFormatData();
        Document formatDoc = DocumentUtil.stringToDocument(formatXml);
        Document formatDataDoc = DocumentUtil.stringToDocument(formatData);

        Element ly = formatDataDoc.getRootElement();
        Element LY = new Element("LY");
        LY.addContent(ly.cloneContent());

        Element Format = formatDoc.getRootElement();
        Format.addContent(LY);

        Document FormatDoc = new Document(Format);
        String formatXML = DocumentUtil.documentToString(FormatDoc);

        return formatXML;
    }

    /**
     * @param formatId
     * @param frameList key:frameId value:duration
     * @return
     * @see "根据帧列表组合版式XML"
     */
    public String createFormatByFrameList(String formatId, LinkedHashMap<String, String> frameList) {
        LYBean lyBean = new LYBean();
        //按下划线拆分formatId为LN、VN
        List<String> lnVn = StringUtil.stringToList(formatId, "_");
        lyBean.setLN(lnVn.get(0));
        lyBean.setVN(lnVn.get(1));
        lyBean.setFN("" + frameList.size());
        List<String> frameDurationList = new ArrayList<>();
        for (String value : frameList.values()) {
            frameDurationList.add(value);
        }
        lyBean.setTM(ListUtil.listToStringByPunctuation(frameDurationList, ","));

        //根据帧列表生成版式XML
        String formatXml;
        try {
            formatXml = JaxbUtil.toXml(lyBean, LYBean.class);
        } catch (JAXBException e) {
            logger.error("Generate XML error", e);
            return "";
        }
        Document formatDoc = DocumentUtil.stringToDocument(formatXml);
        Element ly = formatDoc.getRootElement();
        for (Map.Entry<String, String> entry : frameList.entrySet()) {
            String frameData = frameFileManager.select(entry.getKey()).getFrameData();
            Document frameDoc = DocumentUtil.stringToDocument(frameData);
            Element fr = frameDoc.getRootElement();
            Element FR = new Element("FR");
            FR.addContent(fr.cloneContent());
            ly.addContent(FR);
        }

        return DocumentUtil.documentToString(formatDoc);
    }

    /**
     * @param formatInfo
     * @return
     * @see "版式XML生成"
     */
    private String formatStringGeneration(FormatInfo formatInfo) {
        Format format = new Format();
        List<FrameListInfo> frameList = formatManager.getFrameListFromFormatData(formatInfo.getFormatData());
        List<DTVar> dtVarList = new ArrayList<>();
        for (FrameListInfo f : frameList) {
            DTVar dtVar = dtVarXMLGeneration.DTVarGeneration(f.getFrameName());
            dtVarList.add(dtVar);
        }

        format.setID(formatInfo.getFormatID());
        format.setName(formatInfo.getFormatName());
        format.setScreenType(formatInfo.getScreenType());
        format.setScreenWidth(formatInfo.getScreenWidth());
        format.setScreenHeight(formatInfo.getScreenHeight());
        format.setScreenColor(formatInfo.getScreenColor());
        format.setDtVarList(dtVarList);

        String formatXml = null;
        try {
            formatXml = JaxbUtil.toXml(format, Format.class);
        } catch (JAXBException e) {
            logger.error("Generation XML error", e);
        }
        return formatXml;
    }
}
