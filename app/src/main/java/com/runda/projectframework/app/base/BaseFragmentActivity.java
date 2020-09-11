package com.runda.projectframework.app.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.runda.projectframework.app.di.AppViewModelFactory;
import com.runda.projectframework.utils.KProgressHUDUtil;
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
        initEvents();
        initNoNetworkEvent();
        initTokenOverTimeEvent();
        initShowOrDismissWaitingEvent();
        start();
    }

    public void showWaitingView() {
        KProgressHUDUtil.showWaitingView(this);
    }

    public KProgressHUD getWaitingView(boolean cancelable, String title, String detailMsg, boolean hasBackGroudColor) { return KProgressHUDUtil.getWaitingView(this,cancelable,title,detailMsg,hasBackGroudColor);}

    public void hideWaitingView() {
        KProgressHUDUtil.hideWaitingView();
    }

    protected void initImmersionBar() {
        ImmersionBar.with(this).init();
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

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
        ActivityUtils.getActivityList().remove(this);
    }
}
