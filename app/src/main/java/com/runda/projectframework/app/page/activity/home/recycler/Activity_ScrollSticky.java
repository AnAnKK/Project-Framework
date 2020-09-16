package com.runda.projectframework.app.page.activity.home.recycler;

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
 * @Description:    SwipeRefreshLayout的使用
 * @Author:         An_K
 * @CreateDate:     2020/9/11 10:47
 * @Version:        1.0
 */
public class Activity_ScrollSticky extends BaseActivity<BaseViewModel> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public int getLayout() {
        return R.layout.activity_scrollsticky;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(toolbar).autoStatusBarDarkModeEnable(true,0.2f).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setNavigationOnClickListener(view -> finish());
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
