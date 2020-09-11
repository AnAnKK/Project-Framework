package com.runda.projectframework.app.page.activity.home.loadsir;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseFragment;
import com.runda.projectframework.app.base.BaseLazyFragment;
import com.runda.projectframework.app.others.callback.ErrorCallback;
import com.runda.projectframework.app.others.callback.LoadingCallback;
import com.runda.projectframework.app.others.callback.NoNetWorkCallback;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.viewmodel.ViewModel_MainPage_Mine;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    多个页面左右互动,会显示出中间页面的布局;效果不好,超过3个页面建议子类中实现Sir
 * @Author:         An_K
 * @CreateDate:     2020/9/9 11:51
 * @Version:        1.0
 */
public class FragmentLazy_SirLoad extends BaseLazyFragment<ViewModel_MainPage_Mine> {


    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String TAG = getClass().getSimpleName();
    private int num;

    public static FragmentLazy_SirLoad newInstance(int num) {
        Bundle args = new Bundle();
        args.putInt("num",num);
        FragmentLazy_SirLoad fragment = new FragmentLazy_SirLoad();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num = getArguments().getInt("num");
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_sirload;
    }

    @Override
    public View getRegisterLoadSir() {
        return relativeLayout;
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).keyboardEnable(true).init();
    }

    @Override
    public ViewModel_MainPage_Mine initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_MainPage_Mine.class);
    }

    @Override
    public void initEvents() {
        button.setText("显示错误界面"+num);
        toolbar.setNavigationOnClickListener(view -> _mActivity.finish());
        Disposable errorPageClick = RxView.clicks(button)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    loadService.showCallback(ErrorCallback.class);
                });
        getViewModel().getRxEventManager().addRxEvent(errorPageClick);

        setupSirLoad();
        setupUserInfoLiveData();
    }

    private void setupSirLoad() {
        loadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void onNetReload(View v) {
        loadService.showCallback(LoadingCallback.class);
        getViewModel().getUserInfo();
    }


    @Override
    public void start() {
        getViewModel().getUserInfo();
    }

    private void setupUserInfoLiveData(){
        getViewModel().getUserInfoLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                loadService.showSuccess();
            } else {
                loadService.showCallback(ErrorCallback.class);
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }
        });
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
            loadService.showCallback(NoNetWorkCallback.class);
        });
    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }
}
