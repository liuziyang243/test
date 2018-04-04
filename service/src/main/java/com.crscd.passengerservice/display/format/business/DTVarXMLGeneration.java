package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.COL;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DE;
import com.crscd.passengerservice.display.format.domainobject.DTVarXMLBean.DTVar;
import com.crscd.passengerservice.display.format.po.DataTableRowVarBean;
import com.crscd.passengerservice.display.format.util.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class DTVarXMLGeneration {
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(DTVarXMLGeneration.class);
    //数据库
    private DataSet oracleDataSet;

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public String DTVarStringGeneration(String frameName) {
        String dtVarXML = "";
        DTVar dtVar = DTVarGeneration(frameName);

        try {
            dtVarXML = JaxbUtil.toXml(dtVar, DTVar.class);
        } catch (Exception e) {
            logger.error("Bean to Xml error", e);
            return "";
        }

        return dtVarXML;
    }

    public DTVar DTVarGeneration(String frameName) {
        Map<String, Map<String, String>> elementMap = DTMapGeneration(frameName);
        if (elementMap == null) {
            return null;
        }
        List<DE> deList = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : elementMap.entrySet()) {
            List<COL> colList = new ArrayList<>();
            for (Map.Entry<String, String> entry1 : entry.getValue().entrySet()) {
                COL col = new COL();
                col.setID(entry1.getKey());
                col.setVarName(entry1.getValue());
                colList.add(col);
            }
            String lang = oracleDataSet.selectColumn(DataTableRowVarBean.class, "Lang", "FrameName=? AND ElementID=?", frameName, entry.getKey());
            String source = oracleDataSet.selectColumn(DataTableRowVarBean.class, "DataSource", "FrameName=? AND ElementID=?", frameName, entry.getKey());
            DE de = new DE();
            de.setID(entry.getKey());
            de.setSource(source);
            de.setLang(lang);
            de.setCol(colList);
            deList.add(de);
        }

        DTVar dtVar = new DTVar();
        dtVar.setFrameName(frameName);
        dtVar.setDE(deList);

        return dtVar;
    }

    // 生成动态数据表格
    private Map<String, Map<String, String>> DTMapGeneration(String frameName) {
        /*指定帧的所有列变量列表*/
        List<DataTableRowVarBean> dataTableRowVarList = oracleDataSet.selectListWithCondition(DataTableRowVarBean.class, "FrameName=?", frameName);
        if (dataTableRowVarList == null || dataTableRowVarList.size() == 0) {
            return null;
        }

        /*动态表格元素编码列表*/
        List<String> elementList = new ArrayList<String>();
        for (int i = 0; i < dataTableRowVarList.size(); i++) {
            if (i == 0) {
                elementList.add(dataTableRowVarList.get(i).getElementID());
                continue;
            }
            if (!elementList.contains(dataTableRowVarList.get(i).getElementID())) {
                elementList.add(dataTableRowVarList.get(i).getElementID());
            }
        }

        /*元素对应列名称Map*/
        Map<String, Map<String, String>> elementColVariateMap = new HashMap<String, Map<String, String>>();
        for (String elementID : elementList) {
            Map<String, String> colMap = new HashMap<String, String>();
            for (DataTableRowVarBean aDataTableRowVarList : dataTableRowVarList) {
                if (aDataTableRowVarList.getElementID().equals(elementID)) {
                    String key = aDataTableRowVarList.getColID();
                    String value = aDataTableRowVarList.getVariateName();
                    colMap.put(key, value);
                }
            }
            elementColVariateMap.put(elementID, colMap);
        }

        return elementColVariateMap;
    }

}
