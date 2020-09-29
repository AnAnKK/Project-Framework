package com.runda.projectframework.app.page.activity.home.coordinatorLayout.behavior;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

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
 * @CreateDate:     2020/9/29 8:48
 * @Version:        1.0
 */
public class Activity_BehaviorSearch extends BaseActivity<BaseViewModel> {

    private String TAG = "Activity_BehaviorSearch";

    @BindView(R.id.layout_search)
    LinearLayout layout_search;

    @Override
    public int getLayout() {
        return R.layout.activity_search_behavior;
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
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        layout_search.setBackgroundColor(Color.argb(0, 51, 170, 255));
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
