package com.runda.projectframework.app.page;


import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.runda.projectframework.R;
import com.runda.projectframework.app.others.GlideApp;
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
public class Adapter_TransImage extends BaseQuickAdapter<String, BaseViewHolder> {
    private RxOnItemClickListener<String> listener;

    public Adapter_TransImage(int layoutResId, @Nullable List<String> data) {
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

        ImageView imageView = helper.getView(R.id.iv_thum);
        GlideApp.with(getContext())
                .load(item)
                .error(R.drawable.icon_image_error)
                .placeholder(R.drawable.icon_image_default)
                .into(imageView);

        if (listener == null) {
            helper.getView(R.id.iv_thum).setOnClickListener(null);
        } else {
            helper.getView(R.id.iv_thum).setOnClickListener(view ->
                    listener.getListener().onItemClick(helper.getAdapterPosition(), view, item));
        }

    }


}
