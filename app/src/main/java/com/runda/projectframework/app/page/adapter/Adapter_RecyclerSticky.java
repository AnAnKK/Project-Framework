package com.runda.projectframework.app.page.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.runda.projectframework.R;
import com.runda.projectframework.app.page.adapter.holder.ViewHolder_StickyGroup;
import com.runda.projectframework.app.page.adapter.listener.RxItemClickEvent;
import com.runda.projectframework.app.page.adapter.listener.RxOnItemClickListener;
import com.runda.projectframework.app.repository.bean.test.ListItem;
import com.runda.projectframework.app.repository.bean.test.StickyListItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/1 10:46
 * @Version:        1.0
 */
public class Adapter_RecyclerSticky extends RecyclerView.Adapter<ViewHolder_StickyGroup> {

    private List<ListItem> mListItems = new ArrayList<>();

    private RxOnItemClickListener<String> listener;

    public Observable<RxItemClickEvent<String>> getRxOnItemClickListener() {
        if (listener == null) {
            listener = new RxOnItemClickListener<>();
        }
        return listener;
    }

    public static final int VIEW_TYPE_NON_STICKY = R.layout.item_menu_main;
    public static final int VIEW_TYPE_STICKY = R.layout.item_menu_sticky;


    @NonNull
    @Override
    public ViewHolder_StickyGroup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);
        return new ViewHolder_StickyGroup(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_StickyGroup holder, int position) {
        if (mListItems.get(position) instanceof StickyListItem){
            holder.bindSticky((StickyListItem) mListItems.get(position));
        }else {
            holder.bind(mListItems.get(position));
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (mListItems.get(position) instanceof StickyListItem) {
            return VIEW_TYPE_STICKY;
        }
        return VIEW_TYPE_NON_STICKY;
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public void setListItems() {
        mListItems.clear();

        for (int m = 0; m < 10; m++) {
            for (int i = 0; i < 4; i++) {
                mListItems.add(new ListItem("item == "+m+"   "+i));
            }
            mListItems.add(new StickyListItem("StickItem =="+m));
        }
        notifyDataSetChanged();
    }
}
