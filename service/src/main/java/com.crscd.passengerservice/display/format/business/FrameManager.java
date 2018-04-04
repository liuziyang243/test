package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.text.DocumentUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.COL;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DE;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DTVar;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.Frame;
import com.crscd.passengerservice.display.format.domainobject.FrameInfo;
import com.crscd.passengerservice.display.format.po.DataTableRowVarBean;
import com.crscd.passengerservice.display.format.po.FrameInfoBean;
import com.crscd.passengerservice.display.format.util.JaxbUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/9/1.
 */
public class FrameManager {
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(FrameManager.class);
    //帧初始二级编号
    private static int FrameSubID = 0;
    //Config Manager
    ConfigManager configManager;
    //数据库
    private DataSet oracleDataSet;

    private FrameFileManager frameFileManager;

    private FrameXMLGeneration frameXMLGeneration;

    public FrameManager() {
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setFrameFileManager(FrameFileManager frameFileManager) {
        this.frameFileManager = frameFileManager;
    }

    public void setFrameXMLGeneration(FrameXMLGeneration frameXMLGeneration) {
        this.frameXMLGeneration = frameXMLGeneration;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    /**
     * 读取帧列表
     *
     * @param stationName
     * @param screenType
     * @param screenWidth
     * @param screenHeight
     * @param screenColor
     * @return Map key:frameName value:frameData
     */
    public HashMap<String, String> getFrameList(String stationName, String screenType, String screenWidth, String screenHeight, String screenColor) {
        String condition = "StationName=? AND ScreenType=? AND ScreenWidth=? AND ScreenHeight=? AND ScreenColor=?";
        List<FrameInfoBean> fibList = oracleDataSet.selectListWithCondition(FrameInfoBean.class, condition, stationName, screenType, screenWidth, screenHeight, screenColor);
        if (fibList == null) {
            return null;
        }

        Map<String, String> frameMap = new HashMap<String, String>();
        for (FrameInfoBean aFibList : fibList) {
            String key = aFibList.getFrameName();
            String value = frameXMLGeneration.FrameVarXMLGeneration(aFibList.getFrameName());
            frameMap.put(key, value);
        }

        return (HashMap<String, String>) frameMap;
    }

    /**
     * @param stationName
     * @param frameVarData
     * @return
     * @see '帧入库'
     */
    public boolean SaveFrameData(String stationName, String frameVarData) {
        //动态表格列变量新增结果
        boolean DTVarInsertResult = true;
        //帧格式新增结果
        boolean frameInsertResult = false;
        //帧入库结果
        boolean insertResult = false;

        Document frameDoc = DocumentUtil.stringToDocument(frameVarData);
        Element frameElement = frameDoc.getRootElement();
        Element frElement = frameElement.getChild("FR");
        Element FR = new Element("FR");
        FR.addContent(frElement.cloneContent());
        frameElement.removeChild("FR");
        Element frameData = new Element("Frame");
        frameData.addContent(frameElement.cloneContent());

        Document FrameDoc = new Document(frameData);
        Document frDoc = new Document(FR);

        String frameString = DocumentUtil.documentToString(FrameDoc);
        String frString = DocumentUtil.documentToString(frDoc);
        //帧字节大于60000时，返回false
        if (frString.length() > 60000) {
            return false;
        }
        Frame frame = JaxbUtil.converyToJavaBean(frameString, Frame.class);

        frameInsertResult = saveFrameFile(frame, frString, stationName);
        DTVarInsertResult = saveDTVarData(frame);
        if (DTVarInsertResult && frameInsertResult) {
            insertResult = true;
        }

        return insertResult;
    }

    /**
     * @return PlanData：计划类数据
     * TicketData：客票类数据
     * @see "获取数据源列表"
     */
    public List<String> getDataSourceType() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("PlanData");
        dataSource.add("TicketData");
        return dataSource;
    }

    /**
     * @param dataSourceType
     * @return
     * @see "获取动态表格可用变量"
     */
    public List<String> getDataElement(String dataSourceType) {
        List<String> dataElement = new ArrayList<>();
        switch (dataSourceType) {
            //客票可用变量
            case "TicketData":
                dataElement.add("trainNum");//车次
                dataElement.add("planArriveTime");//计划到达时间
                dataElement.add("planDepartureTime");//计划出发时间
                dataElement.add("startStation");//始发站
                dataElement.add("finalStation");//终到站
                //动态生成日期+席别变量
                //获取所有客票席别
                List<String> ticketSeats = StringUtil.stringToList(ConfigHelper.getString("TicketSeatTypes"), ",");
                //获取票额最大显示天数
                int days = ConfigHelper.getInt("TicketSeatDays");
                for (int i = 0; i < days; i++) {
                    for (String seatName : ticketSeats) {
                        String elementName = "" + i + "day_" + seatName;//席别变量名为"Xday_席别" 如："0day_HardSead"
                        dataElement.add(elementName);
                    }
                    String dateelementName = "" + i + "day_date";//可显示日期
                    dataElement.add(dateelementName);
                }
                return dataElement;
            //计划可用变量
            case "PlanData":
                dataElement.add("trainNum");//车次
                dataElement.add("actualArriveTime");//实际到达时间
                dataElement.add("actualDepartureTime");//实际出发时间
                dataElement.add("startStation");//始发站
                dataElement.add("finalStation");//终点站
                dataElement.add("checkInState");//检票状态
                dataElement.add("arriveState");//到达状态
                dataElement.add("departureState");//出发状态
                dataElement.add("waitZone");//候车区
                dataElement.add("trackNumber");//股道
                dataElement.add("terminated");//停开标志
                dataElement.add("entrancePort");//进站口
                dataElement.add("platform");//站台
                return dataElement;
            default:
                return dataElement;
        }
    }

    /**
     * 帧修改
     *
     * @param frameVarData
     * @return
     */
    public boolean updateFrameData(String frameVarData) {
        //动态表格列变量新增结果
        boolean DTVarInsertResult = true;
        //帧格式新增结果
        boolean frameInsertResult = false;
        //帧入库结果
        boolean updateResult = false;

        Document frameDoc = DocumentUtil.stringToDocument(frameVarData);
        Element frameElement = frameDoc.getRootElement();
        Element frElement = frameElement.getChild("FR");
        Element FR = new Element("FR");
        FR.addContent(frElement.cloneContent());
        frameElement.removeChild("FR");
        Element newFrameElement = new Element("Frame");
        newFrameElement.addContent(frameElement.cloneContent());

        Document FrameDoc = new Document(newFrameElement);
        Document frDoc = new Document(FR);

        String frameString = DocumentUtil.documentToString(FrameDoc);
        String frString = DocumentUtil.documentToString(frDoc);
        //帧字节大于60000时，返回false
        if (frString.length() > 60000) {
            return false;
        }
        Frame frame = JaxbUtil.converyToJavaBean(frameString, Frame.class);
        //更新帧格式XML
        String stationName = oracleDataSet.selectColumn(FrameInfoBean.class, "StationName", "FrameName=?", frame.getName());
        frameInsertResult = saveFrameFile(frame, frString, stationName);
        //删除原帧中动态表格列变量描述
        oracleDataSet.delete(DataTableRowVarBean.class, "FrameName=?", frame.getName());
        DTVarInsertResult = saveDTVarData(frame);
        if (DTVarInsertResult && frameInsertResult) {
            updateResult = true;
        }
        return updateResult;
    }

    /**
     * @param stationName
     * @return
     * @see '自动获取帧编号'
     */
    public String getFrameID(String stationName) {
        String frameID = "";
        List<String> existIDList = oracleDataSet.selectColumnList(FrameInfoBean.class, "FrameName", "StationName=?", "FrameName", stationName);
        //帧编号为 站码+(0-2000)
        if (FrameSubID > 1998) {
            FrameSubID = 0;
        }
        for (int i = FrameSubID + 1; i < 2000; i++) {
            String stationCode = configManager.getStationCodeByName(stationName);
            String aFrameID = stationCode + "" + i;
            if (existIDList.contains(aFrameID)) {
                FrameSubID = i;
            } else {
                frameID = aFrameID;
                FrameSubID = i;
                break;
            }
        }

        if (StringUtil.isEmpty(frameID)) {
            return "Frame IDs are Used up.Please deletion some frames";
        }

        return frameID;
    }

    /**
     * @param frameName
     * @return
     * @see "删除帧"
     */
    public boolean deleteFrame(String frameName) {
        FrameInfoBean frameInfo = oracleDataSet.select(FrameInfoBean.class, "FrameName=?", frameName);
        if (frameInfo == null) {
            return false;
        }
        String usedFormat = frameInfo.getUsedFormat();
        List<String> usedFormatList = StringUtil.stringToList(usedFormat, ",");
        if (ListUtil.isEmpty(usedFormatList)) {
//			删除帧版式描述
            if (!frameFileManager.delete(frameInfo)) {
                return false;
            }
//			删除动态表格列变量描述
            DataTableRowVarBean dataTableRowVar = oracleDataSet.select(DataTableRowVarBean.class, "FrameName=?", frameName);
            if (dataTableRowVar != null) {
                oracleDataSet.delete(DataTableRowVarBean.class, "FrameName=?", frameName);
            }
            return true;
        }

        return false;
    }

    /**
     * @param frameName
     * @return
     * @see "读取帧格式"
     */
    public String getFrameAndDTVar(String frameName) {
        FrameInfo frameInfo = frameFileManager.select(frameName);
        Frame frame = new Frame();
        frame.setName(frameName);
        frame.setShowName(frameInfo.getShowName());
        frame.setScreenType(frameInfo.getScreenType());
        frame.setScreenWidth(frameInfo.getScreenWidth());
        frame.setScreenHeight(frameInfo.getScreenHeight());
        frame.setScreenColor(frameInfo.getScreenColor());
        frame.setDtVar(getDTVar(frameName));
        //Frame中添加帧格式XML<FR>节点
        Document frDoc = DocumentUtil.stringToDocument(frameInfo.getFrameData());
        Element fr = frDoc.getRootElement();
        String frameString;
        try {
            frameString = JaxbUtil.toXml(frame, Frame.class);
        } catch (JAXBException e) {
            logger.error("Object to Xml error", e);
            return "";
        }
        Document frameDoc = DocumentUtil.stringToDocument(frameString);
        Element frameElement = frameDoc.getRootElement();
        Element newFr = new Element("FR");
        newFr.addContent(fr.cloneContent());
        frameElement.addContent(newFr);
        Element newFrameElement = new Element("Frame");
        newFrameElement.addContent(frameElement.cloneContent());
        Document newFrameDoc = new Document(newFrameElement);

        return DocumentUtil.documentToString(newFrameDoc);

    }

    //获取帧中的动态表格元素信息
    private DTVar getDTVar(String frameName) {
        DTVar dtVar = new DTVar();
        List<DataTableRowVarBean> dataTableRowVars = oracleDataSet.selectListWithCondition(DataTableRowVarBean.class, "FrameName=?", frameName);
        if (ListUtil.isEmpty(dataTableRowVars)) {
            return dtVar;
        }
        dtVar.setFrameName(frameName);
        List<String> allElementList = new ArrayList<String>();
        for (int j = 0; j < dataTableRowVars.size(); j++) {
            allElementList.add(dataTableRowVars.get(j).getElementID());
        }
        List<String> elementList = ListUtil.removeDuplicateWithOrder(allElementList);
        List<DE> deList = new ArrayList<>();
        for (int k = 0; k < elementList.size(); k++) {
            DE de = new DE();
            List<DataTableRowVarBean> colRowVars = oracleDataSet.selectListWithCondition(
                    DataTableRowVarBean.class, "FrameName=? AND ElementID=?", frameName, elementList.get(k));
            de.setID(elementList.get(k));
            de.setLang(colRowVars.get(0).getLang());
            de.setSource(colRowVars.get(0).getDataSource());
            List<COL> colList = new ArrayList<>();
            for (int l = 0; l < colRowVars.size(); l++) {
                COL col = new COL();
                col.setID(colRowVars.get(l).getColID());
                col.setVarName(colRowVars.get(l).getVariateName());
                colList.add(col);
            }
            de.setCol(colList);
            deList.add(de);
        }
        dtVar.setDE(deList);
        return dtVar;
    }

    //保存或更新帧格式
    private boolean saveFrameFile(Frame frame, String frameData, String stationName) {
        FrameInfo frameInfo = new FrameInfo();
        frameInfo.setFrameName(frame.getName());
        frameInfo.setStationName(stationName);
        frameInfo.setShowName(frame.getShowName());
        frameInfo.setScreenType(frame.getScreenType());
        frameInfo.setScreenWidth(frame.getScreenWidth());
        frameInfo.setScreenHeight(frame.getScreenHeight());
        frameInfo.setScreenColor(frame.getScreenColor());
        frameInfo.setUsedFormat("");
        frameInfo.setFrameData(frameData);

        List<String> existIDList = oracleDataSet.selectColumnList(FrameInfoBean.class, "FrameName", "StationName=?", "FrameName", stationName);
        if (existIDList.contains(frame.getName())) {
            return frameFileManager.update(frameInfo);
        } else {
            return frameFileManager.insert(frameInfo);
        }
    }

    //保存数据表格设置
    private boolean saveDTVarData(Frame frame) {
        boolean DTVarInsertResult = true;

        DTVar dtVar = frame.getDtVar();
        List<DE> deList = dtVar.getDE();

        for (DE de : deList) {
            String ElementID = de.getID();
            String DataSource = de.getSource();
            String Language = de.getLang();
            List<COL> colList = de.getCol();
            for (COL col : colList) {
                String COLID = col.getID();
                String VarName = col.getVarName();
                DataTableRowVarBean dtrvb = new DataTableRowVarBean();
                dtrvb.setFrameName(frame.getName());
                dtrvb.setElementID(ElementID);
                dtrvb.setDataSource(DataSource);
                dtrvb.setLang(Language);
                dtrvb.setColID(COLID);
                dtrvb.setVariateName(VarName);

                boolean result = oracleDataSet.insert(dtrvb);
                if (!result) {
                    DTVarInsertResult = false;
                    break;
                }
            }
        }

        return DTVarInsertResult;
    }


}
