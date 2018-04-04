package com.crscd.passengerservice.log.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lzy
 * Date: 2017/9/11
 * Time: 10:07
 */
public class LogManager {
    private static Logger clientLogger = LoggerFactory.getLogger(LogManager.class);

    public static void logClientError(String ip, String time, String csLoc, String loc, String error) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ip]:").append(ip).append('\n')
                .append("[time]:").append(time).append('\n')
                .append("[source file]:").append(csLoc).append('\n')
                .append("[client/background]:").append(loc).append('\n')
                .append("[error]:").append(error).append('\n')
                .append("---------------------------------");
        clientLogger.error(sb.toString());
    }
}
