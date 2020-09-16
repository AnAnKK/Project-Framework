package com.runda.projectframework.app.page.activity.home.recycler;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.adapter.Adapter_QucikAdapter;
import com.runda.projectframework.app.page.adapter.holder.Holder_MainPage_Footer;
import com.runda.projectframework.app.page.adapter.holder.Holder_MainPage_Header1;
import com.runda.projectframework.app.page.adapter.holder.Holder_MainPage_Header2;
import com.runda.toolbar.RDToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * @Description: BaseRecyclerViewAdapterHelper 基本用法
 * header footer 动画效果
 * @Author: An_K
 * @CreateDate: 2020/9/11 10:47
 * @Version: 1.0
 */
public class Activity_QuickAdapter extends BaseActivity<BaseViewModel> {

    @BindView(R.id.toolbar)
    RDToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Adapter_QucikAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_recylist;
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
        toolbar.getTitleView().setText("BaseRecyclerViewAdapterHelper 基本用法");
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

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("第 " + i + " 条");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_QuickAdapter.this));
        adapter = new Adapter_QucikAdapter(R.layout.item_recyclerview, list);
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        recyclerView.setAdapter(adapter);
        Disposable event_itemClicked = adapter.getRxOnItemClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    ToastUtils.showShort(event.data());
                });
        getViewModel().getRxEventManager().addRxEvent(event_itemClicked);

        initHeader1();
        initHeader2();
        initFooter();
    }

    private void initHeader1() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("第 " + i + " 条");
        }
        Holder_MainPage_Header1 header1 = new Holder_MainPage_Header1(Activity_QuickAdapter.this, list,recyclerView);
        Disposable header1Click = header1.getClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    if (event.position() == 0) {
                        ToastUtils.showShort("header1 根布局");
                    }
                    if (event.position() == 1) {
                        ToastUtils.showShort("header1 图片");
                    }
                    if (event.position() == 2) {
                        ToastUtils.showShort("header1 button");
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(header1Click);
        header1.initialize();
        if (header1.getRootView() != null) {
            adapter.addHeaderView(header1.getRootView(), 0);
        }
    }

    private void initHeader2() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("第 " + i + " 条");
        }
        Holder_MainPage_Header2 header2 = new Holder_MainPage_Header2(Activity_QuickAdapter.this, list,recyclerView);
        Disposable header1Click = header2.getClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    if (event.position() == 0) {
                        ToastUtils.showShort("header2 根布局");
                    }
                    if (event.position() == 1) {
                        ToastUtils.showShort("header2 图片");
                    }
                    if (event.position() == 2) {
                        ToastUtils.showShort("header2 button");
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(header1Click);
        header2.initialize();
        if (header2.getRootView() != null) {
            adapter.addHeaderView(header2.getRootView(), 1);
        }
    }


    private void initFooter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("第 " + i + " 条");
        }
        Holder_MainPage_Footer footer = new Holder_MainPage_Footer(Activity_QuickAdapter.this, list,recyclerView);
        Disposable header1Click = footer.getClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    if (event.position() == 0) {
                        ToastUtils.showShort("Footer 根布局");
                    }
                    if (event.position() == 1) {
                        ToastUtils.showShort("Footer 图片");
                    }
                    if (event.position() == 2) {
                        ToastUtils.showShort("Footer button");
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(header1Click);
        footer.initialize();
        if (footer.getRootView() != null) {
            adapter.addFooterView(footer.getRootView());
        }
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
