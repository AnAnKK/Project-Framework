package com.runda.projectframework.app.page.adapter.tree;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.runda.projectframework.R;
import com.runda.projectframework.app.others.interfaces.onTreeItemClick;
import com.runda.projectframework.app.repository.bean.test.tree.TreeNode;

import org.jetbrains.annotations.NotNull;

public class ThirdProvider extends BaseNodeProvider {

    private onTreeItemClick listener;

    public void setonTreeItemClickListener(onTreeItemClick listener){
        this.listener = listener;
    }

    @Override
    public int getItemViewType() {
        return 3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_third;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        TreeNode entity = (TreeNode) data;
        helper.setText(R.id.title, entity.getClassifyName());
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        super.onClick(helper, view, data, position);
        listener.onClick(position,view,data);
    }
}
