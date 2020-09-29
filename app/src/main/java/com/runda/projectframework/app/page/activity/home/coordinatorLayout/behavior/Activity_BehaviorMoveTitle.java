package com.runda.projectframework.app.page.activity.home.coordinatorLayout.behavior;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;

import butterknife.BindView;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/29 17:31
 * @Version:        1.0
 */
public class Activity_BehaviorMoveTitle extends BaseActivity<BaseViewModel> {

    private String TAG = "Activity_BehaviorMoveTitle";


    @Override
    public int getLayout() {
        return R.layout.activity_behaviormovetitle;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
//        ImmersionBar.with(this).fitsSystemWindows(true).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
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

}
