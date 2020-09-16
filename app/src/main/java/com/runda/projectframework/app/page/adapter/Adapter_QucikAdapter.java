package com.runda.projectframework.app.page.adapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.runda.projectframework.R;
import com.runda.projectframework.app.page.adapter.listener.RxItemClickEvent;
import com.runda.projectframework.app.page.adapter.listener.RxOnItemClickListener;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/1 10:46
 * @Version:        1.0
 */
public class Adapter_QucikAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private RxOnItemClickListener<String> listener;

    public Adapter_QucikAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);

    }

    public Observable<RxItemClickEvent<String>> getRxOnItemClickListener() {
        if (listener == null) {
            listener = new RxOnItemClickListener<>();
        }
        return listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {


        helper.setText(R.id.text,item);
        if (listener == null) {
            helper.getView(R.id.cardView).setOnClickListener(null);
        } else {
            helper.getView(R.id.cardView).setOnClickListener(view ->
                    listener.getListener().onItemClick(helper.getAdapterPosition(), view, item));
        }

    }


}
