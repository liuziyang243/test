package com.crscd.passengerservice.display.format.business;

import com.crscd.passengerservice.display.format.domainobject.FormatInfo;
import com.crscd.passengerservice.display.format.domainobject.FrameInfo;
import com.crscd.passengerservice.display.format.po.FormatInfoBean;
import com.crscd.passengerservice.display.format.po.FrameInfoBean;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class InfoToBean {

    public FrameInfoBean frameInfoToBean(FrameInfo frameInfo, String storagePath) {
        FrameInfoBean bean = new FrameInfoBean();
        bean.setFrameName(frameInfo.getFrameName());
        bean.setShowName(frameInfo.getShowName());
        bean.setStationName(frameInfo.getStationName());
        bean.setScreenType(frameInfo.getScreenType());
        bean.setScreenWidth(frameInfo.getScreenWidth());
        bean.setScreenHeight(frameInfo.getScreenHeight());
        bean.setScreenColor(frameInfo.getScreenColor());
        bean.setUsedFormat(frameInfo.getUsedFormat());
        bean.setStoragePath(storagePath);

        return bean;
    }

    public FrameInfo beanToFrameInfo(FrameInfoBean frameInfoBean, String frameData) {
        FrameInfo frameInfo = new FrameInfo();
        frameInfo.setFrameName(frameInfoBean.getFrameName());
        frameInfo.setShowName(frameInfoBean.getShowName());
        frameInfo.setStationName(frameInfoBean.getStationName());
        frameInfo.setScreenType(frameInfoBean.getScreenType());
        frameInfo.setScreenWidth(frameInfoBean.getScreenWidth());
        frameInfo.setScreenHeight(frameInfoBean.getScreenHeight());
        frameInfo.setScreenColor(frameInfoBean.getScreenColor());
        frameInfo.setUsedFormat(frameInfoBean.getUsedFormat());
        frameInfo.setFrameData(frameData);

        return frameInfo;
    }

    public FormatInfoBean formatInfoToBean(FormatInfo formatInfo, String storagePath) {
        FormatInfoBean bean = new FormatInfoBean();
        bean.setFormatID(formatInfo.getFormatID());
        bean.setStoragePath(storagePath);
        bean.setScreenType(formatInfo.getScreenType());
        bean.setStationName(formatInfo.getStationName());
        bean.setScreenWidth(formatInfo.getScreenWidth());
        bean.setScreenHeight(formatInfo.getScreenHeight());
        bean.setScreenColor(formatInfo.getScreenColor());
        bean.setFormatName(formatInfo.getFormatName());
        bean.setFormatID(formatInfo.getFormatID());
        bean.setVersion(formatInfo.getVersion());

        return bean;
    }

    public FormatInfo beanToFormatInfo(FormatInfoBean fib, String formatDate) {
        FormatInfo formatInfo = new FormatInfo();
        formatInfo.setFormatID(fib.getFormatID());
        formatInfo.setFormatData(formatDate);
        formatInfo.setScreenType(fib.getScreenType());
        formatInfo.setStationName(fib.getStationName());
        formatInfo.setScreenWidth(fib.getScreenWidth());
        formatInfo.setScreenHeight(fib.getScreenHeight());
        formatInfo.setScreenColor(fib.getScreenColor());
        formatInfo.setFormatName(fib.getFormatName());
        formatInfo.setFormatID(fib.getFormatID());
        formatInfo.setVersion(fib.getVersion());

        return formatInfo;
    }
}
