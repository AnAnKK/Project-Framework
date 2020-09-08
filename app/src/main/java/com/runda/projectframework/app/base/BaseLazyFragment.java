package com.runda.projectframework.app.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.di.AppViewModelFactory;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventBusUtil;
import com.runda.projectframework.utils.KProgressHUDUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: 懒加载Fragment,滑动到当前页面才加载数据并且不会重复加载
 */

public abstract class BaseLazyFragment<T extends BaseViewModel> extends SupportFragment {

    @Inject
    AppViewModelFactory viewModelFactory;

    public boolean isVisible = false;
    public boolean isInit = false;
    public boolean isLoadOver = false;
    private T viewModel;
    private Unbinder unBinder;

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
        return inflater.inflate(getLayout(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unBinder = ButterKnife.bind(this, view);
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
        initStateLayoutEvent();
        initTokenOverTimeEvent();
        initShowOrDismissWaitingEvent();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null) {
            unBinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    protected void receiveEvent(Event event) {}

    protected void receiveStickyEvent(Event event) {}

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

    public void showWaitingView(boolean cancelable, String message) {
        KProgressHUDUtil.showWaitingView(_mActivity,cancelable,message);
    }

    public void hideWaitingView() {
        KProgressHUDUtil.hideWaitingView();
    }

    public T getViewModel() {
        return viewModel;
    }

    public AppViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    public abstract int getLayout();

    public abstract T initViewModel();

    public abstract void initEvents();


    public abstract void start();

    public abstract void initNotSignEvent();

    public abstract void initNoNetworkEvent();

    public abstract void initTokenOverTimeEvent();

    public abstract void initShowOrDismissWaitingEvent();

    public abstract void initStateLayoutEvent();
}
