package com.crscd.passengerservice.result;

import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/1/16 8:34
 * @version v1.00
 */
public class PagedGenAndDelPlanResultMessage extends ResultMessage {

    private int pageNum;

    private int page;

    /**
     * key: planKey, value: error message
     */
    private Map<String, String> pagedErrorDetails;

    private long uuid;

    /**
     * 如果部分错误，则在pagedErrorDetails中存储错误原因
     *
     * @param pageNum
     * @param page
     * @param pagedErrorDetails
     * @param uuid
     */
    public PagedGenAndDelPlanResultMessage(int pageNum, int page, Map<String, String> pagedErrorDetails, long uuid) {
        super(30);
        this.uuid = uuid;
        this.pageNum = pageNum;
        this.page = page;
        this.pagedErrorDetails = pagedErrorDetails;
    }

    /**
     * 如果生成正确，则返回true
     */
    public PagedGenAndDelPlanResultMessage() {
        super(200);
        this.uuid = -1;
        this.page = 0;
        this.pageNum = 0;
        this.pagedErrorDetails = new HashMap<>();
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Map<String, String> getPagedErrorDetails() {
        return pagedErrorDetails;
    }

    public void setPagedErrorDetails(Map<String, String> pagedErrorDetails) {
        this.pagedErrorDetails = pagedErrorDetails;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }
}
