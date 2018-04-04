package com.crscd.passengerservice.result.page;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-04-02 下午3:00
 */
public class PageBase {
    private int currentPage;
    private int pageSize;
    private int totalPage;
    private long totalRows;

    public PageBase() {
        this.totalPage = 0;
        this.totalRows = 0;
    }

    public PageBase(int currentPage, int pageSize, long totalRows) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRows = totalRows;
        countTotalPage();
    }

    private void countTotalPage() {
        if (totalRows % pageSize == 0) {
            this.totalPage = (int) totalRows / pageSize;
        } else {
            this.totalPage = (int) totalRows / pageSize + 1;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

}
