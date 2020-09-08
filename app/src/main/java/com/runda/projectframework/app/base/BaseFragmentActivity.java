package com.runda.projectframework.app.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.app.di.AppViewModelFactory;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventBusUtil;
import com.runda.projectframework.utils.KProgressHUDUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * author:  RD_YH
 * date:    2018/10/9
 * version: v1.0
 * description: 基础FragmentActivity
 */
public abstract class BaseFragmentActivity<T extends BaseViewModel> extends SupportActivity {

    @Inject
    AppViewModelFactory viewModelFactory;
    private T viewModel;
    protected Unbinder unBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityUtils.getActivityList().add(this);
        setContentView(getLayout());
        unBinder = ButterKnife.bind(this);
        initImmersionBar();
        viewModel = initViewModel();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        initEvents();
        initNoNetworkEvent();
        initStateLayoutEvent();
        initTokenOverTimeEvent();
        initShowOrDismissWaitingEvent();
        start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        if (unBinder != null) {
            unBinder.unbind();
        }
        ActivityUtils.getActivityList().remove(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
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

    protected void initImmersionBar() {
        ImmersionBar.with(this).init();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    public void showWaitingView(boolean cancelable, String message) {
        KProgressHUDUtil.showWaitingView(this,cancelable,message);
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


    public abstract void initNoNetworkEvent();

    public abstract void initTokenOverTimeEvent();

    public abstract void initShowOrDismissWaitingEvent();

    public abstract void initStateLayoutEvent();
}
