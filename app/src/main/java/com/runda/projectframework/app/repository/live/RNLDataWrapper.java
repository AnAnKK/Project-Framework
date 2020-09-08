package com.runda.projectframework.app.repository.live;


import androidx.annotation.NonNull;

import com.runda.projectframework.app.repository.RepositoryException;

/**
 * Created by Kongdq
 * @date 2019/11/18
 * Description: 下拉刷新或上拉加载数据Wrapper
 */
public class RNLDataWrapper<T> {

    private boolean success;

    private T data;

    private RepositoryException error;

    private int operation = 0;

    private int requestPage = 0;

    private int originalPage = 0;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RepositoryException getError() {
        return error;
    }

    public void setError(RepositoryException error) {
        this.error = error;
    }

    /**
     * 0:none; 1:getting; 2:refreshing; 3:loading
     */
    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public int getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(int requestPage) {
        this.requestPage = requestPage;
    }

    public int getOriginalPage() {
        return originalPage;
    }

    public void setOriginalPage(int originalPage) {
        this.originalPage = originalPage;
    }


    public RNLDataWrapper() {
    }

    public RNLDataWrapper(boolean success, @NonNull T data,
                          int operation, int requestPage, int originalPage) {
        this.data = data;
        this.success = success;
        this.operation = operation;
        this.requestPage = requestPage;
        this.originalPage = originalPage;
    }

    public RNLDataWrapper(boolean success, @NonNull RepositoryException error,
                          int operation, int requestPage, int originalPage) {
        this.error = error;
        this.success = success;
        this.operation = operation;
        this.requestPage = requestPage;
        this.originalPage = originalPage;
    }
}
