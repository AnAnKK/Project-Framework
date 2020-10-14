package com.runda.projectframework.app.page.activity.home;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.widget.xui.marquee.MarqueeFactory;
import com.runda.projectframework.app.widget.xui.marquee.MarqueeView;
import com.runda.projectframework.app.widget.xui.marquee.SimpleNoticeMF;
import com.runda.projectframework.app.widget.xui.marquee.SimpleNoticeMF2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_Marquee extends BaseActivity<BaseViewModel> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.marqueeView1)
    MarqueeView marqueeView1;
    @BindView(R.id.marqueeView2)
    MarqueeView marqueeView2;
    @BindView(R.id.marqueeView3)
    MarqueeView marqueeView3;
    @BindView(R.id.marqueeView4)
    MarqueeView marqueeView4;
    @BindView(R.id.marqueeView5)
    MarqueeView marqueeView5;

    @Override
    public int getLayout() {
        return R.layout.activity_marquee;
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
        toolbar.setTitle("滚动");
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {

        final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");

        MarqueeFactory<TextView, String> marqueeFactory1 = new SimpleNoticeMF(getApplicationContext());
        marqueeView1.setMarqueeFactory(marqueeFactory1);
        marqueeView1.startFlipping();
        marqueeFactory1.setOnItemClickListener((view, holder) -> ToastUtils.showShort(holder.getData()));
        marqueeFactory1.setData(datas);

        MarqueeFactory<TextView, String> marqueeFactory2 = new SimpleNoticeMF(getApplicationContext());
        marqueeFactory2.setOnItemClickListener((view, holder) -> ToastUtils.showShort(holder.getData()));
        marqueeFactory2.setData(datas);
        marqueeView2.setMarqueeFactory(marqueeFactory2);
        marqueeView2.setAnimDuration(15000);
        marqueeView2.setInterval(16000);
        marqueeView2.startFlipping();

        MarqueeFactory<TextView, String> marqueeFactory3 = new SimpleNoticeMF(getApplicationContext());
        marqueeFactory3.setOnItemClickListener((view, holder) -> ToastUtils.showShort(holder.getData()));
        marqueeFactory3.setData(datas);
        marqueeView3.setMarqueeFactory(marqueeFactory3);
        marqueeView3.setAnimInAndOut(R.anim.marquee_left_in, R.anim.marquee_right_out);
        marqueeView3.setAnimDuration(8000);
        marqueeView3.setInterval(8500);
        marqueeView3.startFlipping();

        MarqueeFactory<TextView, String> marqueeFactory4 = new SimpleNoticeMF(getApplicationContext());
        marqueeFactory4.setOnItemClickListener((view, holder) -> ToastUtils.showShort(holder.getData()));
        marqueeFactory4.setData(datas);
        marqueeView4.setAnimInAndOut(R.anim.marquee_top_in, R.anim.marquee_bottom_out);
        marqueeView4.setMarqueeFactory(marqueeFactory4);
        marqueeView4.startFlipping();

        MarqueeFactory<RelativeLayout, String> marqueeFactory5 = new SimpleNoticeMF2(getApplicationContext());
        marqueeFactory5.setOnItemClickListener((view, holder) -> ToastUtils.showShort(holder.getData()));
        marqueeFactory5.setData(datas);
        marqueeView5.setAnimInAndOut(R.anim.marquee_top_in, R.anim.marquee_bottom_out);
        marqueeView5.setMarqueeFactory(marqueeFactory5);
        marqueeView5.startFlipping();

//        List<ComplexItemEntity> complexDatas = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            complexDatas.add(new ComplexItemEntity("标题 " + i, "副标题 " + i, "时间 " + i));
//        }
//        MarqueeFactory<RelativeLayout, ComplexItemEntity> marqueeFactory5 = new ComplexViewMF(getContext());
//        marqueeFactory5.setOnItemClickListener((view, holder) -> ToastUtils.showShort(holder.getData().toString()));
//        marqueeFactory5.setData(complexDatas);
//        marqueeView5.setAnimInAndOut(R.anim.marquee_top_in, R.anim.marquee_bottom_out);
//        marqueeView5.setMarqueeFactory(marqueeFactory5);
//        marqueeView5.startFlipping();


//        RxManager.delay(5, (o) -> mTvMarquee.addDisplayString("这是动态添加的消息"));
//
//        RxManager.delay(10, (o) -> mTvMarquee.clear());
//
//        RxManager.delay(15, (o) -> mTvMarquee.addDisplayString("这是动态添加的消息"));
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
