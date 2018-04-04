package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.util.text.DocumentUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DTVar;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.Frame;
import com.crscd.passengerservice.display.format.domainobject.FrameInfo;
import com.crscd.passengerservice.display.format.util.JaxbUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class FrameXMLGeneration {
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(FrameXMLGeneration.class);

    private DTVarXMLGeneration dtVarXMLGeneration;

    private FrameFileManager frameFileManager;

    public void setDtVarXMLGeneration(DTVarXMLGeneration dtVarXMLGeneration) {
        this.dtVarXMLGeneration = dtVarXMLGeneration;
    }

    public void setFrameFileManager(FrameFileManager frameFileManager) {
        this.frameFileManager = frameFileManager;
    }

    public String FrameVarXMLGeneration(String frameName) {
        FrameInfo frameInfo = frameFileManager.select(frameName);
        if (frameInfo == null) {
            return null;
        }

        String frameXml = FrameStringGeneration(frameInfo);
        if (StringUtil.isEmpty(frameXml)) {
            return null;
        }
        //帧版式
        String frameData = frameInfo.getFrameData();
        //生成Document
        Document frameDoc = DocumentUtil.stringToDocument(frameXml);
        Document frameDataDoc = DocumentUtil.stringToDocument(frameData);

        Element fr = frameDataDoc.getRootElement();
        Element FR = new Element("FR");
        FR.addContent(fr.cloneContent());

        Element Frame = frameDoc.getRootElement();
        Frame.addContent(FR);
        Element newFrame = new Element("Frame");
        newFrame.addContent(Frame.cloneContent());

        Document FrameDoc = new Document(newFrame);
        String frameXML = DocumentUtil.documentToString(FrameDoc);
        return frameXML;
    }

    private String FrameStringGeneration(FrameInfo frameInfo) {
        DTVar dtVar = dtVarXMLGeneration.DTVarGeneration(frameInfo.getFrameName());
        Frame frame = new Frame();
        frame.setName(frameInfo.getFrameName());
        frame.setShowName(frameInfo.getShowName());
        frame.setScreenType(frameInfo.getScreenType());
        frame.setScreenWidth(frameInfo.getScreenWidth());
        frame.setScreenHeight(frameInfo.getScreenHeight());
        frame.setScreenColor(frameInfo.getScreenColor());
        frame.setDtVar(dtVar);
        //帧属性信息及动态表格列属性信息
        String frameXml;
        try {
            frameXml = JaxbUtil.toXml(frame, Frame.class);
        } catch (JAXBException e) {
            logger.error("Generation XML error", e);
            return null;
        }

        return frameXml;
    }

}
