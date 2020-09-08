package com.runda.projectframework.app.page.adapter.listener;

import android.view.View;

/**
 * Created by Kongdq
 * @date 2019/11/8
 * Description:
 */
public class RxItemClickEvent<T> {

    private final View view;
    private final int position;
    private final T data;

    public RxItemClickEvent(View view, int position, T data) {
        this.view = view;
        this.position = position;
        this.data = data;
    }

    public View view() {
        return view;
    }

    public int position() {
        return position;
    }

    public T data() {
        return data;
    }
}
