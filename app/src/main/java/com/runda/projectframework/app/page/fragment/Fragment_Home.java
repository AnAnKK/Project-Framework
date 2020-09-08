package com.runda.projectframework.app.page.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseLazyFragment;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.activity.home.Activity_Popup;
import com.runda.projectframework.app.page.activity.home.immersionbar.Activity_ImmersionBarSlideTrans;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_Profile;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_RefreshBasic;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WebView;
import com.runda.projectframework.app.page.activity.Activity_FuncList;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WeiboPage;
import com.runda.projectframework.app.page.adapter.Adapter_FuncItem;
import com.runda.projectframework.app.page.viewmodel.ViewModel_MainPage_Home;
import com.runda.projectframework.app.repository.bean.PageTextClzInfo;
import com.runda.projectframework.utils.ApkUpdateUtils;
import com.runda.projectframework.utils.IntentUtil;
import com.scwang.smart.refresh.header.FalsifyFooter;
import com.scwang.smart.refresh.header.FalsifyHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by Kongdq
 * @date 2019/11/7
 * Description: 首页
 */
public class Fragment_Home extends BaseLazyFragment<ViewModel_MainPage_Home> {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static final String SmartRefreshLayout = "SmartRefreshLayout";
    public static final String ImmersionBarString = "ImmersionBar";
    public static final String PopString = "Pop";

    private String TAG = getClass().getSimpleName();

    public static Fragment_Home newInstance() {
        Bundle args = new Bundle();
        Fragment_Home fragment = new Fragment_Home();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarColor(R.color.color_primary).fitsSystemWindows(true).keyboardEnable(true).init();
    }

    @Override
    public ViewModel_MainPage_Home initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_MainPage_Home.class);
    }

    @Override
    public void initEvents() {
//        版本更新
        ApkUpdateUtils.appUpdate(_mActivity,Constants.APP_UPDATEURL,false);
    }

    @Override
    public void start() {
        setData();
    }

    private void setData() {
        List<PageTextClzInfo> list = new ArrayList<>();
        list.add(new PageTextClzInfo(SmartRefreshLayout, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(ImmersionBarString, Activity_ImmersionBarSlideTrans.class.getSimpleName()));
        list.add(new PageTextClzInfo(PopString, Activity_FuncList.class.getSimpleName()));
        refreshLayout.setRefreshHeader(new FalsifyHeader(_mActivity));
        refreshLayout.setRefreshFooter(new FalsifyFooter(_mActivity));
        refreshLayout.setEnableLoadMore(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(_mActivity,VERTICAL));
        Adapter_FuncItem adapter = new Adapter_FuncItem(R.layout.item_text,list);
        Disposable event_itemClicked = adapter.getRxOnItemClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    if (refreshLayout.getState() != RefreshState.None) {
                        return;
                    }

                    switch (event.data().getName()){
                        case SmartRefreshLayout:
                            List<PageTextClzInfo> list1 = new ArrayList<>();
                            list1.add(new PageTextClzInfo("WebView", Activity_WebView.class.getName()));
                            list1.add(new PageTextClzInfo("WeiBoPage", Activity_WeiboPage.class.getName()));
                            list1.add(new PageTextClzInfo("个人中心", Activity_Profile.class.getName()));
                            list1.add(new PageTextClzInfo("基本使用", Activity_RefreshBasic.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list1);
                                    intent.putExtra("title",SmartRefreshLayout);
                                }
                            });
                            break;
                        case ImmersionBarString:
                            List<PageTextClzInfo> list2 = new ArrayList<>();
                            list2.add(new PageTextClzInfo("滑动透明状态栏", Activity_ImmersionBarSlideTrans.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list2);
                                    intent.putExtra("title",ImmersionBarString);
                                }
                            });
                            break;
                        case PopString:
                            IntentUtil.startActivity(_mActivity,Activity_Popup.class);
                            break;


                    }
                });
        getViewModel().getRxEventManager().addRxEvent(event_itemClicked);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initNotSignEvent() {

    }


    @Override
    public void initNoNetworkEvent() {
        getViewModel().getNoNetworkLiveData().observe(this, message -> {
            if (message == null) {
                return;
            }
//            hideWaitingView();
        });
    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }

    @Override
    public void initStateLayoutEvent() {
    }


}
