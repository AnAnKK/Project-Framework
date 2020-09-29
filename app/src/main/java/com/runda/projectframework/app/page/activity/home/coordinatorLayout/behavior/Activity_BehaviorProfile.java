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
 * @Description:    个人中心的behavior; 有问题,当下方scrollview内容少的时候无法滑动
 * @Author:         An_K
 * @CreateDate:     2020/9/29 8:48
 * @Version:        1.0
 */
public class Activity_BehaviorProfile extends BaseActivity<BaseViewModel> {

    private String TAG = "Activity_BehaviorProfile";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayout() {
        return R.layout.activity_profile_behavior;
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
//        ImmersionBar.with(this).titleBar(R.id.toolbar).fitsSystemWindows(true).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
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
