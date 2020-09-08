package com.runda.projectframework.app.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
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
import dagger.android.AndroidInjection;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description:基础Activity
 */

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    @Inject
    AppViewModelFactory viewModelFactory;

    private T viewModel;
    private Unbinder unBinder;

    private KProgressHUD waitingView;


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
        initNoNetworkEvent();
        initStateLayoutEvent();
        initTokenOverTimeEvent();
        initShowOrDismissWaitingEvent();
        initEvents();
        start();
    }

    /**
     * 是否注册EventBus,默认为false
     * @return
     */
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

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

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

    public void showWaitingView(boolean cancelable, String message) {
        KProgressHUDUtil.showWaitingView(this,cancelable,message);
    }

    public void hideWaitingView() {
        KProgressHUDUtil.hideWaitingView();
    }

    protected void initImmersionBar() {
        ImmersionBar.with(this).init();
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
