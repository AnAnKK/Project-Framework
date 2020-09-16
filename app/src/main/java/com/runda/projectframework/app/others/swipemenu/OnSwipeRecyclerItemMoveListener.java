package com.runda.projectframework.app.others.swipemenu;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.Collections;
import java.util.List;

/**
 *
 * @Description:    长按拖拽
 * @Author:         An_K
 * @CreateDate:     2020/9/14 9:32
 * @Version:        1.0
 */
public class OnSwipeRecyclerItemMoveListener<T> implements OnItemMoveListener {
    private BaseQuickAdapter adapter;
    private List<T> list;

    public OnSwipeRecyclerItemMoveListener(List<T> list,BaseQuickAdapter adapter) {
        this.list = list;
        this.adapter = adapter;
    }

    @Override
    public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
        int fromPosition = srcHolder.getAdapterPosition();
        int toPosition = targetHolder.getAdapterPosition();
        Collections.swap(list, fromPosition, toPosition);
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
        int position = srcHolder.getAdapterPosition();
        list.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
