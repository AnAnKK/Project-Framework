package com.runda.projectframework.app.page.activity.home.coordinatorLayout;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseFragmentActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.interfaces.MagicItemClick;
import com.runda.projectframework.app.page.adapter.AdapterMagicIndicator;
import com.runda.projectframework.app.page.adapter.Adapter_FragmentsBind;
import com.runda.projectframework.app.page.fragment.home.FragmentLazy_CorrdinatorLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/27 8:36
 * @Version:        1.0
 */
public class Activity_CoordinatorLayoutBasic3 extends BaseFragmentActivity<BaseViewModel> {

    private String TAG = "Activity_CoordinatorLayoutBasic2";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewPage)
    ViewPager viewPage;

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;

    @Override
    public int getLayout() {
        return R.layout.activity_coordinatorlayoutbasic3;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.toolbar).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setTitle("吉安娜");
        toolbar.setNavigationOnClickListener(view -> Activity_CoordinatorLayoutBasic3.this.finish());
        setSupportActionBar(toolbar);
        bindMagicIndicatorFragment();
    }

    private void bindMagicIndicatorFragment() {
        List<String> titleList = new ArrayList<>();
        titleList.add("语文");
        titleList.add("数学");
        titleList.add("英语");
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < titleList.size(); i++) {
            fragments.add(FragmentLazy_CorrdinatorLayout.newInstance(i));
        }


        Adapter_FragmentsBind adapter_fragmentsBind = new Adapter_FragmentsBind(getSupportFragmentManager(), fragments, titleList);
        //viewpage和fragment绑定
        viewPage.setAdapter(adapter_fragmentsBind);
        viewPage.setCurrentItem(0);
        viewPage.setOffscreenPageLimit(titleList.size()+1);

        //viewpage和indicator绑定
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        AdapterMagicIndicator adapterMagicIndicator = new AdapterMagicIndicator(titleList, Activity_CoordinatorLayoutBasic3.this);
        adapterMagicIndicator.setOnMagicItemClick(new MagicItemClick<String>() {
            @Override
            public void onClick(int index, String data) {
                viewPage.setCurrentItem(index);
            }
        });
        commonNavigator.setAdapter(adapterMagicIndicator);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPage);

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
