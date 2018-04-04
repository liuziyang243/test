package com.crscd.passengerservice.broadcast.template.business;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.passengerservice.broadcast.content.dao.NormalBroadcastContentDAO;
import com.crscd.passengerservice.broadcast.content.domainobject.NormalBroadcastContent;
import com.crscd.passengerservice.broadcast.template.dao.BroadcastTemplateDao;
import com.crscd.passengerservice.broadcast.template.dao.BroadcastTemplateGroupDAO;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplate;
import com.crscd.passengerservice.broadcast.template.domainobject.BroadcastTemplateGroup;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.broadcast.template.po.BroadcastTemplateBean;
import com.crscd.passengerservice.broadcast.template.po.BroadcastTemplateGroupBean;
import com.crscd.passengerservice.config.business.ConfigManager;
import com.crscd.passengerservice.config.enumtype.FirstRegionEnum;
import com.crscd.passengerservice.plan.domainobject.BroadcastStationPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/2
 */
public class BroadcastTemplateManager {
    private static final Logger logger = LoggerFactory.getLogger(BroadcastTemplateManager.class);
    private BroadcastTemplateDao templateDao;
    private BroadcastTemplateGroupDAO groupDAO;
    private NormalBroadcastContentDAO contentDAO;
    private ConfigManager configManager;

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setTemplateDao(BroadcastTemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    public void setGroupDAO(BroadcastTemplateGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public void setContentDAO(NormalBroadcastContentDAO contentDAO) {
        this.contentDAO = contentDAO;
    }

    // 根据站名和广播组名返回指定的广播组
    // 生成计划使用
    public BroadcastTemplateGroup getBroadcastTemplateGroupByName(String stationName, String groupName) {
        BroadcastTemplateGroupBean groupBean = groupDAO.getBroadcastTemplateGroupBean(stationName, groupName);
        if (null == groupBean) {
            logger.warn("Broadcast group is missing at station " + stationName + " with name " + groupName);
            return null;
        }
        return getGroupFromBean(groupBean);
    }

    // 根据站名和广播类型返回模版组
    // 如果广播类型为空，表示返回全部模版
    public List<BroadcastTemplateGroup> getBroadcastTemplateGroupByStation(String stationName, BroadcastKindEnum kind) {
        List<BroadcastTemplateGroup> templateGroupList = new ArrayList<>();
        List<BroadcastTemplateGroupBean> beanList = groupDAO.getBroadcastTemplateGroupBeanList(stationName, kind);
        for (BroadcastTemplateGroupBean bean : beanList) {
            BroadcastTemplateGroup group = getGroupFromBean(bean);
            templateGroupList.add(group);
        }
        return templateGroupList;
    }

    // 根据groupBean获取实体类
    private BroadcastTemplateGroup getGroupFromBean(BroadcastTemplateGroupBean groupBean) {
        List<BroadcastTemplateBean> templateBeanList = templateDao.getBroadcastTemplateBean(groupBean.getStationName(), groupBean.getTemplateGroupName());
        List<BroadcastTemplate> templateList = new ArrayList<>();
        for (BroadcastTemplateBean bean : templateBeanList) {
            NormalBroadcastContent content = contentDAO.getContentByNameForTemplate(bean.getBroadcastContentName(), bean.getBroadcastKind(), bean.getStationName());
            BroadcastTemplate template = new BroadcastTemplate(bean, content);
            templateList.add(template);
        }
        return new BroadcastTemplateGroup(groupBean, templateList);
    }

    // 根据广播模版和计划获取广播区域列表
    public List<String> getBroadcastAreaList(BroadcastTemplate template, BroadcastStationPlan plan) {
        List<String> broadcastAreaList = template.getBroadcastArea();
        List<String> secondaryRegionList = new ArrayList<>();
        for (FirstRegionEnum firstRegion : template.getFirstRegion()) {
            switch (firstRegion) {
                case STATION_ENTRANCE_PORT:
                    secondaryRegionList.addAll(plan.getStationEntrancePort());
                    break;
                case STATION_EXIT_PORT:
                    secondaryRegionList.addAll(plan.getStationExitPort());
                    break;
                case ABOARD_CHECK_GATE:
                    secondaryRegionList.addAll(plan.getAboardCheckGate());
                    break;
                case EXIT_CHECK_GATE:
                    secondaryRegionList.addAll(plan.getExitCheckGate());
                case WAIT_ZONE:
                    secondaryRegionList.addAll(plan.getWaitZone());
                    break;
                default:
            }
        }
        secondaryRegionList.add(plan.getDockingPlatform());
        broadcastAreaList.addAll(configManager.getBroadcastAreaListBySecondaryRegion(secondaryRegionList, plan.getPresentStation().getStationName()));
        return ListUtil.removeDuplicateElement(broadcastAreaList);
    }
}
