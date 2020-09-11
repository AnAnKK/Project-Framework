package com.runda.projectframework.app.page.activity.home.loadsir;

import android.view.View;
import android.widget.FrameLayout;

import androidx.lifecycle.ViewModelProviders;

import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseFragmentActivity;
import com.runda.projectframework.app.base.BaseViewModel;

import butterknife.BindView;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/9 11:51
 * @Version:        1.0
 */
public class Activity_LoadSirFragment extends BaseFragmentActivity<BaseViewModel> {


    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @Override
    public int getLayout() {
        return R.layout.layout_framelayout;
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
    }

    @Override
    public void start() {
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, Fragment_SirLoad.newInstance(1)).commit();
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
