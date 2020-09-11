package com.runda.projectframework.app.page.activity.home.loadsir;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.others.callback.NoSignCallback;
import com.runda.projectframework.app.others.callback.EmptyCallback;
import com.runda.projectframework.app.others.callback.ErrorCallback;
import com.runda.projectframework.app.others.callback.LoadingCallback;
import com.runda.projectframework.app.others.callback.NoNetWorkCallback;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.viewmodel.ViewModel_MainPage_Mine;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/8 17:01
 * @Version:        1.0
 */
public class Activity_LoadSirActivity extends BaseActivity<ViewModel_MainPage_Mine> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textView_empty)
    TextView textView_empty;

    @BindView(R.id.textView_error)
    TextView textView_error;

    @BindView(R.id.textView_noNetwork)
    TextView textView_noNetwork;

    @BindView(R.id.textView_notSigned)
    TextView textView_notSigned;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;


    @Override
    public int getLayout() {
        return R.layout.activity_loadsir;
    }

    @Override
    public View getRegisterLoadSir() {
        return linearLayout;
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(toolbar).autoStatusBarDarkModeEnable(true,0.2f).init();
    }

    @Override
    public ViewModel_MainPage_Mine initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_MainPage_Mine.class);
    }

    @Override
    public void initEvents() {
        toolbar.setNavigationOnClickListener(view -> finish());

        Disposable emptyClick = RxView.clicks(textView_empty)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    loadService.showCallback(EmptyCallback.class);
                });
        Disposable errorClick = RxView.clicks(textView_error)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    loadService.showCallback(ErrorCallback.class);
                });
        Disposable noNetWorkClick = RxView.clicks(textView_noNetwork)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    loadService.showCallback(NoNetWorkCallback.class);
                });
        Disposable customClick = RxView.clicks(textView_notSigned)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    loadService.showCallback(NoSignCallback.class);
                });
        getViewModel().getRxEventManager().addRxEvent(emptyClick);
        getViewModel().getRxEventManager().addRxEvent(errorClick);
        getViewModel().getRxEventManager().addRxEvent(noNetWorkClick);
        getViewModel().getRxEventManager().addRxEvent(customClick);

        setupUserInfoLiveData();

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
