package com.runda.projectframework.app.repository;

import com.google.gson.annotations.SerializedName;

/**
 * author:  RD_CY
 * date:    2017/5/2
 * version: v1.0
 * description: 数据获取结果
 */
public class RepositoryResultNoData {

    @SerializedName("success")
    private boolean success;

    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RepositoryResultNoData() {
    }

    public RepositoryResultNoData(boolean success, int statusCode) {
        this.success = success;
        this.statusCode = statusCode;
    }

    public RepositoryResultNoData(boolean success, int statusCode, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
    }
}
