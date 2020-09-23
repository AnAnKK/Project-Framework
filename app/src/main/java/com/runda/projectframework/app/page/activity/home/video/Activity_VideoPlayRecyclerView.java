package com.runda.projectframework.app.page.activity.home.video;

import android.widget.FrameLayout;
import androidx.lifecycle.ViewModelProviders;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseFragmentActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.page.fragment.home.Fragment_VideoPlayRecyclerView;
import butterknife.BindView;

/**
 *
 * @Description:    dkplayer 简单的视频播放,用的时候再去找吧,简单
 * @Author:         An_K
 * @CreateDate:     2020/9/18 16:20
 * @Version:        1.0
 */
public class Activity_VideoPlayRecyclerView extends BaseFragmentActivity<BaseViewModel> {

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
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, Fragment_VideoPlayRecyclerView.newInstance()).commit();
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
