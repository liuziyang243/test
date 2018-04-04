package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.text.DocumentUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.COL;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DE;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DTVar;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.Format;
import com.crscd.passengerservice.display.format.domainobject.FormatAndFrameListInfo;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;
import com.crscd.passengerservice.display.format.domainobject.FrameInfo;
import com.crscd.passengerservice.display.format.domainobject.FrameListInfo;
import com.crscd.passengerservice.display.format.po.DataTableRowVarBean;
import com.crscd.passengerservice.display.format.po.FormatInfoBean;
import com.crscd.passengerservice.display.format.po.FrameInfoBean;
import com.crscd.passengerservice.display.format.po.ScreenManageBean;
import com.crscd.passengerservice.display.format.util.JaxbUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.util.*;

/**
 * Created by cuishiqing on 2017/9/1.
 */
public class FormatManager {
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(FormatManager.class);
    //版式初始二级编号
    private static int FormatSubID = 0;
    //数据库
    private DataSet oracleDataSet;
    //版式文件管理
    private FormatFileManager formatFileManager;
    //帧文件管理
    private FrameFileManager frameFileManager;
    //帧管理
    private FrameManager frameManager;
    //版式XML生成
    private FormatXMLGeneration formatXMLGeneration;
    //Comfig Manager
    private ConfigManager configManager;

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setFrameFileManager(FrameFileManager frameFileManager) {
        this.frameFileManager = frameFileManager;
    }

    public void setFormatXMLGeneration(FormatXMLGeneration formatXMLGeneration) {
        this.formatXMLGeneration = formatXMLGeneration;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setFormatFileManager(FormatFileManager formatFileManager) {
        this.formatFileManager = formatFileManager;
    }

    public void setFrameManager(FrameManager frameManager) {
        this.frameManager = frameManager;
    }

    /**
     * 获取版式列表
     *
     * @param stationName
     * @param screenType
     * @param screenWidth
     * @param screenHeight
     * @param screenColor
     * @return key: 版式编号
     * value：版式名称
     */
    public HashMap<String, String> getFormatList(String stationName, String screenType, String screenWidth, String screenHeight, String screenColor) {
        Map<String, String> formatMap = new HashMap<String, String>();
        String condition = "StationName=? AND ScreenType=? AND ScreenWidth=? AND ScreenHeight=? AND ScreenColor=?";
        List<FormatInfoBean> formatList = oracleDataSet.selectListWithCondition(FormatInfoBean.class, condition, stationName, screenType, screenWidth, screenHeight, screenColor);
        for (FormatInfoBean aFormatList : formatList) {
            String formatID = aFormatList.getFormatID();
            String formatName = aFormatList.getFormatName();
            formatMap.put(formatID, formatName);
        }

        return (HashMap<String, String>) formatMap;
    }

    /**
     * @param formatNo
     * @param deleteFrame 1：删除帧 0：保留帧
     * @return Format is used : 版式使用中，无法删除
     * Can not find this format : 找不到该版式
     * Delete Format failed : 删除版式失败
     * Delete success : 删除版式成功
     * @see "删除版式"
     */
    public String deleteFormat(String formatNo, int deleteFrame) {
        String sql_c = "SELECT CurrentFormatNo FROM ScreenManage";
        List<String> cFormatNoList = oracleDataSet.select(sql_c);
        String sql_s = "SELECT StandbyFormatNo FROM ScreenManage";
        List<String> sFormatNoList = oracleDataSet.select(sql_s);
        String sql_f = "SELECT FasFormatNo FROM ScreenManage";
        List<String> fFormatNoList = oracleDataSet.select(sql_f);

        if (cFormatNoList.contains(formatNo) || sFormatNoList.contains(formatNo) || fFormatNoList.contains(formatNo)) {
            return "Format is used";
        }

        FormatInfoBean fib = oracleDataSet.select(FormatInfoBean.class, "FormatID=?", formatNo);
        if (fib == null) {
            return "Can not find this format";
        }
        DeleteFrameUsedFormat(formatNo);
        if (deleteFrame == 1) {
            List<FrameListInfo> frameListInfos = getFramesInFormat(formatNo);
            if (formatFileManager.delete(fib)) {
                return DeleteFrameInFormat(frameListInfos);
            } else {
                return "Delete Format failed";
            }
        } else {
            if (formatFileManager.delete(fib)) {
                return "Delete success";
            } else {
                return "Delete format failed";
            }
        }
    }

    /**
     * @param formatInfo
     * @param frameList
     * @return The format is too complex : 版式过于复杂
     * Save failed ：存储失败
     * The format ID has existed ：版式编号已存在
     * Save successfully ：存储成功
     * FrameList is empty ：帧列表为空
     * @see "根据帧列表生成并存储版式XML"
     */
    public String saveFormatData(FormatInfo formatInfo, LinkedHashMap<String, String> frameList) {
        if (MapUtil.isEmpty(frameList)) {
            return "FrameList is empty";
        }
        String formatId = formatInfo.getFormatID();
        String formatData = formatXMLGeneration.createFormatByFrameList(formatId, frameList);
        //判断版式XML是否超长
        if (formatData.length() > 60000) {
            return "The format is too complex";
        }
        formatInfo.setFormatData(formatData);
        formatInfo.setVersion(UUID.randomUUID().toString());
        //判断formatId是否冲突
        List<String> formatIdList = getAllFormatId();
        if (formatIdList.contains(formatId)) {
            return "The format ID has existed";
        } else {
            if (!formatFileManager.insert(formatInfo)) {
                return "Save failed";
            }
            addFrameUsedFormat(frameList, formatInfo);
        }
        return "Save successfully";
    }

    /**
     * @param formatId
     * @param frameList
     * @return The format is too complex ：版式过于复杂
     * Format update failed ：版式更新失败
     * This format is not existing ：版式不存在
     * Update successfully ：更新成功
     * @see "版式更新"
     */
    public String updateFormatData(String formatId, LinkedHashMap<String, String> frameList) {
        FormatInfo formatInfo = formatFileManager.select(formatId);
        String formatData = formatXMLGeneration.createFormatByFrameList(formatId, frameList);
        //判断版式XML是否超长
        if (formatData.length() > 60000) {
            return "The format is too complex";
        }
        formatInfo.setFormatData(formatData);
        formatInfo.setVersion(UUID.randomUUID().toString());
        //判断是否包含需更新的formatID，如不包括则更新失败
        List<String> formatIdList = getAllFormatId();
        if (formatIdList.contains(formatId)) {
            if (!formatFileManager.update(formatInfo)) {
                return "Format update failed";
            }
            addFrameUsedFormat(frameList, formatInfo);
        } else {
            return "This format is not existing";
        }
        return "Update successfully";
    }

    /**
     * @param screenId
     * @param formatType 1：当前版式 2：空闲版式 0：火灾版式
     * @param formatNo
     * @return
     * @see "版式绑定"
     */
    public boolean formatBinding(int screenId, int formatType, String formatNo) {
        ScreenManageBean screenManageBean = oracleDataSet.select(ScreenManageBean.class, "screenID=?", screenId);
        if (null == screenManageBean) {
            ScreenManageBean nScreenManageBean = new ScreenManageBean();
            nScreenManageBean.setScreenID(screenId);
            if (formatType == 0) {
                nScreenManageBean.setFasFormatNo(formatNo);
            } else if (formatType == 1) {
                nScreenManageBean.setCurrentFormatNo(formatNo);
            } else {
                nScreenManageBean.setStandbyFormatNo(formatNo);
            }
            return oracleDataSet.insert(nScreenManageBean);
        } else {
            if (formatType == 0) {
                screenManageBean.setFasFormatNo(formatNo);
            } else if (formatType == 1) {
                screenManageBean.setCurrentFormatNo(formatNo);
            } else {
                screenManageBean.setStandbyFormatNo(formatNo);
            }
            return oracleDataSet.update(screenManageBean, "screenID=?", screenId);
        }
    }

    /**
     * @param formatId
     * @return FormatAndFrameListInfo
     * @see "获取版式及版式包含的帧信息列表"
     */
    public FormatAndFrameListInfo getFormatAndFrameListInfo(String formatId) {
        FormatInfo formatInfo = formatFileManager.select(formatId);
        if (null == formatInfo) {
            return null;
        }
        FormatAndFrameListInfo formatAndFrameListInfo = new FormatAndFrameListInfo();
        formatAndFrameListInfo.setFormatInfo(formatInfo);
        formatAndFrameListInfo.setFrameList(getFramesInFormat(formatId));
        return formatAndFrameListInfo;
    }

    /**
     * @param formatData
     * @return
     * @see "根据formatData获取版式中的帧列表"
     */
    public List<FrameListInfo> getFrameListFromFormatData(String formatData) {
        Document formatDoc = DocumentUtil.stringToDocument(formatData);
        Element root = formatDoc.getRootElement();
        Element tm = root.getChild("TM");
        String tmValue = tm.getValue();
        List<String> durationList = StringUtil.stringToList(tmValue, ",");//帧显示时间List
        List<String> frameNameList = new ArrayList<String>();//帧名称List
        List frameElementList = root.getChildren("FR");
        for (Object aFrameElementList : frameElementList) {
            Element fr = (Element) aFrameElementList;
            frameNameList.add(fr.getChildText("Duriation"));
        }

        List<FrameListInfo> FrameInfoList = new ArrayList<FrameListInfo>();
        for (int i = 0; i < frameNameList.size(); i++) {
            FrameListInfo fi = new FrameListInfo();
            String frameName = frameNameList.get(i);
            fi.setFrameName(frameName);
            String showName = oracleDataSet.select(FrameInfoBean.class, "FrameName=?", frameName).getShowName();
            if (showName != null) {
                fi.setShowName(showName);
            } else {
                fi.setShowName("--");
            }
            fi.setFrameDuration(durationList.get(i));
            FrameInfoList.add(fi);
        }

        return FrameInfoList;
    }

    /**
     * @param screenId
     * @param formatType 1：当前版式 2：空闲版式 0：火灾版式
     * @return
     * @see "获取绑定版式"
     */
    public FormatAndFrameListInfo getBoundFormat(int screenId, int formatType) {
        ScreenManageBean smBean = oracleDataSet.select(ScreenManageBean.class, "screenID=?", screenId);
        if (null == smBean) {
            return null;
        }
        String formatId = "";
        if (formatType == 0) {
            formatId = smBean.getFasFormatNo();
        } else if (formatType == 1) {
            formatId = smBean.getCurrentFormatNo();
        } else {
            formatId = smBean.getStandbyFormatNo();
        }
        if (StringUtil.isEmpty(formatId)) {
            return null;
        }
        return getFormatAndFrameListInfo(formatId);
    }

    /**
     * @param stationName
     * @param formatData
     * @return FormatID has existed : 版式ID冲突
     * The format is too complex ：版式过于复杂
     * Fail to import format ：版式导入失败
     * Fail to import format's frames ：版式中的帧导入失败
     * Fail to import Datatable in format ：动态表格格式入库失败
     * Import successfully ：导入成功
     * @see "导入版式"
     */
    public String importFormatData(String stationName, String formatData) {
        Document formatDataDoc = DocumentUtil.stringToDocument(formatData);
        Element formatElement = formatDataDoc.getRootElement();
        String formatId = formatElement.getChildText("ID");
        //判断版式ID是否重复
        List<String> formatIdList = getAllFormatId();
        if (formatIdList.contains(formatId)) {
            return "FormatID has existed";
        }
        //判断版式XML是否过长
        Element ly = formatElement.getChild("LY");
        Element newLY = new Element("LY");
        newLY.addContent(ly.cloneContent());
        Document lyDoc = new Document(newLY);
        String lyString = DocumentUtil.documentToString(lyDoc);
        if (lyString.length() > 60000) {
            return "The format is too complex";
        }
        //版式XML存储
        FormatInfo formatInfo = new FormatInfo();
        formatElement.removeChild("LY");
        Element newFormatElement = new Element("Format");
        newFormatElement.addContent(formatElement.cloneContent());
        Document formatDoc = new Document(newFormatElement);
        String formatInfoString = DocumentUtil.documentToString(formatDoc);
        Format format = JaxbUtil.converyToJavaBean(formatInfoString, Format.class);
        formatInfo.setFormatID(format.getID());
        formatInfo.setFormatName(format.getName());
        formatInfo.setFormatData(lyString);
        formatInfo.setStationName(stationName);
        formatInfo.setScreenType(format.getScreenType());
        formatInfo.setScreenWidth(format.getScreenWidth());
        formatInfo.setScreenHeight(format.getScreenHeight());
        formatInfo.setScreenColor(format.getScreenColor());
        formatInfo.setVersion(UUID.randomUUID().toString());
        if (!formatFileManager.insert(formatInfo)) {
            return "Fail to import format";
        }
        //版式中的帧存储
        List<Element> FRList = ly.getChildren("FR");
        for (int i = 0; i < FRList.size(); i++) {
            String frameName = FRList.get(i).getChildText("Duriation");
            Element FRCopy = new Element("FR");
            FRCopy.addContent(FRList.get(i).cloneContent());
            Document FRDoc = new Document(FRCopy);
            String frameData = DocumentUtil.documentToString(FRDoc);
            FrameInfo frameInfo = new FrameInfo();
            frameInfo.setFrameName(frameName);
            frameInfo.setStationName(stationName);
            frameInfo.setScreenType(format.getScreenType());
            frameInfo.setScreenWidth(format.getScreenWidth());
            frameInfo.setScreenHeight(format.getScreenHeight());
            frameInfo.setScreenColor(format.getScreenColor());
            frameInfo.setFrameData(frameData);
            frameInfo.setUsedFormat(formatId);
            if (!frameFileManager.insert(frameInfo)) {
                return "Fail to import format's frames";
            }
        }
        //动态表格格式入库
        List<DTVar> dtVarList = format.getDtVarList();
        if (null == dtVarList) {
            return "Import successfully";
        }
        for (int i = 0; i < dtVarList.size(); i++) {
            DataTableRowVarBean dtrvBean = new DataTableRowVarBean();
            dtrvBean.setFrameName(dtVarList.get(i).getFrameName());
            List<DE> deList = dtVarList.get(i).getDE();
            for (int j = 0; j < deList.size(); j++) {
                dtrvBean.setElementID(deList.get(j).getID());
                dtrvBean.setLang(deList.get(j).getLang());
                dtrvBean.setDataSource(deList.get(j).getSource());
                List<COL> colList = deList.get(j).getCol();
                for (int k = 0; k < colList.size(); k++) {
                    dtrvBean.setColID(colList.get(k).getID());
                    dtrvBean.setVariateName(colList.get(k).getVarName());
                    if (!oracleDataSet.insert(dtrvBean)) {
                        return "Fail to import Datatable in format";
                    }
                }
            }
        }

        return "Import successfully";
    }

    /**
     * @param formatId 版式ID
     * @return
     * @see "导出版式"
     */
    public String exportFormatData(String formatId) {
        //生成版式基本信息及动态表格数据信息
        Format format = new Format();
        FormatInfo formatInfo = formatFileManager.select(formatId);
        format.setID(formatId);
        format.setName(formatInfo.getFormatName());
        format.setScreenType(formatInfo.getScreenType());
        format.setScreenWidth(formatInfo.getScreenWidth());
        format.setScreenHeight(formatInfo.getScreenHeight());
        format.setScreenColor(formatInfo.getScreenColor());
        List<DTVar> dtVarList = getDTVarList(formatInfo);
        format.setDtVarList(dtVarList);
        String dataFormatXml;
        try {
            dataFormatXml = JaxbUtil.toXml(format, Format.class);
        } catch (JAXBException e) {
            logger.error("Generate dataFormatXml error", e);
            return "";
        }
        //在版式XML中添加版式内容
        Document dataformatDoc = DocumentUtil.stringToDocument(dataFormatXml);
        Document lyDoc = DocumentUtil.stringToDocument(formatInfo.getFormatData());
        Element lyElement = lyDoc.getRootElement();
        Element formatElement = dataformatDoc.getRootElement();
        Element newLyElement = new Element("LY");
        newLyElement.addContent(lyElement.cloneContent());
        formatElement.addContent(newLyElement);
        Element newFormatElement = new Element("Format");
        newFormatElement.addContent(formatElement.cloneContent());
        Document formatDoc = new Document(newFormatElement);
        return DocumentUtil.documentToString(formatDoc);
    }

    /**
     * @param stationName
     * @return Format IDs are Used up.Please deletion some formats ：FormatID已分配完
     * 版式编号格式为“站码_XXXX”
     * 其中XXXX的取值范围为0~1999，当此范围内所有ID均分配完毕，返回ID用尽提示信息
     * @see "生成版式编号"
     */
    public String getFormatId(String stationName) {
        String formatId = "";
        List<String> existIdList = oracleDataSet.selectColumnList(FormatInfoBean.class, "FormatID", "StationName=?", "FormatID", stationName);
        //版式二级编号大于1998时重置为0并搜索0~1998中的可用编号
        if (FormatSubID > 1998) {
            FormatSubID = 0;
        }
        for (int i = FormatSubID + 1; i < 2000; i++) {
            String stationCode = configManager.getStationCodeByName(stationName);
            String aFormatId = stationCode + "_" + i;
            if (existIdList.contains(aFormatId)) {
                FormatSubID = i;
                continue;
            } else {
                formatId = aFormatId;
                FormatSubID = i;
                break;
            }
        }
        //版式编号已用尽，返回提示信息
        if (StringUtil.isEmpty(formatId)) {
            return "Format IDs are Used up.Please deletion some formats";
        }
        return formatId;
    }

    /**
     * @param formatInfo
     * @return
     * @see "获取动态表格格式"
     */
    private List<DTVar> getDTVarList(FormatInfo formatInfo) {
        List<DTVar> dtVarList = new ArrayList<>();
        String formatData = formatInfo.getFormatData();
        List<FrameListInfo> frameList = getFramesInFormat(formatInfo.getFormatID());
        for (int i = 0; i < frameList.size(); i++) {
            //生成各帧表格信息列表
            String frameName = frameList.get(i).getFrameName();
            List<DataTableRowVarBean> dataTableRowVars = oracleDataSet.selectListWithCondition(DataTableRowVarBean.class, "FrameName=?", frameName);
            if (ListUtil.isEmpty(dataTableRowVars)) {
                continue;
            }
            DTVar dtVar = new DTVar();
            dtVar.setFrameName(frameName);
            //生成各元素表格信息列表
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
            dtVarList.add(dtVar);
        }
        return dtVarList;
    }

    //删除帧的版式使用标记
    private void DeleteFrameUsedFormat(String formatNo) {
        List<FrameListInfo> frameList = getFramesInFormat(formatNo);
        if (null == frameList) {
            return;
        }
        for (int i = 0; i < frameList.size(); i++) {
            String frameName = frameList.get(i).getFrameName();
            FrameInfoBean frameInfoBean = oracleDataSet.select(FrameInfoBean.class, "FrameName=?", frameName);
            String usedFormat = frameInfoBean.getUsedFormat();
            List<String> usedFormatList = StringUtil.stringToList(usedFormat, ",");
            for (int j = 0; j < usedFormatList.size(); j++) {
                if (usedFormatList.get(j).equals(formatNo)) {
                    usedFormatList.remove(j);
                }
            }
            usedFormat = ListUtil.listToStringByPunctuation(usedFormatList, ",");
            frameInfoBean.setUsedFormat(usedFormat);
            oracleDataSet.update(frameInfoBean, "FrameName=?", frameName);
        }
    }

    //删除版式中包含的帧
    private String DeleteFrameInFormat(List<FrameListInfo> frameList) {
        List<String> deleteFailedFrameList = new ArrayList<>();
        int frameListSize = frameList.size();
        //依次删除版式中的帧，并将删除失败帧名称加入deleteFailedFrameList
        for (int i = 0; i < frameListSize; i++) {
            String frameName = frameList.get(i).getFrameName();
            if (!frameManager.deleteFrame(frameName)) {
                deleteFailedFrameList.add(frameName);
            }
        }

        if (ListUtil.isEmpty(deleteFailedFrameList)) {
            return "Delete Success";
        } else {
            String result = "";
            for (int j = 0; j < deleteFailedFrameList.size(); j++) {
                result = result + deleteFailedFrameList.get(j) + " ";
            }
            result = "Frame " + result + "deletion failed. Check if they are used by other format";
            return result;
        }
    }

    //获取版式中的帧列表
    private List<FrameListInfo> getFramesInFormat(String formatNo) {
        FormatInfo fi = formatFileManager.select(formatNo);
        if (null == fi) {
            return null;
        }
        String formatData = fi.getFormatData();
        return getFrameListFromFormatData(formatData);
    }

    //获取所有版式ID
    private List<String> getAllFormatId() {
        List<String> formatIdList;
        String sql = "SELECT FormatID FROM FormatInfo";
        formatIdList = oracleDataSet.select(sql);
        return formatIdList;
    }

    //向帧信息中添加版式使用标记
    private void addFrameUsedFormat(LinkedHashMap<String, String> frameList, FormatInfo formatInfo) {
        String stationName = formatInfo.getStationName();
        String screenType = formatInfo.getScreenType();
        String screenWidth = formatInfo.getScreenWidth();
        String screenHeight = formatInfo.getScreenHeight();
        String screenColor = formatInfo.getScreenColor();
        String formatID = formatInfo.getFormatID();

        //删除所有帧中该版式的使用标记
        List<FrameInfoBean> frameListAll = oracleDataSet.selectListWithCondition(FrameInfoBean.class,
                "stationName=? AND ScreenType=? AND ScreenWidth=? AND ScreenHeight=? And ScreenColor=?",
                stationName, screenType, screenWidth, screenHeight, screenColor);
        for (int i = 0; i < frameListAll.size(); i++) {
            FrameInfoBean frame = frameListAll.get(i);
            String fraName = frame.getFrameName();
            String usedFormat = frame.getUsedFormat();
            List<String> usedFormatList = StringUtil.stringToList(usedFormat, ",");
            for (int j = 0; j < usedFormatList.size(); j++) {
                if (usedFormatList.get(j).equals(formatID)) {
                    usedFormatList.remove(j);
                }
            }
            usedFormat = ListUtil.listToStringByPunctuation(usedFormatList, ",");
            frame.setUsedFormat(usedFormat);
            oracleDataSet.update(frame, "FrameName=?", fraName);
        }

        //在版式中的所有帧信息中添加版式使用标记
        for (Map.Entry<String, String> entry : frameList.entrySet()) {
            String frameName = entry.getKey();
            String usedFormat = oracleDataSet.selectColumn(FrameInfoBean.class, "UsedFormat", "FrameName=?", frameName);
            List<String> usedFormatList = StringUtil.stringToList(usedFormat, ",");
            if (usedFormatList.contains(formatID)) {
                continue;
            } else {
                usedFormatList.add(formatID);
            }
            usedFormat = ListUtil.listToStringByPunctuation(usedFormatList, ",");
            FrameInfoBean frameInfoBean = oracleDataSet.select(FrameInfoBean.class, "FrameName=?", frameName);
            frameInfoBean.setUsedFormat(usedFormat);
            oracleDataSet.update(frameInfoBean, "FrameName=?", frameName);
        }
    }
}
