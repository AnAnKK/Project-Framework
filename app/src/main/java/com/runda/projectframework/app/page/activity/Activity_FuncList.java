package com.runda.projectframework.app.page.activity;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.adapter.Adapter_FuncItem;
import com.runda.projectframework.app.repository.bean.PageTextClzInfo;
import com.runda.toolbar.RDToolbar;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/11 9:26
 * @Version:        1.0
 */
public class Activity_FuncList extends BaseActivity<BaseViewModel> {

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
    protected void onResume() {
        super.onResume();
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
        toolbar.getTitleView().setText(getIntent().getStringExtra("title"));
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
        List<PageTextClzInfo> flist = getIntent().getParcelableArrayListExtra("list");
        setData(flist);
    }

    private void setData(List<PageTextClzInfo> flist) {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableLoadMore(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        Adapter_FuncItem adapter = new Adapter_FuncItem(R.layout.item_text,flist);
        Disposable event_itemClicked = adapter.getRxOnItemClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    if (refreshLayout.getState() != RefreshState.None) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClassName(this,event.data().getClazzName());
                    startActivity(intent);
                });
        getViewModel().getRxEventManager().addRxEvent(event_itemClicked);
        recyclerView.setAdapter(adapter);
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
