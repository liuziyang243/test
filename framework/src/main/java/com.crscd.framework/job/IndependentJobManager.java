package com.crscd.framework.job;

/**
 * Create by: lzy
 * Date: 2017/6/15
 * Time: 13:04
 */
public class IndependentJobManager {

    // 门禁服务调度器
    private static IndependentJobHelper accessControlJobHelper = new IndependentJobHelper("AccessControl", "3");

    // 综显服务调度器
    private static IndependentJobHelper displayJobHelper = new IndependentJobHelper("Display", "20");

    // 客票服务调度器
    private static IndependentJobHelper ticketJobHelper = new IndependentJobHelper("Ticket", "10");

    // CTC服务调度器
    private static IndependentJobHelper ctcJobHelper = new IndependentJobHelper("CTC", "10");

    // 设备状态刷新调度器
    private static IndependentJobHelper refreshStateJobHelper = new IndependentJobHelper("RefreshState", "5");

    //客户端心跳调度器
    private static IndependentJobHelper clientConnectJobHelper = new IndependentJobHelper("clientConnect", "30");

    //综显控制服务器器心跳调度器
    private static IndependentJobHelper screenCtrlServerJobHelper = new IndependentJobHelper("screenCtrlServer", "10");

    public static IndependentJobHelper getRefreshStateJobHelper() {
        return refreshStateJobHelper;
    }

    public static IndependentJobHelper getCtcJobHelper() {
        return ctcJobHelper;
    }

    public static IndependentJobHelper getDisplayJobHelper() {
        return displayJobHelper;
    }

    public static IndependentJobHelper getAccessControlJobHelper() {
        return accessControlJobHelper;
    }

    public static IndependentJobHelper getTicketJobHelper() {
        return ticketJobHelper;
    }

    public static IndependentJobHelper getClientConnectJobHelper() {
        return clientConnectJobHelper;
    }

    public static IndependentJobHelper getScreenCtrlServerJobHelper() {
        return screenCtrlServerJobHelper;
    }


}
