package com.runda.projectframework.app.page.adapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.runda.projectframework.R;
import com.runda.projectframework.app.page.adapter.listener.RxMultipleViewItemClickEvent;
import com.runda.projectframework.app.page.adapter.listener.RxOnMultipleViewItemClickListener;

import java.util.List;

import io.reactivex.Observable;


/**
 *
 * @Description:    自定义侧边栏点击事件
 * @Author:         An_K
 * @CreateDate:     2020/9/14 11:37
 * @Version:        1.0
 */
public class Adapter_SwipeDefined extends BaseQuickAdapter<String, BaseViewHolder> {

    private RxOnMultipleViewItemClickListener<String> listener;

    public Adapter_SwipeDefined(@Nullable List<String> data) {
        super(R.layout.item_menu_define, data);
    }

    public Observable<RxMultipleViewItemClickEvent<String>> getRxOnItemClickListener() {
        if (listener == null) {
            listener = new RxOnMultipleViewItemClickListener<>();
        }
        return listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String addressInfo) {
        if (listener == null) {
            helper.getView(R.id.content_view).setOnClickListener(null);
            helper.getView(R.id.btn_start).setOnClickListener(null);
            helper.getView(R.id.left_view).setOnClickListener(null);
            helper.getView(R.id.right_view).setOnClickListener(null);
        } else {
            helper.getView(R.id.content_view).setOnClickListener(view -> {
                listener.getListener().onItemClick(helper.getAdapterPosition(),0, view, addressInfo);
            });
            helper.getView(R.id.btn_start).setOnClickListener(view -> {
                listener.getListener().onItemClick(helper.getAdapterPosition(),2, view, addressInfo);
            });
            helper.getView(R.id.left_view).setOnClickListener(view -> {
                listener.getListener().onItemClick(helper.getAdapterPosition(),-1, view, addressInfo);
            });
            helper.getView(R.id.right_view).setOnClickListener(view -> {
                listener.getListener().onItemClick(helper.getAdapterPosition(),1, view, addressInfo);
            });
        }


    }
}
