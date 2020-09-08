package com.runda.projectframework.app.page;

import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseFragmentActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.page.adapter.Adapter_MainPage;
import com.runda.projectframework.app.page.fragment.Fragment_Function;
import com.runda.projectframework.app.page.fragment.Fragment_Home;
import com.runda.projectframework.app.page.fragment.Fragment_Mine;
import com.runda.projectframework.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Kongdq
 * @date 2019/11/1
 * Description: 首页
 */
public class Activity_MainPage extends BaseFragmentActivity<BaseViewModel> implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.bottomBar_mainPage)
    BottomNavigationBar bottomBar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private String TAG = getClass().getSimpleName();

    @Override
    public int getLayout() {
        return R.layout.activity_mainpage;
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }


    @Override
    public void initEvents() {
        setupBottomBar();
        setupViewPager();
    }

    @Override
    public void start() {
    }



    private void setupBottomBar() {
        //添加item
        bottomBar.setTabSelectedListener(this);
        bottomBar.clearAll();
        bottomBar.addItem(
                new BottomNavigationItem(R.drawable.icon_main_tab_main_selected, R.string.homePage)
                        .setInactiveIconResource(R.drawable.icon_main_tab_main))
                .addItem(new BottomNavigationItem(R.drawable.icon_main_tab_function_selected, R.string.function)
                        .setInactiveIconResource(R.drawable.icon_main_tab_function))
                .addItem(new BottomNavigationItem(R.drawable.icon_main_tab_mine_selected, R.string.mine)
                        .setInactiveIconResource(R.drawable.icon_main_tab_mine))
        .initialise();
        bottomBar.setFirstSelectedPosition(0);
    }

    private void setupViewPager() {

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(Fragment_Home.newInstance());
        fragments.add(Fragment_Function.newInstance());
        fragments.add(Fragment_Mine.newInstance());
        viewPager.setAdapter(new Adapter_MainPage(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void initNoNetworkEvent() {
        getViewModel().getNoNetworkLiveData().observe(this, message -> {
            if (message == null) {
                return;
            }
            ToastUtils.showShort(message);
        });
    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {
        getViewModel().getShowOrDismissWaitingLiveData().observe(this, holder -> {
            if (holder == null) {
                return;
            }
            if (holder.isShow()) {
                showWaitingView(true,holder.getMessage());
            } else {
                hideWaitingView();
            }
        });
    }

    @Override
    public void initStateLayoutEvent() {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private static boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            ToastUtils.showShort(getResources().getString(R.string.tipsExit));
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            ActivityUtils.finishAllActivities();
            System.exit(0);
        }
    }
}
