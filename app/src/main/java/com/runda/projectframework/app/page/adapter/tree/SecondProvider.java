package com.runda.projectframework.app.page.adapter.tree;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.runda.projectframework.R;
import com.runda.projectframework.app.others.interfaces.onTreeItemClick;
import com.runda.projectframework.app.repository.bean.test.tree.TreeNode;

import org.jetbrains.annotations.NotNull;

public class SecondProvider extends BaseNodeProvider {

    private onTreeItemClick listener;

    public void setonTreeItemClickListener(onTreeItemClick listener){
        this.listener = listener;
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        TreeNode entity = (TreeNode) data;
        helper.setText(R.id.title, entity.getClassifyName());

        if(entity.isHasChildren()){
            helper.setVisible(R.id.iv,true);
            if (entity.isExpanded()) {
                helper.setImageResource(R.id.iv, R.mipmap.arrow_b);
            } else {
                helper.setImageResource(R.id.iv, R.mipmap.arrow_r);
            }
        }else {
            helper.setVisible(R.id.iv,false);
        }

    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        TreeNode entity = (TreeNode) data;

        if(entity.isHasChildren()){
            if (entity.isExpanded()) {
                getAdapter().collapse(position);
            } else {
                getAdapter().expandAndCollapseOther(position);
            }
        }else {
            ToastUtils.showShort(entity.getClassifyName());
            listener.onClick(position,view,entity);
        }


    }
}
