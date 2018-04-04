package com.crscd.passengerservice.context;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.translation.serviceinterface.TranslatorInterface;
import com.crscd.framework.translation.serviceinterface.impl.TranslatorInterfaceImpl;
import com.crscd.framework.util.base.ContextUtil;
import com.crscd.passengerservice.broadcast.business.BroadcastPlanExecuteManager;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.ctc.business.CtcMsgManager;
import com.crscd.passengerservice.display.business.GetPlanDataElementInterface;
import com.crscd.passengerservice.display.business.MakeGuidePlanOnScreen;
import com.crscd.passengerservice.display.format.business.FormatManager;
import com.crscd.passengerservice.display.format.business.FrameManager;
import com.crscd.passengerservice.display.format.serviceinterface.FormatSendInterface;
import com.crscd.passengerservice.display.format.serviceinterface.FormatSendInterfaceImpl;
import com.crscd.passengerservice.display.guiderule.business.PlanOnOffTimeImpl;
import com.crscd.passengerservice.display.guiderule.business.PlanOnOffTimeInterface;
import com.crscd.passengerservice.display.screencontrolserver.business.*;
import com.crscd.passengerservice.log.business.OperationLogManager;
import com.crscd.passengerservice.multimedia.business.MaterialManager;
import com.crscd.passengerservice.multimedia.serviceinterface.PlayListManagerInterface;
import com.crscd.passengerservice.multimedia.serviceinterface.implement.PlayListManagerImplement;
import com.crscd.passengerservice.plan.business.deletion.PlanDeleter;
import com.crscd.passengerservice.plan.business.deletion.PlanDeleterParallel;
import com.crscd.passengerservice.plan.business.deletion.PlanDeleterStream;
import com.crscd.passengerservice.plan.business.generation.PlanGenerateInDB;
import com.crscd.passengerservice.plan.business.generation.PlanGenerator;
import com.crscd.passengerservice.plan.business.generation.PlanGeneratorParallel;
import com.crscd.passengerservice.plan.business.innerinterface.DeletePeriodPlanInterface;
import com.crscd.passengerservice.plan.business.innerinterface.GeneratePeriodPlanInterface;
import com.crscd.passengerservice.plan.pool.BroadcastPlanDataPool;
import com.crscd.passengerservice.plan.pool.DispatchPlanDataPool;
import com.crscd.passengerservice.plan.pool.GuidePlanDataPool;
import com.crscd.passengerservice.plan.pool.PassengerPlanDataPool;
import com.crscd.passengerservice.soapinterface.BroadcastServiceInterface;
import com.crscd.passengerservice.soapinterface.implement.*;
import com.crscd.passengerservice.ticket.bussiness.StructTransformer;
import com.crscd.passengerservice.ticket.serviceinterface.TicketMsgManageInterface;
import com.crscd.passengerservice.ticket.serviceinterface.impl.TicketMsgManageImplement;
import com.crscd.passengerservice.warning.business.MonitoringInterface;
import com.crscd.passengerservice.warning.business.ScreenCtrlServerMonitorImpl;
import org.springframework.context.ApplicationContext;

/**
 * @author lzy
 * Date: 2017/8/17
 * Time: 16:32
 */
public class ContextHelper {
    private static final String DATABASE_IN_USE = ConfigHelper.getString("travelService.dataBaseInUse");
    private static ApplicationContext ctx = ContextUtil.getInstance();

    public static ConfigManager getConfigManager() {
        return ctx.getBean("configManager", ConfigManager.class);
    }

    public static GeneratePeriodPlanInterface getPlanGenerator() {
        return ctx.getBean("planGenerator", PlanGenerator.class);
    }

    public static GeneratePeriodPlanInterface getPlanGeneratorParallel() {
        return ctx.getBean("planGeneratorParallel", PlanGeneratorParallel.class);
    }

    public static DeletePeriodPlanInterface getPlanDeleter() {
        return ctx.getBean("planDeleter", PlanDeleter.class);
    }

    public static DeletePeriodPlanInterface getPlanDeleterParallel() {
        return ctx.getBean("planDeleterParallel", PlanDeleterParallel.class);
    }

    public static DeletePeriodPlanInterface getPlanDeleterStream() {
        return ctx.getBean("planDeleterStream", PlanDeleterStream.class);
    }

    public static PlanGenerateInDB getPlanGenerateInDB() {
        return ctx.getBean("planGenerateInDB", PlanGenerateInDB.class);
    }

    public static DataSet getOracleDataSet() {
        return ctx.getBean("oracleDataSet", DataSet.class);
    }

    public static DataSet getDataSet() {
        return ContextUtil.getInstance().getBean(DATABASE_IN_USE, DataSet.class);
    }

    // Only for test
    public static DataSet getTestDataSet() {
        return ctx.getBean(DATABASE_IN_USE, DataSet.class);
    }

    public static BroadcastServiceInterface getBroadcastSerivce() {
        return ctx.getBean("broadcastService", BroadcastServiceInterface.class);
    }

    public static ExosystemServiceInterfaceImpl getExoSystemSerivce() {
        return ctx.getBean("exoSystemService", ExosystemServiceInterfaceImpl.class);
    }

    public static GuideServiceInterfaceImpl getGuideSerivce() {
        return ctx.getBean("guideService", GuideServiceInterfaceImpl.class);
    }

    public static LogServiceInterfaceImpl getLogSerivce() {
        return ctx.getBean("logService", LogServiceInterfaceImpl.class);
    }

    public static PlanServiceInterfaceImpl getPlanSerivce() {
        return ctx.getBean("planService", PlanServiceInterfaceImpl.class);
    }

    public static SystemServiceInterfaceImpl getSystemSerivce() {
        return ctx.getBean("systemService", SystemServiceInterfaceImpl.class);
    }

    public static TranslatorInterface getTranslator() {
        return ctx.getBean("translator", TranslatorInterfaceImpl.class);
    }

    public static OperationLogManager getOperationLogManager() {
        return ctx.getBean("operationLogManager", OperationLogManager.class);
    }

    public static MakeGuidePlanOnScreen getGuidePlanOnScreenRefresher() {
        return ContextUtil.getInstance().getBean("makeGuidePlanOnScreen", MakeGuidePlanOnScreen.class);
    }

    public static TicketMsgManageInterface getTicketMsgManager() {
        return ContextUtil.getInstance().getBean("ticketMsgManageImplement", TicketMsgManageImplement.class);
    }

    public static MonitoringInterface getScreenCtrlServerMonitor() {
        return ContextUtil.getInstance().getBean("screenCtrlServerMonitorImpl", ScreenCtrlServerMonitorImpl.class);
    }

    public static FormatSendInterface getFormatSendInterface() {
        return ContextUtil.getInstance().getBean("formatSendInterfaceImpl", FormatSendInterfaceImpl.class);
    }

    public static PlanOnOffTimeInterface getPlanOnOffTimeInterface() {
        return ContextUtil.getInstance().getBean("planOnOffTimeImpl", PlanOnOffTimeImpl.class);
    }

    public static GetPlanDataElementInterface getPlanDataElementInterface() {
        return ContextUtil.getInstance().getBean("makeGuidePlanOnScreen", MakeGuidePlanOnScreen.class);
    }

    public static BroadcastPlanExecuteManager getBroadcastPlanExecuteManager() {
        return ContextUtil.getInstance().getBean("broadcastPlanExecuteManager", BroadcastPlanExecuteManager.class);
    }

    public static CtcMsgManager getCTCMsgManager() {
        return ContextUtil.getInstance().getBean("ctcMsgManager", CtcMsgManager.class);
    }

    /////////////////////////Ticket Manager///////////////////////////////
    public static StructTransformer getStructTransformer() {
        return ContextUtil.getInstance().getBean("structTransformer", StructTransformer.class);
    }

    /////////////////////////Display Manager//////////////////////////////
    public static FrameManager getFrameManager() {
        return ContextUtil.getInstance().getBean("frameManager", FrameManager.class);
    }

    public static FormatManager getFormatManager() {
        return ContextUtil.getInstance().getBean("formatManager", FormatManager.class);
    }

    /////////////////////////Multimedia Manager//////////////////////////////
    public static MaterialManager getMaterialManager() {
        return ContextUtil.getInstance().getBean("materialManager", MaterialManager.class);
    }

    // 获取计划存储内存池
    public static DispatchPlanDataPool getDispatchPlanPool() {
        return ContextUtil.getInstance().getBean("dispatchPlanDataPool", DispatchPlanDataPool.class);
    }

    public static PassengerPlanDataPool getPassengerPlanPool() {
        return ContextUtil.getInstance().getBean("passengerPlanDataPool", PassengerPlanDataPool.class);
    }

    public static GuidePlanDataPool getGuidePlanPool() {
        return ContextUtil.getInstance().getBean("guidePlanDataPool", GuidePlanDataPool.class);
    }

    public static BroadcastPlanDataPool getBroadcastPlanPool() {
        return ContextUtil.getInstance().getBean("broadcastPlanDataPool", BroadcastPlanDataPool.class);
    }

    /////////////////////////Screen Control Server//////////////////////////////
    public static Authentication getAuthentication() {
        return ContextUtil.getInstance().getBean("authentication", Authentication.class);
    }

    public static Heartbeat getHeartbeat() {
        return ContextUtil.getInstance().getBean("heartbeat", Heartbeat.class);
    }

    public static DeviceManage getDeviceManage() {
        return ContextUtil.getInstance().getBean("DeviceManage", DeviceManage.class);
    }

    public static PlayList getPlayList() {
        return ContextUtil.getInstance().getBean("PlayList", PlayList.class);
    }

    public static Material getMaterial() {
        return ContextUtil.getInstance().getBean("Material", Material.class);
    }

    public static FormatSend getFormatSend() {
        return ContextUtil.getInstance().getBean("FormatSend", FormatSend.class);
    }

    public static PlayListManagerInterface getPlaylistManager() {
        return ContextUtil.getInstance().getBean("playListManagerImplement", PlayListManagerImplement.class);
    }

}
