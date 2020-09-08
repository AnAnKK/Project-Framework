package com.runda.projectframework.app.repository;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: 仓库错误
 */
public class RepositoryException extends Exception {

    private int errorCode;

    private String errorMessage;

    public RepositoryException(int code, String message) {
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
