package com.runda.projectframework.app.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.runda.projectframework.R;
import com.runda.projectframework.app.di.AppViewModelFactory;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.utils.EventBusUtil;
import com.runda.projectframework.utils.KProgressHUDUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 * @Description:    懒加载Fragment,滑动到当前页面才加载数据并且不会重复加载;用于和viewpage以及tab绑定时
 * @Author:         An_K
 * @CreateDate:     2020/9/9 17:42
 * @Version:        1.0
 */

public abstract class BaseLazyFragment<T extends BaseViewModel> extends SupportFragment {

    @Inject
    AppViewModelFactory viewModelFactory;

    public boolean isVisible = false;
    public boolean isInit = false;
    public boolean isLoadOver = false;
    private T viewModel;
    private Unbinder unBinder;
    public LoadService loadService;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisible = isVisibleToUser;
        setParam();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = initViewModel();
        isInit = true;
        View rootView = inflater.inflate(getLayout(), container, false);
        unBinder = ButterKnife.bind(this, rootView);
        View viewRegisterLoadSir = getRegisterLoadSir();
        if(viewRegisterLoadSir !=null){
            loadService = LoadSir.getDefault().register(viewRegisterLoadSir, (Callback.OnReloadListener) this::onNetReload);
            return loadService.getLoadLayout();
        }else {
            return rootView;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View titleBar = view.findViewById(setTitleBar());
        ImmersionBar.setTitleBar(_mActivity, titleBar);
        View statusBarView = view.findViewById(setStatusBarView());
        ImmersionBar.setStatusBarView(_mActivity, statusBarView);
        setParam();
    }

    private void setParam() {
        if (isInit && !isLoadOver && isVisible) {
            isLoadOver = true;
            start();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initEvents();
        initNotSignEvent();
        initNoNetworkEvent();
        initTokenOverTimeEvent();
        initShowOrDismissWaitingEvent();
    }

    public void showWaitingView() { KProgressHUDUtil.showWaitingView(_mActivity); }

    public KProgressHUD getWaitingView(boolean cancelable, String title, String detailMsg, boolean hasBackGroudColor) { return KProgressHUDUtil.getWaitingView(_mActivity,cancelable,title,detailMsg,hasBackGroudColor);}


    public void hideWaitingView() {
        KProgressHUDUtil.hideWaitingView();
    }

    public void initImmersionBar() {
        ImmersionBar.with(this).init();
    }

    private boolean isImmersionBarEnabled() {
        return true;
    }

    protected int setTitleBar() {
        return R.id.toolbar;
    }

    protected int setStatusBarView() {
        return 0;
    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(Event event) {}

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(Event event) {}

    public T getViewModel() {
        return viewModel;
    }

    public AppViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    public abstract int getLayout();

    public abstract View getRegisterLoadSir();

    public abstract T initViewModel();

    public abstract void initEvents();

    public abstract void onNetReload(View v);

    public abstract void start();

    public abstract void initNotSignEvent();

    public abstract void initNoNetworkEvent();

    public abstract void initTokenOverTimeEvent();

    public abstract void initShowOrDismissWaitingEvent();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null) { unBinder.unbind();}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) { EventBusUtil.unregister(this);}
    }
}
