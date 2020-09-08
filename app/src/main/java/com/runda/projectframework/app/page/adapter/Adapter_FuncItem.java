package com.runda.projectframework.app.page.adapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runda.projectframework.R;
import com.runda.projectframework.app.page.adapter.listener.RxItemClickEvent;
import com.runda.projectframework.app.page.adapter.listener.RxOnItemClickListener;
import com.runda.projectframework.app.repository.bean.PageTextClzInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/1 10:46
 * @Version:        1.0
 */
public class Adapter_FuncItem extends BaseQuickAdapter<PageTextClzInfo, BaseViewHolder> {
    private RxOnItemClickListener<PageTextClzInfo> listener;

    public Adapter_FuncItem(int layoutResId, @Nullable List<PageTextClzInfo> data) {
        super(layoutResId, data);

    }

    public Observable<RxItemClickEvent<PageTextClzInfo>> getRxOnItemClickListener() {
        if (listener == null) {
            listener = new RxOnItemClickListener<>();
        }
        return listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, PageTextClzInfo item) {


        helper.setText(R.id.textview,item.getName());
        if (listener == null) {
            helper.getView(R.id.textview).setOnClickListener(null);
        } else {
            helper.getView(R.id.textview).setOnClickListener(view ->
                    listener.getListener().onItemClick(helper.getAdapterPosition(), view, item));
        }

    }


}
