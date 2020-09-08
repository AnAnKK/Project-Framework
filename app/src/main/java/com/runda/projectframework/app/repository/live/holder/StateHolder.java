package com.runda.projectframework.app.repository.live.holder;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: 状态信息持有者
 */
public class StateHolder {

    private int stateCode;

    private String message;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StateHolder() {
    }

    public StateHolder(int stateCode, String message) {
        this.stateCode = stateCode;
        this.message = message;
    }
}
