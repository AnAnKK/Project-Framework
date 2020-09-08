package com.runda.projectframework.app.repository;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: 数据获取结果
 */
public class RepositoryResult<T> {

//    private boolean success;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String msg;

    private T data;

//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }

    public int getStatusCode() {
        return code;
    }

    public void setStatusCode(int statusCode) {
        this.code = statusCode;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RepositoryResult() {
    }

//    public RepositoryResult(boolean success, int statusCode) {
//        this.success = success;
//        this.code = statusCode;
//    }

//    public RepositoryResult(boolean success, int statusCode, String message) {
//        this.success = success;
//        this.code = statusCode;
//        this.msg = message;
//    }

    public RepositoryResult(int statusCode, String message) {
        this.code = statusCode;
        this.msg = message;
    }

}
