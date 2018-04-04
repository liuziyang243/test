package com.crscd.passengerservice.multimedia.util;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.util.text.FtpUtil;
import com.crscd.passengerservice.app.ServiceConstant;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-03-12 上午10:21
 */
public class FtpHelper {

    public static FtpUtil getFtpUtil() {
        String ftpIP = ConfigHelper.getString(ServiceConstant.FTP_ADDRESS);
        int port = Integer.parseInt(ConfigHelper.getString(ServiceConstant.FTP_PORT));
        String userName = ConfigHelper.getString(ServiceConstant.FTP_SERVER_USERNAME);
        String password = ConfigHelper.getString(ServiceConstant.FTP_SERVER_PASSWORD);
        return new FtpUtil(ftpIP, port, userName, password);
    }
}
