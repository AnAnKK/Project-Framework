package com.runda.projectframework.app.page.activity.login;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.Activity_MainPage;
import com.runda.projectframework.app.page.adapter.GuidePagerAdapter;
import com.runda.projectframework.app.widget.indicator.ScaleCircleNavigator;
import com.runda.projectframework.utils.TokenUtils;
import com.tencent.mmkv.MMKV;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Kongdq
 * @date 2019/11/1
 * Description:
 */
public class Activity_Guide extends BaseActivity<BaseViewModel> implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPage)
    ViewPager mViewPager;

    @BindView(R.id.btn_guide_complete)
    Button buttonJump;

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;

    private GuidePagerAdapter mPagerAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.toolbar_transparent).init();
    }

    @Override
    public void initEvents() {
        Disposable toolBarClick = RxView.clicks(buttonJump)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if(TokenUtils.hasToken()){
                        ActivityUtils.startActivity(Activity_MainPage.class);
                        finish();
                    }else {
                        ActivityUtils.startActivity(Activity_Login.class);
                        finish();
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(toolBarClick);
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
        initViewPage();
        initIndicator();
    }


    private void initViewPage() {
        mPagerAdapter = new GuidePagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    private void initIndicator() {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(3);
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
//                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mViewPager.getCurrentItem() == mPagerAdapter.getCount() - 1 && positionOffsetPixels > 0) {
            buttonJump.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            buttonJump.setVisibility(mViewPager.getCurrentItem() == mPagerAdapter.getCount() - 1 ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
