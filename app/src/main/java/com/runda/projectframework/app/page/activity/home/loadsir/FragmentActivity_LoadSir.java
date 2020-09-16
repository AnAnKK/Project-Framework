package com.runda.projectframework.app.page.activity.home.loadsir;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

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
import com.runda.projectframework.app.widget.ColorFlipPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/9 11:51
 * @Version:        1.0
 */
public class FragmentActivity_LoadSir extends BaseFragmentActivity<BaseViewModel> {

    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;

    @BindView(R.id.viewPage)
    ViewPager viewPage;

    @Override
    public int getLayout() {
        return R.layout.layout_fragmentactivity;
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
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void start() {
        bindMagicIndicatorFragment();
//        initMagicIndicator7();
    }

    //卡顿
    private void bindMagicIndicatorFragment() {
        List<String> titleList = new ArrayList<>();
        titleList.add("语文");
        titleList.add("数学");
        titleList.add("英语");
        titleList.add("语文阅读");
        titleList.add("保健操");
        titleList.add("趣味配音");
        titleList.add("英语绘本");
        titleList.add("课外课程阅读");
        titleList.add("课堂知识回顾");
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < titleList.size(); i++) {
            fragments.add(FragmentLazy_SirLoad.newInstance(i));
        }


        Adapter_FragmentsBind adapter_fragmentsBind = new Adapter_FragmentsBind(getSupportFragmentManager(), fragments, titleList);
        //viewpage和fragment绑定
        viewPage.setAdapter(adapter_fragmentsBind);
        viewPage.setCurrentItem(0);
        viewPage.setOffscreenPageLimit(titleList.size()+1);

        //viewpage和indicator绑定
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        AdapterMagicIndicator adapterMagicIndicator = new AdapterMagicIndicator(titleList, FragmentActivity_LoadSir.this);
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

    //流畅
    private void initMagicIndicator7() {
        ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);
        viewPage.setAdapter(mExamplePagerAdapter);
        magicIndicator.setBackgroundColor(Color.parseColor("#fafafa"));
        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#00c853"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPage.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#00c853"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magicIndicator, viewPage);
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
