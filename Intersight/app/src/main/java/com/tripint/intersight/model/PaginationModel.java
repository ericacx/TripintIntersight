package com.tripint.intersight.model;

import java.io.Serializable;

/**
 * 分页对象
 */
public class PaginationModel implements Serializable {
    private int currentPage; //当前页
    private int pageSize; //每页的数量
    private int totalCount; //中页数

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
