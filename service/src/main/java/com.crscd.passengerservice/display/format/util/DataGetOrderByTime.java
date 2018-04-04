package com.crscd.passengerservice.display.format.util;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.passengerservice.display.format.domainobject.PlanDataElement;
import com.crscd.passengerservice.ticket.po.TicketScreenDataBean;

import java.util.*;

/**
 * Created by cuishiqing on 2017/9/5.
 */
public class DataGetOrderByTime {
    //数据库
    private DataSet oracleDataSet;

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    /**
     * @param stationName
     * @return
     * @see '按计划发车时间对售票大屏数据进行排序'
     */
    public List<String> TicketScreenDataOrderByDeparT(String stationName) {
        List<String> trainNumList1 = oracleDataSet.selectColumnList(
                TicketScreenDataBean.class, "TrainNum", "StationName=? AND PlanDepartureTime<>?", "PlanDepartureTime", stationName, "--");
        List<String> trainNumList2 = oracleDataSet.selectColumnList(
                TicketScreenDataBean.class, "TrainNum", "StationName=? AND PlanDepartureTime=?", "PlanArriveTime", stationName, "--");
        return ListUtil.copyList(trainNumList1, trainNumList2);
    }

    /**
     * 计划列表按到达时间排序
     *
     * @param pdeList
     * @return
     */
    public List<Map<String, String>> generatePlanTableDataMapArrive(List<PlanDataElement> pdeList) {
        List<Map<String, String>> ptdMapList = new ArrayList<>();
        if (ListUtil.isEmpty(pdeList)) {
            return ptdMapList;
        }
        List<PlanDataElement> planListInOdr = new ArrayList<>();
        List<PlanDataElement> arriveTEmpty = new ArrayList<>();
        for (PlanDataElement p : pdeList) {
            if ("--".equals(p.getActualArriveTime())) {
                arriveTEmpty.add(p);
                pdeList.remove(p);
            }
        }
        planListInOdr = ListUtil.copyList(PlanListOdrByArriveT(pdeList), PlanListOdrByDepartureT(arriveTEmpty));
        getPTDMap(ptdMapList, planListInOdr);
        return ptdMapList;

    }

    // todo: 可以考虑将key值修改为Enum类型
    private void getPTDMap(List<Map<String, String>> ptdMapList, List<PlanDataElement> planListInOdr) {
        for (PlanDataElement p : planListInOdr) {
            Map<String, String> ptdMap = new HashMap<>();
            ptdMap.put("trainNum", p.getTrainNum());
            ptdMap.put("actualArriveTime", p.getActualArriveTime());
            ptdMap.put("actualDepartureTime", p.getActualDepartureTime());
            ptdMap.put("startStation", p.getStartStation());
            ptdMap.put("finalStation", p.getFinalStation());
            ptdMap.put("checkInState", p.getCheckInState());
            ptdMap.put("arriveState", p.getArriveState());
            ptdMap.put("departureState", p.getDepartureState());
            ptdMap.put("waitZone", ListUtil.listToStringByPunctuation(p.getWaitZone(), ","));
            ptdMap.put("trackNumber", p.getTrackNumber());
            ptdMap.put("terminated", p.getTerminated());
            ptdMap.put("entrancePort", ListUtil.listToStringByPunctuation(p.getEntrancePort(), ","));
            ptdMap.put("platform", p.getPlatform());
            ptdMapList.add(ptdMap);
        }
    }

    /**
     * 计划列表按发车时间排序
     *
     * @param pdeList
     * @return
     */
    public List<Map<String, String>> generatePlanTableDataMapDeparture(List<PlanDataElement> pdeList) {
        List<Map<String, String>> ptdMapList = new ArrayList<>();
        if (ListUtil.isEmpty(pdeList)) {
            return ptdMapList;
        }
        List<PlanDataElement> planListInOdr = new ArrayList<>();
        List<PlanDataElement> deparTEmpty = new ArrayList<>();
        for (Iterator<PlanDataElement> iter = pdeList.iterator(); iter.hasNext(); ) {
            PlanDataElement pde = iter.next();
            if ("--".equals(pde.getActualDepartureTime())) {
                deparTEmpty.add(pde);
                iter.remove();
            }
        }
        planListInOdr = ListUtil.copyList(PlanListOdrByDepartureT(pdeList), PlanListOdrByArriveT(deparTEmpty));
        getPTDMap(ptdMapList, planListInOdr);
        return ptdMapList;
    }

    /**
     * 按到点时间对计划数据进行排序
     *
     * @param pdes
     * @return
     */
    private List<PlanDataElement> PlanListOdrByArriveT(List<PlanDataElement> pdes) {
        Comparator<PlanDataElement> comparator = new Comparator<PlanDataElement>() {//比较器 先比较优先级 再比较下发时间

            @Override
            public int compare(PlanDataElement pde1, PlanDataElement pde2) {
                return pde1.getActualArriveTime().compareTo(pde2.getActualArriveTime());
            }
        };

        pdes.sort(comparator);

        return pdes;
    }

    /**
     * 按发点时间对计划数据进行排序
     *
     * @param pdes
     * @return
     */
    private List<PlanDataElement> PlanListOdrByDepartureT(List<PlanDataElement> pdes) {
        Comparator<PlanDataElement> comparator = new Comparator<PlanDataElement>() {//比较器 先比较优先级 再比较下发时间

            @Override
            public int compare(PlanDataElement pde1, PlanDataElement pde2) {
                return pde1.getActualDepartureTime().compareTo(pde2.getActualDepartureTime());
            }
        };

        pdes.sort(comparator);

        return pdes;
    }
}
