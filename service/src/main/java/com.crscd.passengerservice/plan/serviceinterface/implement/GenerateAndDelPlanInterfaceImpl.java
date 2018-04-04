package com.crscd.passengerservice.plan.serviceinterface.implement;

import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.number.RandomUtil;
import com.crscd.passengerservice.plan.business.BasicPlanManager;
import com.crscd.passengerservice.plan.business.PlanGenAndDelManager;
import com.crscd.passengerservice.plan.dao.OrganizeTemplateDAO;
import com.crscd.passengerservice.plan.domainobject.BasicPlan;
import com.crscd.passengerservice.plan.domainobject.BasicTrainStationInfo;
import com.crscd.passengerservice.plan.domainobject.OrganizeTemplate;
import com.crscd.passengerservice.plan.dto.BasicPlanDTO;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import com.crscd.passengerservice.plan.serviceinterface.GenerateAndDelPlanInterface;
import com.crscd.passengerservice.plan.util.PlanHelper;
import com.crscd.passengerservice.result.GenAndDelPlanResultMessage;
import com.crscd.passengerservice.result.PagedGenAndDelPlanResultMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzy
 * Date: 2017/8/30
 * Time: 9:00
 */
public class GenerateAndDelPlanInterfaceImpl implements GenerateAndDelPlanInterface {

    private PlanGenAndDelManager manager;
    private BasicPlanManager basicPlanManager;
    private OrganizeTemplateDAO dao;
    private ConcurrentHashMap<Long, List<PagedGenAndDelPlanResultMessage>> resultMessageCache;

    public GenerateAndDelPlanInterfaceImpl(PlanGenAndDelManager manager, OrganizeTemplateDAO dao, BasicPlanManager basicPlanManager) {
        this.manager = manager;
        this.dao = dao;
        this.basicPlanManager = basicPlanManager;
        this.resultMessageCache = new ConcurrentHashMap<>();
    }

    @Override
    public List<BasicPlanDTO> getValidBasicPlan(String trainNum, String stationName) {
        List<BasicPlanDTO> dtoList = new ArrayList<>();
        List<BasicPlan> basicPlanList = basicPlanManager.getBasicPlanList(stationName, trainNum, TrainTypeEnum.ALL, true);
        for (BasicPlan plan : basicPlanList) {
            if (dao.existOrganizeTemplateInDB(plan.getTrainNum(), stationName)) {
                OrganizeTemplate template = dao.getOrganizeTemplate(plan.getTrainNum(), stationName);
                if (!"default".equals(template.getBroadcastTemplateGroupName())) {
                    BasicPlanDTO dto = new BasicPlanDTO(plan);
                    BasicTrainStationInfo info = plan.getSpecifiedTrainStationInfo(stationName);
                    PlanHelper.setBasicPlanDTOTime(info, dto);
                    dtoList.add(dto);
                }
            }
        }
        return dtoList;
    }

    @Override
    public GenAndDelPlanResultMessage generatePlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {

        return new GenAndDelPlanResultMessage(manager.generatePlanList(trainNumList, stationName, startDate, endDate));
    }

    @Override
    public GenAndDelPlanResultMessage delPlanList(List<String> trainNumList, String stationName, String startDate, String endDate) {
        return new GenAndDelPlanResultMessage(manager.delPlanList(trainNumList, stationName, startDate, endDate));
    }

    @Override
    public PagedGenAndDelPlanResultMessage generatePlanListPaged(List<String> trainNumList, String stationName, String startDate, String endDate) {
        return genCache(manager.generatePlanList(trainNumList, stationName, startDate, endDate));
    }

    @Override
    public PagedGenAndDelPlanResultMessage delPlanListPaged(List<String> trainNumList, String stationName, String startDate, String endDate) {
        return genCache(manager.delPlanList(trainNumList, stationName, startDate, endDate));
    }

    @Override
    public ResultMessage dropErrorDetailCache(Long uid) {
        resultMessageCache.remove(uid);
        return new ResultMessage();
    }

    @Override
    public PagedGenAndDelPlanResultMessage getErrorDetailsByPageNum(Long uid, int pageNum) {
        // 如果查询的信息不存在或者页数不正确，则返回一个空的分页
        boolean flag1 = !resultMessageCache.containsKey(uid);
        boolean flag2 = resultMessageCache.get(uid).size() < pageNum;
        boolean flag3 = pageNum <= 0;
        boolean flag4 = resultMessageCache.isEmpty();
        if (flag1 || flag2 || flag3 || flag4) {
            return new PagedGenAndDelPlanResultMessage();
        }
        // 获取缓存中的分页结果
        return resultMessageCache.get(uid).get(pageNum - 1);
    }

    private PagedGenAndDelPlanResultMessage genCache(Map<String, String> details) {
        // 存储有问题的信息
        Map<String, String> errorDetails = new HashMap<>();
        // 首先过滤出有问题的信息列表
        for (Map.Entry<String, String> entry :
                details.entrySet()) {
            if (!("Generate success".equalsIgnoreCase(entry.getValue())
                    || "Delete successful.".equalsIgnoreCase(entry.getValue()))) {
                errorDetails.put(entry.getKey(), entry.getValue());
            }
        }

        // 根据是否有问题决定返回值
        // 没有问题则返回正确
        if (MapUtil.isEmpty(errorDetails)) {
            return new PagedGenAndDelPlanResultMessage();
        } else {
            // 有问题则对问题进行分页
            long uid = RandomUtil.nextLong();
            // 分页大小
            int pageSize = 20;
            // 页数
            int pageNum = (int) Math.ceil((double) errorDetails.size() / pageSize);
            // 分页,如果只有一页则不用缓存，直接返回
            if (pageNum == 1) {
                return new PagedGenAndDelPlanResultMessage(pageNum, 1, errorDetails, uid);
            }
            // 否则需要分页并缓存
            else {
                int index = 0;
                int pageIndex = 1;
                Map<String, String> pagedError = new HashMap<>();
                List<PagedGenAndDelPlanResultMessage> messageList = new ArrayList<>();
                for (Map.Entry<String, String> entry : errorDetails.entrySet()) {
                    pagedError.put(entry.getKey(), entry.getValue());
                    index += 1;
                    // 如果存满一页则存储
                    if (index % pageSize == 0) {
                        PagedGenAndDelPlanResultMessage message = new PagedGenAndDelPlanResultMessage(pageNum, pageIndex, new HashMap<>(pagedError), uid);
                        messageList.add(message);
                        pageIndex += 1;
                        pagedError.clear();
                    }
                    // 如果最后一页不足20条，则也存储
                    else if (index == errorDetails.size()) {
                        PagedGenAndDelPlanResultMessage message = new PagedGenAndDelPlanResultMessage(pageNum, pageIndex, new HashMap<>(pagedError), uid);
                        messageList.add(message);
                    }
                }
                resultMessageCache.put(uid, messageList);
                // 返回第一页
                return resultMessageCache.get(uid).get(0);
            }
        }
    }
}
