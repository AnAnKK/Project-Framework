package com.runda.projectframework.app.repository.live.holder;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: HUD信息持有者
 */
public class WaitingHolder {

    private boolean show = false;

    private String message = "正在获取信息";

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WaitingHolder(boolean show) {
        this.show = show;
    }

    public WaitingHolder(boolean show, String message) {
        this.show = show;
        this.message = message;
    }
}
