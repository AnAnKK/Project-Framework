package com.runda.projectframework.app.repository.bean.others;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * @Description:    分页数据容器
 * @Author:         An_K
 * @CreateDate:     2019/12/18 17:56
 * @Version:        1.0
 */
public class PageDataContainer<T> {

    @SerializedName("pageNum")
    private int pageNum = 1;

    @SerializedName("pageSize")
    private int pageSize = 10;

    @SerializedName("size")
    private int dateSize = 0;

    @SerializedName("total")
    private int totalSize = 0;

    @SerializedName("isFirstPage")
    private boolean isFirstPage = false;

    @SerializedName("isLastPage")
    private boolean isLastPage = false;

    @SerializedName("hasPreviousPage")
    private boolean hasPreviousPage = false;

    @SerializedName("hasNextPage")
    private boolean hasNextPage = false;

    @SerializedName("balance")
    private String balance;

    @SerializedName("list")
    private List<T> data;

    public String getBalance() {
        return balance;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getDateSize() {
        return dateSize;
    }

    public void setDateSize(int dateSize) {
        this.dateSize = dateSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PageDataContainer() {
    }
}
