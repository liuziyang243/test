package com.crscd.passengerservice.notice.business;

import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.passengerservice.notice.dao.NoticeDAO;
import com.crscd.passengerservice.notice.domainobject.NoticeMessage;
import com.crscd.passengerservice.notice.dto.GenerateNoticeInfoDTO;
import com.crscd.passengerservice.notice.enumtype.ProcessStateEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.notice.enumtype.ValidStateEnum;
import com.crscd.passengerservice.plan.business.BroadcastStationPlanManager;
import com.crscd.passengerservice.plan.business.DispatchStationPlanManager;
import com.crscd.passengerservice.plan.business.GuideStationPlanManager;
import com.crscd.passengerservice.plan.business.PassengerStationPlanManager;
import com.crscd.passengerservice.plan.domainobject.KeyBase;
import com.crscd.passengerservice.result.GroupResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzy
 * Noitce记录的是瞬时信息，因此不判断是否重复，允许多用户产生
 * 相同的notice信息
 * Date: 2017/8/29
 * Time: 13:15
 */
public class NoticeManager {
    private NoticeDAO dao;

    private DispatchStationPlanManager dispatchStationPlanManager;
    private PassengerStationPlanManager passengerStationPlanManager;
    private GuideStationPlanManager guideStationPlanManager;
    private BroadcastStationPlanManager broadcastStationPlanManager;

    public NoticeManager(NoticeDAO dao) {
        this.dao = dao;
    }

    public void setDispatchStationPlanManager(DispatchStationPlanManager dispatchStationPlanManager) {
        this.dispatchStationPlanManager = dispatchStationPlanManager;
    }

    public void setPassengerStationPlanManager(PassengerStationPlanManager passengerStationPlanManager) {
        this.passengerStationPlanManager = passengerStationPlanManager;
    }

    public void setGuideStationPlanManager(GuideStationPlanManager guideStationPlanManager) {
        this.guideStationPlanManager = guideStationPlanManager;
    }

    public void setBroadcastStationPlanManager(BroadcastStationPlanManager broadcastStationPlanManager) {
        this.broadcastStationPlanManager = broadcastStationPlanManager;
    }

    public boolean generateMessage(GenerateNoticeInfoDTO dto) {
        return dao.insert(new NoticeMessage(dto));
    }

    public long generateMessageReturnID(GenerateNoticeInfoDTO dto) {
        return dao.insertReturnPK(new NoticeMessage(dto));
    }

    public GroupResultMessage rejectNoticeList(String user, List<Long> idList) {
        HashMap<String, Boolean> resultList = new HashMap<>();
        for (Long id : idList) {
            ResultMessage result = rejectNotice(user, id);
            resultList.put(id.toString(), result.getResult());
        }
        return new GroupResultMessage(resultList);
    }

    private ResultMessage rejectNotice(String user, long id) {
        return dao.updateProcessState(user, id, ProcessStateEnum.REJECT);
    }

    /**
     * 检查message是否有效
     * 判断依据为：
     * 1.修改对象存在
     * 2.修改对象未过期
     * 3.修改内容与修改对象中包含的内容不同
     *
     * @param message
     * @return
     */
    public boolean checkNoticeValid(NoticeMessage message) {
        return getNoticeValidState(message).equals(ValidStateEnum.VALID);
    }

    private ValidStateEnum getNoticeValidState(NoticeMessage message) {
        String planKey = new KeyBase(message.getTrainNum(), DateTimeFormatterUtil.convertDateToString(message.getPlanDate()), message.getStationName()).toString();
        switch (message.getReceiver()) {
            case DISPATCH_PLAN:
                if (dispatchStationPlanManager.existPlan(planKey)) {
                    return ValidStateEnum.PLAN_NO_EXIT;
                }
                if (!dispatchStationPlanManager.getPlan(planKey).getValidFlag()) {
                    return ValidStateEnum.PLAN_OUT_OF_DATE;
                }
                if (dispatchStationPlanManager.checkModifyValid(message)) {
                    return ValidStateEnum.SAME_WITH_PLAN;
                }
                break;
            case PASSENGER_PLAN:
                if (passengerStationPlanManager.existPlan(planKey)) {
                    return ValidStateEnum.PLAN_NO_EXIT;
                }
                if (!passengerStationPlanManager.getPlan(planKey).getValidFlag()) {
                    return ValidStateEnum.PLAN_OUT_OF_DATE;
                }
                if (!passengerStationPlanManager.checkModifyValid(message)) {
                    return ValidStateEnum.SAME_WITH_PLAN;
                }
                break;
            case BROADCAST_PLAN:
                if (broadcastStationPlanManager.existPlan(planKey)) {
                    return ValidStateEnum.PLAN_NO_EXIT;
                }
                if (!broadcastStationPlanManager.getPlan(planKey).getValidFlag()) {
                    return ValidStateEnum.PLAN_OUT_OF_DATE;
                }
                if (!broadcastStationPlanManager.checkModifyValid(message)) {
                    return ValidStateEnum.SAME_WITH_PLAN;
                }
                break;
            case GUIDE_PLAN:
                if (guideStationPlanManager.existPlan(planKey)) {
                    return ValidStateEnum.PLAN_NO_EXIT;
                }
                if (!guideStationPlanManager.getPlan(planKey).getValidFlag()) {
                    return ValidStateEnum.PLAN_OUT_OF_DATE;
                }
                if (!guideStationPlanManager.checkModifyValid(message)) {
                    return ValidStateEnum.SAME_WITH_PLAN;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong plan type in notice id-" + message.getId());
        }
        return ValidStateEnum.VALID;
    }

    /**
     * 接收并更新一组noitce状态
     *
     * @param processUser
     * @param idList
     * @param pageName
     * @return
     */
    public GroupResultMessage acceptNoticeList(String processUser, ArrayList<Long> idList, ReceiverEnum pageName) {
        HashMap<String, Boolean> resultList = new HashMap<>();
        for (Long id : idList) {
            ResultMessage result = acceptNotice(processUser, id, pageName);
            resultList.put(id.toString(), result.getResult());
        }
        return new GroupResultMessage(resultList);
    }

    /**
     * 在客运计划页面将一条notice转发给导向或者广播计划
     *
     * @param id
     * @param receiver
     * @param user
     * @return
     */
    public ResultMessage forwardSingleNotice(long id, ReceiverEnum receiver, String user) {
        NoticeMessage message = dao.getNoticeMessage(id);
        if (null == message) {
            return new ResultMessage(306);
        } else {
            // 不允许转发CTC和客票系统的notice信息
            if (SenderEnum.CTC.equals(message.getSender())
                    || SenderEnum.TICKET.equals(message.getSender())) {
                return new ResultMessage(304);
            }
            message.setReceiver(receiver);
            message.setGenerateUser(user);
            if (dao.insert(message)) {
                return new ResultMessage();
            } else {
                return new ResultMessage(2001);
            }
        }
    }

    /**
     * 接收并更新notice状态
     *
     * @param processUser
     * @param id
     * @param pageName
     * @return
     */
    private ResultMessage acceptNotice(String processUser, long id, ReceiverEnum pageName) {
        NoticeMessage message = dao.getNoticeMessage(id);
        boolean flag;
        if (message == null) {
            return new ResultMessage(2012);
        } else {
            switch (pageName) {
                case GUIDE_PLAN:
                    flag = guideStationPlanManager.modifyPlan(message);
                    break;
                case DISPATCH_PLAN:
                    flag = dispatchStationPlanManager.modifyPlan(message);
                    break;
                case BROADCAST_PLAN:
                    flag = broadcastStationPlanManager.modifyPlan(message);
                    break;
                case PASSENGER_PLAN:
                    flag = passengerStationPlanManager.modifyPlan(message);
                    break;
                default:
                    throw new IllegalArgumentException("Wrong input " + pageName);
            }
            if (flag) {
                return dao.updateProcessState(processUser, id, ProcessStateEnum.ACCEPT);
            } else {
                return new ResultMessage(2002);
            }
        }
    }

    /**
     * 获取全部未处理notice信息
     *
     * @return
     */
    public List<NoticeMessage> getUnhandledNoticeList() {
        return dao.getNoticeList(null, null, null, null, null, ProcessStateEnum.UN_HANDLE);
    }

    /**
     * 获取全部车站未处理信息
     *
     * @param sender
     * @param receiver
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    public List<NoticeMessage> getUnhandledNoticeListByStationAndDate(SenderEnum sender, ReceiverEnum receiver, String stationName, String startDate, String endDate) {
        return dao.getNoticeList(sender, receiver, stationName, startDate, endDate, ProcessStateEnum.UN_HANDLE);
    }

    /**
     * 获取处理后信息的日志
     * 操作日志包括两部分：一部分是来自其他源推送的Notice的操作记录，另一部分是自己修改生成Noitce
     *
     * @param sender
     * @param receiver
     * @param stationName
     * @param startDate
     * @param endDate
     * @return
     */
    public List<NoticeMessage> getHandledNoticeListByStationAndDate(SenderEnum sender, ReceiverEnum receiver, String stationName, String startDate, String endDate) {
        List<NoticeMessage> noticeMessageList = new ArrayList<>();
        // 获取接受的notice的信息
        List<NoticeMessage> acceptedNoticeMessages = dao.getNoticeList(null, receiver, stationName, startDate, endDate, ProcessStateEnum.ACCEPT);
        noticeMessageList.addAll(acceptedNoticeMessages);
        // 获取拒绝的noitce的信息
        List<NoticeMessage> rejectedNoticeMessages = dao.getNoticeList(null, receiver, stationName, startDate, endDate, ProcessStateEnum.REJECT);
        noticeMessageList.addAll(rejectedNoticeMessages);
        // 获取自身操作的notice信息
        List<NoticeMessage> operationNoticeMessages = dao.getNoticeList(sender, null, stationName, startDate, endDate, null);
        noticeMessageList.addAll(operationNoticeMessages);
        return noticeMessageList;
    }
}
