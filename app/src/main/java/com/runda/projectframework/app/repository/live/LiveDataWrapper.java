package com.runda.projectframework.app.repository.live;


import androidx.annotation.NonNull;

import com.runda.projectframework.app.repository.RepositoryException;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: 数据Wrapper
 */
public class LiveDataWrapper<T> {

    private boolean success;

    private T data;

    private String message;

    private RepositoryException error;


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public LiveDataWrapper() {
    }


    public LiveDataWrapper(boolean success, @NonNull RepositoryException error) {
        this.success = success;
        this.error = error;
    }

    public LiveDataWrapper(boolean success, T data, String msg) {
        this.success = success;
        this.data = data;
        this.message = msg;
    }
}
