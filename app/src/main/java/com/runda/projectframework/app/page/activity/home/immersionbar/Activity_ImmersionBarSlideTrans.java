package com.runda.projectframework.app.page.activity.home.immersionbar;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.BarProperties;
import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WebView;
import com.runda.projectframework.app.page.adapter.AdapterBanner;
import com.runda.projectframework.app.page.adapter.Adapter_FuncItem;
import com.runda.projectframework.app.repository.bean.BannerInfo;
import com.runda.projectframework.app.repository.bean.PageTextClzInfo;
import com.runda.projectframework.utils.ViewUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;
import static com.runda.projectframework.app.page.fragment.Fragment_Home.SmartRefreshLayout;

/**
 * Created by Kongdq
 * @date 2019/11/1
 * Description: adapter添加header如果header是banner,banner会抢焦点,github上的问题,期望后面会解决吧
 */
public class Activity_ImmersionBarSlideTrans extends BaseActivity<BaseViewModel> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int mBannerHeight;
    private int totalDy = 0;
    private Banner banner;
    private Adapter_FuncItem adapterFuncItem;

    @Override
    public int getLayout() {
        return R.layout.activity_immersionbarslidetrans;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.toolbar).autoStatusBarDarkModeEnable(true,0.2f).init();
    }


    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        mBannerHeight = ConvertUtils.dp2px(180) - ImmersionBar.getStatusBarHeight(this);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void start() {
        setRecycleviewListener();
        setRecyclerviewData();
        setupImageHeader();
        setupBannerdata();
    }

    private List<BannerInfo> getBannerListData(){
        List<BannerInfo> list = new ArrayList<>();
        BannerInfo bannerInfo1 = new BannerInfo();
        bannerInfo1.setImageUrl("http://112.126.62.229:9999/file/rotationPicture/2ec99bf6-b5ea-4840-8e9e-f3d9e0cbe0df_%E6%95%99%E5%B8%88%E6%8E%88%E8%AF%BE%E5%AE%9A%E4%BB%B7@%E5%87%A1%E7%A7%91%E5%BF%AB%E5%9B%BE.png");
        BannerInfo bannerInfo2 = new BannerInfo();
        bannerInfo2.setImageUrl("http://112.126.62.229:9999/file/rotationPicture/07b58227-3645-4533-acd1-7ecb9cd33379_%E5%B9%B3%E5%8F%B0%E4%BC%98%E5%8A%BF@%E5%87%A1%E7%A7%91%E5%BF%AB%E5%9B%BE.png");
        BannerInfo bannerInfo3 = new BannerInfo();
        bannerInfo3.setImageUrl("http://112.126.62.229:9999/file/rotationPicture/012cd86e-8958-48f6-bd5d-89c1a357babe_file.jpeg");
        BannerInfo bannerInfo4 = new BannerInfo();
        bannerInfo4.setImageUrl("http://112.126.62.229:9999/file/rotationPicture/6ad90294-9271-4183-8699-93e10284f255_file.jpeg");
        list.add(bannerInfo1);
        list.add(bannerInfo2);
        list.add(bannerInfo3);
        list.add(bannerInfo4);
        return list;
    }

    private void setupBannerdata(){
        View bannerLayout = getLayoutInflater().inflate(R.layout.layout_banner, recyclerView, false);
        banner = bannerLayout.findViewById(R.id.banner);
        AdapterBanner adapter = new AdapterBanner(getBannerListData(),this);
        ViewUtils.increaseViewHeightByStatusBarHeight(this, banner);
        ImmersionBar.setTitleBarMarginTop(this, banner);

        banner.setAdapter(adapter)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this))//设置指示器
                .setOnBannerListener((data, position) -> {
                    ToastUtils.showShort("点击了 =="+position);
                });
        adapterFuncItem.addHeaderView(bannerLayout);
    }

    private void setupImageHeader(){
        View bannerLayout = getLayoutInflater().inflate(R.layout.layout_image, recyclerView, false);
        ImageView imageView = bannerLayout.findViewById(R.id.imageView);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.imagetest));
        ViewUtils.increaseViewHeightByStatusBarHeight(this, imageView);
        ImmersionBar.setTitleBarMarginTop(this, imageView);

        adapterFuncItem.addHeaderView(bannerLayout);
    }

    private void setRecyclerviewData() {

        List<PageTextClzInfo> flist = new ArrayList<>();


        for (int i = 0; i < 50; i++) {
            flist.add(new PageTextClzInfo(SmartRefreshLayout, Activity_WebView.class.getName()));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        adapterFuncItem = new Adapter_FuncItem(R.layout.item_text,flist);
        Disposable event_itemClicked = adapterFuncItem.getRxOnItemClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    Intent intent = new Intent();
                    intent.setClassName(this,event.data().getClazzName());
                    startActivity(intent);
                });
        getViewModel().getRxEventManager().addRxEvent(event_itemClicked);
        recyclerView.setAdapter(adapterFuncItem);
    }

    private void setRecycleviewListener() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalDy += dy;
                if (totalDy < 0) {
                    totalDy = 0;
                }
                if (totalDy < mBannerHeight) {
                    float alpha = (float) totalDy / mBannerHeight;
//                    ImmersionBar.with(Activity_ImmersionBarSlideTrans.this).statusBarDarkFont(true, 0.2f).init();
                    toolbar.setAlpha(alpha);
                    //必须加else 否则alpha不会成为1导致颜色透明度偏差
                    Log.e("AAA","dy === "+dy+"totalDy === "+totalDy+"  mBannerHeight == "+mBannerHeight+"   alpha  == "+alpha);
                }else {
                    toolbar.setAlpha(1);
                }
            }
        });


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
    public void initStateLayoutEvent() {

    }
}
