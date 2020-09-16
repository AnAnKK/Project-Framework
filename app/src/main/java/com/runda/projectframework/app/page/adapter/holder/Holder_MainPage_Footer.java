package com.runda.projectframework.app.page.adapter.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runda.projectframework.R;
import com.runda.projectframework.app.page.adapter.listener.RxItemClickEvent;
import com.runda.projectframework.app.page.adapter.listener.RxOnItemClickListener;
import com.runda.projectframework.utils.CheckEmptyUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 * @Description:    header1
 * @Author:         An_K
 * @CreateDate:     2020/9/11 15:40
 * @Version:        1.0
 */
public class Holder_MainPage_Footer {

    private View rootView;
    private ViewGroup viewGroup;
    private Context context;
    private LayoutInflater inflater;

    private List<String> data;
    private RxOnItemClickListener<String> clickListener;

    public Observable<RxItemClickEvent<String>> getClickListener() {
        if (clickListener == null) {
            clickListener = new RxOnItemClickListener<>();
        }
        return clickListener;
    }


    public Holder_MainPage_Footer(Context context, List<String> data,ViewGroup viewGroup) {
        this.data = data;
        this.context = context;
        this.viewGroup = viewGroup;
        this.inflater = LayoutInflater.from(context);
    }

    public void initialize() {
        if (CheckEmptyUtils.isEmpty(data)) {
            return;
        }
        rootView = inflater.inflate(R.layout.layout_footer, viewGroup, false);
        setup();
    }

    private void setup() {
        if (CheckEmptyUtils.isEmpty(data)) {
            return;
        }

        rootView.setOnClickListener(view -> clickListener.getListener().onItemClick(0, view, null));

        rootView.findViewById(R.id.image)
                .setOnClickListener(view -> {
                    clickListener.getListener().onItemClick(1, view, null);
                });
        rootView.findViewById(R.id.button)
                .setOnClickListener(view -> {
                    clickListener.getListener().onItemClick(2, view, null);
                });
    }

    public View getRootView() {
        return rootView;
    }





}
