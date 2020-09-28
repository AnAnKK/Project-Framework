package com.runda.projectframework.app.page.activity.home.coordinatorLayout;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.page.adapter.AdapterBanner;
import com.runda.projectframework.app.page.adapter.Adapter_QucikAdapter;
import com.runda.projectframework.app.repository.bean.BannerInfo;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/27 8:36
 * @Version:        1.0
 */
public class Activity_AppBarLayout extends BaseActivity<BaseViewModel> {

    private String TAG = "Activity_AppBarLayout";


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_appbarlayout;
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
//        ImmersionBar.with(this).statusBarDarkFont(true).fitsSystemWindows(true).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        setUpRecyclerViewData();
        setupBannerdata();
        setAppBarLayoutListener();
    }

    private void setAppBarLayoutListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    ImmersionBar.with(Activity_AppBarLayout.this).fitsSystemWindows(true).init();
                }else{
                    ImmersionBar.with(Activity_AppBarLayout.this).fitsSystemWindows(false).init();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stop();
    }

    private void setupBannerdata() {
        List<BannerInfo> list = new ArrayList<>();
        BannerInfo bannerInfo = new BannerInfo();
        bannerInfo.setImageUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1601276078&di=20cbdbca535261474687f38b802d92ef&src=http://a0.att.hudong.com/56/12/01300000164151121576126282411.jpg");
        BannerInfo bannerInfo2 = new BannerInfo();
        bannerInfo2.setImageUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3922290090,3177876335&fm=26&gp=0.jpg");
        BannerInfo bannerInfo3 = new BannerInfo();
        bannerInfo3.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601287017318&di=b7ec3b231327285cbe59c7a8e97c3171&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F70%2F91%2F01300000261284122542917592865.jpg");
        list.add(bannerInfo);
        list.add(bannerInfo2);
        list.add(bannerInfo3);
        AdapterBanner adapter = new AdapterBanner(list,this);
        banner.setAdapter(adapter);
        banner.setIndicator(new CircleIndicator(this));
    }

    @Override
    public void onNetReload(View v) {

    }

    private void setUpRecyclerViewData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("第 " + i + " 条");
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        Adapter_QucikAdapter adapter = new Adapter_QucikAdapter(R.layout.item_recyclerview, list);
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        recyclerView.setAdapter(adapter);

    }

//    private void setupBannerdata(){
//        List<BannerInfo> list = new ArrayList<>();
//        BannerInfo bannerInfo = new BannerInfo();
//        bannerInfo.setImageUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1601276078&di=20cbdbca535261474687f38b802d92ef&src=http://a0.att.hudong.com/56/12/01300000164151121576126282411.jpg");
//        BannerInfo bannerInfo2 = new BannerInfo();
//        bannerInfo2.setImageUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3922290090,3177876335&fm=26&gp=0.jpg");
//        BannerInfo bannerInfo3 = new BannerInfo();
//        bannerInfo3.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601287017318&di=b7ec3b231327285cbe59c7a8e97c3171&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F70%2F91%2F01300000261284122542917592865.jpg");
//        BannerInfo bannerInfo4 = new BannerInfo();
//        bannerInfo4.setImageUrl("http://112.126.62.229:9999/file/rotationPicture/6ad90294-9271-4183-8699-93e10284f255_file.jpeg");
//        list.add(bannerInfo);
//        list.add(bannerInfo2);
//        list.add(bannerInfo3);
//        list.add(bannerInfo4);
//        View bannerLayout = View.inflate(Activity_AppBarLayout.this,R.layout.layout_banner,null);
//        Banner banner = bannerLayout.findViewById(R.id.banner);
//        AdapterBanner adapter = new AdapterBanner(list,this);
//
//        banner.setAdapter(adapter)
//                .addBannerLifecycleObserver(this)//添加生命周期观察者
//                .setIndicator(new CircleIndicator(this))//设置指示器
//                .setOnBannerListener((data, position) -> {
//                    ToastUtils.showShort("点击了 =="+position);
//                });
//    }


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
