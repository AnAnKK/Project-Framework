package com.runda.projectframework.app.page.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseLazyFragment;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventCode;

/**
 * Created by Kongdq
 *
 * @date 2019/12/4
 * Description: 家长端任务
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
    }


    @Override
    public void start() {

    }



    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case EventCode.LOGINOUT_PARENT:
            case EventCode.LOGININ_PARENT:
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
//            getNoNetworkAlerter().show();
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
