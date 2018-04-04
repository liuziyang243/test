package com.crscd.passengerservice.notice.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.framework.util.text.JsonUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import com.crscd.passengerservice.notice.domainobject.NoticeMessage;
import com.crscd.passengerservice.notice.enumtype.ProcessStateEnum;
import com.crscd.passengerservice.notice.enumtype.ReceiverEnum;
import com.crscd.passengerservice.notice.enumtype.SenderEnum;
import com.crscd.passengerservice.notice.po.NoticeMessageBean;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/8/29
 * Time: 12:49
 */
public class NoticeDAO {
    private DataSet dataSet;

    public NoticeDAO(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    // 获取指定车站在指定日期的日志
    public List<NoticeMessage> getNoticeList(SenderEnum sender, ReceiverEnum receiver, String stationName, String start, String end, ProcessStateEnum state) {
        List<NoticeMessageBean> beanList = getNoticeBeanList(sender, receiver, stationName, start, end, state);
        List<NoticeMessage> messageList = new ArrayList<>();
        for (NoticeMessageBean bean : beanList) {
            messageList.add(new NoticeMessage(bean));
        }
        return messageList;
    }

    // 将消息插入数据库中
    public boolean insert(NoticeMessage notice) {
        NoticeMessageBean bean = getDOfromPO(notice);
        return dataSet.insert(bean);
    }

    // 将消息插入数据库中并返回id
    public long insertReturnPK(NoticeMessage notice) {
        NoticeMessageBean bean = getDOfromPO(notice);
        return CastUtil.castLong(dataSet.insertReturnPK(bean));
    }

    // 更新notice状态
    public ResultMessage updateProcessState(String user, long id, ProcessStateEnum state) {
        NoticeMessageBean bean = getNoticeMessageBean(id);
        if (bean == null) {
            return new ResultMessage(2012);
        } else {
            if (!bean.getProcessState().equals(ProcessStateEnum.UN_HANDLE)) {
                return new ResultMessage(301);
            } else {
                bean.setProcessUser(user);
                bean.setProcessTime(DateTimeUtil.getCurrentDatetimeString());
                bean.setProcessState(state);
                if (!dataSet.update(bean)) {
                    return new ResultMessage(2002);
                }
                return new ResultMessage();
            }
        }
    }

    public NoticeMessage getNoticeMessage(long id) {
        NoticeMessageBean bean = getNoticeMessageBean(id);
        if (bean == null) {
            return null;
        }
        return new NoticeMessage(bean);
    }

    private NoticeMessageBean getNoticeMessageBean(long id) {
        String condition = "id=?";
        return dataSet.select(NoticeMessageBean.class, condition, id);
    }

    // 从数据库获取指定车站在指定日期的日志
    // 过滤参数包括站名、站名和类型
    // 允许过滤参数为空，表示无此参数过滤
    private List<NoticeMessageBean> getNoticeBeanList(SenderEnum sender, ReceiverEnum receiver, String stationName, String startDate, String endDate, ProcessStateEnum state) {
        List<NoticeMessageBean> noticeMessageList = new ArrayList<>();
        Map<String, Object> conditionParamMap = new HashMap<>();
        if (null != stationName) {
            conditionParamMap.put("stationName=?", stationName);
        }
        if (null != startDate) {
            conditionParamMap.put("to_date(planDate,'yyyy-mm-dd')>=to_date(?,'yyyy-mm-dd')", startDate);
        }
        if (null != endDate) {
            conditionParamMap.put("to_date(planDate,'yyyy-mm-dd')<=to_date(?,'yyyy-mm-dd')", endDate);
        }
        if (null != state) {
            conditionParamMap.put("processState=?", state);
        }
        if (null != sender) {
            conditionParamMap.put("sender=?", sender);
        }
        if (null != receiver) {
            conditionParamMap.put("receiver=?", receiver);
        }
        if (conditionParamMap.isEmpty()) {
            noticeMessageList = dataSet.selectList(NoticeMessageBean.class);
        } else if (conditionParamMap.size() == 1) {
            for (Map.Entry<String, Object> entry : conditionParamMap.entrySet()) {
                noticeMessageList = dataSet.selectListWithCondition(NoticeMessageBean.class, entry.getKey(), entry.getValue());
            }
        } else {
            Object[] parms = new String[conditionParamMap.size()];
            int index = 0;
            StringBuilder condition = new StringBuilder();
            for (Map.Entry<String, Object> entry : conditionParamMap.entrySet()) {
                if (index == 0) {
                    condition = new StringBuilder(entry.getKey());
                } else {
                    condition.append(" AND ").append(entry.getKey());
                }
                if (entry.getValue() instanceof Enum) {
                    parms[index] = entry.getValue().toString();
                } else {
                    parms[index] = entry.getValue();
                }
                index += 1;
            }
            noticeMessageList = dataSet.selectListWithCondition(NoticeMessageBean.class, condition.toString(), parms);
        }
        return noticeMessageList;
    }

    // 将notice信息拷贝到bean中
    private NoticeMessageBean getDOfromPO(NoticeMessage notice) {
        NoticeMessageBean bean = MapperUtil.map(notice, NoticeMessageBean.class);
        bean.setPlanDate(DateTimeFormatterUtil.convertDateToString(notice.getPlanDate()));
        bean.setModifiedDataMap(JsonUtil.toJSON(notice.getModifiedDataMap()));
        return bean;
    }

}
