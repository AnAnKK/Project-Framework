package com.runda.projectframework.app.page;

import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseFragmentActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.page.adapter.Adapter_MainPage;
import com.runda.projectframework.app.page.fragment.Fragment_Function;
import com.runda.projectframework.app.page.fragment.Fragment_Home;
import com.runda.projectframework.app.page.fragment.Fragment_Mine;

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
public class Activity_MainPage extends BaseFragmentActivity<BaseViewModel> implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

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
        setupBottomNatigation();
        setupViewPager();
    }

    @Override
    public void start() {
    }

    private void setupBottomNatigation(){
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
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
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        viewPager.setCurrentItem(item.getOrder(),false);
        return true;
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
