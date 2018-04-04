package com.crscd.passengerservice.display.format.business;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.io.FileUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.display.format.domainobject.FrameInfo;
import com.crscd.passengerservice.display.format.po.FrameInfoBean;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class FrameFileManager {
    //帧存储路径
    private static String path = System.getProperty("user.dir") + "\\Frame\\";
    //数据库
    private DataSet oracleDataSet;

    private InfoToBean infoToBean;

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    public void setInfoToBean(InfoToBean infoToBean) {
        this.infoToBean = infoToBean;
    }

    public boolean insert(FrameInfo frame) {
        //以文件形式存储版式
        String fPath = path + frame.getFrameName() + ".txt";
        if (!FileUtil.writeFile(fPath, frame.getFrameData())) {
            return false;
        }
        //版式参数存入FormatInfo表
        FrameInfoBean frameInfoBean = infoToBean.frameInfoToBean(frame, fPath);
        return oracleDataSet.insert(frameInfoBean);
    }

    /*
     * 注意入参中frame中的frameData为实际帧内容
     */
    public boolean update(FrameInfo frame) {
        String fPath = path + frame.getFrameName() + ".txt";
        FileUtil.deleteSingleFile(fPath);
        boolean updateFile = FileUtil.writeFile(fPath, frame.getFrameData());
        FrameInfoBean frameInfoBean = infoToBean.frameInfoToBean(frame, fPath);
        return updateFile && oracleDataSet.update(frameInfoBean, "FrameName=?", frame.getFrameName());
    }

    public boolean delete(FrameInfoBean frame) {
        String fPath = frame.getStoragePath();
        return FileUtil.deleteSingleFile(fPath) && oracleDataSet.delete(FrameInfoBean.class, "FrameName=?", frame.getFrameName());
    }

    public FrameInfo select(String frameName) {
        FrameInfoBean fib = oracleDataSet.select(FrameInfoBean.class, "FrameName=?", frameName);
        if (fib == null) {
            return null;
        }
        String framePath = fib.getStoragePath();
        if (StringUtil.isEmpty(FileUtil.readFile(framePath))) {
            return null;
        }
        return infoToBean.beanToFrameInfo(fib, FileUtil.readFile(framePath));
    }
}
