package com.runda.projectframework.app.page.adapter.listener;

import android.view.View;

/**
 * author: RD_CY
 * date: 2017/2/16
 * author: RD_YH
 * date: 2018/10/10
 * version: 1.0
 * description: 点击事件
 */
public class RxMultipleViewItemClickEvent<T> {

    private final View view;
    private final int position;
    private final int which;
    private final T data;

    public RxMultipleViewItemClickEvent(View view, int position, int which, T data) {
        this.view = view;
        this.position = position;
        this.which = which;
        this.data = data;
    }

    public View view() {
        return view;
    }

    public int position() {
        return position;
    }

    public int which() {
        return which;
    }

    public T data() {
        return data;
    }
}
