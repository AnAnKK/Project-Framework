package com.runda.projectframework.app.page.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseLazyFragment;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventCode;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/9 11:51
 * @Version:        1.0
 */
public class Fragment_Function extends BaseLazyFragment<BaseViewModel> {



    private String TAG = getClass().getSimpleName();

    public static Fragment_Function newInstance() {
        Bundle args = new Bundle();
        Fragment_Function fragment = new Fragment_Function();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_function;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarColor(R.color.color_primary).keyboardEnable(true).init();
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
//        showWaitingView(true,"title");
    }

    @Override
    public void onNetReload(View v) {

    }


    @Override
    public void start() {

    }



    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(Event event) {
        switch (event.getCode()) {
            case EventCode.SIGN_OUT:
            case EventCode.SIGN_IN:
                start();
                break;
        }
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
        });
    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }
}
