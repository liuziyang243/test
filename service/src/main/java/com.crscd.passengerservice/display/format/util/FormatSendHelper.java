package com.crscd.passengerservice.display.format.util;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.display.format.business.FormatFileManager;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;
import com.crscd.passengerservice.display.format.po.ScreenManageBean;
import com.crscd.passengerservice.display.screencontrolserver.business.FormatSend;

/**
 * Created by cuishiqing on 2017/9/7.
 */
public class FormatSendHelper {
    private DataSet oracleDataSet;
    private FormatFileManager formatFileManager;
    private FormatSend formatSend;

    /**
     * 是否需要下发版式内容的判断
     * 若最新版式编号及版本号与已下发的版式一致，且该版式已下发过，则返回true
     *
     * @param screenManageBean
     * @param formatInfo
     * @return
     */
    private static boolean formatSentJudgement(ScreenManageBean screenManageBean, FormatInfo formatInfo) {
        if (null == screenManageBean) {
            return false;
        }

        String formatId_s = screenManageBean.getUsedFormat();
        String version_s = screenManageBean.getVersion();
        String sendStatus_s = screenManageBean.getFormatSendStatus();
        String formatId_f = formatInfo.getFormatID();
        String version_f = formatInfo.getVersion();

        return formatId_s.equals(formatId_f) && version_s.equals(version_f) && "1".equals(sendStatus_s);
    }

    public void setFormatSend(FormatSend formatSend) {
        this.formatSend = formatSend;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setFormatFileManager(FormatFileManager formatFileManager) {
        this.formatFileManager = formatFileManager;
    }

    /**
     * 版式下发
     *
     * @param formatID
     * @param format
     * @param screenID
     * @param data
     * @return
     */
    public boolean formatSender(String formatID, String format, int screenID, String data) {
        ScreenManageBean screenManageBean = oracleDataSet.select(ScreenManageBean.class, "screenID = ?", screenID);
        FormatInfo formatInfo = formatFileManager.select(formatID);
        if (formatSentJudgement(screenManageBean, formatInfo)) {
            format = "--";
        }
        boolean result = formatSend.formatSendSingle(formatID, format, screenID, data);
        if (result) {
            screenManageBean.setCurrentFormatNo(formatID);
            screenManageBean.setVersion(formatInfo.getVersion());
            screenManageBean.setFormatSendStatus("1");
            oracleDataSet.update(screenManageBean);
        }
        return result;
    }
}
