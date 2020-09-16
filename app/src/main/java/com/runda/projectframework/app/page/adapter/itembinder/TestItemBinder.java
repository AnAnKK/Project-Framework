package com.runda.projectframework.app.page.adapter.itembinder;


import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.binder.QuickItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.runda.projectframework.R;
import com.runda.projectframework.app.page.adapter.listener.RxMultipleViewItemClickEvent;
import com.runda.projectframework.app.page.adapter.listener.RxOnMultipleViewItemClickListener;
import com.runda.projectframework.app.repository.bean.test.TestInfo1;
import com.runda.projectframework.app.repository.bean.test.TestInfo2;
import com.runda.projectframework.app.repository.bean.test.TestInfo3;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/1 10:46
 * @Version:        1.0
 */
public class TestItemBinder {

    public static RxOnMultipleViewItemClickListener<Object> listener;

    public static Observable<RxMultipleViewItemClickEvent<Object>> getRxOnItemClickListener() {
        if (listener == null) {
            listener = new RxOnMultipleViewItemClickListener<>();
        }
        return listener;
    }

    public static class Test1ItemBinder extends QuickItemBinder<TestInfo1> {

        @Override
        public int getLayoutId() {
            return R.layout.item_test1;
        }

        @Override
        public void convert(@NotNull BaseViewHolder holder, TestInfo1 data) {
            if (listener == null) {
                holder.getView(R.id.textview).setOnClickListener(null);
            } else {
                holder.getView(R.id.textview).setOnClickListener(view ->
                        listener.getListener().onItemClick(holder.getAdapterPosition(),0, view, data));
            }
        }

        @Override
        public void onClick(@NotNull BaseViewHolder holder, @NotNull View view, TestInfo1 data, int position) {
            Toast.makeText(getContext(), "click index: " + position, Toast.LENGTH_SHORT).show();
        }
    }

    public static class Test2ItemBinder extends QuickItemBinder<TestInfo2> {

        @Override
        public int getLayoutId() {
            return R.layout.item_test2;
        }

        @Override
        public void convert(@NotNull BaseViewHolder holder, TestInfo2 data) {
            if (listener == null) {
                holder.getView(R.id.image).setOnClickListener(null);
            } else {
                holder.getView(R.id.image).setOnClickListener(view ->
                        listener.getListener().onItemClick(holder.getAdapterPosition(),1, view, data));
            }
        }

        @Override
        public void onClick(@NotNull BaseViewHolder holder, @NotNull View view, TestInfo2 data, int position) {
            Toast.makeText(getContext(), "click index: " + position, Toast.LENGTH_SHORT).show();
        }
    }


    public static class Test3ItemBinder extends QuickItemBinder<TestInfo3> {

        @Override
        public int getLayoutId() {
            return R.layout.item_test3;
        }

        @Override
        public void convert(@NotNull BaseViewHolder holder, TestInfo3 data) {
            if (listener == null) {
                holder.getView(R.id.button1).setOnClickListener(null);
                holder.getView(R.id.button2).setOnClickListener(null);
            } else {
                holder.getView(R.id.button1).setOnClickListener(view ->
                        listener.getListener().onItemClick(holder.getAdapterPosition(),2, view, data));
                holder.getView(R.id.button2).setOnClickListener(view ->
                        listener.getListener().onItemClick(holder.getAdapterPosition(),3, view, data));
            }
        }

        @Override
        public void onClick(@NotNull BaseViewHolder holder, @NotNull View view, TestInfo3 data, int position) {
            Toast.makeText(getContext(), "click index: " + position, Toast.LENGTH_SHORT).show();
        }
    }

}
