package com.runda.projectframework.app.page.activity.home.coordinatorLayout;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:
 * @Author:         An_K
 * 1.  : layout_scrollFlags(AppBarLayout的属性;要在AppBarLayout子View中添加该属性)
 *          scroll|enterAlways  向下滑动的时候隐藏,向上滑动的时候显示
 *          scroll|enterAlwaysCollapsed 知道滑动到顶部才显示
 *          scroll|exitUntilCollapsed 一直显示
 *          scroll|snap 和enterAlwaysCollapsed效果一样...
 * 2.   :layout_collapseMode(CollapsingToolbarLayout的属性;要在CollapsingToolbarLayout子View中添加该属性)
 *          parallax    滑动的时候隐藏
 *          pin 滑动的时候一直显示
 * 3.   AppBarLayout关于滑动的一些bug加上app:layout_behavior=".AppBarLayoutBehavior"  这个即可
 * 4.   CollapsingToolbarLayout内部ImageView如果图片过大,会很卡,这个暂时不知道咋解决
 * @CreateDate:     2020/9/27 8:36
 * @Version:        1.0
 */
public class Activity_CoordinatorLayoutBasic extends BaseActivity<BaseViewModel> {

    private String TAG = "Activity_CoordinatorLayoutBasic";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.imageBack)
    ImageView imageBack;

    @BindView(R.id.imageLike)
    ImageView imageLike;

    @BindView(R.id.imageShare)
    ImageView imageShare;

    @BindView(R.id.imageCollect)
    ImageView imageCollect;

    @Override
    public int getLayout() {
        return R.layout.activity_coordinatorlayoutbasic;
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
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {

        Disposable backClick = RxView.clicks(imageBack)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    finish();
                });
        getViewModel().getRxEventManager().addRxEvent(backClick);

        setAppBarListener();
    }

    private void setAppBarListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(verticalOffset == 0){
                    imageBack.setImageResource(R.drawable.icon_toolbar_back_2);
                    imageLike.setImageResource(R.drawable.icon_header_like_2);
                    imageShare.setImageResource(R.drawable.icon_header_share_2);
                    imageCollect.setImageResource(R.drawable.icon_header_collect_2);
                }else {
                    imageBack.setImageResource(R.drawable.icon_toolbar_back);
                    imageLike.setImageResource(R.drawable.icon_header_like);
                    imageShare.setImageResource(R.drawable.icon_header_share);
                    imageCollect.setImageResource(R.drawable.icon_header_collect);
                }

//                alpha会导致图标也看不到,所以这时候不能用setAlpha
//                if (verticalOffset == 0) {
//                    toolbar.setAlpha(0);
//                } else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
//                    toolbar.setAlpha(1);
//                } else {
//                    float percentage = (float) verticalOffset / appBarLayout.getTotalScrollRange();
//                    toolbar.setAlpha(Math.abs(percentage));
//                }

                if (verticalOffset == 0) {
                    toolbar.setBackgroundColor(Color.argb(0, 0, 200, 255));
                } else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    toolbar.setBackgroundColor(Color.argb(255, 0, 200, 255));
                } else {
                    float percentage = (float) verticalOffset / appBarLayout.getTotalScrollRange();
                    int alpha = Math.round((1 - percentage) * 255);
                    if (alpha != 255) {
                        toolbar.setBackgroundColor(Color.argb(alpha, 0, 200, 255));
                    }
                }
            }
        });
    }

    @Override
    public void onNetReload(View v) {

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
