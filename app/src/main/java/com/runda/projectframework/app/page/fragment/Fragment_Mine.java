package com.runda.projectframework.app.page.fragment;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseLazyFragment;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventCode;
import com.runda.projectframework.app.page.viewmodel.ViewModel_MainPage_Mine;

/**
 * Created by Kongdq
 *
 * @date 2019/11/7
 * Description: 我的
 */
public class Fragment_Mine extends BaseLazyFragment<ViewModel_MainPage_Mine> {

    private String TAG = getClass().getSimpleName();

    public static Fragment_Mine newInstance() {
        Bundle args = new Bundle();
        Fragment_Mine fragment = new Fragment_Mine();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_mine;
    }
    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarColor(R.color.color_primary).keyboardEnable(true).init();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public ViewModel_MainPage_Mine initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_MainPage_Mine.class);
    }

    @Override
    public void initEvents() {

    }


    @Override
    public void start() {
        if (Constants.ISLOGIN) {
            getViewModel().getUserInfo();
        }
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case EventCode.LOGINOUT_PARENT:
                break;
            case EventCode.LOGININ_PARENT:
                break;
        }
    }

    @Override
    public void initNotSignEvent() {

    }

    @Override
    public void initNoNetworkEvent() {

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
