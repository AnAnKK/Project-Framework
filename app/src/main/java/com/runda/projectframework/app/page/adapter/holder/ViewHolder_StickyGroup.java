package com.runda.projectframework.app.page.adapter.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.runda.projectframework.R;
import com.runda.projectframework.app.repository.bean.test.ListItem;
import com.runda.projectframework.app.repository.bean.test.StickyListItem;

import static com.runda.projectframework.app.page.adapter.Adapter_RecyclerSticky.VIEW_TYPE_NON_STICKY;

/**
 * @Description:
 * @Author: An_K
 * @CreateDate: 2020/9/16 8:30
 * @Version: 1.0
 */
public class ViewHolder_StickyGroup extends RecyclerView.ViewHolder {

    private TextView listTitle;
    private TextView stickyTitle;

    public ViewHolder_StickyGroup(View itemView,int viewType) {
        super(itemView);

        if(VIEW_TYPE_NON_STICKY == viewType){
            listTitle = itemView.findViewById(R.id.tv_title);
        }else {
            stickyTitle = itemView.findViewById(R.id.tv_stickyTitle);
        }
    }

    public void bind(ListItem item) {
        listTitle.setText(item.title);
    }

    public void bindSticky(StickyListItem stickyListItem){
        stickyTitle.setText(stickyListItem.getTitle());
    }
}
