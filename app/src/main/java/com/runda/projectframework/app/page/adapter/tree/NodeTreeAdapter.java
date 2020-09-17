package com.runda.projectframework.app.page.adapter.tree;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.runda.projectframework.app.others.interfaces.onTreeItemClick;
import com.runda.projectframework.app.repository.bean.test.tree.TreeNode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NodeTreeAdapter extends BaseNodeAdapter {

    private onTreeItemClick listener;

    //
//    @Override
//    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
//        BaseNode node = data.get(position);
//        if (node instanceof FirstNode) {
//            return 1;
//        } else if (node instanceof SecondNode) {
//            return 2;
//        } else if (node instanceof ThirdNode) {
//            return 3;
//        }
//        return -1;
//    }

    public void setonTreeItemClickListener(onTreeItemClick listener){
        this.listener = listener;
    }

    public NodeTreeAdapter() {
        super();
        FirstProvider firstProvider = new FirstProvider();
        SecondProvider secondProvider = new SecondProvider();
        ThirdProvider thirdProvider = new ThirdProvider();
        addNodeProvider(firstProvider);
        addNodeProvider(secondProvider);
        addNodeProvider(thirdProvider);
        secondProvider.setonTreeItemClickListener((position, view, data) -> listener.onClick(position,view,data));
        thirdProvider.setonTreeItemClickListener((position, view, data) -> listener.onClick(position,view,data));
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        TreeNode node = (TreeNode) data.get(position);
        if (node.getLevel() == 1) {
            return 1;
        } else if (node.getLevel() == 2) {
            return 2;
        } else if (node.getLevel() == 3) {
            return 3;
        }
        return -1;
    }

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

}
