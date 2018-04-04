package com.crscd.passengerservice.app;

/**
 * @author lzy
 * @date 2017/8/7
 */
public interface ServiceConstant {
    String SUPER_ADMIN = "Admin";

    String UNKNOWN_PLATFORM = "unknown platform";
    String UNKNOWN_BUREAU_CODE = "unknown bureauCode";

    String ENG = "English";
    String CN = "Chinese";

    String STATION_NAME = "stationName";

    String KEY = "planKey";

    //////////////SOAP HEADER定义///////////////////
    String QNAME_SPACE = "http://crscd.com.cn/auth";
    String USER_NAME = "username";
    String USER_PASSWORD = "password";
    String USER_LAN = "language";
    String USER_STATION = "station";
    String USER_IP_ADDRESS = "ipAddress";

    //////////////FTP配置///////////////////
    String FTP_ADDRESS = "travelService.ftpAddress";
    String FTP_PORT = "travelService.ftpServerPort";
    String FTP_SERVER_USERNAME = "travelService.FTPUserName";
    String FTP_SERVER_PASSWORD = "travelService.FTPPassWord";

    //////////////素材审批///////////////////
    //素材已发送
    String MATERIAL_SENT = "Sent";
    //素材未发送
    String MATERIAL_UNSENT = "Unsent";
    //素材未审批
    String MATERIAL_UNREVIEWED = "Unreviewed";
    //素材已审批
    String MATERIAL_REVIEWED = "Reviewed";
    //素材审核未通过
    String MATERIAL_NOT_APPROVED = "Not approved";
    //素材审核通过
    String MATERIAL_APPROVED = "Approved";

    //soapHeader的常量名称
    String USERNAME = "username";
    String PASSWORD = "password";
    String LANGUAGE = "language";
    String STATION_ID = "stationid";

    String CENTER = "center";
    String SYSTEM = "system";

    int SINGLE_NOTICE = -1;

    ////////////综显屏幕控制/////////////////
    //屏幕待机
    String SCREENCONTROL_STANDBY = "0X01";
    //屏幕开启
    String SCREENCONTROL_TURNON = "0X02";
    //屏幕下电
    String SCREENCONTROL_POWEROFF = "0X03";
    //屏幕上电
    String SCREENCONTROL_POWERON = "0X04";

    ////////////综显屏幕状态/////////////////
    //屏幕开启
    String SCREENSTATUS_POWERON = "0XA1";
    //屏幕待机
    String SCREENSTATUS_STANDBY = "0XA2";
    //屏幕下电
    String SCREENSTASTUS_POWEROFF = "0XA3";
    //屏幕通信中断
    String SCREENSTATUS_COMMUNICATIONFAILURE = "0XA4";

    ////////////综显屏幕状态/////////////////
    //版式下发成功
    String FORMATSEND_OK = "0XA7";
    //版式下发失败
    String FORMATSEND_FAIL = "0XA8";
    //版式缺失
    String FORMATSEND_FORMATMISSING = "0XA9";
    //播放列表缺失
    String FORMATSEND_PLAYLISTMISSING = "0XAA";

}
