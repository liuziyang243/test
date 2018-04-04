package com.crscd.passengerservice.display.format.business;


import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.translation.serviceinterface.TranslatorInterface;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.display.format.domainobject.DataXMLBean.DC;
import com.crscd.passengerservice.display.format.domainobject.DataXMLBean.LY;
import com.crscd.passengerservice.display.format.domainobject.DataXMLBean.NewDataSet;
import com.crscd.passengerservice.display.format.domainobject.FrameListInfo;
import com.crscd.passengerservice.display.format.po.DataTableRowVarBean;
import com.crscd.passengerservice.display.format.util.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/9/7.
 */
public class DataXMLGeneration {
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(FrameXMLGeneration.class);
    //数据库
    private DataSet oracleDataSet;
    //翻译接口
    private TranslatorInterface translatorInterface;

    private FormatManager formatManager;

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setFormatManager(FormatManager formatManager) {
        this.formatManager = formatManager;
    }

    /**
     * 生成版式实时数据（计划类及客票）
     *
     * @param formatID
     * @param formatData
     * @param screenID
     * @param tableData
     * @return
     */
    public String DataXmlGeneration(String formatID, String formatData, int screenID, List<Map<String, String>> tableData) {
        List<String> lnVn = StringUtil.stringToList(formatID, "_");
        String ln = lnVn.get(0);
        String vn = lnVn.get(1);
        NewDataSet newDataset = new NewDataSet();
        LY ly = new LY();
        ly.setLN(ln);
        ly.setVN(vn);
        /*获取版式中的帧列表*/
        List<FrameListInfo> frameList = formatManager.getFrameListFromFormatData(formatData);
        /*获取版式中所有动态表格元素在版式中的ID*/
        List<String> dtElementList = new ArrayList<>();
        for (FrameListInfo frame : frameList) {
            String frameName = frame.getFrameName();
            List<DataTableRowVarBean> dtrvList = oracleDataSet.selectListWithCondition(
                    DataTableRowVarBean.class, "FrameName=?", frameName);
            if (dtrvList == null || dtrvList.size() == 0) {
                continue;
            }
            for (DataTableRowVarBean dtrv : dtrvList) {
                dtElementList.add(dtrv.getElementID());
            }
        }

        //对版式中没有动态表格的默认处理
        if (dtElementList.size() == 0 || dtElementList == null) {
            DC dc = new DC();
            dc.setID("1");
            List<String> row = new ArrayList<>();
            row.add("LN1=null");
            dc.setRow(row);
            List<DC> dcList = new ArrayList<>();
            dcList.add(dc);
            ly.setDC(dcList);
            newDataset.setLy(ly);
            try {
                return JaxbUtil.toXml(newDataset, NewDataSet.class);
            } catch (JAXBException e) {
                logger.error("Generation XML error", e);
                return "";
            }
        }

        dtElementList = new ArrayList(new HashSet(dtElementList));
        //生成动态表格元素的数据
        List<DC> dcList = new ArrayList<>();
        for (String dtElementID : dtElementList) {
            List<String> colIDList = oracleDataSet.selectColumnList(
                    DataTableRowVarBean.class, "ColID", "ElementID=?", "ColID", dtElementID);
            colIDList = ListUtil.removeDuplicateWithOrder(colIDList);
            DC dc = dcGeneration(dtElementID, colIDList, screenID, tableData);
            dcList.add(dc);
        }
        ly.setDC(dcList);

        newDataset.setLy(ly);
        try {
            return JaxbUtil.toXml(newDataset, NewDataSet.class);
        } catch (JAXBException e) {
            logger.error("Generation XML error", e);
            return "";
        }
    }

    /**
     * 售票窗口屏数据XML生成
     *
     * @param formatID
     * @return
     */
    public String TicketWinScreenDataXmlGeneration(String formatID) {
        String dataXML;

        List<String> lnVn = StringUtil.stringToList(formatID, "_");
        String ln = lnVn.get(0);
        String vn = lnVn.get(1);
        NewDataSet newDataset = new NewDataSet();
        LY ly = new LY();
        DC dc = new DC();
        dc.setID("1");
        List<String> row = new ArrayList<>();
        row.add("LN1=null");
        dc.setRow(row);
        List<DC> dcList = new ArrayList<>();
        dcList.add(dc);
        ly.setLN(ln);
        ly.setVN(vn);
        ly.setDC(dcList);
        newDataset.setLy(ly);

        try {
            dataXML = JaxbUtil.toXml(newDataset, NewDataSet.class);
        } catch (JAXBException e) {
            return "";
        }
        return dataXML;
    }

    /**
     * 生成DC节点
     *
     * @param dtElementID
     * @param colIDs
     * @param screenID
     * @param tableData
     * @return
     */
    private DC dcGeneration(String dtElementID, List<String> colIDs, int screenID, List<Map<String, String>> tableData) {
        DC dc = new DC();
        //生成row
        List<String> row = new ArrayList<>();
        for (Map<String, String> t : tableData) {
            String rowString = rowGeneration(t, colIDs, dtElementID);
            if (rowString == null) {
                continue;
            }
            row.add(rowString);
        }
        dc.setRow(row);
        dc.setID(dtElementID);
        return dc;
    }

    private String rowGeneration(Map<String, String> planDataMap, List<String> colIDs, String dtElementID) {
        if (ListUtil.isEmpty(colIDs) || MapUtil.isEmpty(planDataMap)) {
            return null;
        }
        String ci = colIDs.get(0);
        DataTableRowVarBean dtrv = oracleDataSet.select(DataTableRowVarBean.class, "ElementID=? AND ColID=?", dtElementID, ci);
        //语言类型
        String lan = dtrv.getLang();
        //对列编号进行从小到大排序
        colIDs = ListUtil.listStringIntOrder(colIDs);
        List<String> lns = new ArrayList<>();
        for (int i = 0; i < colIDs.size(); i++) {
            String colID = colIDs.get(i);
            DataTableRowVarBean rowVarBeanBean = oracleDataSet.select(DataTableRowVarBean.class, "ElementID=? AND ColID=?", dtElementID, colID);
            String variateName = rowVarBeanBean.getVariateName();
            //本列对应值
            String value = planDataMap.get(variateName);
            if (StringUtil.isEmpty(value)) {
                value = "--";
            }

            if (!"--".equals(value)) {
                if ("actualDepartureTime".equals(variateName) || "actualArriveTime".equals(variateName)) {
                    if (!DateTimeFormatterUtil.isDateTimeStringValidFormat(value, FrameworkConstant.TIME_NOSECOND_PATTERN)) {
                        value = DateTimeFormatterUtil.getTimeWithoutSecond(value);
                    }
                }
            }

            //语言翻译
            if (!"English".equals(lan)) {
                value = translatorInterface.makeTranslation(value, lan);
            }

            String ln = "LN" + colID + "=" + value;
            lns.add(ln);
        }
        String rowString = ListUtil.listToStringByPunctuation(lns, ",");
        return rowString;
    }
}
