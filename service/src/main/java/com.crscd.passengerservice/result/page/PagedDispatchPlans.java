package com.crscd.passengerservice.result.page;

import com.crscd.passengerservice.plan.dto.DispatchPlanDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-04-02 下午5:05
 */
public class PagedDispatchPlans extends PageBase {
    private List<DispatchPlanDTO> dataList;

    public PagedDispatchPlans() {
        this.dataList = new ArrayList<>();
    }

    public PagedDispatchPlans(int currentPage, int pageSize, long totalRows, List<DispatchPlanDTO> dataList) {
        super(currentPage, pageSize, totalRows);
        this.dataList = dataList;
    }

    public List<DispatchPlanDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DispatchPlanDTO> dataList) {
        this.dataList = dataList;
    }
}
