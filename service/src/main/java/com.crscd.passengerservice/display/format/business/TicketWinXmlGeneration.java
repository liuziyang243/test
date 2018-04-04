package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.DataUtil;
import com.crscd.framework.util.text.DocumentUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.config.domainobject.ScreenConfig;
import com.crscd.passengerservice.display.format.domainobject.TicketWinScreenContent;
import com.crscd.passengerservice.display.format.po.DefaultFormatInfoBean;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.List;

/**
 * Created by cuishiqing on 2017/9/7.
 */
public class TicketWinXmlGeneration {
    //数据库
    private DataSet oracleDataSet;

    public TicketWinXmlGeneration() {
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    /**
     * 售票窗口屏版式XML生成
     *
     * @param tsc
     * @param type
     * @return
     */
    public String TicketWinScreenFormatGeneration(TicketWinScreenContent tsc, String type) {
        String formatXml;
        int screenID = tsc.getScreenID();
        ScreenConfig screenBean = oracleDataSet.select(ScreenConfig.class, "ScreenID=?", screenID);
        if (screenBean == null) {
            return null;
        }
        int screenWidth = screenBean.getScreenWidth();
        int screenHeight = screenBean.getScreenHeight();
        DefaultFormatInfoBean dfi = new DefaultFormatInfoBean();

        switch (type) {
            case "Notice":
                dfi = oracleDataSet.select(DefaultFormatInfoBean.class, "ScreenType=? AND ScreenWidth=? AND ScreenHight=? AND FormatName=?",
                        "TicketWindowScreen", screenWidth, screenHeight, "" + tsc.getWinNum().length());
                formatXml = FormatXmlMaker(dfi.getFormatData(), tsc, "Notice");
                break;
            case "FAS":
                dfi = oracleDataSet.select(DefaultFormatInfoBean.class, "ScreenType=? AND ScreenWidth=? AND ScreenHight=? AND FormatName=?",
                        "TicketWindowScreen", screenWidth, screenHeight, "FAS");
                formatXml = FormatXmlMaker(dfi.getFormatData(), tsc, "FAS");
                break;
            default:
                formatXml = "";
                break;
        }
        return formatXml;
    }

    /**
     * 修改默认版式XML
     *
     * @param defaultXml
     * @param tsc
     * @param type
     * @return
     */
    private String FormatXmlMaker(String defaultXml, TicketWinScreenContent tsc, String type) {
        if (StringUtil.isEmpty(defaultXml)) {
            return null;
        }
        Document formatDoc = DocumentUtil.stringToDocument(defaultXml);
        Element root = formatDoc.getRootElement();
        Element LN = root.getChild("LN");
        LN.setText("0000");
        Element VN = root.getChild("VN");
        VN.setText("" + tsc.getScreenID());
        Element FR = root.getChild("FR");
        Element Duriation = FR.getChild("Duriation");
        Duriation.setText("0000" + tsc.getScreenID());
        if ("Notice".equals(type)) {
            @SuppressWarnings("unchecked")
            List<Element> SW = FR.getChildren("SW");
            for (int i = 0; i < SW.size(); i++) {
                Element ID = SW.get(i).getChild("ID");
                ID.setText("0000" + tsc.getScreenID() + i);
                Element T = SW.get(i).getChild("T");
                if (i == 0) {
                    T.setText(tsc.getWinNum());
                } else if (i == 1) {
                    T.setText(tsc.getContent());
                }
            }
        }

        return DataUtil.documentToString(formatDoc);
    }

}
