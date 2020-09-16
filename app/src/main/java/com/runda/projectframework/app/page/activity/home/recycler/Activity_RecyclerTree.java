package com.runda.projectframework.app.page.activity.home.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.interfaces.onTreeItemClick;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.adapter.tree.NodeTreeAdapter;
import com.runda.projectframework.app.repository.bean.test.tree.TreeInfoAll;
import com.runda.projectframework.app.repository.bean.test.tree.TreeNode;
import com.runda.projectframework.utils.LogUtil;
import com.runda.toolbar.RDToolbar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    BaseRecyclerViewAdapterHelper
 * @Author:         An_K
 * @CreateDate:     2020/9/11 10:48
 * @Version:        1.0
 */
public class Activity_RecyclerTree extends BaseActivity<BaseViewModel> {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.toolbar)
    RDToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayout() {
        return R.layout.activity_list;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_primary).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.getTitleView().setText("BaseRecyclerViewAdapterHelper树形");
        Disposable toolBarClick = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> finish());

        getViewModel().getRxEventManager().addRxEvent(toolBarClick);
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
        String s = ResourceUtils.readAssets2String("TreeData.txt");
        TreeInfoAll treeInfoAll = GsonUtils.fromJson(s, TreeInfoAll.class);
        List<TreeNode> data = treeInfoAll.getData();

        List<BaseNode> nodeListData = getNodeListData(data);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NodeTreeAdapter adapter = new NodeTreeAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setList(nodeListData);
        adapter.setonTreeItemClickListener(new onTreeItemClick() {
            @Override
            public void onClick(int position, View view, Object data) {
                TreeNode treeNode = (TreeNode)data;
                ToastUtils.showShort("position == "+position+"   "+treeNode.getClassifyName());
            }
        });
        LogUtil.e(TAG,s);

    }


    private List<BaseNode> getNodeListData( List<TreeNode> listData) {
        List<BaseNode> list = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {

            List<BaseNode> secondNodeList = new ArrayList<>();
            TreeNode firstNodeData = listData.get(i);
            for (int m = 0; m < firstNodeData.getChildren().size(); m++) {

                TreeNode secondNodeData = firstNodeData.getChildren().get(m);
                List<BaseNode> thirdNodeList = new ArrayList<>();
                thirdNodeList.addAll(secondNodeData.getChildren());
                secondNodeData.setExpanded(false);
                secondNodeData.setChildNodes(thirdNodeList);
                secondNodeList.add(secondNodeData);
            }

            firstNodeData.setChildNodes(secondNodeList);

            firstNodeData.setExpanded(false);

            list.add(firstNodeData);
        }
        return list;
    }

    @Override
    public void initNoNetworkEvent() {

    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }

}
