package com.runda.projectframework.app.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.runda.projectframework.app.di.AppViewModelFactory;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.utils.EventBusUtil;
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
    public LoadService loadService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityUtils.getActivityList().add(this);
        setContentView(getLayout());
        unBinder = ButterKnife.bind(this);
        initImmersionBar();
        viewModel = initViewModel();
        if (isRegisterEventBus()) {EventBusUtil.register(this);}
        View viewRegisterLoadSir = getRegisterLoadSir();
        if(viewRegisterLoadSir !=null){loadService = LoadSir.getDefault().register(viewRegisterLoadSir, (Callback.OnReloadListener) this::onNetReload);}
        initNoNetworkEvent();
        initTokenOverTimeEvent();
        initShowOrDismissWaitingEvent();
        initEvents();
        start();
    }

    public void showWaitingView() { KProgressHUDUtil.showWaitingView(this);}

    public KProgressHUD getWaitingView(boolean cancelable, String title, String detailMsg, boolean hasBackGroudColor) { return KProgressHUDUtil.getWaitingView(this,cancelable,title,detailMsg,hasBackGroudColor);}

    public void hideWaitingView() { KProgressHUDUtil.hideWaitingView(); }

    protected void initImmersionBar() { ImmersionBar.with(this).init(); }

    /**
     * EventBus
     * @return
     */
    protected boolean isRegisterEventBus() { return false; }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(Event event) {}

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(Event event) {}


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

    public abstract void initNoNetworkEvent();

    public abstract void initTokenOverTimeEvent();

    public abstract void initShowOrDismissWaitingEvent();

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


}
