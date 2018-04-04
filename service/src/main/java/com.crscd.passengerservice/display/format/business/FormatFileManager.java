package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.io.FileUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.display.format.domainobject.FormatInfo;
import com.crscd.passengerservice.display.format.po.FormatInfoBean;

/**
 * Created by cuishiqing on 2017/8/31.
 */
public class FormatFileManager {
    //版式存储路径
    private static String path = System.getProperty("user.dir") + "\\Format\\";
    //数据库
    private DataSet oracleDataSet;

    private InfoToBean infoToBean;

    public void setInfoToBean(InfoToBean infoToBean) {
        this.infoToBean = infoToBean;
    }

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public boolean insert(FormatInfo format) {
        //以文件形式存储版式
        String fPath = path + format.getFormatID() + ".txt";
        if (!FileUtil.writeFile(fPath, format.getFormatData())) {
            return false;
        }
        //版式参数存入FormatInfo表
        FormatInfoBean bean = infoToBean.formatInfoToBean(format, fPath);
        return oracleDataSet.insert(bean);
    }

    public boolean update(FormatInfo format) {
        String fPath = path + format.getFormatID() + ".txt";
        FileUtil.deleteSingleFile(fPath);
        boolean updateFile = FileUtil.writeFile(fPath, format.getFormatData());
        FormatInfoBean bean = infoToBean.formatInfoToBean(format, fPath);
        return updateFile && oracleDataSet.update(bean, "FormatID=?", bean.getFormatID());
    }

    public boolean delete(FormatInfoBean format) {
        String fPath = format.getStoragePath();
        return FileUtil.deleteSingleFile(fPath) && oracleDataSet.delete(FormatInfoBean.class, "FormatID=?", format.getFormatID());
    }

    public FormatInfo select(String formatID) {
        FormatInfoBean fib = oracleDataSet.select(FormatInfoBean.class, "FormatID=?", formatID);
        if (fib == null) {
            return null;
        }
        String formatPath = fib.getStoragePath();
        if (StringUtil.isEmpty(FileUtil.readFile(formatPath))) {
            return null;
        }
        return infoToBean.beanToFormatInfo(fib, FileUtil.readFile(formatPath));
    }

}
